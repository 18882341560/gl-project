package com.greelee.tool.util.pattern;

import java.util.regex.Pattern;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 匹配模式常量类
 */
public class PatternConst {

    private PatternConst() {
    }

    /**
     * 以数字开头
     */
    public static final Pattern START_WITH_NUMBER = Pattern.compile("[0-9]*");
    /**
     * 下划线接一个字母或数字
     */
    public static final Pattern UNDERLINE_WITH_CHAR = Pattern.compile("(_[A-Za-z0-9])");
}
