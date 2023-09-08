package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * r_job 实体
 *
 * @Author RocSea
 * @Date 2022/7/19
 */
@Table(name = "r_job")
public class JobDO {
    @Id
    @Column
    private Long idJob;
    @Column
    private Integer idDirectory;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String extendedDescription;
    @Column
    private String jobVersion;
    @Column
    private Integer jobStatus;
    @Column
    private Integer idDatabaseLog;
    @Column
    private String tableNameLog;
    @Column
    private String createdUser;
    @Column
    private Timestamp createdDate;
    @Column
    private String modifiedUser;
    @Column
    private Timestamp modifiedDate;
    @Column
    private Integer useBatchId;
    @Column
    private Integer passBatchId;
    @Column
    private Integer useLogfield;
    @Column
    private String sharedFile;

    public Long getIdJob() {
        return idJob;
    }

    public void setIdJob(Long idJob) {
        this.idJob = idJob;
    }

    public Integer getIdDirectory() {
        return idDirectory;
    }

    public void setIdDirectory(Integer idDirectory) {
        this.idDirectory = idDirectory;
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

    public String getExtendedDescription() {
        return extendedDescription;
    }

    public void setExtendedDescription(String extendedDescription) {
        this.extendedDescription = extendedDescription;
    }

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String jobVersion) {
        this.jobVersion = jobVersion;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getIdDatabaseLog() {
        return idDatabaseLog;
    }

    public void setIdDatabaseLog(Integer idDatabaseLog) {
        this.idDatabaseLog = idDatabaseLog;
    }

    public String getTableNameLog() {
        return tableNameLog;
    }

    public void setTableNameLog(String tableNameLog) {
        this.tableNameLog = tableNameLog;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Timestamp getCreatedDate() {
        return createdDate == null ? null : new Timestamp(createdDate.getTime());
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate == null ? null : new Timestamp(createdDate.getTime());
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate == null ? null : new Timestamp(modifiedDate.getTime());
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate == null ? null : new Timestamp(modifiedDate.getTime());
    }

    public Integer getUseBatchId() {
        return useBatchId;
    }

    public void setUseBatchId(Integer useBatchId) {
        this.useBatchId = useBatchId;
    }

    public Integer getPassBatchId() {
        return passBatchId;
    }

    public void setPassBatchId(Integer passBatchId) {
        this.passBatchId = passBatchId;
    }

    public Integer getUseLogfield() {
        return useLogfield;
    }

    public void setUseLogfield(Integer useLogfield) {
        this.useLogfield = useLogfield;
    }

    public String getSharedFile() {
        return sharedFile;
    }

    public void setSharedFile(String sharedFile) {
        this.sharedFile = sharedFile;
    }
}