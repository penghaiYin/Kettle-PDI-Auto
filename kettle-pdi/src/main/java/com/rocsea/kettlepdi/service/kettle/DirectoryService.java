package com.rocsea.kettlepdi.service.kettle;

import com.rocsea.etlcommon.exception.BusinessException;
import com.rocsea.etlcommon.model.bo.kettlepdi.DirectoryPointBO;
import com.rocsea.etlcommon.model.entity.kettleresource.DirectoryDO;
import com.rocsea.etlcommon.dao.kettleresource.DirectoryDAO;
import com.rocsea.kettlepdi.utils.KettleEtlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
public class DirectoryService {
    // key = moduleType + sourceSchemaName, value = 父文件目录
    private static final ConcurrentHashMap<String, DirectoryDO> parentDirectoryMap = new ConcurrentHashMap<>();
    private static final AtomicLong directoryCurId = new AtomicLong();

    private final DirectoryDAO directoryDAO;

    public DirectoryService(DirectoryDAO directoryDAO) {
        this.directoryDAO = directoryDAO;
    }

    @PostConstruct
    private void initializeCache() {
        final Long directoryIdMax = directoryDAO.getMaxId().orElse(0L);
        directoryCurId.set(directoryIdMax);
    }

    public DirectoryPointBO getPoints() {
        final int moduleType = KettleEtlUtils.getModuleType();
        final String sourceSchemaName = KettleEtlUtils.getSourceSchemaName();
        final String sourceTableName = KettleEtlUtils.getSourceTableName();
        final String parentDirectoryMapKey = moduleType + sourceSchemaName;
        DirectoryPointBO directoryPointBO = new DirectoryPointBO();
        List<DirectoryDO> directoryList = new ArrayList<>();
        // 获取当前目录表的ID值
        DirectoryDO parent = parentDirectoryMap.get(parentDirectoryMapKey);
        if (Objects.isNull(parent)) {
            parent = directoryDAO.getOne(moduleType, sourceSchemaName).orElse(null);
            // 添加到缓存，下次不查数据库
            if (Objects.nonNull(parent))
                parentDirectoryMap.put(parentDirectoryMapKey, parent);
        }
        if (Objects.nonNull(parent)) {
            final int parentId = parent.getIdDirectory().intValue();
            DirectoryDO child = directoryDAO.getOne(parentId, sourceTableName).orElse(null);
            if (Objects.nonNull(child)) throw new BusinessException("该表已存在，请先删除旧的再构建");

            DirectoryDO childPoint = new DirectoryDO(directoryCurId.incrementAndGet(), parentId, sourceTableName);
            directoryList.add(childPoint);
        } else {
            DirectoryDO parentPoint = new DirectoryDO(directoryCurId.incrementAndGet(), moduleType, sourceSchemaName);
            directoryList.add(parentPoint);
            // 添加到缓存
            parentDirectoryMap.put(parentDirectoryMapKey, parentPoint);

            final long parentId = directoryCurId.get();
            DirectoryDO childPoint = new DirectoryDO(directoryCurId.incrementAndGet(), (int) parentId, sourceTableName);
            directoryList.add(childPoint);
        }
        KettleEtlUtils.setIdDirectory(directoryCurId.get());
        directoryPointBO.setDirectoryList(directoryList);
        return directoryPointBO;
    }

}
