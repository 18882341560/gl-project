package com.greelee.auth.dao;

import com.greelee.auth.model.SysResourceDO;
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
public interface SysResourceDao extends BaseDao<SysResourceDO> {
    /**
     * 通过 pid 与类型获取对象列表
     * @param pid
     * @param type
     * @return
     */
    List<SysResourceDO> listByPidAndType(@Param("pid") Long pid, @Param("type") Integer type);

    /**
     * 是否存在
     * @param pid
     * @return
     */
    boolean isExistByPid(@Param("pid") Object pid);

    /**
     * 获取权限字符串
     * @param userId
     * @return
     */
    List<String> listPermissionByUserId(@Param("userId") Object userId);

    /**
     * 判断用户的资源是否存在
     * @param userId
     * @param id
     * @return
     */
    boolean isExistByUserIdAndResourceId(@Param("userId") Object userId, @Param("id") Long id);

    /**
     * 判断是否有正常状态的父级对象
     * @param pid
     * @param status
     * @return
     */
    boolean isExistByPidAndStatus(@Param("pid") Object pid, @Param("status") Integer status);

    /**
     * 通过 pid 改变 pTitle
     * @param pTitle
     * @param pid
     * @return
     */
    int updatePTitleByPid(@Param("pTitle") String pTitle, @Param("pid") Long pid);
}
