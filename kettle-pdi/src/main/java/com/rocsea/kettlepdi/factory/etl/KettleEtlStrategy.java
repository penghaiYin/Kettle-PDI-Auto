package com.rocsea.kettlepdi.factory.etl;

import com.rocsea.etlcommon.model.bo.excel.EtlExcelSheetBO;
import java.util.List;

/**
 * @Author RocSea
 * @Date 2023/6/8
 */
public interface KettleEtlStrategy {
    void build(List<EtlExcelSheetBO> etlExcelSheetBOS);
}
