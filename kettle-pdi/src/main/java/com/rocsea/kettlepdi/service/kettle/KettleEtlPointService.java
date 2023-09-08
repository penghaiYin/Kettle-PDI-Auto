package com.rocsea.kettlepdi.service.kettle;

import com.rocsea.etlcommon.model.bo.kettlepdi.*;
import com.rocsea.etlcommon.model.entity.kettleresource.JobDO;
import com.rocsea.etlcommon.model.entity.kettleresource.TransformationDO;
import com.rocsea.kettlepdi.utils.KettleEtlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * kettle机器服务
 *
 * @Author RocSea
 * @Date 2022/12/2
 */
@Service
public class KettleEtlPointService {
    @Resource
    private DirectoryService directoryService;
    @Resource
    private DatabaseService databaseService;
    @Resource
    private TransformationService transformationService;
    @Resource
    private JobService jobService;
    public DirectoryPointBO getDirectoryPoint() {
        return directoryService.getPoints();
    }

    public DatabasePointBO getDatabasePoint() {
        return databaseService.getPoints();
    }

    /**
     * 获取转换切入点
     * @param templateId 模板ID
     * @return TransformationPoint
     */
    public TransformationPointBO getTransformationPoint(Long templateId) {
        List<TransformationDO> transformationTemplateList = transformationService.listTransformation(templateId);
        IdMaxTransRelatedBO idMaxTransRelatedBO = KettleEtlUtils.getIdMaxTransRelated();
        if (Objects.isNull(idMaxTransRelatedBO)) {
            idMaxTransRelatedBO = transformationService.getIdMaxTransRelated();
            KettleEtlUtils.setIdMaxTransRelated(idMaxTransRelatedBO);
        }
        return new TransformationPointBO(transformationTemplateList);
    }

    /**
     * 获取Job切入点
     * @param templateId 模板ID
     * @return JobPoint
     */
    public JobPointBO getJobPoint(Long templateId) {
        List<JobDO> jobDOS = jobService.listJob(templateId);
        IdMaxJobRelatedBO idMaxJobRelatedBO = KettleEtlUtils.getIdMaxJobRelated();
        if(Objects.isNull(idMaxJobRelatedBO)){
            idMaxJobRelatedBO = jobService.getidMaxJobRelated();
            KettleEtlUtils.setIdMaxJobRelated(idMaxJobRelatedBO);
        }
        return new JobPointBO(jobDOS);
    }

}
