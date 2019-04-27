package com.greelee.tool.util.container;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 判断工具
 */
public class JudgeUtil {

    private JudgeUtil() {
    }

    /**
     * 不为 Null 或 空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return null != array && array.length > 0;
    }

    /**
     * 不为 Null 或 空
     */
    public static <E> boolean isNotEmpty(Collection<E> collection) {
        return null != collection && !collection.isEmpty();
    }

    /**
     * 不为 Null 或 空
     */
    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return null != map && !map.isEmpty();
    }

    /**
     * 不为null，且大于0
     */
    public static boolean isNonNullAndGreaterThanZero(Long id) {
        return null != id && id > 0;
    }
}
