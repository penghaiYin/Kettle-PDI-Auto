package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_job_note 实体
 *
 * @Author RocSea
 * @Date 2022/7/19
 */
@Table(name = "r_job_note")
public class JobNoteDO {
    @Column
    private Long idJob;
    @Column
    private Integer idNote;

    public Long getIdJob() {
        return idJob;
    }

    public void setIdJob(Long idJob) {
        this.idJob = idJob;
    }

    public Integer getIdNote() {
        return idNote;
    }

    public void setIdNote(Integer idNote) {
        this.idNote = idNote;
    }
}