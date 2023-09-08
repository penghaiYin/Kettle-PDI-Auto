package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * r_step 实体
 * @Author RocSea
 * @Date 2022/7/12
 */
@Table(name = "r_step")
public class StepDO {
    @Id
    @Column
    private Long idStep;
    @Column
    private Long idTransformation;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Long idStepType;
    @Column
    private Long distribute;
    @Column
    private Long copies;
    @Column
    private Long guiLocationX;
    @Column
    private Long guiLocationY;
    @Column
    private Long guiDraw;
    @Column
    private String copiesString;

    public Long getIdStep() {
        return idStep;
    }

    public void setIdStep(Long idStep) {
        this.idStep = idStep;
    }

    public Long getIdTransformation() {
        return idTransformation;
    }

    public void setIdTransformation(Long idTransformation) {
        this.idTransformation = idTransformation;
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

    public Long getIdStepType() {
        return idStepType;
    }

    public void setIdStepType(Long idStepType) {
        this.idStepType = idStepType;
    }

    public Long getDistribute() {
        return distribute;
    }

    public void setDistribute(Long distribute) {
        this.distribute = distribute;
    }

    public Long getCopies() {
        return copies;
    }

    public void setCopies(Long copies) {
        this.copies = copies;
    }

    public Long getGuiLocationX() {
        return guiLocationX;
    }

    public void setGuiLocationX(Long guiLocationX) {
        this.guiLocationX = guiLocationX;
    }

    public Long getGuiLocationY() {
        return guiLocationY;
    }

    public void setGuiLocationY(Long guiLocationY) {
        this.guiLocationY = guiLocationY;
    }

    public Long getGuiDraw() {
        return guiDraw;
    }

    public void setGuiDraw(Long guiDraw) {
        this.guiDraw = guiDraw;
    }

    public String getCopiesString() {
        return copiesString;
    }

    public void setCopiesString(String copiesString) {
        this.copiesString = copiesString;
    }
}
