package com.greelee.log.manager.impl;


import com.greelee.log.dao.ApiLogDao;
import com.greelee.log.manager.ApiLogRequest;
import com.greelee.log.model.ApiLogDO;
import com.greelee.tool.component.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Component
public class ApiLogRequestImpl implements ApiLogRequest {
    private ApiLogDao apiLogDao;

    @Autowired
    public ApiLogRequestImpl(ApiLogDao apiLogDao) {
        this.apiLogDao = apiLogDao;
    }

    @Override
    public Integer save(ApiLogDO object) throws ServiceException {
        return apiLogDao.save(object);
    }
}
