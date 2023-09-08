package com.rocsea.etlcommon.model.entity.kettleresource;

import javax.persistence.*;

/**
 * r_jobentry_copy 实体
 *
 * @Author RocSea
 * @Date 2022/7/19
 */
@Table(name = "r_jobentry_copy")
public class JobentryCopyDO {
    @Id
    @Column
    private Long idJobentryCopy;
    @Column
    private Long idJobentry;
    @Column
    private Long idJob;
    @Column
    private Integer idJobentryType;
    @Column
    private Integer nr;
    @Column
    private Integer guiLocationX;
    @Column
    private Integer guiLocationY;
    @Column
    private Integer guiDraw;
    @Column
    private Integer parallel;

    public Long getIdJobentryCopy() {
        return idJobentryCopy;
    }

    public void setIdJobentryCopy(Long idJobentryCopy) {
        this.idJobentryCopy = idJobentryCopy;
    }

    public Long getIdJobentry() {
        return idJobentry;
    }

    public void setIdJobentry(Long idJobentry) {
        this.idJobentry = idJobentry;
    }

    public Long getIdJob() {
        return idJob;
    }

    public void setIdJob(Long idJob) {
        this.idJob = idJob;
    }

    public Integer getIdJobentryType() {
        return idJobentryType;
    }

    public void setIdJobentryType(Integer idJobentryType) {
        this.idJobentryType = idJobentryType;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public Integer getGuiLocationX() {
        return guiLocationX;
    }

    public void setGuiLocationX(Integer guiLocationX) {
        this.guiLocationX = guiLocationX;
    }

    public Integer getGuiLocationY() {
        return guiLocationY;
    }

    public void setGuiLocationY(Integer guiLocationY) {
        this.guiLocationY = guiLocationY;
    }

    public Integer getGuiDraw() {
        return guiDraw;
    }

    public void setGuiDraw(Integer guiDraw) {
        this.guiDraw = guiDraw;
    }

    public Integer getParallel() {
        return parallel;
    }

    public void setParallel(Integer parallel) {
        this.parallel = parallel;
    }
}