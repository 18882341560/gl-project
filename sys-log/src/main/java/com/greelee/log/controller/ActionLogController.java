package com.greelee.log.controller;

import com.greelee.log.constant.ActionLogEnum;
import com.greelee.log.dao.Action;
import com.greelee.log.model.ActionLogDO;
import com.greelee.log.service.ActionLogService;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Api(tags = "操作日志模块")
@RestController
@RequestMapping("/sys/action-log")
public class ActionLogController {

    private ActionLogService actionLogService;

    @Autowired
    public ActionLogController(ActionLogService actionLogService) {
        this.actionLogService = actionLogService;
    }


    private static final String LIST_DESC = "获取操作日志列表";

    @RequiresPermissions("sys:log:action-log:list")
    @ApiOperation(value = LIST_DESC)
    @ApiImplicitParam(name = "actionLogDO", value = "日志对象", required = true, dataTypeClass = ActionLogDO.class)
    @RequestMapping(value = "/list", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = LIST_DESC)
    public String list(@ModelAttribute ActionLogDO actionLogDO) throws ServiceException {
        ResResult result = actionLogService.list(actionLogDO);
        return result.getStr(LIST_DESC);
    }

}
