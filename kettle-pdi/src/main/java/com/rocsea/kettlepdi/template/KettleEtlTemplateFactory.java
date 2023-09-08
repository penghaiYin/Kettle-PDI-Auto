package com.rocsea.kettlepdi.template;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * kettle模板工厂
 * @Author RocSea
 * @Date 2022/12/7
 */
public final class KettleEtlTemplateFactory {

    private KettleEtlTemplateFactory() {
        // hide constructor
    }

    private static final Map<String, KettleEtlTemplate> templateMap = new ConcurrentHashMap<>();

    /**
     * 注册
     * @param name 名字
     * @param template 模板
     */
    public static void register(String name, KettleEtlTemplate template) {
        if (!Objects.isNull(template)) {
            templateMap.put(name, template);
        }
    }

    /**
     * 获取模板
     * @param name 名字
     * @return 模板
     */
    public static KettleEtlTemplate getTemplate(String name){
        return templateMap.get(name);
    }
}
