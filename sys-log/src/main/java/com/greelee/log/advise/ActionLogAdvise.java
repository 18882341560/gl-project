package com.greelee.log.advise;

import com.alibaba.fastjson.JSON;
import com.greelee.log.autoconfigure.LogProperties;
import com.greelee.log.constant.ActionLogEnum;
import com.greelee.log.dao.Action;
import com.greelee.log.dao.ActionLogDao;
import com.greelee.log.model.ActionLogDO;
import com.greelee.tool.util.ip.IpUtil;
import com.greelee.tool.util.secret.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Aspect
@Component
@Slf4j
public class ActionLogAdvise {

    private LogProperties logProperties;

    private ActionLogDao actionLogDao;

    private HttpServletRequest request;

    @Autowired
    public ActionLogAdvise(LogProperties logProperties, ActionLogDao actionLogDao, HttpServletRequest request) {
        this.logProperties = logProperties;
        this.actionLogDao = actionLogDao;
        this.request = request;
    }

    /**
     * 切点 使用 ControllerLog 注解 作为切点表达式。
     */
    @Pointcut("@annotation(action)")
    public void actionAspect(Action action) {
    }

    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint ProceedingJoinPoint接口有一个方法proceed(),用于执行目标方法;该方法的返回值是目标方法的返回值
     * @return 可定义的返回值, 不更改则为目标方法返回值
     */
    @Around(value = "actionAspect(action)", argNames = "proceedingJoinPoint,action")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, Action action) throws Throwable {
        Object result;
        LocalDateTime startTime = LocalDateTime.now();
        result = proceedingJoinPoint.proceed();
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        /*
         * 如果是登陆操作,则 identify 为登录名,param 为空
         */
        String identify;
        String param = "";
        if (Objects.equals(action.type(), ActionLogEnum.LOGIN)) {
            identify = request.getParameter("username");
            param = JSON.toJSONString(request.getParameterMap());
        } else {
            identify = String.valueOf(JwtUtil.getIdentify(request));
        }
        /*
         * 通过 request 对象与切点注解对象获取数据
         */
        ActionLogDO actionLogDO = ActionLogDO.builder()
                .type(action.type().name())
                .identify(identify)
                .description(action.desc())
                .uri(request.getRequestURI())
                .param(param)
                .duration(duration.toMillis())
                .ip(IpUtil.getIp(request))
                .visitTime(startTime)
                .createTime(endTime)
                .build();

        if (logProperties.isWriteToDataBase()) {
            actionLogDao.save(actionLogDO);
        } else {
            log.info(actionLogDO.toString());
        }
        return result;
    }

}
