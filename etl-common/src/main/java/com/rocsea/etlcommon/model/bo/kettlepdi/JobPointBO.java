package com.rocsea.etlcommon.model.bo.kettlepdi;

import com.rocsea.etlcommon.model.entity.kettleresource.JobDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author RocSea
 * @Date 2023/1/17
 */
public class JobPointBO {
    private List<JobDO> job;

    public JobPointBO(List<JobDO> job) {
        this.job = job;
    }

    public List<JobDO> getJob() {
        return Objects.isNull(job) ? new ArrayList<>() : new ArrayList<>(job);
    }

    public void setJob(List<JobDO> job) {
        this.job = Objects.isNull(job) ? new ArrayList<>() : new ArrayList<>(job);
    }
}
