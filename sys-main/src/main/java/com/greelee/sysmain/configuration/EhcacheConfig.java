package com.greelee.sysmain.configuration;

import com.greelee.sysmain.properties.ProjectProperties;
import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/27
 * @describe: ehcache 设置类
 */
@Configuration
@EnableCaching
public class EhcacheConfig {

    /**
     * 注册 ehcache
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(ProjectProperties projectProperties) {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(projectProperties.getEhcacheClasspath()));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    /**
     * 以 ehcache 作为 spring 的 cacheManager
     */
    @Bean("ehCacheCacheManager")
    @Primary
    public EhCacheCacheManager ehCacheCacheManager(CacheManager cacheManager) {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(cacheManager);
        return ehCacheCacheManager;
    }

}
