package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * r_trans_hop 实体
 * @Author RocSea
 * @Date 2022/7/11
 */
@Table(name = "r_trans_hop")
public class TransHopDO {
    @Id
    @Column
    private Long idTransHop;
    @Column
    private Long idTransformation;
    @Column
    private Long idStepFrom;
    @Column
    private Long idStepTo;
    @Column
    private Long enabled;


    public Long getIdTransHop() {
        return idTransHop;
    }

    public void setIdTransHop(Long idTransHop) {
        this.idTransHop = idTransHop;
    }

    public Long getIdTransformation() {
        return idTransformation;
    }

    public void setIdTransformation(Long idTransformation) {
        this.idTransformation = idTransformation;
    }

    public Long getIdStepFrom() {
        return idStepFrom;
    }

    public void setIdStepFrom(Long idStepFrom) {
        this.idStepFrom = idStepFrom;
    }

    public Long getIdStepTo() {
        return idStepTo;
    }

    public void setIdStepTo(Long idStepTo) {
        this.idStepTo = idStepTo;
    }

    public Long getEnabled() {
        return enabled;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }
}
