package com.greelee.log.model;


import com.greelee.tool.component.mvc.base.PageBean;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 异常日志
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionLogDO extends PageBean implements Serializable {

    private static final long serialVersionUID = 6736232635537737172L;
    /**
     * id
     */
    private Integer id;
    /**
     * 身份(如用户名,手机号,用户 id 等)(索引)
     */
    private String identify;
    /**
     * 异常请求 ip
     */
    private String ip;
    /**
     * 异常请求接口
     */
    private String uri;
    /**
     * 异常请求参数 json 字符串
     */
    private String param;
    /**
     * 异常类名
     */
    private String className;
    /**
     * 异常信息
     */
    private String message;
    /**
     * 堆栈追溯信息
     */
    private String stackTrace;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
