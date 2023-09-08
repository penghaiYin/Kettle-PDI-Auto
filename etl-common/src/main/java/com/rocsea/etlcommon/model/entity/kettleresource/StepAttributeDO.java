package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * r_step_attribute 实体
 *
 * @Author RocSea
 * @Date 2022/7/12
 */
@Table(name = "r_step_attribute")
public class StepAttributeDO {
    @Id
    @Column
    private Long idStepAttribute;
    @Column
    private Long idTransformation;
    @Column
    private Long idStep;
    @Column
    private Long nr;
    @Column
    private String code;
    @Column
    private Long valueNum;
    @Column
    private String valueStr;

    public Long getIdStepAttribute() {
        return idStepAttribute;
    }

    public void setIdStepAttribute(Long idStepAttribute) {
        this.idStepAttribute = idStepAttribute;
    }

    public Long getIdTransformation() {
        return idTransformation;
    }

    public void setIdTransformation(Long idTransformation) {
        this.idTransformation = idTransformation;
    }

    public Long getIdStep() {
        return idStep;
    }

    public void setIdStep(Long idStep) {
        this.idStep = idStep;
    }

    public Long getNr() {
        return nr;
    }

    public void setNr(Long nr) {
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
