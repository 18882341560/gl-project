package com.greelee.auth.model;

import com.greelee.tool.component.mvc.base.PageBean;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 系统资源
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysResourceDO extends PageBean implements Serializable {

    private static final long serialVersionUID = -8719993756547952364L;
    /**
     * 资源类型:菜单
     */
    public static final Integer TYPE_MENU = 1;
    /**
     * 资源类型:按钮
     */
    public static final Integer TYPE_BUTTON = 2;
    /**
     * 系统资源
     */
    public static final Integer SYS_RESOURCE = 1;
    /**
     * 自定义资源
     */
    public static final Integer CUSTOM_RESOURCE = 2;
    /**
     * 资源状态:正常
     */
    public static final Integer STATUS_OK = 1;
    /**
     * 资源状态:停用
     */
    public static final Integer STATUS_BLOCK = 2;
    /**
     * ID
     */
    private Long id;
    /**
     * 父级资源ID
     */
    private Long pid;
    /**
     * 父级资源标题
     */
    private String pTitle;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源标题
     */
    private String title;
    /**
     * 资源描述
     */
    private String description;
    /**
     * 资源访问路径
     */
    private String url;
    /**
     * 资源类型：1.菜单；2.按钮
     */
    private Integer type;
    /**
     * 系统资源类型：1.系统资源（无法删除）；2.自定义资源
     */
    private Integer sysType;
    /**
     * 资源排序
     */
    private Integer seq;
    /**
     * 资源状态：1.正常；2.停用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 资源权限
     */
    private String permission;
    /**
     * 资源图标
     */
    private String icon;

    private List<SysResourceDO> children;
}
