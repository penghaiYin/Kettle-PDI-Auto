package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_job_attribute 实体
 *
 * @Author RocSea
 * @Date 2022/7/19
 */
@Table(name = "r_job_attribute")
public class JobAttributeDO {
    @Id
    @Column
    private Long idJobAttribute;
    @Column
    private Long idJob;
    @Column
    private Integer nr;
    @Column
    private String code;
    @Column
    private Long valueNum;
    @Column
    private String valueStr;

    public Long getIdJobAttribute() {
        return idJobAttribute;
    }

    public void setIdJobAttribute(Long idJobAttribute) {
        this.idJobAttribute = idJobAttribute;
    }

    public Long getIdJob() {
        return idJob;
    }

    public void setIdJob(Long idJob) {
        this.idJob = idJob;
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

    public Long getValueNum() {
        return valueNum;
    }

    public void setValueNum(Long valueNum) {
        this.valueNum = valueNum;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}