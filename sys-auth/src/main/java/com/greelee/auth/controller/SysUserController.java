package com.greelee.auth.controller;

import com.google.common.base.Objects;
import com.greelee.auth.model.SysUserDO;
import com.greelee.auth.service.SysUserService;
import com.greelee.log.constant.ActionLogEnum;
import com.greelee.log.dao.Action;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 系统用户接口层
 */
@Api(tags = "系统用户模块")
@RestController
@RequestMapping("/sys/sys-user")
public class SysUserController {

    private SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    private static final String LOGIN_DESC = "系统用户登录";

    @ApiOperation(value = LOGIN_DESC, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/login", method = POST)
    @Action(type = ActionLogEnum.LOGIN, desc = LOGIN_DESC)
    public String login(@RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = true) String password) throws ServiceException {
        ResResult result = sysUserService.login(username, password);
        return result.getStr(LOGIN_DESC);
    }

    private static final String LOGOUT_DESC = "退出登录";

    @ApiOperation(value = LOGOUT_DESC, notes = "")
    @RequestMapping(value = "/logout", method = POST)
    @Action(type = ActionLogEnum.LOGOUT, desc = LOGOUT_DESC)
    public String logout(HttpServletRequest request) throws ServiceException {
        ResResult result = sysUserService.logout(request);
        return result.getStr(LOGOUT_DESC);
    }

    private static final String LOGINED_DESC = "验证登录";

    @ApiOperation(value = LOGINED_DESC, notes = "")
    @RequestMapping(value = "/logined", method = POST)
    @Action(type = ActionLogEnum.GET, desc = LOGINED_DESC)
    public String logined(HttpServletRequest request) throws ServiceException {
        ResResult result = sysUserService.logined(request);
        return result.getStr(LOGINED_DESC);
    }

    private static final String GET_SELF_INFO_DESC = "获取个人基本信息";

    @ApiOperation(value = GET_SELF_INFO_DESC, notes = "")
    @RequestMapping(value = "/get_self_info", method = POST)
    @Action(type = ActionLogEnum.GET, desc = GET_SELF_INFO_DESC)
    public String getSelfInfo() throws ServiceException {
        ResResult result = sysUserService.getSelfInfo();
        return result.getStr(GET_SELF_INFO_DESC);
    }

    private static final String MODIFY_SELF_INFO_DESC = "修改个人基本信息(username,memo,sex)";

    @ApiOperation(value = MODIFY_SELF_INFO_DESC, notes = "")
    @ApiImplicitParam(name = "sysUserDO", value = "系统用户对象", required = true, dataTypeClass = SysUserDO.class)
    @RequestMapping(value = "/set_self_info", method = POST)
    @Action(type = ActionLogEnum.UPDATE, desc = MODIFY_SELF_INFO_DESC)
    public String modifySelfInfo(@ModelAttribute SysUserDO sysUserDO) throws ServiceException {
        ResResult result = sysUserService.setSelfInfo(sysUserDO);
        return result.getStr(MODIFY_SELF_INFO_DESC);
    }

    private static final String LIST_DESC = "获取用户列表";

    @RequiresPermissions("sys:user:get")
    @ApiOperation(value = LIST_DESC, notes = "")
    @ApiImplicitParam(name = "sysUserDO", value = "系统用户对象", required = true, dataTypeClass = SysUserDO.class)
    @RequestMapping(value = "/list", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = LIST_DESC)
    public String list(@ModelAttribute SysUserDO sysUserDO) throws ServiceException {
        ResResult result = sysUserService.listWithRole(sysUserDO);
        return result.getStr(LIST_DESC);
    }

    private static final String SAVE_DESC = "新增用户";

    @RequiresPermissions("sys:user:save")
    @ApiOperation(value = SAVE_DESC, notes = "")
    @ApiImplicitParam(name = "sysUserDO", value = "系统用户对象", required = true, dataTypeClass = SysUserDO.class)
    @RequestMapping(value = "/save", method = POST)
    @Action(type = ActionLogEnum.SAVE, desc = SAVE_DESC)
    public String save(@ModelAttribute SysUserDO sysUserDO) throws ServiceException {
        /**
         * 接口校验
         */
        if (!legalParam(sysUserDO)) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM).getStr(SAVE_DESC);
        }
        ResResult result = sysUserService.save(sysUserDO);
        return result.getStr(SAVE_DESC);
    }


    private static final String UPDATE_DESC = " 设置用户机构信息";

    @RequiresPermissions("sys:user:set-corp")
    @ApiOperation(value = UPDATE_DESC, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户自增 id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "corpId", value = "机构单位 ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "corpName", value = "机构单位名称", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/set_corp", method = POST)
    @Action(type = ActionLogEnum.UPDATE, desc = UPDATE_DESC)
    public String setCorp(@RequestParam(value = "id", required = true) Long id,
                          @RequestParam(value = "corpId", required = true) Long corpId,
                          @RequestParam(value = "corpName", required = true) String corpName) throws ServiceException {
        ResResult result = sysUserService.setCorp(id, corpId, corpName);
        return result.getStr(UPDATE_DESC);
    }

    private static final String DELETE_DESC = "删除用户";

    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = DELETE_DESC, notes = "")
    @ApiImplicitParam(name = "id", value = "系统用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/delete_by_id", method = POST)
    @Action(type = ActionLogEnum.DELETE, desc = DELETE_DESC)
    public String deleteById(@RequestParam(value = "id", required = true) Object id) throws ServiceException {
        ResResult result = sysUserService.deleteById(id);
        return result.getStr(DELETE_DESC);
    }

    private static final String DELETE_LIST_DESC = "批量删除用户";

    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = DELETE_LIST_DESC, notes = "")
    @ApiImplicitParam(name = "ids", value = "系统用户ID数组", required = true, dataType = "Long")
    @RequestMapping(value = "/delete_by_ids", method = POST)
    @Action(type = ActionLogEnum.DELETE, desc = DELETE_LIST_DESC)
    public String deleteByIds(@RequestParam(value = "ids", required = true) Object[] ids) throws ServiceException {
        ResResult result = sysUserService.deleteByIds(ids);
        return result.getStr(DELETE_LIST_DESC);
    }

    private static final String SET_ROLE_DESC = "设置用户角色";

    @RequiresPermissions("sys:user:set-role")
    @ApiOperation(value = SET_ROLE_DESC, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "roleIds", value = "系统角色ID数组", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/set_user_roles", method = POST)
    @Action(type = ActionLogEnum.UPDATE, desc = SET_ROLE_DESC)
    public String setUserRole(@RequestParam(value = "id", required = true) Long id,
                              @RequestParam(value = "roleIds", required = true) Long[] roleIds) throws ServiceException {
        ResResult result = sysUserService.setUserRole(id, roleIds);
        return result.getStr(SET_ROLE_DESC);
    }

    private static final String SET_USERS_ROLE_DESC = "设置多个用户的角色";

    @RequiresPermissions("sys:user:set-role")
    @ApiOperation(value = SET_USERS_ROLE_DESC, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "系统用户ID数组", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "roleId", value = "系统角色ID", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/set_users_role", method = POST)
    @Action(type = ActionLogEnum.UPDATE, desc = SET_USERS_ROLE_DESC)
    public String setUsersRole(@RequestParam(value = "ids", required = true) Long[] ids,
                               @RequestParam(value = "roleId", required = true) Long roleId) throws ServiceException {
        ResResult result = sysUserService.setUsersRole(ids, roleId);
        return result.getStr(SET_USERS_ROLE_DESC);
    }

    /**
     * 参数校验
     */
    private boolean legalParam(SysUserDO sysUserDO) {
        if (null == sysUserDO) {
            return false;
        }
        return StringUtils.isNotBlank(sysUserDO.getAdAccount())
                && StringUtil.isNotBlank(sysUserDO.getEmail())
                && StringUtil.isNotBlank(sysUserDO.getMobile())
                && (Objects.equal(sysUserDO.getStatus(), SysUserDO.STATUS_OK) || Objects.equal(sysUserDO.getStatus(), SysUserDO.STATUS_BLOCK))
                && StringUtil.isNotBlank(sysUserDO.getUsername())
                && (null != sysUserDO.getCorpId())
                && StringUtil.isNotBlank(sysUserDO.getCorpName());
    }
}
