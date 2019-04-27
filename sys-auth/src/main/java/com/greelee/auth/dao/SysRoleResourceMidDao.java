package com.greelee.auth.dao;


import com.greelee.auth.model.SysRoleResourceMidDO;
import com.greelee.tool.component.mvc.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Mapper
@Repository
public interface SysRoleResourceMidDao extends BaseDao<SysRoleResourceMidDO> {
    /**
     * 通过资源 resourceId 判断是否存在
     * @param resourceId
     * @return
     */
    boolean isExistByResourceId(@Param("resourceId") Object resourceId);
    /**
     * 通过 userId 获取资源 id 列表
     * @param userId
     * @return
     */
    List<Long> listResourceIdByUserId(@Param("userId") Object userId);

    /**
     * 通过角色 id 删除
     * @param roleId
     * @return
     */
    int deleteByRoleId(@Param("roleId") Object roleId);

    /**
     * 通过资源 id 删除
     * @param resourceId
     * @return
     */
    int deleteByResourceId(@Param("resourceId") Object resourceId);

    /**
     * 通过角色 id 与资源 id 删除
     * @param roleId
     * @param resourceId
     * @return
     */
    int deleteByRoleIdAndResourceId(@Param("roleId") Long roleId, @Param("resourceId") Long resourceId);

    /**
     * 通过角色 id 获取资源 id 列表(元素唯一)
     * @param roleId
     * @return
     */
    Set<Long> listResourceIdByRoleId(@Param("roleId") Long roleId);

}
