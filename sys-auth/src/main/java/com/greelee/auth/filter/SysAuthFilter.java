package com.greelee.auth.filter;

import com.alibaba.fastjson.JSON;
import com.greelee.log.manager.ExceptionLogRequest;
import com.greelee.log.model.ExceptionLogDO;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResResult;
import com.greelee.tool.util.auto.SpringContextHandler;
import com.greelee.tool.util.ip.IpUtil;
import com.greelee.tool.util.secret.JwtConst;
import com.greelee.tool.util.secret.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 登录验证过滤器, 返回前端相关 json 信息,不作为 bean 注册,直接在{@link ShiroFilterFactoryBean#setFilters(Map)} 中作为一个对象 new 一个
 */
public class SysAuthFilter extends FormAuthenticationFilter {

    private ExceptionLogRequest exceptionLogRequest;

    /**
     * 验证 token 等相关验证信息,判定请求是否能够通过
     *
     * @param request     请求
     * @param response    响应
     * @param mappedValue 请求数据
     * @return <code>true</code> if request should be allowed access
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        /*
         * 登录接口不拦截
         */
        if (isLoginRequest(request, response)) {
            return true;
        }
        /*
         * 获取 token
         */
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = JwtUtil.getToken(httpServletRequest);
        /*
         * 验证 token ,之后可结合 JWT
         */
        if (null != token) {
            Claims claims = JwtUtil.parseJwt(token, JwtConst.JWT_SECRET);
            /*
             * token未过期且符合指定条件
             */
            return isAllowed(claims);
        } else {
            return false;
        }
    }

    /**
     * token 是否允许访问
     *
     * @param claims token 解析后的请求
     * @return 是否允许访问
     */
    private boolean isAllowed(Claims claims) {
        if (null != claims) {
            Subject subject = SecurityUtils.getSubject();
            if (null != subject) {
                PrincipalCollection principals = subject.getPrincipals();
                if (null != principals) {
                    Set principalSet = principals.asSet();
                    Object claimSubject = claims.getSubject();
                    Object claimUsername = claims.get("username");
                    return Objects.equals(claimUsername, claimSubject) && principalSet.contains(claimSubject);
                }
            }
        }
        return false;
    }

    /**
     * 访问被拒绝执行此方法
     *
     * @param request     请求
     * @param response    响应
     * @param mappedValue 请求数据
     * @return 被拒绝
     * @throws Exception 异常
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        /**
         * 获取验证主题
         */
        ResResult result;
        Subject subject = getSubject(request, response);
        if (null == subject.getPrincipal()) {
            /**
             * 未登录
             */
            result = ResResult.fail(ResCode.NOT_LOGIN);
        } else {
            /**
             * 被拒绝
             */
            result = ResResult.fail(ResCode.NO_AUTH);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        /**
         * 输出 json
         */
        try (PrintWriter out = response.getWriter()) {
            String resultStr = result.getStr("拒绝访问");
            saveExceptionLog((HttpServletRequest) request, result.getMsg());
            out.write(resultStr);
            out.flush();
        }
        return false;
    }

    private void saveExceptionLog(HttpServletRequest request, String msg) {
        ExceptionLogDO exceptionLogDO = ExceptionLogDO.builder()
                .uri(request.getRequestURI())
                .ip(IpUtil.getIp(request))
                .message(msg)
                .createTime(LocalDateTime.now())
                .identify(String.valueOf(JwtUtil.getIdentify(request)))
                .param(JSON.toJSONString(request.getParameterMap()))
                .stackTrace(msg)
                .className("null")
                .build();
        if (Objects.isNull(exceptionLogRequest)) {
            exceptionLogRequest = SpringContextHandler.getBean(ExceptionLogRequest.class);
        }
        try {
            exceptionLogRequest.save(exceptionLogDO);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
