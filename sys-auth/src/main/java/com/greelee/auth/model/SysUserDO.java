package com.greelee.auth.model;

import com.greelee.tool.component.mvc.base.PageBean;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 系统用户表
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDO extends PageBean implements Serializable {
    private static final long serialVersionUID = -6731216054651148787L;
    /**
     * 状态:正常
     */
    public static final Integer STATUS_OK = 1;
    /**
     * 状态:停用
     */
    public static final Integer STATUS_BLOCK = 2;
    /**
     * ID
     */
    private Long id;
    /**
     * AD域登录名
     */
    private String adAccount;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态：1.正常；2.禁用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 单位ID
     */
    private Long corpId;
    /**
     * 职务
     */
    private String headship;
    /**
     * 是否启用：1.启用；0.停用
     */
    private Boolean enabled;
    /**
     * 备注
     */
    private String memo;
    /**
     * 是否是超级管理员:1.是；2.否
     */
    private Boolean isAdmin;
    /**
     * 级别（处级、副处级、科级、副科级、股级、一般员工）
     */
    private String level;
    /**
     * 参公时间
     */
    private String workTime;
    /**
     * 性别
     */
    private String sex;

    /**
     * 单位名称
     */
    private String corpName;
}
