package com.zonefun.backend.util;

import org.springframework.util.ObjectUtils;

/**
 * @ClassName CodeTypeTransfer
 * @Description 代码格式转换工具类
 * @Date 2022/8/22 15:16
 * @Author ZoneFang
 */
public class CodeTypeTransferUtil {

    /**
     * 双指针法
     * l之前的表示已经转换过的字符
     * l-r之间的表示需要被转换的单词
     * r之后表示还没有循环到的字符
     *
     * @param s 需要转换的字符串
     * @return java.lang.StringBuilder
     * @date 2022/8/22 10:13
     * @author ZoneFang
     */
    public static String camelTransToSnake(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        int l = 0;
        int r = 0;
        while (r < s.length()) {
            //如果下一个字符是小写
            if (r + 1 < s.length() && !isUpper(s.charAt(r + 1))) {
                while (true) {
                    r++;
                    if (r == s.length()) {
                        stringBuilder.append(s.substring(l).toLowerCase());
                        return stringBuilder.toString();
                    }
                    if (isUpper(s.charAt(r))) {
                        stringBuilder.append(s.substring(l, r).toLowerCase() + "_");
                        l = r;
                        break;
                    }
                }
            } else if (r + 1 < s.length()) {//如果下个字符是连着的大写 如HTTP
                //判断是不是第一个字符，如果不判断则会出现aA 变为aa的情况 而不是a_a
                if (r == 0 && isUpper(s.charAt(r + 1))) {
                    r++;
                    stringBuilder.append(s.substring(l, r).toLowerCase() + "_");
                    l = r;
                } else {
                    while (true) {
                        r++;
                        if (r == s.length()) {
                            stringBuilder.append(s.substring(l).toLowerCase());
                            return stringBuilder.toString();
                        }
                        if (!isUpper(s.charAt(r))) {
                            r--;
                            stringBuilder.append(s.substring(l, r).toLowerCase() + "_");
                            l = r;
                            break;
                        }
                    }
                }
            } else {//如果是到达最后了
                stringBuilder.append(s.substring(l).toLowerCase());
                r++;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 是否是大写
     *
     * @param c 判断字符
     * @return boolean
     * @date 2022/8/22 10:13
     * @author ZoneFang
     */
    public static boolean isUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    /**
     * 蛇型转为大驼峰
     *
     * @param str 字符串
     * @return java.lang.String
     * @date 2022/9/8 14:25
     * @author ZoneFang
     */
    public static String snakeToUpperCamelCase(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return "";
        }
        // 以不是字母和数字的字符作为分隔符
        String[] strings = str.split("[^a-zA-Z0-9]+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(getTitleCase(strings[i]));
        }
        return sb.toString();
    }

    /**
     * 蛇型转化为小驼峰
     *
     * @param str 字符串
     * @return java.lang.String
     * @date 2022/9/8 14:23
     * @author ZoneFang
     */
    public static String snakeToLowerCamelCase(String str) {
        str = snakeToUpperCamelCase(str);
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return java.lang.String
     * @date 2022/9/8 14:23
     * @author ZoneFang
     */
    public static String getTitleCase(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return "";
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
