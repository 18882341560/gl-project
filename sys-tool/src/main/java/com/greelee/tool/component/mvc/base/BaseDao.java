package com.greelee.tool.component.mvc.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: dao基本类
 */
public interface BaseDao<T> {

    List<T> list(@Param("query") T query);

    int count(@Param("query") T query);

    T getById(@Param("id") Object id);

    boolean isExistById(@Param("id") Object id);

    int save(T object);

    int update(T object);

    int deleteById(@Param("id") Object id);

    int deleteByIds(@Param("ids") Object[] ids);
}
