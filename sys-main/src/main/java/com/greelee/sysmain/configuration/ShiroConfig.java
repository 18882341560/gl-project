package com.greelee.sysmain.configuration;

import com.google.common.collect.Maps;
import com.greelee.auth.filter.SysAuthFilter;
import com.greelee.auth.filter.SysRememberMeFilter;
import com.greelee.auth.realm.SysUserRealm;
import com.greelee.sysmain.properties.ProjectProperties;
import com.greelee.tool.util.secret.JwtConst;
import io.swagger.models.HttpMethod;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.Map;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/27
 * @describe:  shiro 设置
 * 权限系统完成后独立出来
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录接口 url
        shiroFilterFactoryBean.setLoginUrl("/sys/sys_user/login");
        // 登录成功返回接口 url
        shiroFilterFactoryBean.setSuccessUrl("/sys/sys_user/logined");
        // 无权限接口 url
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/sys_user/logined");

        Map<String, Filter> filters = Maps.newHashMap();
        final String sysAuthKey = "token";
        final String rememberMeKey = "rememberMe";
        filters.put(sysAuthKey, new SysAuthFilter());
        filters.put(rememberMeKey, new SysRememberMeFilter());

        shiroFilterFactoryBean.setFilters(filters);

        //注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，匹配了就不再继续验证
        //所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了。
        Map<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();

        //swagger
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs/**", "anon");

        //druid
        filterChainDefinitionMap.put("/druid/**", "anon");

        //static
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/", "anon");

        filterChainDefinitionMap.put("/**", "anon");
        // filterChainDefinitionMap.put("/**", sysAuthKey);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // shiroFilterFactoryBean.setFilterChainDefinitions();
        return shiroFilterFactoryBean;
    }

    /**
     * shiro缓存 使用 ehCache 管理
     */
    @Bean
    public org.apache.shiro.cache.CacheManager ehCacheManager(net.sf.ehcache.CacheManager cacheManager, ProjectProperties projectProperties) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        ehCacheManager.setCacheManagerConfigFile("classpath:" + projectProperties.getEhcacheClasspath());
        return ehCacheManager;
    }

    @Bean
    public SessionDAO sessionDAO(org.apache.shiro.cache.CacheManager cacheManager) {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setCacheManager(cacheManager);
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        return sessionDAO;
    }

    /**
     * session 管理器
     */
    @Bean
    public SessionManager sessionManager(org.apache.shiro.cache.CacheManager cacheManager, ProjectProperties projectProperties) {
        // return new ServletContainerSessionManager();
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        // listeners.add(new ShiroSessionListener());
        // sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionDAO(sessionDAO(cacheManager));
        // session 超时时间
        sessionManager.setGlobalSessionTimeout(projectProperties.getSessionTimeout());
        // session 定期验证
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // session 验证间隔(毫秒)
        sessionManager.setSessionValidationInterval(projectProperties.getSessionTimeout() / 3);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * rememberMe cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie(ProjectProperties projectProperties) {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间,单位秒
        simpleCookie.setMaxAge(projectProperties.getRememberMe());
        return simpleCookie;
    }

    /**
     * RememberMe 管理器
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(ProjectProperties projectProperties) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie(projectProperties));
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * shiro 核心,安全管理器
     */
    @Bean
    public SecurityManager securityManager(org.apache.shiro.cache.CacheManager cacheManager, SysUserRealm sysUserRealm, SessionManager sessionManager, RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager);
        // 登录认证
        // securityManager.setAuthenticator();
        // 权限认证
        // securityManager.setAuthorizer();
        // 登录权限认证
        securityManager.setRealm(sysUserRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    /**
     * CORS 过滤器,加在 Shiro 过滤器之前
     */
    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("x-requested-with");
        config.addAllowedHeader("X-Custom-Header");
        config.addAllowedHeader(JwtConst.AUTHORIZATION);
        config.addAllowedMethod(HttpMethod.GET.name());
        config.addAllowedMethod(HttpMethod.POST.name());
        config.addAllowedMethod(HttpMethod.PUT.name());
        config.addAllowedMethod(HttpMethod.DELETE.name());
        config.addAllowedMethod(HttpMethod.OPTIONS.name());
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    /**
     * 实现 spring 管理 shiro bean 的生命周期
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 实现spring的自动代理
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        creator.setUsePrefix(true);
        return creator;
    }

    /**
     * 支持权限验证注解
     * <li>{@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}</li>
     * <li>{@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}</li>
     * <li>{@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}</li>
     * <li>{@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}</li>
     * <li>{@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}</li>
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
