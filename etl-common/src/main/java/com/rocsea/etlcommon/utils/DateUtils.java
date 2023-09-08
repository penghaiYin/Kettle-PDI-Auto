package com.rocsea.etlcommon.utils;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author RocSea
 * @Date 2023/6/15
 */
public final class DateUtils {
    private DateUtils() {}

    public static String[] getYearMonths(String startYearMonth, String endYearMonth) {
        YearMonth start = YearMonth.parse(startYearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
        YearMonth end = YearMonth.parse(endYearMonth, DateTimeFormatter.ofPattern("yyyyMM"));

        List<String> yearMonths = new ArrayList<>();
        YearMonth current = start;

        while (current.isBefore(end) || current.equals(end)) {
            String formattedYearMonth = current.format(DateTimeFormatter.ofPattern("yyyy_MM"));
            yearMonths.add(formattedYearMonth);
            current = current.plusMonths(1);
        }

        return yearMonths.toArray(new String[0]);
    }
}
