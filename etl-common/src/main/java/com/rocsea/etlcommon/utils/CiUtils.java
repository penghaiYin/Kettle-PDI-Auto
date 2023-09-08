package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.model.enums.DatadictModuleEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CI工具类
 *
 * @author RocSea
 */
public final class CiUtils {
    private static final Log logger = LogFactory.getLog(CiUtils.class);
    private static final String COLUMN_URL = "http://restnewqa.innodealing.com/backend-tool/sql/replace/list/columns?dbType=MYSQL";
    private static final String DATA_DICT_URL = "http://restnewqa.innodealing.com/data-dict-search/search/list/short-name";
    private static final String DATA_DICT_URL_INTL = "http://restnewqa.innodealing.com/data-dict-search/search/list/intl/short-name";

    private CiUtils() {
        // hide constructor
    }

    public static void check(String sql, Integer dataDictModule, String tableName) {
        Set<String> dictSet = new HashSet<>();
        try {
            if (DatadictModuleEnum.INTGERNAL.getValue() == dataDictModule) {
                Set<String> dataDictColumns = getDataDict(DATA_DICT_URL);
                dictSet.addAll(dataDictColumns);
            }
            if (DatadictModuleEnum.INTGERNATIONAL.getValue() == dataDictModule) {
                Set<String> dataDictColumns = getDataDict(DATA_DICT_URL_INTL);
                dictSet.addAll(dataDictColumns);
            }
            Set<String> sqlColumns = getColumns(sql);
            if (sqlColumns.isEmpty()) {
                return;
            }
            if (dictSet.containsAll(sqlColumns)) {
                logger.info(String.format("表名==>%s CI通过...", tableName));
                return;
            }
            sqlColumns.removeAll(dictSet);
            logger.error(String.format("Failed! not found in dictionary ==> %s\nPlease check the table ==> %s",
                    sqlColumns, tableName));
        } catch (Exception e) {
            logger.error("CI异常: " + e.getMessage());
        }
    }

    private static Set<String> getDataDict(String url) throws Exception {
        return arrayStrToSet(get(url));
    }

    private static Set<String> getColumns(String sqlContext) throws Exception {
        return arrayStrToSet(post(COLUMN_URL, sqlContext));
    }

    private static Set<String> arrayStrToSet(String str) {
        if (str.equals("[]")) {
            return new HashSet<>();
        }
        return Arrays.stream(str.replace("[", "").replace("]", "")
                .replace(", ", ",").replace("\"", "")
                .trim().toLowerCase(Locale.ROOT).split(",")).collect(Collectors.toSet());
    }

    private static String get(String uri) throws Exception {
        InputStream in = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.connect();
            in = connection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            return buffer.lines().collect(Collectors.joining());
        } catch (Exception e) {
            logger.error(String.format("系统正在维护,请等待三分钟再试 %s", e.getMessage()));
            return null;
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.error("[error msg：" + e2.getMessage() + "]");
            }
        }
    }

    private static String post(String uri, String body) throws Exception {
        PrintWriter out = null;
        InputStream in = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            out = new PrintWriter(connection.getOutputStream());
            out.print(body);
            out.flush();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                in = connection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                return buffer.lines().collect(Collectors.joining());
            } else {
                in = connection.getErrorStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                logger.info(String.format("请检查sql是否规范, 比如少逗号，少括号，数字中间有空格，负号和数字有空格等。 建议放到开发工具中进行验证后再次CI， 可直接使用命令 [java Ci]    参考信息： %s",
                        buffer.lines().collect(Collectors.joining())));
                return null;
            }
        } catch (Exception e) {
            logger.error(String.format("系统正在维护,请等待三分钟再试 %s", e.getMessage()));
            return null;
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (Exception e2) {
                logger.error("[error msg：" + e2.getMessage() + "]");
            }
        }
    }

}
