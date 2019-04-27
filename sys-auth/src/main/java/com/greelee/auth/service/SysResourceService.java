package com.greelee.auth.service;


import com.greelee.auth.model.SysResourceDO;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.mvc.base.BaseService;
import com.greelee.tool.component.response.ResResult;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
public interface SysResourceService extends BaseService<SysResourceDO> {
    /**
     * 根据权限获取资源树
     * @param type
     * @return
     * @throws ServiceException
     */
    ResResult tree(Integer type) throws ServiceException;

    /**
     * 获取全部资源树,仅用于设置角色资源
     * @param type
     * @return
     * @throws ServiceException
     */
    ResResult allTree(Integer type) throws ServiceException;

    /**
     * 根据资源 id 获取用户是否有此资源的权限
     * @param id
     * @return
     * @throws ServiceException
     */
    ResResult getPermission(Long id) throws ServiceException;

}
