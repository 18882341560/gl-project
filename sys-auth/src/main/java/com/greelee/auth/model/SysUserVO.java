package com.greelee.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 系统用户视图对象
 */
@Getter
@Setter
@ToString
public class SysUserVO extends SysUserDO {

    private static final long serialVersionUID = 4751170260630274056L;
    /**
     * 角色名称列表
     */
    private List<String> roleTitleList;
}
