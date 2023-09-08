package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_jobentry_database 实体
 *
 * @Author RocSea
 * @Date 2022/7/19
 */
@Table(name = "r_jobentry_database")
public class JobentryDatabaseDO {
    @Column
    private Integer idJob;
    @Column
    private Long idJobentry;
    @Column
    private Integer idDatabase;

    public Integer getIdJob() {
        return idJob;
    }

    public void setIdJob(Integer idJob) {
        this.idJob = idJob;
    }

    public Long getIdJobentry() {
        return idJobentry;
    }

    public void setIdJobentry(Long idJobentry) {
        this.idJobentry = idJobentry;
    }

    public Integer getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(Integer idDatabase) {
        this.idDatabase = idDatabase;
    }
}