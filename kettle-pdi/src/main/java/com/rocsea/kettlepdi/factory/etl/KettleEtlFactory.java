package com.rocsea.kettlepdi.factory.etl;

import com.rocsea.kettlepdi.model.enums.KettleEtlStrategyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * kettle构建策略工厂
 * @Author RocSea
 * @Date 2023/6/7
 */
@Component
public class KettleEtlFactory {
    private final Map<Integer, KettleEtlStrategy> strategyMap = new ConcurrentHashMap<>();

    /**
     * 构造方法
     *
     * @param kettleEtlSingleStrategy singleEtlStrategy
     * @param kettleEtlYearMonthStrategy yearMonthEtlStrategy
     */
    @Autowired
    public KettleEtlFactory(KettleEtlSingleStrategy kettleEtlSingleStrategy,
                            KettleEtlYearMonthStrategy kettleEtlYearMonthStrategy) {
        strategyMap.put(KettleEtlStrategyEnum.SINGLE.getValue(), kettleEtlSingleStrategy);
        strategyMap.put(KettleEtlStrategyEnum.YEAR_MONTH.getValue(), kettleEtlYearMonthStrategy);
    }

    /**
     * 获取自动构建策略
     *
     * @param buildEnumValue 枚举整型值
     * @return AutoBuildStrategy
     */
    public KettleEtlStrategy getEtlBuildStrategy(int buildEnumValue) {
        return strategyMap.get(buildEnumValue);
    }

}
