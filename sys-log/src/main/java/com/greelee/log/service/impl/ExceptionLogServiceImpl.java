package com.greelee.log.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.greelee.log.dao.ExceptionLogDao;
import com.greelee.log.model.ExceptionLogDO;
import com.greelee.log.service.ExceptionLogService;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResList;
import com.greelee.tool.component.response.ResResult;
import com.greelee.tool.util.container.JudgeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Service
public class ExceptionLogServiceImpl implements ExceptionLogService {
    private ExceptionLogDao exceptionLogDao;

    @Autowired
    public ExceptionLogServiceImpl(ExceptionLogDao exceptionLogDao) {
        this.exceptionLogDao = exceptionLogDao;
    }

    /**
     * <p>默认方法内代码段:</p>
     * <pre class="code">
     * PageHelper.startPage(query);
     * List&#060;T&#062; list = xxxDao.list(query);
     * if (ContainerUtil.isNotEmpty(list)) {
     *     ResList&#060;T&#062; resList = new ResList&#060;&#062;(list,((Page)list).getTotal());
     *     return ResResult.success(resList);
     * }
     * return ResResult.fail(ResCode.NOT_FOUND,"列表为空");
     * </pre>
     */
    @Override
    public ResResult list(ExceptionLogDO query) throws ServiceException {
        PageHelper.startPage(query);
        List<ExceptionLogDO> list = exceptionLogDao.list(query);
        if (JudgeUtil.isNotEmpty(list)) {
            ResList<ExceptionLogDO> resList = ResList.getInstance(list, ((Page) list).getTotal());
            return ResResult.success(resList);
        }
        return ResResult.fail(ResCode.NOT_FOUND, "列表为空");
    }
}
