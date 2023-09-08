package com.rocsea.kettlepdi.service.file;

import com.rocsea.kettlepdi.model.config.etl.KettleEtlFileConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author RocSea
 * @Date 2022/12/21
 */
@Service
public final class FileResourceService {
    @Resource
    private KettleEtlFileConfig kettleEtlFileConfig;
    private final AtomicInteger kettle = new AtomicInteger();

    @PostConstruct
    private void load() {
        kettle.set(kettleEtlFileConfig.getSerialNum());
    }

    public String getPath() {
        return kettleEtlFileConfig.getPath();
    }

    public AtomicInteger getKettle() {
        return kettle;
    }
}
