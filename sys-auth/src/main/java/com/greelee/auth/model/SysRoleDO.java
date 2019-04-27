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
 * @describe: 系统角色对象
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDO extends PageBean implements Serializable {
    private static final long serialVersionUID = -7693682376474166779L;
    /**
     * 系统角色
     */
    public static final Integer TYPE_SYS = 1;
    /**
     * 自定义角色
     */
    public static final Integer TYPE_CUSTOM = 2;
    /**
     * 角色状态:正常
     */
    public static final Integer STATUS_OK = 1;
    /**
     * 角色状态:禁用
     */
    public static final Integer STATUS_BLOCK = 2;
    /**
     * ID
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色标题
     */
    private String title;
    /**
     * 角色类型：1.系统角色（无法删除）；2.用户自定义角色
     */
    private Integer type;
    /**
     * 角色状态：1.正常；2.停用
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
}
