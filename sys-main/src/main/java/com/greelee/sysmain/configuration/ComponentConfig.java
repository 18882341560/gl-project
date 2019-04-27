package com.greelee.sysmain.configuration;

import com.greelee.tool.component.converter.LocalDateTimeFormatter;
import com.greelee.tool.util.secret.JwtConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sqm
 * @version 1.0
 * @date 2019/02/13
 * @description: 类描述: 组件配置,根据需要注册相应的组件
 **/
@Configuration
public class ComponentConfig implements WebMvcConfigurer {


    /**
     * String → LocalDateTime  符合 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
     * Formatter 与 Converter 可直接注册, 注册方式: ConversionService
     */
    @Bean
    public LocalDateTimeFormatter localDateTimeFormatter() {
        return new LocalDateTimeFormatter();
    }

    /**
     * activiti 任务执行器设置Primary
     */
    @Primary
    @Bean
    public TaskExecutor primaryTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    /**
     * 跨域设置(配置 shiro 后需单独配置跨域过滤器{@link ShiroConfig#corsFilter()})
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(CorsConfiguration.ALL)
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.OPTIONS.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
                .allowedHeaders("Content-Type", "x-requested-with", "X-Custom-Header", JwtConst.AUTHORIZATION)
                // 响应头表示是否可以将对请求的响应暴露给页面。返回true则可以，其他值均不可以。
                // Credentials可以是 cookies, authorization headers 或 TLS client certificates。
                .allowCredentials(true)
                // 最大缓存时长 默认1800-30分钟
                .maxAge(3600L);
    }
}
