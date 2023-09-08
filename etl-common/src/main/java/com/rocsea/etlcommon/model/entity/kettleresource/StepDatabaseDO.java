package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * r_step_database 实体
 * @Author RocSea
 * @Date 2022/7/12
 */
@Table(name = "r_step_database")
public class StepDatabaseDO {
    @Column
    private Long idTransformation;
    @Column
    private Long idStep;
    @Column
    private Long idDatabase;

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

    public Long getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(Long idDatabase) {
        this.idDatabase = idDatabase;
    }
}
