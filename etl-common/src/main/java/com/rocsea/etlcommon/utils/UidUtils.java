package com.rocsea.etlcommon.utils;

import java.util.UUID;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
public final class UidUtils {
    private UidUtils() {

    }

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
