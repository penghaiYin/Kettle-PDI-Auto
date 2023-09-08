package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.exception.BusinessException;
import com.rocsea.etlcommon.model.bo.excel.EtlExcelColumnBO;
import com.rocsea.etlcommon.model.bo.excel.EtlExcelSheetBO;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.rocsea.etlcommon.model.constant.MysqlScriptConstant.*;

/**
 * mysql建表规范（etl）工具类
 *
 * @Author RocSea
 * @Date 2023/5/15
 */
public final class MysqlTableStandardUtils {
    private static final String SPLIT = "',";


    private MysqlTableStandardUtils() {
    }

    /**
     * 通过Excel规范，解析mysql建表语句
     *
     * @param sourceCreateTableSql mysql建表语句
     * @param etlExcelSheetBO        etlExcel规范
     * @return String
     */
    public static String formatTableDescByExcel(String sourceCreateTableSql, EtlExcelSheetBO etlExcelSheetBO) {
        if (StringUtils.isEmpty(sourceCreateTableSql)) {
            throw new BusinessException("源表建表语句为空");
        }
        Map<String, EtlExcelColumnBO> etlExcelColumnMap = etlExcelSheetBO.getEtlExcelColumnList().stream().collect(Collectors.toMap(EtlExcelColumnBO::getSourceColumnEnName,
                Function.identity(), (x1, x2) -> x2));

        String[] sourceColumnsDesc = sourceCreateTableSql.split(SPLIT);
        final String sourceTableHead = sourceColumnsDesc[0];
        final String replaceSourceTableHead = sourceTableHead.replace(etlExcelSheetBO.getSourceTableName(),
                etlExcelSheetBO.getTargetTableName());
        final String pkColumnName = getPkName(replaceSourceTableHead);
        EtlExcelColumnBO pkColumn = etlExcelColumnMap.get(pkColumnName);
        return processTableHead(replaceSourceTableHead, pkColumn) + processTableTail(etlExcelSheetBO, sourceColumnsDesc, pkColumnName, etlExcelColumnMap);
    }

    private static String processTableTail(EtlExcelSheetBO etlExcelSheetBO, String[] sourceColumnsDesc, String pkColumnName,
                                           Map<String, EtlExcelColumnBO> etlExcelColumnMap) {
        StringBuilder sb = new StringBuilder();
        Map<String, String> usefulFieldMap = new HashMap<>();
        /*
         找到下面循环的结束位置
         */
        List<String> columnList = Arrays.asList(sourceColumnsDesc);
        int endIndex = IntStream.range(0, columnList.size())
                .filter(i -> columnList.get(i).contains(PRIMARY_KEY))
                .findFirst()
                .orElse(-1);

        for (int index = 1; index < endIndex; index++) {
            String columnDesc = sourceColumnsDesc[index];
            int columnStartIndex = columnDesc.indexOf('`');
            int columnEndIndex = columnDesc.lastIndexOf('`');
            String columnName = columnDesc.substring(columnStartIndex + 1, columnEndIndex);
            EtlExcelColumnBO etlExcelColumnBO = etlExcelColumnMap.get(columnName);
            /*
              是否需要跳过
             */
            if (shouldSkip(etlExcelSheetBO, etlExcelColumnBO, usefulFieldMap)) {
                continue;
            }

            final String sourceColumnEnName = etlExcelColumnBO.getSourceColumnEnName();
            final String sourceDataType = etlExcelColumnBO.getSourceDataType();
            final String suggestedEnName = etlExcelColumnBO.getSuggestedEnName();
            final String suggestedChName = etlExcelColumnBO.getSuggestedChName();
            final String suggestedDataType = etlExcelColumnBO.getSuggestedDataType();

            /*
              替换字段英文名称
             */
            final String replaceEnName = StringUtils.replace(columnDesc, sourceColumnEnName, suggestedEnName);
            /*
              替换字段中文描述
             */
            String[] comments = replaceEnName.split(COMMENT);
            final String replaceChName = comments[0] + COMMENT + " '" + suggestedChName;
            String etlColumnDesc = StringUtils.replace(replaceChName, sourceDataType, suggestedDataType);

            /*
              字段不是DEFAULT NULL，也不是 NOT NULL
             */
            if (!etlColumnDesc.contains(NOT_NULL) && !etlColumnDesc.contains(DEFAULT_NULL)) {
                etlColumnDesc =
                        LINE_FEED + "`" + suggestedEnName + "` " + suggestedDataType + " DEFAULT NULL" + " COMMENT '" + suggestedChName;
            }
            sb.append(etlColumnDesc).append(SPLIT);
        }

        /*
          拼接Deleted字段
         */
        final String deletedDescFormat = LINE_FEED + "  `deleted` tinyint(4) unsigned DEFAULT %s COMMENT '是否删除',";
        String deletedDesc;
        if (etlExcelSheetBO.getHasLogicDel()) {
            deletedDesc = String.format(deletedDescFormat, NULL);
        } else {
            deletedDesc = String.format(deletedDescFormat, "'0'");
        }
        sb.append(deletedDesc);

        /*
          其它固定字段
         */
        final String sourceTableDesc = etlExcelSheetBO.getSourceTableDesc();
        sb.append(LINE_FEED).append("  `update_time_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间id',");
        sb.append(LINE_FEED).append("  `create_time` datetime DEFAULT NULL COMMENT '创建时间',");
        sb.append(LINE_FEED).append("  `update_time` datetime DEFAULT NULL COMMENT '更新时间',");
        sb.append(LINE_FEED).append("  PRIMARY KEY (`").append(pkColumnName).append("`) USING BTREE,");
        sb.append(LINE_FEED).append("  KEY `idx_update_time_id` (`update_time_id`),");
        sb.append(LINE_FEED).append("  KEY `idx_update_time` (`update_time`)");
        sb.append(LINE_FEED).append(") ENGINE=InnoDB COMMENT='").append(sourceTableDesc).append("';");
        // 解析得到的有用字段
        etlExcelSheetBO.setUsefulFieldMap(usefulFieldMap);
        return sb.toString();
    }

    private static boolean shouldSkip(EtlExcelSheetBO etlExcelSheetBO, EtlExcelColumnBO etlExcelColumnBO, Map<String, String> usefulFieldMap) {
        if (Objects.isNull(etlExcelColumnBO)) {
            return true;
        }
        boolean isReserve = com.rocsea.etlcommon.utils.ExcelSemanticsUtils.of(etlExcelColumnBO.getHasReserve()).isReserve();
        if (isReserve) {
            String sourceColumnEnName = etlExcelColumnBO.getSourceColumnEnName();
            String suggestedEnName = etlExcelColumnBO.getSuggestedEnName();
            boolean isRequiredParse = com.rocsea.etlcommon.utils.ExcelSemanticsUtils.of(suggestedEnName).isRequiredParse();
            if (isRequiredParse) {
                return Boolean.FALSE;
            }
            if (UPDATE_TIME.equals(suggestedEnName)) {
                usefulFieldMap.put(UPDATE_TIME, sourceColumnEnName);
            }
            if (DELETED.equals(suggestedEnName)) {
                etlExcelSheetBO.setHasLogicDel(Boolean.TRUE);
                usefulFieldMap.put(DELETED, sourceColumnEnName);
            }

        }
        return Boolean.TRUE;
    }


    private static String processTableHead(String tableHead, EtlExcelColumnBO pkColumn) {
        StringBuilder sb = new StringBuilder();
        tableHead = tableHead.replace("TABLE ", "TABLE `bond_ext_etl`.");
        tableHead = tableHead.replace("AUTO_INCREMENT ", "");
        tableHead = tableHead.replace(pkColumn.getSourceColumnEnName(), pkColumn.getSuggestedEnName());
        tableHead = tableHead.replace(pkColumn.getSourceDataType(), pkColumn.getSuggestedDataType());
        tableHead = tableHead.replace(pkColumn.getSourceColumnChName(), pkColumn.getSuggestedChName());
        sb.append(tableHead).append("',");
        return sb.toString();
    }

    private static String getPkName(String tableHead) {
        final String pkColumnDesc = tableHead.split("\n")[1];
        int headSegmentStartIndex = pkColumnDesc.indexOf('`');
        int headSegmentEndIndex = pkColumnDesc.lastIndexOf('`');
        return pkColumnDesc.substring(headSegmentStartIndex + 1, headSegmentEndIndex);
    }

}
