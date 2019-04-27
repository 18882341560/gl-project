package com.greelee.log.manager.impl;

import com.alibaba.fastjson.JSON;
import com.greelee.log.autoconfigure.LogProperties;
import com.greelee.log.dao.ExceptionLogDao;
import com.greelee.log.manager.ExceptionLogRequest;
import com.greelee.log.model.ExceptionLogDO;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.util.ip.IpUtil;
import com.greelee.tool.util.secret.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Component
@Slf4j
public class ExceptionLogRequestImpl implements ExceptionLogRequest {

    private ExceptionLogDao exceptionLogDao;

    private LogProperties logProperties;

    @Autowired
    public ExceptionLogRequestImpl(LogProperties logProperties, ExceptionLogDao exceptionLogDao) {
        this.logProperties = logProperties;
        this.exceptionLogDao = exceptionLogDao;
    }

    /**
     */
    @Override
    public void saveExceptionLog(HttpServletRequest request, Throwable e) throws ServiceException {
        ExceptionLogDO logDO = ExceptionLogDO.builder()
                .identify(String.valueOf(JwtUtil.getIdentify(request)))
                .ip(IpUtil.getIp(request))
                .uri(request.getRequestURI())
                .param(JSON.toJSONString(request.getParameterMap()))
                .className(e.getClass().getName())
                .message(ExceptionUtils.getMessage(e))
                .stackTrace(ExceptionUtils.getStackTrace(e))
                .createTime(LocalDateTime.now())
                .build();
        if (logProperties.isWriteToDataBase()) {
            exceptionLogDao.save(logDO);
        } else {
            log.error(logDO.getMessage(),e);
        }
    }

    /**
     * 错误记录
     * 自定义 message
     */
    @Override
    public void saveExceptionLog(HttpServletRequest request,String msg, Exception e) throws ServiceException {
        ExceptionLogDO logDO = ExceptionLogDO.builder()
                .message(msg)
                .className(e.getClass().getName())
                .stackTrace(ExceptionUtils.getStackTrace(e))
                .createTime(LocalDateTime.now())
                .identify(JwtUtil.getIdentify(request))
                .ip(IpUtil.getIp(request))
                .param(JSON.toJSONString(request.getParameterMap()))
                .uri(request.getRequestURI())
                .build();
        exceptionLogDao.save(logDO);
        if (logProperties.isWriteToDataBase()) {
            exceptionLogDao.save(logDO);
        } else {
            log.error(msg, e);
        }
    }

    /**
     * 错误记录
     * 自定义 message
     */
    @Override
    public void saveExceptionLog(HttpServletRequest request,String msg) throws ServiceException {
        ExceptionLogDO logDO = ExceptionLogDO.builder()
                .message(msg)
                .className("自定义异常")
                .stackTrace("自定义异常")
                .createTime(LocalDateTime.now())
                .identify(JwtUtil.getIdentify(request))
                .ip(IpUtil.getIp(request))
                .param(JSON.toJSONString(request.getParameterMap()))
                .uri(request.getRequestURI())
                .build();
        exceptionLogDao.save(logDO);
        if (logProperties.isWriteToDataBase()) {
            exceptionLogDao.save(logDO);
        } else {
            log.error(msg);
        }
    }

    @Override
    public Integer save(ExceptionLogDO object) throws ServiceException {
        return exceptionLogDao.save(object);
    }
}
