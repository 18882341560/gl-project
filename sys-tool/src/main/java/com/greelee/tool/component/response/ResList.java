package com.greelee.tool.component.response;

import lombok.*;

import java.util.Collection;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 响应 Data 为列表时的数据{@link ResResult#setData(Object)}
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResList<T> {

    /**
     * 获取实例
     */
    public static <T> ResList<T> page(Collection<T> list, Number count) {
        return getInstance(list, count);
    }

    /**
     * 获取实例
     * new MethodName : {@link ResList#}
     */
    @Deprecated
    public static <T> ResList<T> getList(Collection<T> list, Number count) {
        return getInstance(list, count);
    }

    /**
     * 获取实例
     */
    public static <T> ResList<T> getInstance(Collection<T> list, Number count) {
        return new ResList<>(list, count);
    }

    /**
     * 返回的分页列表数据
     */
    private Collection<T> list;

    /**
     * 返回的总行数
     */
    private Number count;
}
