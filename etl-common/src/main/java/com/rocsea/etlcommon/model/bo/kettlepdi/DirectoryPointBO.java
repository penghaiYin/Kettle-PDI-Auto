package com.rocsea.etlcommon.model.bo.kettlepdi;

import com.rocsea.etlcommon.model.entity.kettleresource.DirectoryDO;

import java.util.List;

/**
 * @Author RocSea
 * @Date 2023/1/17
 */
public class DirectoryPointBO {
    private List<DirectoryDO> directoryList;

    public List<DirectoryDO> getDirectoryList() {
        return directoryList;
    }

    public void setDirectoryList(List<DirectoryDO> directoryList) {
        this.directoryList = directoryList;
    }
}
