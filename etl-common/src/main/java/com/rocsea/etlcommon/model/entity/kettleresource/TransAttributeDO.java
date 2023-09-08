package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * r_trans_attribute 实体
 * @Author RocSea
 * @Date 2022/7/11
 */
@Table(name = "r_trans_attribute")
public class TransAttributeDO {
    @Id
    @Column
    private Long idTransAttribute;
    @Column
    private Long idTransformation;
    @Column
    private Long nr;
    @Column
    private String code;
    @Column
    private Long valueNum;
    @Column
    private String valueStr;

    public Long getIdTransAttribute() {
        return idTransAttribute;
    }

    public void setIdTransAttribute(Long idTransAttribute) {
        this.idTransAttribute = idTransAttribute;
    }

    public Long getIdTransformation() {
        return idTransformation;
    }

    public void setIdTransformation(Long idTransformation) {
        this.idTransformation = idTransformation;
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
