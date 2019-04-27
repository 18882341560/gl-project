package com.greelee.auth.dao;


import com.greelee.auth.model.SysRoleDO;
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
public interface SysRoleDao extends BaseDao<SysRoleDO> {

    /**
     * 获取指定用户的角色名称
     * @param userId
     * @return
     */
    List<String> listRoleNameByUserId(@Param("userId") Object userId);
    /**
     * 获取指定用户的角色标题
     * @param userId
     * @return
     */
    List<String> listRoleTitleByUserId(@Param("userId") Object userId);
}
