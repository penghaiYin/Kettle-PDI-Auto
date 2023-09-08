package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_directory 实体
 *
 * @Author RocSea
 * @Date 2022/7/12
 */
@Table(name = "r_directory")
public class DirectoryDO {
    @Id
    @Column
    private Long idDirectory;
    @Column
    private Integer idDirectoryParent;
    @Column
    private String directoryName;

    public DirectoryDO(Long idDirectory, Integer idDirectoryParent, String directoryName) {
        this.idDirectory = idDirectory;
        this.idDirectoryParent = idDirectoryParent;
        this.directoryName = directoryName;
    }

    public Long getIdDirectory() {
        return idDirectory;
    }

    public void setIdDirectory(Long idDirectory) {
        this.idDirectory = idDirectory;
    }

    public Integer getIdDirectoryParent() {
        return idDirectoryParent;
    }

    public void setIdDirectoryParent(Integer idDirectoryParent) {
        this.idDirectoryParent = idDirectoryParent;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }
}