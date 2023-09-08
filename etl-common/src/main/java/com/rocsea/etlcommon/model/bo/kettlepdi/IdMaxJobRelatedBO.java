package com.rocsea.etlcommon.model.bo.kettlepdi;

/**
 * 存放作业相关表的当前主键最大值
 *
 * @Author RocSea
 * @Date 2022/7/15
 */
public class IdMaxJobRelatedBO {
    /**
     * 存放 r_job 当前主键最大值
     */
    private Long idJobMax;
    /**
     * 存放 r_job_attribute 当前主键最大值
     */
    private Long idJobAttributeMax;
    /**
     * 存放 r_jobentry 当前主键最大值
     */
    private Long idJobEntryMax;

    /**
     * 存放 r_jobentry_attribute 当前主键最大值
     */
    private Long idJobEntryAttributeMax;

    /**
     * 存放 r_jobentry_copy 当前主键最大值
     */
    private Long idJobEntryCopyMax;

    /**
     * 存放 r_job_hop 当前主键最大值
     */
    private Long idJobHopMax;

    public IdMaxJobRelatedBO(Long idJobMax,
                             Long idJobAttributeMax,
                             Long idJobEntryMax,
                             Long idJobEntryAttributeMax,
                             Long idJobEntryCopyMax,
                             Long idJobHopMax) {
        this.idJobMax = idJobMax;
        this.idJobAttributeMax = idJobAttributeMax;
        this.idJobEntryMax = idJobEntryMax;
        this.idJobEntryAttributeMax = idJobEntryAttributeMax;
        this.idJobEntryCopyMax = idJobEntryCopyMax;
        this.idJobHopMax = idJobHopMax;
    }

    public Long getIdJobMax() {
        return idJobMax;
    }

    public void setIdJobMax(Long idJobMax) {
        this.idJobMax = idJobMax;
    }

    public Long getIdJobAttributeMax() {
        return idJobAttributeMax;
    }

    public void setIdJobAttributeMax(Long idJobAttributeMax) {
        this.idJobAttributeMax = idJobAttributeMax;
    }

    public Long getIdJobEntryMax() {
        return idJobEntryMax;
    }

    public void setIdJobEntryMax(Long idJobEntryMax) {
        this.idJobEntryMax = idJobEntryMax;
    }

    public Long getIdJobEntryAttributeMax() {
        return idJobEntryAttributeMax;
    }

    public void setIdJobEntryAttributeMax(Long idJobEntryAttributeMax) {
        this.idJobEntryAttributeMax = idJobEntryAttributeMax;
    }

    public Long getIdJobEntryCopyMax() {
        return idJobEntryCopyMax;
    }

    public void setIdJobEntryCopyMax(Long idJobEntryCopyMax) {
        this.idJobEntryCopyMax = idJobEntryCopyMax;
    }

    public Long getIdJobHopMax() {
        return idJobHopMax;
    }

    public void setIdJobHopMax(Long idJobHopMax) {
        this.idJobHopMax = idJobHopMax;
    }

    public Long addAndGetIdJobMax() {
        return ++idJobMax;
    }
    public Long addAndGetIdJobAttributeMax() {
        return ++idJobAttributeMax;
    }

    public Long addAndGetIdJobEntryMax() {
        return ++idJobEntryMax;
    }

    public Long addAndGetIdJobEntryAttributeMax() {
        return ++idJobEntryAttributeMax;
    }

    public Long addAndGetIdJobEntryCopyMax() {
        return ++idJobEntryCopyMax;
    }

    public Long addAndGetIdJobHopMax() {
        return ++idJobHopMax;
    }
}
