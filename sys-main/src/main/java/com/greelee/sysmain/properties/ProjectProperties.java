package com.greelee.sysmain.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/27
 * @describe: 本项目相关属性
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {
    /**
     * 项目包路径:com.greelee.sysmain
     */
    private String projectPackage;
    /**
     * 开发者代号
     */
    private String developerCode = "geLin";
    /**
     * 项目代号
     */
    private String code = "";
    /**
     * 项目名称
     */
    private String name = "";
    /**
     * 项目描述
     */
    private String description = "";
    /**
     * 版本
     */
    private String version = "";
    /**
     * 服务器地址
     */
    private String termsOfServiceUrl = "";
    /**
     * 联系人姓名
     */
    private String contactName = "";
    /**
     * 联系人 URL
     */
    private String contactUrl = "";
    /**
     * 联系人 Email
     */
    private String contactEmail = "";
    /**
     * 是否开启 Swagger
     */
    private Boolean enableSwagger = true;
    /**
     * ehcache 的classpath位置(不用加 classpath 前缀)
     */
    private String ehcacheClasspath = "cache/ehcache.xml";
    /**
     * 登录失效时间(毫秒),默认1小时
     */
    private long sessionTimeout = 3600000L;
    /**
     * 记住我时间(秒),默认7天
     */
    private int rememberMe = 604800;
    /**
     * Druid 加密公钥
     */
    private String druidPublicKey;
}
