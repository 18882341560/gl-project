package com.greelee.auth.controller;

import com.google.common.base.Objects;
import com.greelee.auth.model.SysRoleDO;
import com.greelee.auth.service.SysRoleService;
import com.greelee.log.constant.ActionLogEnum;
import com.greelee.log.dao.Action;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResResult;
import io.swagger.annotations.*;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 系统角色接口层
 */
@Api(tags = "系统角色模块")
@RestController
@RequestMapping("/sys/sys-role")
public class SysRoleController {

    private SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }


    private static final String LIST_DESC = "获取角色列表";

    @RequiresPermissions("sys:role:get")
    @ApiOperation(value = LIST_DESC, notes = "")
    @ApiImplicitParam(name = "sysRoleDO", value = "系统角色对象", required = true, dataTypeClass = SysRoleDO.class)
    @RequestMapping(value = "/list", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = LIST_DESC)
    public String list(@ModelAttribute SysRoleDO sysRoleDO) throws ServiceException {
        ResResult result = sysRoleService.list(sysRoleDO);
        return result.getStr(LIST_DESC);
    }

    private static final String SAVE_DESC = "新增角色";

    @RequiresPermissions("sys:role:save")
    @ApiOperation(value = SAVE_DESC, notes = "")
    @ApiImplicitParam(name = "sysRoleDO", value = "系统角色对象", required = true, dataTypeClass = SysRoleDO.class)
    @RequestMapping(value = "/save", method = POST)
    @Action(type = ActionLogEnum.SAVE, desc = SAVE_DESC)
    public String save(@ModelAttribute SysRoleDO sysRoleDO) throws ServiceException {
        /**
         * 接口校验
         */
        if (!legalParam(sysRoleDO)) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM).getStr(SAVE_DESC);
        }
        ResResult result = sysRoleService.save(sysRoleDO);
        return result.getStr(SAVE_DESC);
    }

    private static final String UPDATE_DESC = "修改角色";

    @RequiresPermissions("sys:role:update")
    @ApiOperation(value = UPDATE_DESC, notes = "")
    @ApiImplicitParam(name = "sysRoleDO", value = "系统角色对象", required = true, dataTypeClass = SysRoleDO.class)
    @RequestMapping(value = "/update", method = POST)
    @Action(type = ActionLogEnum.UPDATE, desc = UPDATE_DESC)
    public String update(@ModelAttribute SysRoleDO sysRoleDO) throws ServiceException {
        /**
         * 接口校验
         */
        if (!legalParam(sysRoleDO)) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM).getStr(UPDATE_DESC);
        }
        ResResult result = sysRoleService.update(sysRoleDO);
        return result.getStr(UPDATE_DESC);
    }

    private static final String DELETE_DESC = "删除角色";

    @RequiresPermissions("sys:role:delete")
    @ApiOperation(value = DELETE_DESC, notes = "")
    @ApiImplicitParam(name = "id", value = "系统角色ID", required = true, dataType = "Long")
    @RequestMapping(value = "/delete_by_id", method = POST)
    @Action(type = ActionLogEnum.DELETE, desc = DELETE_DESC)
    public String deleteById(@RequestParam(value = "id", required = true) Object id) throws ServiceException {
        ResResult result = sysRoleService.deleteById(id);
        return result.getStr(DELETE_DESC);
    }

    private static final String DELETE_LIST_DESC = "批量删除角色";

    @RequiresPermissions("sys:role:delete")
    @ApiOperation(value = DELETE_LIST_DESC, notes = "")
    @ApiImplicitParam(name = "ids", value = "系统角色ID数组", required = true, dataType = "Long")
    @RequestMapping(value = "/delete_by_ids", method = POST)
    @Action(type = ActionLogEnum.DELETE, desc = DELETE_LIST_DESC)
    public String deleteByIds(@RequestParam(value = "ids", required = true) Object[] ids) throws ServiceException {
        ResResult result = sysRoleService.deleteByIds(ids);
        return result.getStr(DELETE_LIST_DESC);
    }

    private static final String SET_RESOURCE_DESC = "设置角色资源";

    @RequiresPermissions("sys:role:set-resource")
    @ApiOperation(value = SET_RESOURCE_DESC, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统角色ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "resourceIds", value = "系统资源ID数组", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/set_role_resource", method = POST)
    @Action(type = ActionLogEnum.UPDATE, desc = SET_RESOURCE_DESC)
    public String setRoleResource(@RequestParam(value = "id", required = true) Long id,
                                  @RequestParam(value = "resourceIds", required = true) Long[] resourceIds) throws ServiceException {
        ResResult result = sysRoleService.setRoleResource(id, resourceIds);
        return result.getStr(SET_RESOURCE_DESC);
    }

    private static final String LIST_ID_DESC = "根据角色ID获取资源ID列表";

    @RequiresPermissions("sys:role:set-resource")
    @ApiOperation(value = LIST_ID_DESC, notes = "根据角色ID获取资源ID列表,渲染树结构时使用")
    @RequestMapping(value = "/list_resource_id_by_id", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = LIST_ID_DESC)
    public String listResourceIdById(
            @ApiParam(name = "id", value = "系统角色 ID", required = true)
            @RequestParam(value = "id", required = true) Long id) throws ServiceException {
        ResResult result = sysRoleService.listResourceIdById(id);
        return result.getStr(LIST_ID_DESC);
    }

    /**
     * 参数校验
     */
    private boolean legalParam(SysRoleDO sysRoleDO) {
        if (null == sysRoleDO) {
            return false;
        }
        return StringUtils.isNotBlank(sysRoleDO.getName())
                && StringUtil.isNotBlank(sysRoleDO.getTitle())
                && (Objects.equal(sysRoleDO.getType(), SysRoleDO.TYPE_SYS) || Objects.equal(sysRoleDO.getType(), SysRoleDO.TYPE_CUSTOM))
                && (Objects.equal(sysRoleDO.getStatus(), SysRoleDO.STATUS_OK) || Objects.equal(sysRoleDO.getStatus(), SysRoleDO.STATUS_BLOCK));
    }
}
