package com.rocsea.etlcommon.utils;

import com.google.common.collect.Lists;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

/**
 * BeanCopy
 * @author rocsea
 */
public final class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 复制对象属性
     *
     * @param source 来源
     * @param target 目标
     */
    public static void copyProperties(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    /**
     * List copy
     *
     * @param sourceList 源List
     * @param targetCls        目标类型
     * @param <T>        泛型对象
     * @return 目标List
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetCls) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> list = Lists.newArrayListWithExpectedSize(sourceList.size());
        for (Object source : sourceList) {
            list.add(copyProperties(source, targetCls));
        }
        return list;
    }


    /**
     * 生成key
     *
     * @param srcClazz 源文件的class
     * @param tgtClazz 目标文件的class
     * @return string
     */
    private static String genKey(Class<?> srcClazz, Class<?> tgtClazz) {
        return srcClazz.getName() + "&" + tgtClazz.getName();
    }

    /**
     * BeanCopier的copy
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        T t = createInstance(targetClass);
        copyProperties(source, t);
        return t;
    }

    private static <T> T createInstance(Class<T> targetClass) {
        T t;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
        }
        return t;
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        String beanKey = genKey(sourceClass, targetClass);
        BeanCopier copier = BEAN_COPIER_CACHE.get(beanKey);
        if (Objects.isNull(copier)) {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_CACHE.put(beanKey, copier);
        }
        return copier;
    }
}