package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_jobentry 实体
 *
 * @Author RocSea
 * @Date 2022/7/19
 */
@Table(name = "r_jobentry")
public class JobentryDO {
    @Id
    @Column
    private Long idJobentry;
    @Column
    private Long idJob;
    @Column
    private Integer idJobentryType;
    @Column
    private String name;
    @Column
    private String description;

    public Long getIdJobentry() {
        return idJobentry;
    }

    public void setIdJobentry(Long idJobentry) {
        this.idJobentry = idJobentry;
    }

    public Long getIdJob() {
        return idJob;
    }

    public void setIdJob(Long idJob) {
        this.idJob = idJob;
    }

    public Integer getIdJobentryType() {
        return idJobentryType;
    }

    public void setIdJobentryType(Integer idJobentryType) {
        this.idJobentryType = idJobentryType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}