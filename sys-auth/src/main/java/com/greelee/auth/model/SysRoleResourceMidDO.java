package com.greelee.auth.model;

import lombok.*;

import java.time.LocalDateTime;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 角色-资源中间对象
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleResourceMidDO {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 资源ID
     */
    private Long resourceId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
