package com.greelee.log.manager;


import com.greelee.log.model.ExceptionLogDO;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.mvc.base.BaseRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
public interface ExceptionLogRequest extends BaseRequest<ExceptionLogDO> {

    /**
     * 保存错误信息
     */
    void saveExceptionLog(HttpServletRequest request, Throwable e) throws ServiceException;

    /**
     * 错误记录
     * 自定义 message
     */
    void saveExceptionLog(HttpServletRequest request, String msg, Exception e) throws ServiceException;

    /**
     * 错误记录
     */
    void saveExceptionLog(HttpServletRequest request, String msg) throws ServiceException;
}
