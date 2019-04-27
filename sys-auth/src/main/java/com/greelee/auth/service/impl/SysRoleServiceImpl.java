package com.greelee.auth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.greelee.auth.dao.SysRoleDao;
import com.greelee.auth.dao.SysRoleResourceMidDao;
import com.greelee.auth.dao.SysUserRoleMidDao;
import com.greelee.auth.model.SysRoleDO;
import com.greelee.auth.model.SysRoleResourceMidDO;
import com.greelee.auth.service.SysRoleService;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResList;
import com.greelee.tool.component.response.ResResult;
import com.greelee.tool.util.container.JudgeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    private SysRoleDao sysRoleDao;

    private SysUserRoleMidDao sysUserRoleMidDao;

    private SysRoleResourceMidDao sysRoleResourceMidDao;

    @Autowired
    public SysRoleServiceImpl(SysRoleDao sysRoleDao, SysUserRoleMidDao sysUserRoleMidDao, SysRoleResourceMidDao sysRoleResourceMidDao) {
        this.sysRoleDao = sysRoleDao;
        this.sysUserRoleMidDao = sysUserRoleMidDao;
        this.sysRoleResourceMidDao = sysRoleResourceMidDao;
    }

    @Override
    public ResResult list(SysRoleDO query) throws ServiceException {
        if (Objects.isNull(query.getStatus())) {
            query.setStatus(SysRoleDO.STATUS_OK);
        }
        PageHelper.startPage(query);
        List<SysRoleDO> list = sysRoleDao.list(query);
        if (JudgeUtil.isNotEmpty(list)) {
            ResList<SysRoleDO> resList = ResList.getList(list,((Page)list).getTotal());
            return ResResult.success(resList);
        }
        return ResResult.fail(ResCode.NOT_FOUND,"列表为空");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult save(SysRoleDO object) throws ServiceException {
        LocalDateTime now = LocalDateTime.now();
        object.setCreateTime(now);
        object.setUpdateTime(now);
        return sysRoleDao.save(object) > 0 ? ResResult.success() : ResResult.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult update(SysRoleDO object) throws ServiceException {
        object.setUpdateTime(LocalDateTime.now());
        return sysRoleDao.update(object) > 0 ? ResResult.success(): ResResult.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult deleteById(Object id) throws ServiceException {
        /**
         * 判断是否有用户属于此角色,有则不能删除
         */
        if (sysUserRoleMidDao.isExistByRoleId(id)) {
            return ResResult.fail(ResCode.FAILED, "有用户使用此角色");
        }
        boolean flag = sysRoleDao.deleteById(id) > 0;
        if (flag) {
            // 删除此角色 id 对应的全部用户 id 关联对象
            sysUserRoleMidDao.deleteByRoleId(id);
            // 删除此 id 对应的角色资源关联对象
            sysRoleResourceMidDao.deleteByRoleId(id);
        }
        return  flag ? ResResult.success() : ResResult.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult deleteByIds(Object[] ids) throws ServiceException {
        List<Object> deleted = Lists.newArrayList();
        List<Object> notDelete = Lists.newArrayList();
        /**
         *判断是否有用户属于此角色,有则不能删除,将未删除的与已删除的角色 id 列表返回给前端
         */
        for (Object id : ids) {
            if ( !sysUserRoleMidDao.isExistByRoleId(id) && sysRoleDao.deleteById(id) > 0) {
                // 不存在且已删除则将 id 添加到已删除列表
                deleted.add(id);
                // 删除此角色 id 对应的全部用户 id 关联对象
                sysUserRoleMidDao.deleteByRoleId(id);
                // 删除此 id 对应的角色资源关联对象
                sysRoleResourceMidDao.deleteByRoleId(id);
            } else {
                notDelete.add(id);
            }
        }
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("deleted", deleted);
        dataMap.put("notDelete", notDelete);
        if (notDelete.size() > 0) {
            return ResResult.response(ResCode.FAILED, "有用户使用此角色", dataMap);
        }
        return ResResult.success(dataMap);
    }

    /**
     * 设置角色权限
     *
     * @param id
     * @param resourceIds
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult setRoleResource(Long id, Long[] resourceIds) throws ServiceException {
        int count;
        /**
         * 判断要设置的资源 id 列表是否为空,为空则删除当前关联对象;不为空则遍历
         */
        if (JudgeUtil.isNotEmpty(resourceIds)) {
            // 设置角色资源关联对象
            count = setRoleResourceMidByRoleIdAndResourceIds(id, resourceIds);
        } else {
            // 删除此角色 id 对应的全部资源 id 关联对象
            count = sysRoleResourceMidDao.deleteByRoleId(id);
        }
        return ResResult.success(count);
    }

    /**
     * 获取此角色拥有的资源 id 列表
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    @Override
    public ResResult listResourceIdById(Long id) throws ServiceException {
        Set<Long> resourceIdSet = sysRoleResourceMidDao.listResourceIdByRoleId(id);
        return ResResult.success(resourceIdSet);
    }

    /**
     * 设置角色资源关联对象,通过角色 id 与资源 id 列表
     * @param id
     * @param resourceIds
     * @return
     */
    private int setRoleResourceMidByRoleIdAndResourceIds(Long id, Long[] resourceIds) {
        int count;
        /**
         * 获取角色对应的原资源 id 列表
         */
        Set<Long> oldResourceIdSet = sysRoleResourceMidDao.listResourceIdByRoleId(id);
        /**
         * 若为空则添加全部资源 id 关联,若不为空则判断
         */
        if (JudgeUtil.isNotEmpty(oldResourceIdSet)) {
            /**
             * 若要设置的资源 id 存在在当前资源 id 列表内,从当前资源 id 列表内删除(不更新),
             * 若要设置的资源 id 不存在在当前资源 id 列表内,新增;遍历完后,删除当前资源 id 列表的对应关联表数据
             */
            count = setRoleResourceMidByRoleIdAndResourceIdsAndOldResourceIdList(id, resourceIds, oldResourceIdSet);
        } else {
            // 直接保存
            count = saveRoleResourceMidByRoleIdAndResourceIds(id, resourceIds);
        }
        return count;
    }

    /**
     * 若要设置的资源 id 存在在当前资源 id 列表内,从当前资源 id 列表内删除(不更新),
     * 若要设置的资源 id 不存在在当前资源 id 列表内,新增;遍历完后,删除当前资源 id 列表的对应关联表数据
     */
    private int setRoleResourceMidByRoleIdAndResourceIdsAndOldResourceIdList(Long id, Long[] resourceIds, Set<Long> oldResourceIdSet) {
        int count = 0;
        for (Long resourceId : resourceIds) {
            if (oldResourceIdSet.contains(resourceId)) {
                oldResourceIdSet.remove(resourceId);
            } else {
                count += saveRoleResourceMidDO(id, resourceId);
            }
        }
        if (JudgeUtil.isNotEmpty(oldResourceIdSet)) {
            for (Long oldResourceId : oldResourceIdSet) {
                count += sysRoleResourceMidDao.deleteByRoleIdAndResourceId(id, oldResourceId);
            }
        }
        return count;
    }

    /**
     * 保存角色资源关联对象,通过角色 id 与资源 id 列表
     * @param id
     * @param resourceIds
     * @return
     */
    private int saveRoleResourceMidByRoleIdAndResourceIds(Long id, Long[] resourceIds) {
        int count = 0;
        for (Long resourceId : resourceIds) {
            count += saveRoleResourceMidDO(id, resourceId);
        }
        return count;
    }

    /**
     * 保存关联对象
     * @param roleId
     * @param resourceId
     * @return
     */
    private int saveRoleResourceMidDO(Long roleId, Long resourceId) {
        SysRoleResourceMidDO sysRoleResourceMidDO = SysRoleResourceMidDO.builder()
                .roleId(roleId)
                .resourceId(resourceId)
                .createTime(LocalDateTime.now())
                .build();
        return sysRoleResourceMidDao.save(sysRoleResourceMidDO);
    }
}
