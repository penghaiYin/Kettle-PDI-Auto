package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.model.enums.DeleteTypeEnum;

import java.util.Random;

/**
 * @Author RocSea
 * @Date 2023/5/12
 */
public final class ConvertUtils {
    private ConvertUtils() {
    }

    public static int getBuildModuleValue(String text) {
        int moduleType = -1;
        switch (text) {
            case "国内现网":
                moduleType = 2;
                break;
            case "国内重构":
                moduleType = 6;
                break;
            case "国内落地":
            case "国际现网":
                moduleType = 1;
                break;
            case "新三方库":
                moduleType = 41;
                break;
        }
        return moduleType;
    }

    public static String getBuildModuleText(String serviceModule) {
        String buildModule = "";
        switch (serviceModule) {
            case "国内现网":
                buildModule = "dm";
                break;
            case "国内重构":
                buildModule = "onshore";
                break;
            case "国内落地":
            case "国际现网":
                buildModule = "internation";
                break;
            case "新三方库":
                buildModule = "third";
                break;
        }
        return buildModule;
    }

    public static Integer getDataDictModule(String serviceModule) {
        Integer dataDictModule;
        switch (serviceModule) {
            case "国内现网":
            case "国内重构":
            case "新三方库":
                dataDictModule = 1;
                break;
            case "国内落地":
            case "国际现网":
                dataDictModule = 2;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + serviceModule);
        }
        return dataDictModule;
    }


    public static int getServiceModuleByExcel(String text) {
        int moduleType = -1;
        switch (text) {
            case "国内现网":
                moduleType = 2;
                break;
            case "国内重构":
                moduleType = 1;
                break;
            case "国内落地":
            case "国际现网":
                moduleType = 3;
                break;
            case "新三方库":
                moduleType = 4;
                break;
        }
        return moduleType;
    }

    public static int getDeleteType(boolean hasPhysicalDel) {
        if (hasPhysicalDel) {
            return DeleteTypeEnum.PHYSICAL.getValue();
        }
        return DeleteTypeEnum.LOGIC.getValue();
    }


    public static String getDirPath(int type, String schemaName, String tableName) {
        String path = "";
        switch (type) {
            case 1:
                path = "/international";
                break;
            case 2:
                path = "/dm";
                break;
            case 6:
                path = "/onshore";
                break;
            case 41:
                path = "/third_part";
                break;
        }
        return path + "/" + schemaName + "/" + tableName;
    }

    public static String getRandomTime(String cron, int minuteMax) {
        Random r = new Random();
        int minute = r.nextInt(minuteMax);
        int second = r.nextInt(60);
        String minuteString = String.valueOf(minute);
        String secondString = String.valueOf(second);
        if (minute < 10) {
            minuteString = 0 + minuteString;
        }
        if (second < 10) {
            secondString = 0 + secondString;
        }

        return cron.replace("mm", minuteString).replace("ss", secondString);
    }

    public static String getCategoryId(int type) {
        String categoryID = "";
        switch (type) {
            case 1:
                categoryID = "e08877aff139be077bb309fb54a1d814";
                break;
            case 2:
                categoryID = "94ddda19530282a0a1ed332e7bb631c6";
                break;
            case 6:
                categoryID = "6ce43758888e3f2eebb7f83774af0bbc";
                break;
            case 41:
                categoryID = "981a14ec81ae51be9f47d43ced91fed3";
                break;
        }
        return categoryID;
    }

    public static String getDatabaseNameByServiceModule(Integer serviceModule) {
        String databaseName = "";
        switch (serviceModule) {
            case 1:
                databaseName = "onshore";
                break;
            case 2:
                databaseName = "dm";
                break;
            case 3:
                databaseName = "internation";
                break;
            case 4:
                databaseName = "third";
                break;
        }
        return databaseName;
    }

    public static String getModuleNameByServiceModule(Integer serviceModule) {
        String databaseName = "";
        switch (serviceModule) {
            case 1:
                databaseName = "国内重构";
                break;
            case 2:
                databaseName = "老国内";
                break;
            case 3:
                databaseName = "国际";
                break;
            case 4:
                databaseName = "新三方";
                break;
        }
        return databaseName;
    }

}
