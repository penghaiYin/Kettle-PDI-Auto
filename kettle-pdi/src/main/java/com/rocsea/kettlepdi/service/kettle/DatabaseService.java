package com.rocsea.kettlepdi.service.kettle;

import com.rocsea.etlcommon.model.bo.kettlepdi.DatabaseBO;
import com.rocsea.etlcommon.model.bo.kettlepdi.DatabasePointBO;
import com.rocsea.etlcommon.model.entity.kettleresource.DatabaseAttributeDO;
import com.rocsea.etlcommon.model.entity.kettleresource.DatabaseDO;
import com.rocsea.etlcommon.utils.BeanCopyUtils;
import com.rocsea.etlcommon.dao.kettleresource.DatabaseAttributeDAO;
import com.rocsea.etlcommon.dao.kettleresource.DatabaseDAO;
import com.rocsea.kettlepdi.utils.KettleEtlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @Author RocSea
 * @Date 2022/12/7
 */
@Service
public class DatabaseService {
    @Resource
    private DatabaseCacheService databaseCacheService;

    private final DatabaseDAO databaseDAO;
    private final DatabaseAttributeDAO databaseAttributeDAO;
    // key = name, value = databaseDO
    private static final ConcurrentHashMap<String, DatabaseDO> databaseMap = new ConcurrentHashMap<>();
    private static final AtomicLong databaseCurId = new AtomicLong();
    private static final AtomicLong databaseAttributeCurId = new AtomicLong();

    public DatabaseService(DatabaseDAO databaseDAO,
                           DatabaseAttributeDAO databaseAttributeDAO) {
        this.databaseDAO = databaseDAO;
        this.databaseAttributeDAO = databaseAttributeDAO;
    }

    @PostConstruct
    private void initializeCache() {
        final Long databaseIdMax = databaseDAO.getMaxId().orElse(0L);
        databaseCurId.set(databaseIdMax);
        final Long databaseAttributeIdMax = databaseAttributeDAO.getMaxId().orElse(0L);
        databaseAttributeCurId.set(databaseAttributeIdMax);
    }

    public DatabasePointBO getPoints() {
        final int moduleType = KettleEtlUtils.getModuleType();
        final String sourceSchemaName = KettleEtlUtils.getSourceSchemaName();
        final DatabaseBO databaseBO = getDatabaseByType(moduleType);
        final String name = databaseBO.getNamePrefix() + sourceSchemaName.toUpperCase();

        DatabaseDO databaseDO = databaseMap.get(name);
        if (Objects.isNull(databaseDO)) {
            databaseDO = databaseDAO.getOne(name, sourceSchemaName).orElse(null);
            if (Objects.nonNull(databaseDO))
                databaseMap.put(name, databaseDO);
        }
        if (Objects.nonNull(databaseDO)) {
            KettleEtlUtils.setIdConnection(databaseDO.getIdDatabase());
            return new DatabasePointBO();
        }
        return createDatabase(databaseBO);

    }

    private DatabasePointBO createDatabase(DatabaseBO databaseBO) {
        final String sourceSchemaName = KettleEtlUtils.getSourceSchemaName();
        final String name = databaseBO.getNamePrefix() + sourceSchemaName.toUpperCase();
        DatabasePointBO databasePointBO = new DatabasePointBO();
        List<DatabaseDO> databaseList = new ArrayList<>();
        List<DatabaseAttributeDO> databaseAttributeList = new ArrayList<>();
        DatabaseDO databaseCopy = BeanCopyUtils.copyProperties(databaseBO, DatabaseDO.class);
        databaseCopy.setIdDatabase(databaseCurId.incrementAndGet());
        databaseCopy.setName(name);
        databaseCopy.setIdDatabaseType(35); // 固定值
        databaseCopy.setIdDatabaseContype(1); // 固定值
        databaseCopy.setDatabaseName(sourceSchemaName);
        databaseCopy.setPort(-1); // 固定值
        databaseList.add(databaseCopy);

        List<String> databaseAttributes = databaseCacheService.getDatabaseAttributes();
        for (int index = 0; index < databaseAttributes.size(); index++) {
            DatabaseAttributeDO databaseAttributeDO = new DatabaseAttributeDO();
            databaseAttributeDO.setIdDatabaseAttribute(databaseAttributeCurId.incrementAndGet());
            databaseAttributeDO.setIdDatabase(databaseCurId.intValue());
            databaseAttributeDO.setCode(databaseAttributes.get(index));
            if (index < 4) {
                databaseAttributeDO.setValueStr("true");
            } else if (index < 9) {
                databaseAttributeDO.setValueStr("N");
            } else if (index < 13) {
                databaseAttributeDO.setValueStr("Y");
            } else if (index == 13) {
                databaseAttributeDO.setValueStr("1000");
            } else if (index == 14) {
                databaseAttributeDO.setValueStr(databaseBO.getPort());
            }
            databaseAttributeList.add(databaseAttributeDO);
        }
        KettleEtlUtils.setIdConnection(databaseCurId.get());
        // 添加缓存
        databaseMap.put(name, databaseCopy);

        databasePointBO.setDatabase(databaseList);
        databasePointBO.setDatabaseAttribute(databaseAttributeList);
        return databasePointBO;
    }

    private DatabaseBO getDatabaseByType(int type) {
        DatabaseBO databaseBO = new DatabaseBO();
        String namePrefix = "";
        String hostName = "";
        String userName = "";
        String password = "";
        String port = "";
        switch (type) {
            case 1:
                namePrefix = "INTERNATIONAL_PUBLIC_MYSQL_";
                hostName = "${INTERNATIONAL_PUBLIC_MYSQL_HOST}";
                userName = "${INTERNATIONAL_PUBLIC_MYSQL_USER}";
                password = "${INTERNATIONAL_PUBLIC_MYSQL_PASSWORD}";
                port = "${INTERNATIONAL_PUBLIC_MYSQL_PORT}";
                break;
            case 2:
                namePrefix = "DM_PUBLIC_MYSQL_";
                hostName = "${DM_PUBLIC_MYSQL_HOST}";
                userName = "${DM_PUBLIC_MYSQL_USER}";
                password = "${DM_PUBLIC_MYSQL_PASSWORD}";
                port = "${DM_PUBLIC_MYSQL_PORT}";
                break;
            case 6:
                namePrefix = "ONSHORE_PUBLIC_MYSQL_";
                hostName = "${ONSHORE_PUBLIC_MYSQL_HOST}";
                userName = "${ONSHORE_PUBLIC_MYSQL_USER}";
                password = "${ONSHORE_PUBLIC_MYSQL_PASSWORD}";
                port = "${ONSHORE_PUBLIC_MYSQL_PORT}";
                break;
            case 41:
                namePrefix = "THIRD_PART_PUBLIC_MYSQL_";
                hostName = "${THIRD_PART_PUBLIC_MYSQL_HOST}";
                userName = "${THIRD_PART_PUBLIC_MYSQL_USER}";
                password = "${THIRD_PART_PUBLIC_MYSQL_PASSWORD}";
                port = "${THIRD_PART_PUBLIC_MYSQL_PORT}";
                break;
        }
        databaseBO.setHostName(hostName);
        databaseBO.setPassword(password);
        databaseBO.setPort(port);
        databaseBO.setUsername(userName);
        databaseBO.setNamePrefix(namePrefix);
        return databaseBO;
    }
}
