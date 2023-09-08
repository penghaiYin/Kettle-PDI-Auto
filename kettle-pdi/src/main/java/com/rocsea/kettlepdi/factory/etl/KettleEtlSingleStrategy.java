package com.rocsea.kettlepdi.factory.etl;

import com.rocsea.etlcommon.model.bo.excel.EtlExcelSheetBO;
import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 单表自动构建
 *
 * @Author RocSea
 * @Date 2023/6/8
 */
@Component
public class KettleEtlSingleStrategy extends KettleEtlCommon {
    @Override
    public void build(List<EtlExcelSheetBO> etlExcelSheetBOS) {
        super.buildKettleByTemplate(etlExcelSheetBOS, KettleEtlTemplateEnum.PAGING_UPDATE);
    }
}
