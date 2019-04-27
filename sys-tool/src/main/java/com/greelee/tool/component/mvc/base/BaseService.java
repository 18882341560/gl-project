package com.greelee.tool.component.mvc.base;


import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResResult;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: service基本类
 */
public interface BaseService<T> {
    /**
     * <p>默认方法内代码段:</p>
     * <pre class="code">
     * PageHelper.startPage(query);
     * List&#060;T&#062; list = xxxDao.list(query);
     * if (ContainerUtil.isNotEmpty(list)) {
     *     ResList&#060;T&#062; resList = ResList.getList(list,((Page)list).getTotal());
     *     return ResResult.success(resList);
     * }
     * return ResResult.fail(ResCode.NOT_FOUND,"列表为空");
     * </pre>
     */
    default ResResult list(T query) throws ServiceException {
        return ResResult.fail();
    }

    default ResResult count(T query) throws ServiceException {
        return ResResult.fail();
    }

    default ResResult getById(Object id) throws ServiceException {
        return ResResult.fail();
    }

    default ResResult isExistById(Object id) throws ServiceException{
        return ResResult.fail();
    }
    /**
     * <p>默认方法内代码段:</p>
     * <pre class="code">
     *     LocalDateTime now = LocalDateTime.now();
     *     object.setCreateTime(now);
     *     object.setUpdateTime(now);
     *     return sysUserDao.save(object) > 0 ? ResResult.success() : ResResult.fail();
     * </pre>
     */
    default ResResult save(T object) throws ServiceException {
        return ResResult.fail();
    }
    /**
     * <p>默认方法内代码段:</p>
     * <pre class="code">
     *     object.setUpdateTime(LocalDateTime.now());
     *     return sysUserDao.update(object) > 0 ? ResResult.success(): ResResult.fail();
     * </pre>
     */
    default ResResult update(T object) throws ServiceException {
        return ResResult.fail();
    }
    /**
     * <p>默认方法内代码段:</p>
     * <pre class="code">
     *     return sysUserDao.deleteById(id) > 0 ? ResResult.success() : ResResult.fail();
     * </pre>
     */
    default ResResult deleteById(Object id) throws ServiceException {
        return ResResult.fail();
    }
    /**
     * <p>默认方法内代码段:</p>
     * <pre class="code">
     *     return sysUserDao.deleteByIds(ids) > 0 ? ResResult.success() : ResResult.fail();
     * </pre>
     */
    default ResResult deleteByIds(Object[] ids) throws ServiceException {
        return ResResult.fail();
    }

}
