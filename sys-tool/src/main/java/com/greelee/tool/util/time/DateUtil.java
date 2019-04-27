package com.greelee.tool.util.time;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 时间\日期处理工具类
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 旧的时间类转化为字符串
     *
     * @param date    日期
     * @param pattern 格式
     */
    public static String getDateString(Date date, String pattern) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern);
    }

    /**
     * 将日期和时间字符串转换为时间对象,必须指定日期
     */
    public static LocalDateTime getDateTimeByString(String dateTime) {
        if (StringUtils.isBlank(dateTime)) {
            return null;
        }
        dateTime = dateTime.trim();
        // yyyy-M-d H:m:s.S
        if (dateTime.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$")) {
            return LocalDateTime.parse(dateTime, DateConst.DATE_TIME_SINGLE);
        }
        // yyyy-M-d H:m:s
        if (dateTime.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return LocalDateTime.parse(dateTime, DateConst.DATE_TIME_SINGLE);
        }
        // yyyy/M/d H:m:s
        if (dateTime.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return LocalDateTime.parse(dateTime, DateConst.DATE_TIME_SLASH_SINGLE);
        }
        // yyyy-M-d
        if (dateTime.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return LocalDate.parse(dateTime, DateConst.DATE_SINGLE).atStartOfDay();
        }
        // yyyy-MM
        if (dateTime.matches("^\\d{4}-\\d{1,2}$")) {
            dateTime = dateTime.concat("-01");
            return LocalDate.parse(dateTime, DateConst.DEFAULT_DATE).atStartOfDay();
        }
        throw new IllegalArgumentException("非法值:'" + dateTime + "'");
    }

    /**
     * 将日期和时间字符串转换为时间对象,必须指定日期
     */
    public static LocalDateTime getDateTimeByString(String date, String time) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        LocalDate localDate = getDateByString(date);
        // 时间为空则为当日开始时间
        if (StringUtils.isBlank(time) && Objects.nonNull(localDate)) {
            return LocalDateTime.of(localDate, LocalTime.MIN);
        }
        LocalTime localTime = getTimeByString(time);
        if (Objects.nonNull(localDate) && Objects.nonNull(localTime)) {
            return LocalDateTime.of(localDate, localTime);
        }
        return null;
    }

    /**
     * 从日期字符串获取日期对象
     */
    public static LocalDate getDateByString(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        date = date.trim();
        if (date.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return LocalDate.parse(date, DateConst.DATE_SINGLE);
        }
        if (date.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")) {
            return LocalDate.parse(date, DateConst.DATE_SLASH_SINGLE);
        }
        throw new IllegalArgumentException("非法值:'" + date + "'");
    }

    /**
     * 从时间字符串获取时间对象
     */
    public static LocalTime getTimeByString(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        time = time.trim();
        if (time.matches("^\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
            return LocalTime.parse(time, DateConst.TIME_SINGLE);
        }
        if (time.matches("^\\d{1,2}:\\d{1,2}")) {
            return LocalTime.parse(time, DateConst.TIME_SINGLE);
        }
        if (time.matches("^\\d{1,2}\\d{1,2}")) {
            //2位数
            int length = time.length();
            if (Objects.equals(2, length)) {
                return LocalTime.parse(time, DateConst.TIME_NOT_DIVIDED_WHTH_Hm);
            }
            // 3位数
            if (Objects.equals(3, length)) {
                // 以1开头的数字前两位被视为小时 Hour
                if (time.startsWith("1")) {
                    return LocalTime.parse(time, DateConst.TIME_NOT_DIVIDED_WITH_HHm);
                } else if (time.startsWith("2")) {
                    String prefix = time.substring(0, 2);
                    int prefixInt = Integer.parseInt(prefix);
                    if (prefixInt <= 24) {
                        return LocalTime.parse(time, DateConst.TIME_NOT_DIVIDED_WITH_HHm);
                    } else {
                        return LocalTime.parse(time, DateConst.TIME_NOT_DIVIDED_WITH_Hmm);
                    }
                } else {
                    return LocalTime.parse(time, DateConst.TIME_NOT_DIVIDED_WITH_Hmm);
                }
            }
            // 4位数
            if (Objects.equals(4, length)) {
                return LocalTime.parse(time, DateConst.TIME_NOT_DIVIDED_WITH_HHmm);
            }
        }
        throw new IllegalArgumentException("非法值:'" + time + "'");
    }
}
