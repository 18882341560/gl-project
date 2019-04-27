package com.greelee.auth.service;


import com.greelee.auth.model.SysOrganizationDO;
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
public interface SysOrganizationService extends BaseService<SysOrganizationDO> {

    /**
     * 获取机构树
     * @return
     * @throws ServiceException
     */
    ResResult tree()throws ServiceException;
}
