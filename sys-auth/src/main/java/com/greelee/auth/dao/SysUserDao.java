package com.greelee.auth.dao;


import com.greelee.auth.model.SysUserDO;
import com.greelee.auth.model.SysUserVO;
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
public interface SysUserDao extends BaseDao<SysUserDO> {
    /**
     * 获取对象
     * @param adAccount
     * @return
     */
    SysUserDO getByAdAccount(@Param("adAccount") String adAccount);
    /**
     * 获取对象
     * @param mobile
     * @return
     */
    SysUserDO getByMobile(@Param("mobile") String mobile);
    /**
     * 获取对象
     * @param username
     * @return
     */
    SysUserDO getByUsername(@Param("username") String username);
    /**
     * 获取对象
     * @param email
     * @return
     */
    SysUserDO getByEmail(@Param("email") String email);

    /**
     * 判断是否存在
     * @param adAccount
     * @return
     */
    boolean isExistByAdAccount(@Param("adAccount") String adAccount);

    /**
     * 是否存在
     * @param email
     * @return
     */
    boolean isExistByEmail(@Param("email") String email);

    /**
     * 判断是否存在
     * @param mobile
     * @return
     */
    boolean isExistByMobile(@Param("mobile") String mobile);

    /**
     * 判断是否存在
     * @param userId
     * @return
     */
    boolean isExistByUserId(@Param("userId") Object userId);

    /**
     * 判断用户引用的 corpId 是否在给定的机构 ID 中存在
     * @param organizationId
     * @return
     */
    boolean isExistByOrganizationId(@Param("organizationId") Object organizationId);

    /**
     * 更新机构信息
     * @param sysUserDO
     * @return
     */
    int updateCorpInfoByDO(SysUserDO sysUserDO);

    /**
     * 更新本人基本信息
     * @param sysUserDO
     * @return
     */
    int updateSelfInfo(SysUserDO sysUserDO);

    /**
     * 返回 {@link SysUserVO}
     * @param query
     * @return
     */
    List<SysUserVO> listVO(@Param("query") SysUserDO query);

    /**
     * 重启用户
     * @param email
     */
    int reuseByEmail(@Param("email") String email);

    /**
     * 通过单位 id 更新单位名称
     * @param corpName
     * @param corpId
     * @return
     */
    int updateCorpNameByCorpId(@Param("corpName") String corpName, @Param("corpId") Integer corpId);
}
