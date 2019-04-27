package com.greelee.tool.component.response;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 删除接口的响应信息
 */
@Getter
@Setter
@ToString
public class DelResInfo {
    /**
     * 已删除的 Id 列表
     */
    private List<Object> deleted = Lists.newArrayList();
    /**
     * 未删除的 ID 列表
     */
    private List<Object> notDelete = Lists.newArrayList();

    /**
     * 新增 id 到删除列表
     */
    public void addDeleted(Object id) {
        deleted.add(id);
    }

    /**
     * 新增 id 到未删除列表
     */
    public void addNotDelete(Object id) {
        notDelete.add(id);
    }
}
