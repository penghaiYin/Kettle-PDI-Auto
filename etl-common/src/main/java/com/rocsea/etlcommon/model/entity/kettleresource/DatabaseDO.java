package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_database 实体
 *
 * @Author RocSea
 * @Date 2022/7/12
 */
@Table(name = "r_database")
public class DatabaseDO {
    @Id
    @Column
    private Long idDatabase;
    @Column
    private String name;
    @Column
    private Integer idDatabaseType;
    @Column
    private Integer idDatabaseContype;
    @Column
    private String hostName;
    @Column
    private String databaseName;
    @Column
    private Integer port;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String servername;
    @Column
    private String dataTbs;
    @Column
    private String indexTbs;

    public Long getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(Long idDatabase) {
        this.idDatabase = idDatabase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdDatabaseType() {
        return idDatabaseType;
    }

    public void setIdDatabaseType(Integer idDatabaseType) {
        this.idDatabaseType = idDatabaseType;
    }

    public Integer getIdDatabaseContype() {
        return idDatabaseContype;
    }

    public void setIdDatabaseContype(Integer idDatabaseContype) {
        this.idDatabaseContype = idDatabaseContype;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getDataTbs() {
        return dataTbs;
    }

    public void setDataTbs(String dataTbs) {
        this.dataTbs = dataTbs;
    }

    public String getIndexTbs() {
        return indexTbs;
    }

    public void setIndexTbs(String indexTbs) {
        this.indexTbs = indexTbs;
    }
}