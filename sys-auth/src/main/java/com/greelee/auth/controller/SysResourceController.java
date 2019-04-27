package com.greelee.auth.controller;

import com.google.common.base.Objects;
import com.greelee.auth.model.SysResourceDO;
import com.greelee.auth.service.SysResourceService;
import com.greelee.log.constant.ActionLogEnum;
import com.greelee.log.dao.Action;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jodd.util.StringUtil;
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
 * @describe: 系统资源接口层
 */
@Api(tags = "系统资源模块")
@RestController
@RequestMapping("/sys/sys-resource")
public class SysResourceController {

    private SysResourceService sysResourceService;

    @Autowired
    public SysResourceController(SysResourceService sysResourceService) {
        this.sysResourceService = sysResourceService;
    }

    private static final String GET_PERMISSION_DESC = "获取此用户的资源权限";

    @ApiOperation(value = GET_PERMISSION_DESC)
    @RequestMapping(value = "/get_permission", method = POST)
    @Action(type = ActionLogEnum.GET, desc = GET_PERMISSION_DESC)
    public String getPermission(@ApiParam(name = "id", value = "资源 id", required = true)
                                @RequestParam(value = "id", required = true) Long id) throws ServiceException {
        ResResult result = sysResourceService.getPermission(id);
        return result.getStr(GET_PERMISSION_DESC);
    }

    private static final String TREE_DESC = "获取资源树";

    @RequiresPermissions("sys:resource:get")
    @ApiOperation(value = TREE_DESC, notes = "根据登录用户所属角色获取资源树")
    @ApiImplicitParam(name = "type", value = "资源类型:0.全部;1.菜单;2.按钮", allowableValues = "0,1,2", required = true, dataType = "String")
    @RequestMapping(value = "/tree", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = TREE_DESC)
    public String tree(@RequestParam(value = "type", required = true) Integer type) throws ServiceException {
        ResResult result = sysResourceService.tree(type);
        return result.getStr(TREE_DESC);
    }


    private static final String ALL_TREE_DESC = "获取全部资源树";

    @RequiresPermissions("sys:resource:get-all")
    @ApiOperation(value = ALL_TREE_DESC, notes = "获取全部资源树,仅用于设置角色资源")
    @ApiImplicitParam(name = "type", value = "资源类型:0.全部;1.菜单;2.按钮", allowableValues = "0,1,2", required = true, dataType = "String")
    @RequestMapping(value = "/all_tree", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = ALL_TREE_DESC)
    public String allTree(@RequestParam(value = "type", required = true) Integer type) throws ServiceException {
        ResResult result = sysResourceService.allTree(type);
        return result.getStr(ALL_TREE_DESC);
    }

    private static final String SAVE_DESC = "新增资源";

    @RequiresPermissions("sys:resource:save")
    @ApiOperation(value = SAVE_DESC)
    @ApiImplicitParam(name = "sysResourceDO", value = "系统资源对象", required = true, dataTypeClass = SysResourceDO.class)
    @RequestMapping(value = "/save", method = POST)
    @Action(type = ActionLogEnum.SAVE, desc = SAVE_DESC)
    public String save(@ModelAttribute SysResourceDO sysResourceDO) throws ServiceException {
        if (!legalParam(sysResourceDO)) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM).getStr(SAVE_DESC);
        }
        ResResult result = sysResourceService.save(sysResourceDO);
        return result.getStr(SAVE_DESC);
    }

    private static final String UPDATE_DESC = "修改资源";

    @RequiresPermissions("sys:resource:update")
    @ApiOperation(value = UPDATE_DESC)
    @ApiImplicitParam(name = "sysResourceDO", value = "系统资源对象", required = true, dataTypeClass = SysResourceDO.class)
    @RequestMapping(value = "/update", method = POST)
    @Action(type = ActionLogEnum.UPDATE, desc = UPDATE_DESC)
    public String update(@ModelAttribute SysResourceDO sysResourceDO) throws ServiceException {
        if (!legalParam(sysResourceDO)) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM).getStr(UPDATE_DESC);
        }
        ResResult result = sysResourceService.update(sysResourceDO);
        return result.getStr(UPDATE_DESC);
    }

    private static final String DELETE_DESC = "删除资源";

    @RequiresPermissions("sys:resource:delete")
    @ApiOperation(value = DELETE_DESC)
    @ApiImplicitParam(name = "id", value = "系统资源ID", required = true, dataType = "Long")
    @RequestMapping(value = "/delete_by_id", method = POST)
    @Action(type = ActionLogEnum.DELETE, desc = DELETE_DESC)
    public String deleteById(@RequestParam(value = "id", required = true) Object id) throws ServiceException {
        ResResult result = sysResourceService.deleteById(id);
        return result.getStr(DELETE_DESC);
    }

    private static final String DELETE_LIST_DESC = "批量删除资源";

    @RequiresPermissions("sys:resource:delete")
    @ApiOperation(value = DELETE_LIST_DESC)
    @ApiImplicitParam(name = "ids", value = "系统资源ID列表", required = true, dataType = "Long")
    @RequestMapping(value = "/delete_by_ids", method = POST)
    @Action(type = ActionLogEnum.DELETE, desc = DELETE_LIST_DESC)
    public String deleteByIds(@RequestParam(value = "ids", required = true) Object[] ids) throws ServiceException {
        ResResult result = sysResourceService.deleteByIds(ids);
        return result.getStr(DELETE_LIST_DESC);
    }

    /**
     * 参数校验
     */
    private boolean legalParam(SysResourceDO sysResourceDO) {
        if (null == sysResourceDO) {
            return false;
        }
        return (null != sysResourceDO.getPid())
                && StringUtil.isNotBlank(sysResourceDO.getUrl())
                && (Objects.equal(sysResourceDO.getType(), SysResourceDO.TYPE_MENU) || Objects.equal(sysResourceDO.getType(), SysResourceDO.TYPE_BUTTON))
                && (Objects.equal(sysResourceDO.getStatus(), SysResourceDO.STATUS_OK) || Objects.equal(sysResourceDO.getStatus(), SysResourceDO.STATUS_BLOCK))
                && (Objects.equal(sysResourceDO.getSysType(), SysResourceDO.SYS_RESOURCE) || Objects.equal(sysResourceDO.getSysType(), SysResourceDO.CUSTOM_RESOURCE))
                && StringUtil.isNotBlank(sysResourceDO.getPermission());
    }
}
