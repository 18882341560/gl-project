package com.greelee.auth.controller;

import com.greelee.auth.model.SysOrganizationDO;
import com.greelee.auth.service.SysOrganizationService;
import com.greelee.log.constant.ActionLogEnum;
import com.greelee.log.dao.Action;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
 * @describe: 系统机构接口层
 */
@Api(tags = "系统机构模块")
@RestController
@RequestMapping("/sys/sys-organization")
public class SysOrganizationController {

    private SysOrganizationService sysOrganizationService;

    @Autowired
    public SysOrganizationController(SysOrganizationService sysOrganizationService) {
        this.sysOrganizationService = sysOrganizationService;
    }

    private static final String TREE_DESC = "获取机构树";

    @RequiresPermissions("sys:organization:get")
    @ApiOperation(value = TREE_DESC)
    @RequestMapping(value = "/tree", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = TREE_DESC)
    public String tree() throws ServiceException {
        ResResult result = sysOrganizationService.tree();
        return result.getStr(TREE_DESC);
    }

    private static final String LIST_DESC = "获取机构列表";

    @RequiresPermissions("sys:organization:get")
    @ApiOperation(value = LIST_DESC, notes = "")
    @ApiImplicitParam(name = "sysOrganizationDO", value = "系统机构对象", required = true, dataTypeClass = SysOrganizationDO.class)
    @RequestMapping(value = "/list", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = LIST_DESC)
    public String list(@ModelAttribute SysOrganizationDO sysOrganizationDO) throws ServiceException {
        ResResult result = sysOrganizationService.list(sysOrganizationDO);
        return result.getStr(LIST_DESC);
    }

    private static final String SAVE_DESC = "新增机构";

    @RequiresPermissions("sys:organization:save")
    @ApiOperation(value = SAVE_DESC, notes = "")
    @ApiImplicitParam(name = "sysOrganizationDO", value = "系统机构对象", required = true, dataTypeClass = SysOrganizationDO.class)
    @RequestMapping(value = "/save", method = POST)
    @Action(type = ActionLogEnum.SAVE, desc = SAVE_DESC)
    public String save(@ModelAttribute SysOrganizationDO sysOrganizationDO) throws ServiceException {
        /**
         * 接口校验
         */
        if (!legalParam(sysOrganizationDO)) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM).getStr(SAVE_DESC);
        }
        ResResult result = sysOrganizationService.save(sysOrganizationDO);
        return result.getStr(SAVE_DESC);
    }


    private static final String UPDATE_DESC = "修改机构";

    @RequiresPermissions("sys:organization:update")
    @ApiOperation(value = UPDATE_DESC, notes = "")
    @ApiImplicitParam(name = "sysOrganizationDO", value = "系统机构对象", required = true, dataTypeClass = SysOrganizationDO.class)
    @RequestMapping(value = "/update", method = POST)
    @Action(type = ActionLogEnum.UPDATE, desc = UPDATE_DESC)
    public String update(@ModelAttribute SysOrganizationDO sysOrganizationDO) throws ServiceException {
        /**
         * 接口校验
         */
        if (!legalParam(sysOrganizationDO)) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM).getStr(UPDATE_DESC);
        }
        ResResult result = sysOrganizationService.update(sysOrganizationDO);
        return result.getStr(UPDATE_DESC);
    }

    private static final String DELETE_DESC = "删除机构";

    @RequiresPermissions("sys:organization:delete")
    @ApiOperation(value = DELETE_DESC, notes = "")
    @ApiImplicitParam(name = "id", value = "系统用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/delete_by_id", method = POST)
    @Action(type = ActionLogEnum.DELETE, desc = DELETE_DESC)
    public String deleteById(@RequestParam(value = "id", required = true) Object id) throws ServiceException {
        ResResult result = sysOrganizationService.deleteById(id);
        return result.getStr(DELETE_DESC);
    }

    private static final String DELETE_LIST_DESC = "批量删除机构";

    @RequiresPermissions("sys:organization:delete")
    @ApiOperation(value = DELETE_LIST_DESC, notes = "")
    @ApiImplicitParam(name = "ids", value = "系统机构ID数组", required = true, dataType = "Long")
    @RequestMapping(value = "/delete_by_ids", method = POST)
    @Action(type = ActionLogEnum.DELETE, desc = DELETE_LIST_DESC)
    public String deleteByIds(@RequestParam(value = "ids", required = true) Object[] ids) throws ServiceException {
        ResResult result = sysOrganizationService.deleteByIds(ids);
        return result.getStr(DELETE_LIST_DESC);
    }

    /**
     * 参数校验
     */
    private boolean legalParam(SysOrganizationDO sysOrganizationDO) {
        if (null == sysOrganizationDO) {
            return false;
        }
        return (null != sysOrganizationDO.getCorpId())
                && StringUtil.isNotBlank(sysOrganizationDO.getCorpName())
                && (null != sysOrganizationDO.getParentCorpId());
    }
}
