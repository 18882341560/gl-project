package com.greelee.tool.util.lang;


import com.greelee.tool.util.pattern.PatternConst;

import java.util.regex.Matcher;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 字符串工具类
 */
public class StrUtil {

    private StrUtil() {
    }

    public static void main(String[] args) {
        System.out.println(insertByLimit("123123123213213", "林", 3));
    }

    /**
     * 根据限制个数数,每到限制位数添加一个空格
     */
    public static String insertBlankByLimit(String original, int limit) {
        return insertByLimit(original, " ", limit);
    }

    /**
     * 根据限制个数数,每到限制位数添加一个指定的插入字符串
     *
     * @param original 原始字符串
     * @param insert   插入字符串
     * @param limit    每段字符个数
     */
    public static String insertByLimit(String original, String insert, int limit) {
        int length = original.length();
        if (length <= limit) {
            return original;
        }
        int count = (int) (Math.ceil(1.0 * length / limit) - 1);
        int insertLength = insert.length();
        StringBuilder stringBuilder = new StringBuilder(original);
        for (int i = 0; i < count; i++) {
            // 每次插入的偏移量
            int offset = limit * (i + 1) + insertLength * i;
            stringBuilder.insert(offset, insert);
        }
        return stringBuilder.toString();
    }

    /**
     * 下划线转驼峰
     */
    public static String convertUnderLineToCamelCase(String underLineString) {
        StringBuilder resultBuilder = new StringBuilder(underLineString);
        String result = underLineString;
        Matcher matcher = PatternConst.UNDERLINE_WITH_CHAR.matcher(resultBuilder);
        while (matcher.find()) {
            String temp = matcher.group(0);
            result = result.replaceAll(temp, temp.replaceAll("_", "")
                    .toUpperCase());
        }
        return result;
    }

    /**
     * 下划线转驼峰
     */
    public static String convertUnderLineToUpperCamelCase(String underLineString) {
        String result = convertUnderLineToCamelCase(underLineString);
        //第一个字母大写
        String firstChar = String.valueOf(result.charAt(0)).toUpperCase();
        result = result.substring(1);
        result = firstChar + result;
        return result;
    }

    /**
     * 下划线转 low 驼峰
     */
    public static String convertUnderLineToLowCamelCase(String underLineString) {
        String result = convertUnderLineToCamelCase(underLineString);
        //第一个字母小写
        String firstChar = String.valueOf(result.charAt(0)).toLowerCase();
        result = result.substring(1);
        result = firstChar + result;
        return result;
    }

    /**
     * 判断字符串是不是以数字开头
     */
    public static boolean isStartWithNumber(String str) {
        Matcher isNum = PatternConst.START_WITH_NUMBER.matcher(str.charAt(0) + "");
        return isNum.matches();
    }
}
