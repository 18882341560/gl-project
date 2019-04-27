package com.greelee.auth.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.greelee.auth.properties.SysAuthProperties;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.util.secret.JwtConst;
import com.greelee.tool.util.secret.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Component
public class AuthComponent {

    private SysAuthProperties sysAuthProperties;

    @Autowired
    public AuthComponent(SysAuthProperties sysAuthProperties) {
        this.sysAuthProperties = sysAuthProperties;
    }

    /**
     * shiro 登录认证 , 成功则返回 token
     */
    public String loginReturnToken(String username, String password) throws ServiceException {
        /**
         * 生成 token subject session
         */
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        Map<String, Object> tokenMap = JSON.parseObject(JSON.toJSONString(token), new TypeReference<Map<String, Object>>() {
        });
        /** 有效时间 */
        long effectiveMillis = sysAuthProperties.getJwtTimeToLive();
        /** 接收方 */
        String audience = sysAuthProperties.getAudience();
        /** 签发方 */
        String issuer = sysAuthProperties.getIssuer();
        return JwtUtil.createJwt(tokenMap, username, audience, issuer, effectiveMillis, JwtConst.JWT_SECRET);
    }

    /**
     * 获取主要验证信息:用户表自增 id
     *
     * @see AuthComponent#getPrimaryPrincipal(Class) 使用这个获取指定类型第一个验证信息
     */
    @Deprecated
    public Object getPrimaryPrincipal() throws ServiceException {
        Subject subject = SecurityUtils.getSubject();
        if (Objects.nonNull(subject)) {
            PrincipalCollection principalCollection = subject.getPrincipals();
            if (Objects.nonNull(principalCollection) && !principalCollection.isEmpty()) {
                return principalCollection.getPrimaryPrincipal();
            }
        }
        return null;
    }

    /**
     * 获取主要验证信息:用户表自增 id
     */
    public <T> T getPrimaryPrincipal(Class<T> clazz) throws ServiceException {
        Subject subject = SecurityUtils.getSubject();
        if (Objects.nonNull(subject)) {
            PrincipalCollection principalCollection = subject.getPrincipals();
            if (Objects.nonNull(principalCollection) && !principalCollection.isEmpty()) {
                return principalCollection.oneByType(clazz);
            }
        }
        return null;
    }
}
