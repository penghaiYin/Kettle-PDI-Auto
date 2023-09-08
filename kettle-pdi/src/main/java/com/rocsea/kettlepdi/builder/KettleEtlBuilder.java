package com.rocsea.kettlepdi.builder;

import com.rocsea.etlcommon.model.bo.kettlepdi.KettleEtlProductBO;
import com.rocsea.kettlepdi.model.request.KettleEtlBuildRequest;
/**
 * @Author RocSea
 * @Date 2023/1/31
 */
public interface KettleEtlBuilder {
    void init(KettleEtlBuildRequest request);
    void buildResource();
    void buildTransformation();
    void buildJob();
    KettleEtlProductBO build();
}
