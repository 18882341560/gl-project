package com.greelee.sysmain.configuration;

import com.greelee.sysmain.properties.ProjectProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/27
 * @describe:  swagger2 配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer {

    private ProjectProperties projectProperties;

    @Autowired
    public Swagger2Config(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    /**
     * 将 swagger-ui 的页面指向具体位置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 创建API应用
     * api() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     */
    @Bean(name = "projectApis")
    public Docket projectApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("project_apis").pathMapping("/")
                // apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
                .apiInfo(apiInfo())
                // select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                .select()
                // .apis(Predicates.or(selector1,selector2))
                .apis(RequestHandlerSelectors.basePackage(projectProperties.getProjectPackage()))
                // .apis(basePackage("com.example.demo.controller"+ SEPARATOR +"com.example.demo.test"))
                // .apis(RequestHandlerSelectors.basePackage("net.fangfa.fangfalogin.ad"))
                // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // .apis(RequestHandlerSelectors.withClassAnnotation(ApiModel.class))
                // 除了被@ApiIgnore指定的请求
                .paths(PathSelectors.any())
                .build()
                .enable(projectProperties.getEnableSwagger());
    }

    @Bean(name = "sysAuthApis")
    public Docket sysLoginApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("sys_auth_apis").pathMapping("/")
                // apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
                .apiInfo(apiInfo())
                // select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.greelee.auth"))
                // 除了被@ApiIgnore指定的请求
                .paths(PathSelectors.any())
                .build()
                .enable(projectProperties.getEnableSwagger());
    }

    @Bean(name = "logApis")
    public Docket logApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("log_apis").pathMapping("/")
                // apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
                .apiInfo(apiInfo())
                // select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.greelee.log"))
                // 除了被@ApiIgnore指定的请求
                .paths(PathSelectors.any())
                .build()
                .enable(projectProperties.getEnableSwagger());
    }

    @Bean(name = "messageApis")
    public Docket messageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("message_apis").pathMapping("/")
                // apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
                .apiInfo(apiInfo())
                // select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.greelee.message"))
                // 除了被@ApiIgnore指定的请求
                .paths(PathSelectors.any())
                .build()
                .enable(projectProperties.getEnableSwagger());
    }

    @Bean(name = "uploadApis")
    public Docket uploadApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("upload_apis").pathMapping("/")
                // apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
                .apiInfo(apiInfo())
                // select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.greelee.upload"))
                // 除了被@ApiIgnore指定的请求
                .paths(PathSelectors.any())
                .build()
                .enable(projectProperties.getEnableSwagger());
    }


    @Bean(name = "workflowApis")
    public Docket workflowApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("workflow_apis").pathMapping("/")
                // apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
                .apiInfo(apiInfo())
                // select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.greelee.workflow"))
                // 除了被@ApiIgnore指定的请求
                .paths(PathSelectors.any())
                .build()
                .enable(projectProperties.getEnableSwagger());
    }

    /**
     * api 的基本信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(projectProperties.getName())
                .description(projectProperties.getDescription())
                .termsOfServiceUrl(projectProperties.getTermsOfServiceUrl())
                .contact(new Contact(projectProperties.getContactName(), projectProperties.getContactUrl(), projectProperties.getContactEmail()))
                .version(projectProperties.getVersion())
                .build();

    }
}
