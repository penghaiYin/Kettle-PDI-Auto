package com.rocsea.kettlepdi.service.kettle;

import com.rocsea.etlcommon.dao.kettleresource.ConditionDAO;
import com.rocsea.etlcommon.dao.kettleresource.*;
import com.rocsea.etlcommon.model.bo.kettlepdi.IdMaxTransRelatedBO;
import com.rocsea.etlcommon.model.entity.kettleresource.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
@Service
public class TransformationService {
    private final TransformationDAO transformationDao;
    private final TransAttributeDAO transAttributeDao;
    private final StepDAO stepDao;
    private final StepAttributeDAO stepAttributeDao;
    private final TransHopDAO transHopDao;
    private final StepDatabaseDAO stepDatabaseDao;
    private final TransStepConditionDAO transStepConditionDao;
    private final ConditionDAO conditionDao;


    public TransformationService(TransformationDAO transformationDao,
                                 TransAttributeDAO transAttributeDao,
                                 StepDAO stepDao,
                                 StepAttributeDAO stepAttributeDao,
                                 TransHopDAO transHopDao,
                                 StepDatabaseDAO stepDatabaseDao,
                                 TransStepConditionDAO transStepConditionDao,
                                 ConditionDAO conditionDao) {
        this.transformationDao = transformationDao;
        this.transAttributeDao = transAttributeDao;
        this.stepDao = stepDao;
        this.stepAttributeDao = stepAttributeDao;
        this.transHopDao = transHopDao;
        this.stepDatabaseDao = stepDatabaseDao;
        this.transStepConditionDao = transStepConditionDao;
        this.conditionDao = conditionDao;
    }

    /**
     * 查询转换相关表的当前主键最大值
     */
    public IdMaxTransRelatedBO getIdMaxTransRelated() {
        //r_transformation
        Optional<Long> idTransformationMax = transformationDao.queryIdTransformationMax();
        Long transformationMaxId = idTransformationMax.orElse(0L);

        // r_trans_attribute
        Optional<Long> idTransAttributeMax = transAttributeDao.queryIdTransAttributeMax();
        Long transAttributeMaxId = idTransAttributeMax.orElse(0L);

        // r_step
        Optional<Long> idStepMax = stepDao.queryIdStepMax();
        Long stepMaxId = idStepMax.orElse(0L);

        // r_step_attribute
        Optional<Long> idStepAttributeMax = stepAttributeDao.queryIdStepAttributeMax();
        Long stepAttributeMaxId = idStepAttributeMax.orElse(0L);

        // trans_hop
        Optional<Long> idTransHopMax = transHopDao.queryIdTransHopMax();
        Long transHopMaxId = idTransHopMax.orElse(0L);
        return new IdMaxTransRelatedBO(transformationMaxId, transAttributeMaxId, stepMaxId, stepAttributeMaxId, transHopMaxId);
    }

    public List<TransAttributeDO> listTransAttribute(Long idTransformation) {
        return transAttributeDao.queryListByIdTransformation(idTransformation);
    }

    public List<StepDatabaseDO> listStepDatabase(Long templateIdTransformation) {
        return stepDatabaseDao.queryListByIdTransformation(templateIdTransformation);
    }

    public List<TransHopDO> listTransHop(Long templateIdTransformation){
        return transHopDao.queryListByIdTransformation(templateIdTransformation);
    }

    public List<TransStepConditionDO> listTransStepCondition(Long templateIdTransformation){
        return transStepConditionDao.queryListByIdTransformation(templateIdTransformation);
    }

    public List<ConditionDO> listCondition(List<Long> idConditions){
        return conditionDao.queryListByIds(idConditions);
    }

    public List<TransformationDO> listTransformation(Long idDirectory) {
        return transformationDao.queryByIdDirectory(idDirectory);
    }

    public List<StepDO> listStep(Long idTransformation) {
        return stepDao.queryListByIdTransformation(idTransformation);
    }

    public List<StepAttributeDO> listStepAttribute(Long idStep) {
        return stepAttributeDao.queryListByIdStep(idStep);
    }
}

