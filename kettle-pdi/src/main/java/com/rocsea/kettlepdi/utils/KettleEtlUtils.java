package com.rocsea.kettlepdi.utils;

import com.rocsea.etlcommon.exception.BusinessException;
import com.rocsea.etlcommon.model.bo.kettlepdi.ColumnInfoBO;
import com.rocsea.etlcommon.model.bo.kettlepdi.IdMaxJobRelatedBO;
import com.rocsea.etlcommon.model.bo.kettlepdi.IdMaxTransRelatedBO;
import com.rocsea.etlcommon.model.bo.kettlepdi.KettleEtlResourceHolder;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaBase;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * @Author RocSea
 * @Date 2022/12/2
 */
public final class KettleEtlUtils {
    private static final String ORIGIN_TABLE_INPUT = "table_input";
    private static final String SHANGHAI_TIME_ZONE = "Asia/Shanghai";
    private final List<ColumnInfoBO> columns;
    private final static ThreadLocal<KettleEtlResourceHolder> buildResourceHolder = ThreadLocal.withInitial(KettleEtlResourceHolder::new);

    private KettleEtlUtils(List<ColumnInfoBO> collection) {
        this.columns = new ArrayList<>(collection);
    }

    public static KettleEtlUtils of(List<ColumnInfoBO> collection) {
        return new KettleEtlUtils(collection);
    }

    public String getRowMetaXml() throws IOException {
        RowMetaInterface row = new RowMeta();
        List<ValueMetaInterface> valueMetaList = mapToValueMetaList(this::transToValueMeta);
        row.setValueMetaList(valueMetaList);
        return row.getMetaXML();
    }

    public <R> List<R> mapToValueMetaList(Function<? super ColumnInfoBO, ? extends R> mapper) {
        List<R> valueMetaList = new ArrayList<>();
        for (ColumnInfoBO t : columns) {
            valueMetaList.add(mapper.apply(t));
        }
        return valueMetaList;
    }

    private ValueMetaInterface transToValueMeta(ColumnInfoBO columnInfoBO) {
        ValueMetaInterface valueMetaInterface = null;
        String columnType = columnInfoBO.getColumnType();
        String columnName = columnInfoBO.getColumnName();
        int length;
        switch (columnInfoBO.getDataType().toUpperCase()) {
            case "VARCHAR":
            case "CHAR":
                length = Integer.parseInt(columnType.substring(columnType.indexOf("(") + 1, columnType.indexOf(")")));
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_STRING, length, -1);
                break;
            case "BIGINT":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_BIGNUMBER, 16, 0);
                break;
            case "TEXT":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_STRING, 16383, -1);
                break;
            case "DATETIME":
            case "TIMESTAMP":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_TIMESTAMP, 0, -1);
                break;
            case "TINYINT":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_INTEGER, 2, 0);
                break;
            case "DATE":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_DATE, -1, -1);
                break;
            case "DECIMAL":
                length = Integer.parseInt(columnType.substring(columnType.indexOf("(") + 1, columnType.indexOf(",")));
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_NUMBER, length, 4);
                break;
            case "SMALLINT":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_INTEGER, 4, 0);
                break;
            case "INT":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_INTEGER, 9, 0);
                break;
            case "MEDIUMTEXT":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_STRING, 4194303, -1);
                break;
            case "LONGTEXT":
                valueMetaInterface = new ValueMetaBase(columnName, ValueMetaInterface.TYPE_STRING, 715827882, -1);
                break;
            default:
                break;
        }
        if (valueMetaInterface == null) {
            throw new BusinessException("field type is not supported");
        }
        valueMetaInterface.setComments(columnInfoBO.getColumnName());
        valueMetaInterface.setOrigin(ORIGIN_TABLE_INPUT);
        valueMetaInterface.setDateFormatLocale(Locale.SIMPLIFIED_CHINESE);
        valueMetaInterface.setDateFormatTimeZone(TimeZone.getTimeZone(SHANGHAI_TIME_ZONE));
        return valueMetaInterface;
    }

    public static List<ColumnInfoBO> getSourceColumnList() {
        return buildResourceHolder.get().getSourceColumnList();
    }

    public static Map<String, String> getAliasMapping() {
        return buildResourceHolder.get().getAliasMapping();
    }

    public static String getSelectColumn() {
        return buildResourceHolder.get().getSelectColumn();
    }

    public static Long getIdConnection() {
        return buildResourceHolder.get().getIdConnection();
    }

    public static Long getIdDirectory() {
        return buildResourceHolder.get().getIdDirectory();
    }

    public static int getModuleType() {
        return buildResourceHolder.get().getModuleType();
    }

    public static String getSourceSchemaName() {
        return buildResourceHolder.get().getSourceSchemaName();
    }

    public static String getSourceTableName() {
        return buildResourceHolder.get().getSourceTableName();
    }

    public static String getTargetTableName() {
        return buildResourceHolder.get().getTargetTableName();
    }

    public static IdMaxTransRelatedBO getIdMaxTransRelated() {
        return buildResourceHolder.get().getIdMaxTransRelated();
    }

    public static IdMaxJobRelatedBO getIdMaxJobRelated() {
        return buildResourceHolder.get().getIdMaxJobRelated();
    }

    public static Map<String, String> getColumnsFormat() {
        return buildResourceHolder.get().getColumnsFormat();
    }

    public static void setSourceColumnList(List<ColumnInfoBO> sourceColumnList) {
        buildResourceHolder.get().setSourceColumnList(sourceColumnList);
    }

    public static void setAliasMapping(Map<String, String> aliasMapping) {
        buildResourceHolder.get().setAliasMapping(aliasMapping);
    }

    public static void setSelectColumn(String selectColumn) {
        buildResourceHolder.get().setSelectColumn(selectColumn);
    }

    public static void setIdConnection(Long idConnection) {
        buildResourceHolder.get().setIdConnection(idConnection);
    }

    public static void setIdDirectory(Long idDirectory) {
        buildResourceHolder.get().setIdDirectory(idDirectory);
    }

    public static void setModuleType(Integer moduleType) {
        buildResourceHolder.get().setModuleType(moduleType);
    }

    public static void setSourceSchemaName(String sourceSchemaName) {
        buildResourceHolder.get().setSourceSchemaName(sourceSchemaName);
    }

    public static void setSourceTableName(String sourceTableName) {
        buildResourceHolder.get().setSourceTableName(sourceTableName);
    }

    public static void setTargetTableName(String targetTableName) {
        buildResourceHolder.get().setTargetTableName(targetTableName);
    }

    public static void setIdMaxTransRelated(IdMaxTransRelatedBO idMaxTransRelatedBO) {
        buildResourceHolder.get().setIdMaxTransRelated(idMaxTransRelatedBO);
    }

    public static void setIdMaxJobRelated(IdMaxJobRelatedBO idMaxJobRelatedBO) {
        buildResourceHolder.get().setIdMaxJobRelated(idMaxJobRelatedBO);
    }

    public static void setColumnsFormat(Map<String, String> columnsFormat) {
        buildResourceHolder.get().setColumnsFormat(columnsFormat);
    }

    public static void setBuildType(Integer kpStrategyType) {
        buildResourceHolder.get().setBuildType(kpStrategyType);
    }

    public static Integer getBuildType() {
        return buildResourceHolder.get().getBuildType();
    }
}
