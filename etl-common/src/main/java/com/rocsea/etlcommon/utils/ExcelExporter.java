package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.model.entity.etlsync.EtlSyncCheckConfigDO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelExporter {

    public static void exportToExcel(Map<Integer, List<EtlSyncCheckConfigDO>> tables, String outputFileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            //创建一个Sheet页
            for (Integer serviceModule : tables.keySet()) {
                final String moduleName = com.rocsea.etlcommon.utils.ConvertUtils.getModuleNameByServiceModule(serviceModule);
                Sheet sheet = workbook.createSheet(moduleName);
                List<EtlSyncCheckConfigDO> sortedList =
                        tables.get(serviceModule).stream().sorted(Comparator.comparing(EtlSyncCheckConfigDO::getSourceSchemaName))
                                .collect(Collectors.toList());
                populateSheetWithData(sheet, sortedList);
            }

            try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
                workbook.write(fos);
            }
        }
    }

    private static void populateSheetWithData(Sheet sheet, List<EtlSyncCheckConfigDO> dataRows) {
        int rowIndex = 0;
        for (EtlSyncCheckConfigDO row : dataRows) {
            Row excelRow = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            excelRow.createCell(cellIndex++).setCellValue(row.getSourceSchemaName());
            excelRow.createCell(cellIndex++).setCellValue(row.getSourceTableName());
        }
    }

}
