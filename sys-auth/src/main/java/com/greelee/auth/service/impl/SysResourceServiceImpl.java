package com.greelee.auth.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.greelee.auth.component.AuthComponent;
import com.greelee.auth.dao.SysResourceDao;
import com.greelee.auth.dao.SysRoleResourceMidDao;
import com.greelee.auth.model.SysResourceDO;
import com.greelee.auth.realm.SysUserRealm;
import com.greelee.auth.service.SysResourceService;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResResult;
import com.greelee.tool.util.container.JudgeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Service
public class SysResourceServiceImpl implements SysResourceService {

    private SysResourceDao sysResourceDao;

    private SysRoleResourceMidDao sysRoleResourceMidDao;

    private AuthComponent authComponent;

    @Autowired
    public SysResourceServiceImpl(SysResourceDao sysResourceDao, SysRoleResourceMidDao sysRoleResourceMidDao, AuthComponent authComponent) {
        this.sysResourceDao = sysResourceDao;
        this.sysRoleResourceMidDao = sysRoleResourceMidDao;
        this.authComponent = authComponent;
    }

    /**
     * 根据资源 id 获取用户是否有此资源的权限
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    @Override
    public ResResult getPermission(Long id) throws ServiceException {
        Long userId = authComponent.getPrimaryPrincipal(Long.class);
        if (Objects.nonNull(userId)) {
            if (Objects.equals(userId, SysUserRealm.ADMIN_ID)) {
                return ResResult.success();
            }
            /**
             * 若查询此资源 id 无对应资源,则代表此资源不在权限管理范围内,可以获取
             */
            if (sysResourceDao.isExistById(id)) {
                if (sysResourceDao.isExistByUserIdAndResourceId(userId, id)) {
                    return ResResult.success();
                } else {
                    return ResResult.fail(ResCode.NO_AUTH);
                }
            } else {
                //此资源不在权限管理范围内,可以获取
                return ResResult.success();
            }
        } else {
            return ResResult.fail(ResCode.NOT_LOGIN);
        }
    }

    /**
     * 根据权限获取资源树
     *
     * @param type
     * @return
     * @throws ServiceException
     */
    @Override
    public ResResult tree(Integer type) throws ServiceException {
        /**
         * 通过签名中的 username 获取资源 id 列表
         */
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            PrincipalCollection principals = subject.getPrincipals();
            if (null != principals) {
                Object userId = principals.getPrimaryPrincipal();
                // 管理员
                List<SysResourceDO> resourceTree;
                if (Objects.equals(SysUserRealm.ADMIN_ID, userId)) {
                    resourceTree = getTree(0L, type);
                } else {
                    // 资源 id 列表
                    List<Long> permissionResourceIdList = sysRoleResourceMidDao.listResourceIdByUserId(userId);
                    resourceTree = getTreeByRole(0L, type, permissionResourceIdList);
                }
                if (JudgeUtil.isNotEmpty(resourceTree)) {
                    return ResResult.success(resourceTree);
                } else {
                    return ResResult.fail(ResCode.NOT_FOUND);
                }
            }
        }
        return ResResult.fail(ResCode.NO_AUTH);
    }

    /**
     * 获取全部资源树,仅用于设置角色资源
     *
     * @param type
     * @return
     * @throws ServiceException
     */
    @Override
    public ResResult allTree(Integer type) throws ServiceException {
        List<SysResourceDO> resourceTree = getTree(0L, type);
        if (JudgeUtil.isNotEmpty(resourceTree)) {
            return ResResult.success(resourceTree);
        } else {
            return ResResult.fail(ResCode.NOT_FOUND);
        }
    }

    /**
     * 获取菜单资源树
     *
     * @param pid  父级资源 id
     * @param type 资源类型
     * @return 菜单资源
     */
    private List<SysResourceDO> getTree(Long pid, Integer type) {
        List<SysResourceDO> resourceDOList = sysResourceDao.listByPidAndType(pid, type);
        if (JudgeUtil.isNotEmpty(resourceDOList)) {
            for (SysResourceDO resourceDO : resourceDOList) {
                Long resourceId = resourceDO.getId();
                // 防止死循环
                if (Objects.equals(pid, resourceId)) {
                    break;
                }
                List<SysResourceDO> children = getTree(resourceId, type);
                if (JudgeUtil.isNotEmpty(children)) {
                    resourceDO.setChildren(children);
                }
            }
        }
        return resourceDOList;
    }

    /**
     * 根据资源 id 获取菜单资源树
     *
     * @param pid                      父级资源 id
     * @param type                     资源类型
     * @param permissionResourceIdList 有资源权限的资源id
     * @return 菜单资源
     */
    private List<SysResourceDO> getTreeByRole(Long pid, Integer type, List<Long> permissionResourceIdList) {
        List<SysResourceDO> resultList = new ArrayList<>();
        List<SysResourceDO> resourceDOList = sysResourceDao.listByPidAndType(pid, type);
        if (JudgeUtil.isNotEmpty(resourceDOList)) {
            for (SysResourceDO resourceDO : resourceDOList) {
                Long resourceId = resourceDO.getId();
                // 防止死循环
                if (Objects.equals(pid, resourceId)) {
                    break;
                }
                // 只有拥有权限才能获取到
                if (permissionResourceIdList.contains(resourceId)) {
                    List<SysResourceDO> children = getTreeByRole(resourceId, type, permissionResourceIdList);
                    if (JudgeUtil.isNotEmpty(children)) {
                        resourceDO.setChildren(children);
                    }
                    resultList.add(resourceDO);
                    permissionResourceIdList.remove(resourceId);
                }
            }
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult save(SysResourceDO object) throws ServiceException {
        LocalDateTime now = LocalDateTime.now();
        object.setCreateTime(now);
        object.setUpdateTime(now);
        return sysResourceDao.save(object) > 0 ? ResResult.success() : ResResult.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult update(SysResourceDO object) throws ServiceException {
        if (Objects.equals(object.getId(), object.getPid())) {
            return ResResult.fail(ResCode.ILLEGAL_PARAM, "父级资源 ID 错误");
        }
        object.setUpdateTime(LocalDateTime.now());
        /**
         * 先获取旧数据,对比要联动更改的数据,若更改则更改全部联动数据
         */
        SysResourceDO oldDO = sysResourceDao.getById(object.getId());
        boolean flag = sysResourceDao.update(object) > 0;
        if (flag
                && Objects.nonNull(oldDO)
                && !Objects.equals(oldDO.getTitle(),object.getTitle())) {
            // 如果资源名称更改,则更改所有 pid 为此资源 id 的 pTitle 属性
            sysResourceDao.updatePTitleByPid(object.getTitle(),object.getId());
        }
        return flag ? ResResult.success(): ResResult.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult deleteById(Object id) throws ServiceException {
        /**
         * 判断此资源是否有子资源,有则不能删除
         */
        if (sysResourceDao.isExistByPidAndStatus(id,SysResourceDO.STATUS_OK)) {
            return ResResult.fail(ResCode.FAILED, "此资源存在子资源");
        }
        /**
         * 判断是否有角色使用此资源,有则不能删除
         */
        if (sysRoleResourceMidDao.isExistByResourceId(id)) {
            return ResResult.fail(ResCode.FAILED, "此资源已被角色使用");
        }
        boolean flag = sysResourceDao.deleteById(id) > 0;
        if (flag) {
            // 删除角色资源关联对象
            sysRoleResourceMidDao.deleteByResourceId(id);
        }
        return flag ? ResResult.success() : ResResult.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult deleteByIds(Object[] ids) throws ServiceException {
        List<Object> deleted = Lists.newArrayList();
        List<Object> notDelete = Lists.newArrayList();
        /**
         * 判断此资源是否有子资源,有则不能删除,判断是否有角色使用此资源,有则不能删除,将未删除的与已删除的资源 id 列表返回给前端
         */
        for (Object id : ids) {
            if (!sysResourceDao.isExistByPidAndStatus(id,SysResourceDO.STATUS_OK) && !sysRoleResourceMidDao.isExistByResourceId(id) && sysResourceDao.deleteById(id) > 0) {
                // 不存在且已删除则将 id 添加到已删除列表
                deleted.add(id);
                // 删除角色资源关联对象
                sysRoleResourceMidDao.deleteByResourceId(id);
            } else {
                notDelete.add(id);
            }
        }
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("deleted", deleted);
        dataMap.put("notDelete", notDelete);
        if (notDelete.size() > 0) {
            return ResResult.response(ResCode.FAILED, "所选资源有子资源", dataMap);
        }
        return ResResult.success(dataMap);
    }
}
