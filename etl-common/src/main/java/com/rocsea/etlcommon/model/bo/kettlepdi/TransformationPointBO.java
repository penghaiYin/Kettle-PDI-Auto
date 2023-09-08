package com.rocsea.etlcommon.model.bo.kettlepdi;

import com.rocsea.etlcommon.model.entity.kettleresource.TransformationDO;

import java.util.List;

/**
 * @Author RocSea
 * @Date 2023/1/17
 */
public class TransformationPointBO {
    private List<TransformationDO> transformation;

    public TransformationPointBO(List<TransformationDO> transformation) {
        this.transformation = transformation;
    }

    public List<TransformationDO> getTransformation() {
        return transformation;
    }

    public void setTransformation(List<TransformationDO> transformation) {
        this.transformation = transformation;
    }

}
