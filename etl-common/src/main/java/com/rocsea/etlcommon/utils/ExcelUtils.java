package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.model.bo.excel.EtlExcelColumnBO;
import com.rocsea.etlcommon.model.bo.excel.EtlExcelSheetBO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class ExcelUtils {
    private static final String SOURCE_COLUMN_EN_NAME = "原字段英文名";
    private static final String SHARDING_DESC = "分表";

    private ExcelUtils() {
    }

    public static List<EtlExcelSheetBO> readExcel(InputStream in, String range) throws IOException {
        List<EtlExcelSheetBO> result = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(in)) {
            Map<String, EtlExcelSheetBO> etlExcelSheetMap = processFirstSheet(workbook.getSheetAt(0));
            int startPosition = 1;
            int endPosition = workbook.getNumberOfSheets();
            if (StringUtils.isNotEmpty(range)) {
                int[] positions = Arrays.stream(range.split("-")).mapToInt(Integer::parseInt).toArray();
                startPosition = positions[0];
                endPosition = positions[1] + 1;
            }
            for (int sheetNum = startPosition; sheetNum < endPosition; sheetNum++) {
                EtlExcelSheetBO etlExcelSheetBO = processEtlTableSheet(workbook.getSheetAt(sheetNum), etlExcelSheetMap);
                result.add(etlExcelSheetBO);
            }
        }
        return result;
    }

    private static EtlExcelSheetBO processEtlTableSheet(Sheet sheet, Map<String, EtlExcelSheetBO> etlExcelSheetMap) {
        String targetTableComment = getRowCellValue(sheet.getRow(1), 2);
        if (targetTableComment.contains(SHARDING_DESC)) {
            return null;
        }
        String sourceTableName = getRowCellValue(sheet.getRow(0), 1);
        String targetTableName = getRowCellValue(sheet.getRow(1), 1);
        String hasPhysicalDelete = getRowCellValue(sheet.getRow(2), 1);

        EtlExcelSheetBO etlExcelSheetBO = etlExcelSheetMap.get(sourceTableName);
        etlExcelSheetBO.setTargetTableName(targetTableName);
        etlExcelSheetBO.setSourceTableName(sourceTableName);
        etlExcelSheetBO.setEtlTemplateType(ExcelSemanticsUtils.of(targetTableName).getTableType());
        etlExcelSheetBO.setHasPhysicalDel(ExcelSemanticsUtils.of(hasPhysicalDelete).hasPhysicalDelete());
        List<EtlExcelColumnBO> etlExcelColumnBOList = new ArrayList<>();
        Map<String, String> etlExcelFormatColumnMap = new HashMap<>();
        boolean flag = false;
        for (int rowNum = 5; rowNum <= sheet.getLastRowNum(); rowNum++) {
            String rowCellValue = getRowCellValue(sheet.getRow(rowNum), 0);
            if (!flag && rowCellValue.contains(SOURCE_COLUMN_EN_NAME)) {
                flag = true;
                continue;
            }
            if (flag) {
                Row row = sheet.getRow(rowNum);
                EtlExcelColumnBO etlExcelColumnBO = new EtlExcelColumnBO();
                etlExcelColumnBO.setSourceColumnEnName(getRowCellValue(row, 0));
                etlExcelColumnBO.setSourceDataType(getRowCellValue(row, 1));
                etlExcelColumnBO.setSourceColumnChName(getRowCellValue(row, 2));
                etlExcelColumnBO.setHasReserve(getRowCellValue(row, 3));
                etlExcelColumnBO.setSuggestedEnName(getRowCellValue(row, 4));
                etlExcelColumnBO.setSuggestedDataType(getRowCellValue(row, 5));
                etlExcelColumnBO.setSuggestedChName(getRowCellValue(row, 6));
                final String rowCellValueFormat = getRowCellValue(row, 7);
                if (StringUtils.isNotEmpty(rowCellValueFormat)) {
                    etlExcelFormatColumnMap.put(etlExcelColumnBO.getSourceColumnEnName(), rowCellValueFormat);
                }
                etlExcelColumnBOList.add(etlExcelColumnBO);
            }
        }
        etlExcelSheetBO.setEtlExcelFormatColumnMap(etlExcelFormatColumnMap);
        etlExcelSheetBO.setEtlExcelColumnList(etlExcelColumnBOList);
        return etlExcelSheetBO;
    }

    private static Map<String, EtlExcelSheetBO> processFirstSheet(Sheet firstSheet) {
        Map<String, EtlExcelSheetBO> sheetMap = new HashMap<>();
        for (int rowNum = 1; rowNum <= firstSheet.getLastRowNum(); rowNum++) {
            EtlExcelSheetBO etlExcelSheetBO = new EtlExcelSheetBO();
            Row row = firstSheet.getRow(rowNum);
            etlExcelSheetBO.setServiceModule(getRowCellValue(row, 0));
            etlExcelSheetBO.setSourceSchemaName(getRowCellValue(row, 2));
            etlExcelSheetBO.setSourceTableDesc(getRowCellValue(row, 5));
            sheetMap.put(getRowCellValue(row, 3), etlExcelSheetBO);
        }
        return sheetMap;
    }

    private static String getRowCellValue(Row row, int position) {
        Cell cell = row.getCell(position);
        if (Objects.nonNull(cell)) {
            return cell.getStringCellValue().trim();
        }
        return "";
    }

}
