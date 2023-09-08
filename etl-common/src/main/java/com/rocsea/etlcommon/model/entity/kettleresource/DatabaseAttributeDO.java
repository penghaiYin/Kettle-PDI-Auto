package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_database_attribute 实体
 *
 * @Author RocSea
 * @Date 2022/7/12
 */
@Table(name = "r_database_attribute")
public class DatabaseAttributeDO {
    @Id
    @Column
    private Long idDatabaseAttribute;
    @Column
    private Integer idDatabase;
    @Column
    private String code;
    @Column
    private String valueStr;

    public Long getIdDatabaseAttribute() {
        return idDatabaseAttribute;
    }

    public void setIdDatabaseAttribute(Long idDatabaseAttribute) {
        this.idDatabaseAttribute = idDatabaseAttribute;
    }

    public Integer getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(Integer idDatabase) {
        this.idDatabase = idDatabase;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}