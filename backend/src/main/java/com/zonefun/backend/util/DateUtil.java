package com.zonefun.backend.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName DateUtil
 * @Description 日期工具
 * @Date 2023/3/11 14:34
 * @Author ZoneFang
 */
public class DateUtil {

    /**
     * 获取系统日期时间字符串
     */
    public static long getCurrDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Long.parseLong(now.format(formatter));
    }
}
