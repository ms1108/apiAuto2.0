package utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    /**
     * 调整时间
     * 当需要返回月份时pattern参数传入MM同理。。。
     * @param offset:1表示明天，-1表示昨天
     * @return
     */
    public static String modifyDate(int offset) {
        return modifyDate(ChronoUnit.DAYS, offset, "yyyy-MM-dd HH:mm:ss");
    }

    public static String modifyDate(ChronoUnit chronoUnit, int offset, String pattern) {
        return formatDate(LocalDateTime.now().plus(offset, chronoUnit), pattern);
    }

    public static String currentDate() {
        return currentDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String currentDate(String pattern) {
        return formatDate(LocalDateTime.now(), pattern);
    }

    public static String formatDate(LocalDateTime date, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(date);
    }

    //当前时间戳
    public static String nowTimeLong() {
        return Long.toString(System.currentTimeMillis());
    }

    //指定时间戳
    public static String timeLongByDate(String dateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateTime, fmt);
        long time = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return Long.toString(time);
    }

}
