package com.greelee.log.controller;

import com.greelee.log.constant.ActionLogEnum;
import com.greelee.log.dao.Action;
import com.greelee.log.model.ApiLogDO;
import com.greelee.log.service.ApiLogService;
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
@Api(tags = "Api 调用日志模块")
@RestController
@RequestMapping("/sys/api-log")
public class ApiLogController {

    private ApiLogService apiLogService;

    @Autowired
    public ApiLogController(ApiLogService apiLogService) {
        this.apiLogService = apiLogService;
    }


    private static final String LIST_DESC = "获取 Api 调用日志列表";

    @RequiresPermissions("sys:log:api-log:list")
    @ApiOperation(value = LIST_DESC)
    @ApiImplicitParam(name = "apiLogDO", value = "日志对象", required = true, dataTypeClass = ApiLogDO.class)
    @RequestMapping(value = "/list", method = POST)
    @Action(type = ActionLogEnum.LIST, desc = LIST_DESC)
    public String list(@ModelAttribute ApiLogDO apiLogDO) throws ServiceException {
        ResResult result = apiLogService.list(apiLogDO);
        return result.getStr(LIST_DESC);
    }

}
