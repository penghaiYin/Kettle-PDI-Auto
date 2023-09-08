package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * r_trans_step_condition 实体
 * @Author RocSea
 * @Date 2022/7/12
 */
@Table(name = "r_trans_step_condition")
public class TransStepConditionDO {
    @Column
    private Long idTransformation;
    @Column
    private Long idStep;
    @Column
    private Long idCondition;

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

    public Long getIdCondition() {
        return idCondition;
    }

    public void setIdCondition(Long idCondition) {
        this.idCondition = idCondition;
    }
}
