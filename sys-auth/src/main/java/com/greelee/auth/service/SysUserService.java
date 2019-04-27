package com.greelee.auth.service;



import com.greelee.auth.model.SysUserDO;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.mvc.base.BaseService;
import com.greelee.tool.component.response.ResResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
public interface SysUserService extends BaseService<SysUserDO> {
    /**
     * 使用用户名,邮箱,手机号,ad 域账号登录
     * @param username
     * @param password
     * @return
     * @throws ServiceException
     */
    ResResult login(String username, String password) throws ServiceException;

    /**
     * 登出
     * @param request
     * @return
     * @throws ServiceException
     */
    ResResult logout(HttpServletRequest request) throws ServiceException;

    /**
     * 登录状态
     * @param request
     * @return
     */
    ResResult logined(HttpServletRequest request);


    /**
     * 设置用户角色
     * @param id
     * @param roleIds
     * @return
     * @throws ServiceException
     */
    ResResult setUserRole(Long id, Long[] roleIds) throws ServiceException;

    /**
     * 设置多个用户的角色
     * @param ids
     * @param roleId
     * @return
     * @throws ServiceException
     */
    ResResult setUsersRole(Long[] ids, Long roleId) throws ServiceException;

    /**
     * 设置用户所属机构
     * @param id
     * @param corpId
     * @param corpName
     * @return
     * @throws ServiceException
     */
    ResResult setCorp(Long id, Long corpId, String corpName) throws ServiceException;

    /**
     * 设置个人信息
     * @param sysUserDO
     * @return
     * @throws ServiceException
     */
    ResResult setSelfInfo(SysUserDO sysUserDO) throws ServiceException;

    /**
     * 获取个人信息
     * @return
     * @throws ServiceException
     */
    ResResult getSelfInfo() throws ServiceException;

    /**
     * 获取用户列表,包含角色信息
     * @param query
     * @return
     * @throws ServiceException
     */
    ResResult listWithRole(SysUserDO query) throws ServiceException;
}
