package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_jobentry_attribute 实体
 *
 * @Author RocSea
 * @Date 2022/7/19
 */
@Table(name = "r_jobentry_attribute")
public class JobentryAttributeDO {
    @Id
    @Column
    private Long idJobentryAttribute;
    @Column
    private Long idJob;
    @Column
    private Long idJobentry;
    @Column
    private Integer nr;
    @Column
    private String code;
    @Column
    private Double valueNum;
    @Column
    private String valueStr;

    public Long getIdJobentryAttribute() {
        return idJobentryAttribute;
    }

    public void setIdJobentryAttribute(Long idJobentryAttribute) {
        this.idJobentryAttribute = idJobentryAttribute;
    }

    public Long getIdJob() {
        return idJob;
    }

    public void setIdJob(Long idJob) {
        this.idJob = idJob;
    }

    public Long getIdJobentry() {
        return idJobentry;
    }

    public void setIdJobentry(Long idJobentry) {
        this.idJobentry = idJobentry;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValueNum() {
        return valueNum;
    }

    public void setValueNum(Double valueNum) {
        this.valueNum = valueNum;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}