package com.rocsea.etlcommon.model.bo.kettlepdi;

/**
 * @Author RocSea
 * @Date 2023/1/17
 */
public class KettleEtlProductBO {
    private String resources;
    private String transformation;
    private String job;
    private String kettleTaskConfig;
    private String kettlePack;

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getKettleTaskConfig() {
        return kettleTaskConfig;
    }

    public void setKettleTaskConfig(String kettleTaskConfig) {
        this.kettleTaskConfig = kettleTaskConfig;
    }

    public String getKettlePack() {
        return kettlePack;
    }

    public void setKettlePack(String kettlePack) {
        this.kettlePack = kettlePack;
    }

    public String get() {
        return resources + transformation + job + kettleTaskConfig + kettlePack;
    }
}
