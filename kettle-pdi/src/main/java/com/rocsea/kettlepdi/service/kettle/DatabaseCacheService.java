package com.rocsea.kettlepdi.service.kettle;

import com.rocsea.etlcommon.model.entity.kettleresource.DatabaseDO;
import com.rocsea.etlcommon.dao.kettleresource.DatabaseDAO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author RocSea
 * @Date 2022/12/21
 */
@Component
public final class DatabaseCacheService {
    private final DatabaseDAO databaseDAO;
    private static final Map<String, Long> databaseConfig = new ConcurrentHashMap<>();
    private static final List<String> databaseAttributes = new ArrayList<>();

    private DatabaseCacheService(DatabaseDAO databaseDAO) {
        this.databaseDAO = databaseDAO;
    }

    @PostConstruct
    public void load() {
        List<DatabaseDO> databaseDOS = databaseDAO.queryAll();
        Map<String, Long> map = databaseDOS.stream().collect(Collectors.toMap(DatabaseDO::getDatabaseName, DatabaseDO::getIdDatabase));
        databaseConfig.putAll(map);
        databaseAttributes.add(0,"EXTRA_OPTION_MYSQL.useCursorFetch");
        databaseAttributes.add(1,"EXTRA_OPTION_MYSQL.useCompression");
        databaseAttributes.add(2,"EXTRA_OPTION_MYSQL.useServerPrepStmts");
        databaseAttributes.add(3,"EXTRA_OPTION_MYSQL.cachePrepStmts");

        databaseAttributes.add(4,"USE_POOLING");
        databaseAttributes.add(5,"IS_CLUSTERED");
        databaseAttributes.add(6,"FORCE_IDENTIFIERS_TO_UPPERCASE");
        databaseAttributes.add(7,"FORCE_IDENTIFIERS_TO_LOWERCASE");
        databaseAttributes.add(8,"QUOTE_ALL_FIELDS");

        databaseAttributes.add(9,"PRESERVE_RESERVED_WORD_CASE");
        databaseAttributes.add(10,"SUPPORTS_TIMESTAMP_DATA_TYPE");
        databaseAttributes.add(11,"SUPPORTS_BOOLEAN_DATA_TYPE");
        databaseAttributes.add(12,"STREAM_RESULTS");

        databaseAttributes.add(13,"EXTRA_OPTION_MYSQL.defaultFetchSize");
        databaseAttributes.add(14,"PORT_NUMBER");
        databaseAttributes.add(15,"PREFERRED_SCHEMA_NAME");
        databaseAttributes.add(16,"SQL_CONNECT");
    }

    public Map<String, Long> getDatabaseConfig() {
        return databaseConfig;
    }

    public List<String> getDatabaseAttributes() {
        return databaseAttributes;
    }
}
