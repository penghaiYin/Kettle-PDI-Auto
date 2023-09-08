package com.rocsea.etlcommon.model.bo.kettlepdi;

/**
 * 存放转换相关表的当前主键最大值
 *
 * @Author RocSea
 * @Date 2022/7/15
 */
public class IdMaxTransRelatedBO {
    private Long idTransformationMax;
    private Long idTransAttributeMax;
    private Long idStepMax;
    private Long idStepAttributeMax;
    private Long idTransHopMax;

    public IdMaxTransRelatedBO(Long idTransformationMax,
                               Long idTransAttributeMax,
                               Long idStepMax,
                               Long idStepAttributeMax,
                               Long idTransHopMax) {
        this.idTransformationMax = idTransformationMax;
        this.idTransAttributeMax = idTransAttributeMax;
        this.idStepMax = idStepMax;
        this.idStepAttributeMax = idStepAttributeMax;
        this.idTransHopMax = idTransHopMax;
    }

    public Long getIdTransformationMax() {
        return idTransformationMax;
    }

    public void setIdTransformationMax(Long idTransformationMax) {
        this.idTransformationMax = idTransformationMax;
    }

    public Long getIdTransAttributeMax() {
        return idTransAttributeMax;
    }

    public void setIdTransAttributeMax(Long idTransAttributeMax) {
        this.idTransAttributeMax = idTransAttributeMax;
    }

    public Long getIdStepMax() {
        return idStepMax;
    }

    public void setIdStepMax(Long idStepMax) {
        this.idStepMax = idStepMax;
    }

    public Long getIdStepAttributeMax() {
        return idStepAttributeMax;
    }

    public void setIdStepAttributeMax(Long idStepAttributeMax) {
        this.idStepAttributeMax = idStepAttributeMax;
    }

    public Long getIdTransHopMax() {
        return idTransHopMax;
    }

    public void setIdTransHopMax(Long idTransHopMax) {
        this.idTransHopMax = idTransHopMax;
    }

    public Long addAndGetIdTransformationMax() {
        return ++idTransformationMax;
    }

    public Long addAndGetIdTransAttributeMax() {
        return ++idTransAttributeMax;
    }

    public Long addAndGetIdStepMax() {
        return ++idStepMax;
    }

    public Long addAndGetIdStepAttributeMax() {
        return ++idStepAttributeMax;
    }

    public Long addAndGetIdTransHopMax() {
        return ++idTransHopMax;
    }

}
