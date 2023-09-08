package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * r_condition 实体
 * @Author RocSea
 * @Date 2022/7/13
 */
@Table(name = "r_condition")
public class ConditionDO {
    @Id
    @Column
    private Long idCondition;
    @Column
    private Long idConditionParent;
    @Column
    private Long negated;
    @Column
    private String operator;
    @Column
    private String leftName;
    @Column
    private String conditionFunction;
    @Column
    private String rightName;
    @Column
    private Long idValueRight;

    public Long getIdCondition() {
        return idCondition;
    }

    public void setIdCondition(Long idCondition) {
        this.idCondition = idCondition;
    }

    public Long getIdConditionParent() {
        return idConditionParent;
    }

    public void setIdConditionParent(Long idConditionParent) {
        this.idConditionParent = idConditionParent;
    }

    public Long getNegated() {
        return negated;
    }

    public void setNegated(Long negated) {
        this.negated = negated;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getConditionFunction() {
        return conditionFunction;
    }

    public void setConditionFunction(String conditionFunction) {
        this.conditionFunction = conditionFunction;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public Long getIdValueRight() {
        return idValueRight;
    }

    public void setIdValueRight(Long idValueRight) {
        this.idValueRight = idValueRight;
    }
}
