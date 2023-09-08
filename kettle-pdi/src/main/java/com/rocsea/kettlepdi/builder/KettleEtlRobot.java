package com.rocsea.kettlepdi.builder;
import com.rocsea.etlcommon.model.bo.kettlepdi.KettleEtlProductBO;
import com.rocsea.kettlepdi.model.request.KettleEtlBuildRequest;
/**
 * @Author RocSea
 * @Date 2023/1/31
 */
public class KettleEtlRobot {
    private KettleEtlBuilder kettleEtlBuilder;

    public KettleEtlRobot(KettleEtlBuilder kettleEtlBuilder) {
        this.kettleEtlBuilder = kettleEtlBuilder;
    }

    public KettleEtlProductBO makeProduct(KettleEtlBuildRequest request) {
        kettleEtlBuilder.init(request);
        kettleEtlBuilder.buildResource();
        kettleEtlBuilder.buildTransformation();
        kettleEtlBuilder.buildJob();
        return kettleEtlBuilder.build();
    }

}
