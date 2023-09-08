package com.rocsea.kettlepdi.factory.etl;

import com.rocsea.etlcommon.exception.BusinessException;
import com.rocsea.etlcommon.model.bo.excel.EtlExcelColumnBO;
import com.rocsea.etlcommon.model.bo.excel.EtlExcelSheetBO;
import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.etlcommon.utils.ConvertUtils;
import com.rocsea.kettlepdi.model.request.KettleEtlBuildRequest;
import com.rocsea.kettlepdi.service.kettle.KettleEtlBuildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Etl公共构建
 *
 * @Author RocSea
 * @Date 2023/6/9
 */
@Component
public class KettleEtlCommon implements KettleEtlStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(KettleEtlCommon.class);
    @Resource
    private KettleEtlBuildService kettleEtlBuildService;
    private static final String NO = "否";
    /**
     * 空方法 由子类实现
     *
     * @param etlExcelSheetBOS 表格
     */
    @Override
    public void build(List<EtlExcelSheetBO> etlExcelSheetBOS) {
        // Do nothing because of to be implemented by concrete subclasses
    }

    /**
     * 根据模板构建
     *
     * @param etlExcelSheetBOS 表格
     * @param templateEnum   模板枚举
     */
    public void buildKettleByTemplate(List<EtlExcelSheetBO> etlExcelSheetBOS, KettleEtlTemplateEnum templateEnum) {
        for (EtlExcelSheetBO etlExcelSheetBO : etlExcelSheetBOS) {
            KettleEtlBuildRequest request = createKettleEtlBuildRequest(etlExcelSheetBO, templateEnum);
            try {
                kettleEtlBuildService.buildKettleEtl(request);
            } catch (Exception ex) {
                LOGGER.error("kettle构建异常", ex);
                throw new BusinessException("kettle构建异常");
            }
        }
    }

    /**
     * 创建kettle构建请求
     *
     * @param etlExcelSheetBO 表格
     * @param templateEnum  模板枚举
     * @return KettleBuildRequest
     */
    public KettleEtlBuildRequest createKettleEtlBuildRequest(EtlExcelSheetBO etlExcelSheetBO, KettleEtlTemplateEnum templateEnum) {
        final String tableName = etlExcelSheetBO.getSourceTableName();
        KettleEtlBuildRequest kettleEtlBuildRequest = new KettleEtlBuildRequest();
        kettleEtlBuildRequest.setModuleType(ConvertUtils.getBuildModuleValue(etlExcelSheetBO.getServiceModule()));
        kettleEtlBuildRequest.setSourceSchemaName(etlExcelSheetBO.getSourceSchemaName());
        kettleEtlBuildRequest.setSourceTableName(tableName);
        kettleEtlBuildRequest.setTargetTableName(etlExcelSheetBO.getTargetTableName());
        kettleEtlBuildRequest.setEtlTemplateType(templateEnum.getValue());
        List<EtlExcelColumnBO> allColumns = etlExcelSheetBO.getEtlExcelColumnList();
        Map<String, String> etlExcelFormatColumnMap = etlExcelSheetBO.getEtlExcelFormatColumnMap();
        Map<String, String> aliasMapping = computeAliasMapping(allColumns);
        String sourceExcludeColumns = computeSourceExcludeColumns(allColumns, etlExcelFormatColumnMap);
        kettleEtlBuildRequest.setSourceExcludeColumns(sourceExcludeColumns);
        kettleEtlBuildRequest.setColumnsFormat(etlExcelFormatColumnMap);
        kettleEtlBuildRequest.setAliasMapping(aliasMapping);
        return kettleEtlBuildRequest;
    }

    private List<EtlExcelColumnBO> filterColumnsByReserve(List<EtlExcelColumnBO> allColumns, String reserveValue) {
        return allColumns.stream()
                .filter(x -> reserveValue.equals(x.getHasReserve()))
                .collect(Collectors.toList());
    }

    private Map<String, String> computeAliasMapping(List<EtlExcelColumnBO> allColumns) {
        return allColumns.stream()
                .filter(x -> !x.getSourceColumnEnName().equals(x.getSuggestedEnName()))
                .collect(Collectors.toMap(EtlExcelColumnBO::getSourceColumnEnName, EtlExcelColumnBO::getSuggestedEnName));
    }

    private String computeSourceExcludeColumns(List<EtlExcelColumnBO> allColumns, Map<String, String> formatColumnMap) {
        return Stream.concat(
                        filterColumnsByReserve(allColumns, NO).stream().map(EtlExcelColumnBO::getSourceColumnEnName),
                        formatColumnMap.keySet().stream()
                )
                .distinct()
                .collect(Collectors.joining(","));
    }

}
