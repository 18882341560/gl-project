package com.greelee.auth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.greelee.auth.dao.SysOrganizationDao;
import com.greelee.auth.dao.SysUserDao;
import com.greelee.auth.model.SysOrganizationDO;
import com.greelee.auth.service.SysOrganizationService;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResList;
import com.greelee.tool.component.response.ResResult;
import com.greelee.tool.util.container.JudgeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author sqm
 * @version 1.0
 * @date 2019/02/21
 * @description: 类描述: 系统机构 Service
 **/
@Service
public class SysOrganizationServiceImpl implements SysOrganizationService {

    private SysOrganizationDao sysOrganizationDao;

    private SysUserDao sysUserDao;

    @Autowired
    public SysOrganizationServiceImpl(SysOrganizationDao sysOrganizationDao, SysUserDao sysUserDao) {
        this.sysOrganizationDao = sysOrganizationDao;
        this.sysUserDao = sysUserDao;
    }

    /**
     * 获取机构树
     *
     * @return
     * @throws ServiceException
     */
    @Override
    public ResResult tree() throws ServiceException {
        List<SysOrganizationDO> treeList = getTree(0);
        return null != treeList && treeList.size() > 0 ? ResResult.success(treeList) : ResResult.fail(ResCode.NOT_FOUND);
    }

    /**
     * 获取机构树列表(递归)
     * @param parentCorpId 父级单位 id
     * @return 机构树列表
     */
    private List<SysOrganizationDO> getTree(Integer parentCorpId) {
        List<SysOrganizationDO> sysOrganizationDOList = sysOrganizationDao.listByParentCorpId(parentCorpId);
        if (JudgeUtil.isNotEmpty(sysOrganizationDOList)) {
            for (SysOrganizationDO sysOrganizationDO : sysOrganizationDOList) {
                Integer corpId = sysOrganizationDO.getCorpId();
                // 防止死循环
                if (Objects.equals(corpId, parentCorpId)) {
                    break;
                }
                String corpName = sysOrganizationDO.getCorpName();
                List<SysOrganizationDO> children = getTree(corpId);
                if (JudgeUtil.isNotEmpty(children)) {
                    sysOrganizationDO.setChildren(children);
                    // 装入父级单位名称
                    children.forEach(child -> child.setParentCorpName(corpName));
                }
            }
        }
        return sysOrganizationDOList;
    }

    @Override
    public ResResult list(SysOrganizationDO query) throws ServiceException {
        PageHelper.startPage(query);
        List<SysOrganizationDO> list = sysOrganizationDao.list(query);
        if (JudgeUtil.isNotEmpty(list)) {
            ResList<SysOrganizationDO> resList = ResList.getList(list,((Page)list).getTotal());
            return ResResult.success(resList);
        }
        return ResResult.fail(ResCode.NOT_FOUND,"列表为空");
    }

    @Override
    public ResResult save(SysOrganizationDO object) throws ServiceException {
        if (sysOrganizationDao.isExistByCorpId(object.getCorpId())) {
            return ResResult.fail(ResCode.UNIQUE_PARAM_EXISTS, "单位 ID 已存在");
        }
        if (Objects.equals(object.getCorpId(),object.getParentCorpId())) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM, "机构 ID 错误");
        }
        LocalDateTime now = LocalDateTime.now();
        object.setCreateTime(now);
        object.setUpdateTime(now);
        return sysOrganizationDao.save(object) > 0 ? ResResult.success() : ResResult.fail();
    }


    @Override
    public ResResult update(SysOrganizationDO object) throws ServiceException {
        object.setUpdateTime(LocalDateTime.now());
        /**
         * 先获取旧数据,对比要联动更改的数据,若更改则更改全部联动数据
         */
        SysOrganizationDO oldDO = sysOrganizationDao.getById(object.getId());
        boolean flag = sysOrganizationDao.update(object) > 0;
        if (flag
                && Objects.nonNull(oldDO)
                && !Objects.equals(oldDO.getCorpName(),object.getCorpName())) {
            // 如果机构名称更改,则更改所有用户表中 corpId 为此机构 id 的 corpName 属性
            sysUserDao.updateCorpNameByCorpId(object.getCorpName(),object.getCorpId());
        }
        return flag ? ResResult.success(): ResResult.fail();
    }

    @Override
    public ResResult deleteById(Object id) throws ServiceException {
        /**
         * 判断此资源是否有子机构,有则不能删除
         */
        if (sysOrganizationDao.isExistByParentCorpId(id)) {
            return ResResult.fail(ResCode.FAILED, "此机构存在子机构");
        }
        /**
         * 判断是否有用户属于此机构,有则不能删除
         */
        if (sysUserDao.isExistByOrganizationId(id)) {
            return ResResult.fail(ResCode.FAILED, "有用户属于此机构");
        }
        return sysOrganizationDao.deleteById(id) > 0 ? ResResult.success() : ResResult.fail();
    }

    @Override
    public ResResult deleteByIds(Object[] ids) throws ServiceException {
        List<Object> deleted = Lists.newArrayList();
        List<Object> notDelete = Lists.newArrayList();
        /**
         * 判断此资源是否有子机构,有则不能删除;判断是否有用户属于此机构,有则不能删除,将未删除的与已删除的机构 id 列表返回给前端
         */
        for (Object id : ids) {
            if (!sysOrganizationDao.isExistByParentCorpId(id) && !sysUserDao.isExistByOrganizationId(id) && sysOrganizationDao.deleteById(id) > 0) {
                // 不存在且已删除则将 id 添加到已删除列表
                deleted.add(id);
            } else {
                notDelete.add(id);
            }
        }
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("deleted", deleted);
        dataMap.put("notDelete", notDelete);
        if (notDelete.size() > 0) {
            return ResResult.response(ResCode.FAILED, "机构有子机构或有用户数据此机构", dataMap);
        }
        return ResResult.success(dataMap);
    }
}
