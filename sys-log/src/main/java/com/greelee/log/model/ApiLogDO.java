package com.greelee.log.model;


import com.greelee.tool.component.mvc.base.PageBean;
import com.greelee.tool.util.http.HttpUtil;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 使用{@link HttpUtil#sendGet(String)}{@link HttpUtil#sendPost(String, List)}向第三方 api 调用时需主动记录
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiLogDO extends PageBean implements Serializable {

    private static final long serialVersionUID = 381084494345225941L;
    /**
     * ID
     */
    private Integer id;
    /**
     * API地址
     */
    private String url;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 请求参数
     */
    private String param;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 响应内容
     */
    private String response;
    /**
     * API描述
     */
    private String description;
}
