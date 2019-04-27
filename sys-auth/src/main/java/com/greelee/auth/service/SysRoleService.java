package com.greelee.auth.service;


import com.greelee.auth.model.SysRoleDO;
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
public interface SysRoleService extends BaseService<SysRoleDO> {
    /**
     * 设置角色权限
     * @param id
     * @param resourceIds
     * @return
     * @throws ServiceException
     */
    ResResult setRoleResource(Long id, Long[] resourceIds)throws ServiceException;

    /**
     * 获取此角色拥有的资源 id 列表
     * @param id
     * @return
     * @throws ServiceException
     */
    ResResult listResourceIdById(Long id) throws ServiceException;
}
