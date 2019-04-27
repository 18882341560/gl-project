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
 * @describe: 访问日志数据对象类(Builder设计模式)
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionLogDO extends PageBean implements Serializable {

    private static final long serialVersionUID = -2525949328228287117L;
    /**
     * id(主键)
     */
    private Integer id;
    /**
     * 类型(索引)
     */
    private String type;
    /**
     * 身份(如用户名,手机号,用户 id 等)(索引)
     */
    private String identify;
    /**
     * 接口描述
     */
    private String description;
    /**
     * 接口地址(索引)
     */
    private String uri;
    /**
     * 请求参数 json 字符串
     */
    private String param;
    /**
     * 接口处理时长(毫秒)
     */
    private Long duration;
    /**
     * 访问 ip(索引)
     */
    private String ip;
    /**
     * 访问时间
     */
    private LocalDateTime visitTime;
    /**
     * 数据行创建时间
     */
    private LocalDateTime createTime;
}
