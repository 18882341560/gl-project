package com.greelee.tool.util.money;

import java.util.Objects;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 金钱工具类
 */
public class MoneyUtil {

    private MoneyUtil() {
    }

    /**
     * 将 long 值的金额转换成字符串类型
     * 不支持负数的参数
     * 主要用于展示
     * 计算时仍然需要使用 Long 类型计算
     */
    public static String getMoneyByLongValue(Long money) {
        if (Objects.isNull(money) || money == 0L) {
            return "0.00";
        }
        //不能作用于负数
        if (money < 0L) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        StringBuilder result = new StringBuilder(String.valueOf(money));
        int length = result.length();
        // 0位数
        if (length == 0) {
            return "0.00";
        }
        // 1位数
        if (length == 1) {
            return "0.0" + result;
        }
        // 2位数
        if (length == 2) {
            return "0." + result;
        }
        //大于两位数
        result.insert(length - 2, ".");
        return result.toString();
    }
}
