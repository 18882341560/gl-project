package com.greelee.auth.dao;


import com.greelee.auth.model.SysUserRoleMidDO;
import com.greelee.tool.component.mvc.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Mapper
@Repository
public interface SysUserRoleMidDao extends BaseDao<SysUserRoleMidDO> {

    /**
     * 是否存在
     * @param roleId
     * @return
     */
    boolean isExistByRoleId(@Param("roleId") Object roleId);

    /**
     * 删除指定的用户的关联角色对象
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") Object userId);

    /**
     * 通过用户 id 获取此用户 id 的角色 id 列表(元素唯一)
     * @param userId
     * @return
     */
    List<Long> listRoleIdByUserId(@Param("userId") Long userId);

    /**
     * 删除指定的关联对象
     * @param userId
     * @param roleId
     * @return
     */
    int deleteByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 删除指定的关联对象
     * @param roleId
     * @return
     */
    int deleteByRoleId(@Param("roleId") Object roleId);

    /**
     * 通过角色 id 获取此角色 id 的用户 id 列表(元素唯一)
     * @param roleId
     * @return
     */
    List<Long> listUserIdByRoleId(@Param("roleId") Long roleId);
}
