package com.greelee.auth.model;

import lombok.*;

import java.time.LocalDateTime;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 用户-角色中间对象
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRoleMidDO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
