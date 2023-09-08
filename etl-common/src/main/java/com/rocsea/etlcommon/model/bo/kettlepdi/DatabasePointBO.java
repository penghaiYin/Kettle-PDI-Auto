package com.rocsea.etlcommon.model.bo.kettlepdi;

import com.rocsea.etlcommon.model.entity.kettleresource.DatabaseAttributeDO;
import com.rocsea.etlcommon.model.entity.kettleresource.DatabaseDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author RocSea
 * @Date 2023/1/17
 */
public class DatabasePointBO {
    private List<DatabaseDO> database;
    private List<DatabaseAttributeDO> databaseAttribute;

    public List<DatabaseDO> getDatabase() {
        return Objects.isNull(database) ? new ArrayList<>() : new ArrayList<>(database);
    }

    public void setDatabase(List<DatabaseDO> database) {
        this.database = Objects.isNull(database) ? new ArrayList<>() : new ArrayList<>(database);
    }

    public List<DatabaseAttributeDO> getDatabaseAttribute() {
        return Objects.isNull(databaseAttribute) ? new ArrayList<>() : new ArrayList<>(databaseAttribute);
    }

    public void setDatabaseAttribute(List<DatabaseAttributeDO> databaseAttribute) {
        this.databaseAttribute = Objects.isNull(databaseAttribute) ? new ArrayList<>() : new ArrayList<>(databaseAttribute);
    }
}
