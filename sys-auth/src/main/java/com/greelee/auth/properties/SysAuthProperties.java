package com.greelee.auth.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "sys.auth")
public class SysAuthProperties {

    /**
     * 默认管理员名称
     */
    private String adminName = "ge.lin";

    /**
     * 默认管理员密码
     */
    private String adminPassword = "ge.lin";

    /**
     * Jwt接收方:定为项目名
     */
    private String audience = "defaultAudience";

    /**
     * Jwt签发方:定为开发者名
     */
    private String issuer = "ge.lin";

    /**
     * JWT签名过期时间(毫秒)
     */
    private long jwtTimeToLive = 3600000L;

    /**
     * 开启缓存
     */
    private boolean cachingEnabled = true;

    /**
     * 开启授权缓存
     */
    private boolean authorizationCachingEnabled = true;
    /**
     * 授权缓存名称
     */
    private String authorizationCacheName = "authorizationCache";
    /**
     * 开启认证缓存
     */
    private boolean authenticationCachingEnabled = true;
    /**
     * 认证缓存名称
     */
    private String authenticationCacheName = "authenticationCache";
}
