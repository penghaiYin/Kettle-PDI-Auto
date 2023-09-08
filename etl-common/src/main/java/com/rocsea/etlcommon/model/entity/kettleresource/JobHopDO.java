package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_job_hop 实体
 *
 * @Author RocSea
 * @Date 2022/7/19
 */
@Table(name = "r_job_hop")
public class JobHopDO {
    @Id
    @Column
    private Long idJobHop;
    @Column
    private Long idJob;
    @Column
    private Integer idJobentryCopyFrom;
    @Column
    private Integer idJobentryCopyTo;
    @Column
    private Integer enabled;
    @Column
    private Integer evaluation;
    @Column
    private Integer unconditional;

    public Long getIdJobHop() {
        return idJobHop;
    }

    public void setIdJobHop(Long idJobHop) {
        this.idJobHop = idJobHop;
    }

    public Long getIdJob() {
        return idJob;
    }

    public void setIdJob(Long idJob) {
        this.idJob = idJob;
    }

    public Integer getIdJobentryCopyFrom() {
        return idJobentryCopyFrom;
    }

    public void setIdJobentryCopyFrom(Integer idJobentryCopyFrom) {
        this.idJobentryCopyFrom = idJobentryCopyFrom;
    }

    public Integer getIdJobentryCopyTo() {
        return idJobentryCopyTo;
    }

    public void setIdJobentryCopyTo(Integer idJobentryCopyTo) {
        this.idJobentryCopyTo = idJobentryCopyTo;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getUnconditional() {
        return unconditional;
    }

    public void setUnconditional(Integer unconditional) {
        this.unconditional = unconditional;
    }
}