package com.rocsea.kettlepdi.factory.etl;

import com.rocsea.etlcommon.model.bo.excel.EtlExcelSheetBO;
import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.etlcommon.utils.BeanCopyUtils;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import static com.rocsea.etlcommon.model.constant.EtlConstant.*;

/**
 * 年月一对一自动构建
 *
 * @Author RocSea
 * @Date 2023/6/8
 */
@Component
public class KettleEtlYearMonthStrategy extends KettleEtlCommon {

    @Override
    public void build(List<EtlExcelSheetBO> etlExcelSheetBOS) {
        for (EtlExcelSheetBO etlExcelSheetBO : etlExcelSheetBOS) {
            EtlExcelSheetBO currentMonthTable = getCurrentMonthTable(etlExcelSheetBO);
            super.buildKettleByTemplate(Collections.singletonList(currentMonthTable), KettleEtlTemplateEnum.PAGING_UPDATE);
        }
    }

    private EtlExcelSheetBO getCurrentMonthTable(EtlExcelSheetBO etlExcelSheetBO) {
        EtlExcelSheetBO etlExcelSheetBOCopy = BeanCopyUtils.copyProperties(etlExcelSheetBO, EtlExcelSheetBO.class);
        final String sourceTableName = etlExcelSheetBOCopy.getSourceTableName();
        final String targetTableName = etlExcelSheetBOCopy.getTargetTableName();
        final String currentYearMonth = getCurrentMonthTable();
        etlExcelSheetBOCopy.setSourceTableName(sourceTableName.replace(YEAR_MONTH, currentYearMonth));
        etlExcelSheetBOCopy.setTargetTableName(targetTableName.replace(YEAR_MONTH, currentYearMonth));
        return etlExcelSheetBOCopy;
    }

    private String getCurrentMonthTable() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取当前年月
        return currentDate.format(DateTimeFormatter.ofPattern("_yyyy_MM"));
    }

}
