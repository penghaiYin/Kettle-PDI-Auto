package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * r_transformation 实体
 *
 * @Author RocSea
 * @Date 2022/7/11
 */
@Table(name = "r_transformation")
public class TransformationDO {
    @Id
    @Column
    private Long idTransformation;
    @Column
    private Long idDirectory;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String extendedDescription;
    @Column
    private String transVersion;
    @Column
    private Long transStatus;
    @Column
    private Long idStepRead;
    @Column
    private Long idStepWrite;
    @Column
    private Long idStepInput;
    @Column
    private Long idStepOutput;
    @Column
    private Long idStepUpdate;
    @Column
    private Long idDatabaseLog;
    @Column
    private String tableNameLog;
    @Column
    private Long useBatchid;
    @Column
    private Long useLogfield;
    @Column
    private Long idDatabaseMaxdate;
    @Column
    private String tableNameMaxdate;
    @Column
    private String fieldNameMaxdate;
    @Column
    private Double offsetMaxdate;
    @Column
    private Double diffMaxdate;
    @Column
    private String createdUser;
    @Column
    private Timestamp createdDate;
    @Column
    private String modifiedUser;
    @Column
    private Timestamp modifiedDate;
    @Column
    private Long sizeRowset;

    public Long getIdTransformation() {
        return idTransformation;
    }

    public void setIdTransformation(Long idTransformation) {
        this.idTransformation = idTransformation;
    }

    public Long getIdDirectory() {
        return idDirectory;
    }

    public void setIdDirectory(Long idDirectory) {
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

    public String getTransVersion() {
        return transVersion;
    }

    public void setTransVersion(String transVersion) {
        this.transVersion = transVersion;
    }

    public Long getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Long transStatus) {
        this.transStatus = transStatus;
    }

    public Long getIdStepRead() {
        return idStepRead;
    }

    public void setIdStepRead(Long idStepRead) {
        this.idStepRead = idStepRead;
    }

    public Long getIdStepWrite() {
        return idStepWrite;
    }

    public void setIdStepWrite(Long idStepWrite) {
        this.idStepWrite = idStepWrite;
    }

    public Long getIdStepInput() {
        return idStepInput;
    }

    public void setIdStepInput(Long idStepInput) {
        this.idStepInput = idStepInput;
    }

    public Long getIdStepOutput() {
        return idStepOutput;
    }

    public void setIdStepOutput(Long idStepOutput) {
        this.idStepOutput = idStepOutput;
    }

    public Long getIdStepUpdate() {
        return idStepUpdate;
    }

    public void setIdStepUpdate(Long idStepUpdate) {
        this.idStepUpdate = idStepUpdate;
    }

    public Long getIdDatabaseLog() {
        return idDatabaseLog;
    }

    public void setIdDatabaseLog(Long idDatabaseLog) {
        this.idDatabaseLog = idDatabaseLog;
    }

    public String getTableNameLog() {
        return tableNameLog;
    }

    public void setTableNameLog(String tableNameLog) {
        this.tableNameLog = tableNameLog;
    }

    public Long getUseBatchid() {
        return useBatchid;
    }

    public void setUseBatchid(Long useBatchid) {
        this.useBatchid = useBatchid;
    }

    public Long getUseLogfield() {
        return useLogfield;
    }

    public void setUseLogfield(Long useLogfield) {
        this.useLogfield = useLogfield;
    }

    public Long getIdDatabaseMaxdate() {
        return idDatabaseMaxdate;
    }

    public void setIdDatabaseMaxdate(Long idDatabaseMaxdate) {
        this.idDatabaseMaxdate = idDatabaseMaxdate;
    }

    public String getTableNameMaxdate() {
        return tableNameMaxdate;
    }

    public void setTableNameMaxdate(String tableNameMaxdate) {
        this.tableNameMaxdate = tableNameMaxdate;
    }

    public String getFieldNameMaxdate() {
        return fieldNameMaxdate;
    }

    public void setFieldNameMaxdate(String fieldNameMaxdate) {
        this.fieldNameMaxdate = fieldNameMaxdate;
    }

    public Double getOffsetMaxdate() {
        return offsetMaxdate;
    }

    public void setOffsetMaxdate(Double offsetMaxdate) {
        this.offsetMaxdate = offsetMaxdate;
    }

    public Double getDiffMaxdate() {
        return diffMaxdate;
    }

    public void setDiffMaxdate(Double diffMaxdate) {
        this.diffMaxdate = diffMaxdate;
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

    public Long getSizeRowset() {
        return sizeRowset;
    }

    public void setSizeRowset(Long sizeRowset) {
        this.sizeRowset = sizeRowset;
    }
}
