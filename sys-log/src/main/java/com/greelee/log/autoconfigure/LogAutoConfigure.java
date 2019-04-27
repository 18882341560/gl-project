package com.greelee.log.autoconfigure;


import com.greelee.log.advise.ActionLogAdvise;
import com.greelee.log.advise.ExceptionControllerAdvice;
import com.greelee.log.dao.ActionLogDao;
import com.greelee.log.dao.ExceptionLogDao;
import com.greelee.log.manager.ExceptionLogRequest;
import com.greelee.log.manager.impl.ExceptionLogRequestImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnBean({SqlSessionFactory.class})
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public ActionLogAdvise actionLogAdvise(LogProperties logProperties, ActionLogDao actionLogDao, HttpServletRequest request) {
        return new ActionLogAdvise(logProperties, actionLogDao, request);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionLogRequest exceptionLogRequest(LogProperties logProperties, ExceptionLogDao exceptionLogDao) {
        return new ExceptionLogRequestImpl(logProperties, exceptionLogDao);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionControllerAdvice exceptionControllerAdvice(ExceptionLogRequest exceptionLogRequest) {
        return new ExceptionControllerAdvice(exceptionLogRequest);
    }
}
