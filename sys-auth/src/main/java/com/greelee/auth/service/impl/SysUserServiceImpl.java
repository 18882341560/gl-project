package com.greelee.auth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.greelee.auth.component.AuthComponent;
import com.greelee.auth.dao.SysRoleDao;
import com.greelee.auth.dao.SysUserDao;
import com.greelee.auth.dao.SysUserRoleMidDao;
import com.greelee.auth.model.SysUserDO;
import com.greelee.auth.model.SysUserRoleMidDO;
import com.greelee.auth.model.SysUserVO;
import com.greelee.auth.service.SysUserService;
import com.greelee.tool.component.exception.ServiceException;
import com.greelee.tool.component.response.ResCode;
import com.greelee.tool.component.response.ResConst;
import com.greelee.tool.component.response.ResList;
import com.greelee.tool.component.response.ResResult;
import com.greelee.tool.util.container.JudgeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
public class SysUserServiceImpl implements SysUserService {




    private SysUserDao sysUserDao;

    private SysUserRoleMidDao sysUserRoleMidDao;

    private SysRoleDao sysRoleDao;

    private AuthComponent authComponent;


    @Autowired
    public SysUserServiceImpl(SysUserDao sysUserDao, SysUserRoleMidDao sysUserRoleMidDao, SysRoleDao sysRoleDao, AuthComponent authComponent) {
        this.sysUserDao = sysUserDao;
        this.sysUserRoleMidDao = sysUserRoleMidDao;
        this.sysRoleDao = sysRoleDao;
        this.authComponent = authComponent;
    }

    /**
     * 使用用户名,邮箱,手机号,ad 域账号登录
     *
     * @param username
     * @param password
     * @return
     * @throws ServiceException
     */
    @Override
    public ResResult login(String username, String password) throws ServiceException {
        /**
         * 管理员登录
         */
        String token = authComponent.loginReturnToken(username, password);
        if (null != token) {
            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put(ResConst.KEY_TOKEN, token);
            return ResResult.success(dataMap);
        } else {
            return ResResult.fail(ResCode.INCORRECT_LOGIN_INFO);
        }
    }

    /**
     * 登出
     *
     * @param request
     * @return
     * @throws ServiceException
     */
    @Override
    public ResResult logout(HttpServletRequest request) throws ServiceException {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResResult.success();
    }

    /**
     * 登录状态
     *
     * @param request
     * @return
     */
    @Override
    public ResResult logined(HttpServletRequest request) {
        return ResResult.success();
    }

    @Override
    public ResResult list(SysUserDO query) throws ServiceException {
        if (Objects.isNull(query.getStatus())) {
            query.setStatus(SysUserDO.STATUS_OK);
        }
        PageHelper.startPage(query);
        List<SysUserDO> list = sysUserDao.list(query);
        if (JudgeUtil.isNotEmpty(list)) {
            ResList<SysUserDO> resList = ResList.getList(list, ((Page) list).getTotal());
            return ResResult.success(resList);
        }
        return ResResult.fail(ResCode.NOT_FOUND, "列表为空");
    }

    /**
     * 获取用户列表,包含角色信息
     *
     * @param query
     * @return {@link SysUserVO}
     * @throws ServiceException
     */
    @Override
    public ResResult listWithRole(SysUserDO query) throws ServiceException {
        if (Objects.isNull(query.getStatus())) {
            query.setStatus(SysUserDO.STATUS_OK);
        }
        PageHelper.startPage(query);
        List<SysUserVO> list = sysUserDao.listVO(query);
        if (JudgeUtil.isNotEmpty(list)) {
            /**
             * 列表中加入角色id 与名称
             */
            ResList<SysUserVO> resList = ResList.getList(list, ((Page) list).getTotal());
            return ResResult.success(resList);
        }
        return ResResult.fail(ResCode.NOT_FOUND, "列表为空");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult save(SysUserDO object) throws ServiceException {
        /**
         * 判断唯一 ID 是否存在
         */
        if (sysUserDao.isExistByAdAccount(object.getAdAccount())) {
            return ResResult.fail(ResCode.UNIQUE_PARAM_EXISTS, "登录名已存在");
        }
        if (sysUserDao.isExistByEmail(object.getEmail())) {
            return ResResult.fail(ResCode.UNIQUE_PARAM_EXISTS, "Email已存在");
        }
        if (sysUserDao.isExistByMobile(object.getMobile())) {
            return ResResult.fail(ResCode.UNIQUE_PARAM_EXISTS, "手机已存在");
        }
        if (sysUserDao.isExistByUserId(object.getUserId())) {
            return ResResult.fail(ResCode.UNIQUE_PARAM_EXISTS, "用户ID已存在");
        }
        LocalDateTime now = LocalDateTime.now();
        object.setCreateTime(now);
        object.setUpdateTime(now);
        return sysUserDao.save(object) > 0 ? ResResult.success() : ResResult.fail();
    }


    /**
     * 设置用户所属机构
     *
     * @param id
     * @param corpId
     * @param corpName
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult setCorp(Long id, Long corpId, String corpName) throws ServiceException {
        SysUserDO sysUserDO = SysUserDO.builder()
                .id(id)
                .corpId(corpId)
                .corpName(corpName)
                .updateTime(LocalDateTime.now())
                .build();
        return sysUserDao.updateCorpInfoByDO(sysUserDO) > 0 ? ResResult.success() : ResResult.fail();
    }

    /**
     * 设置个人信息
     *
     * @param sysUserDO
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult setSelfInfo(SysUserDO sysUserDO) throws ServiceException {
        // 确认是本人登录
        Long id = authComponent.getPrimaryPrincipal(Long.class);
        if (Objects.nonNull(id)) {
            if (Objects.equals(sysUserDO.getId(), id)) {
                return sysUserDao.updateSelfInfo(sysUserDO) > 0 ? ResResult.success() : ResResult.fail();
            } else {
                return ResResult.fail(ResCode.NO_AUTH, "非本人操作");
            }
        } else {
            return ResResult.fail(ResCode.NOT_LOGIN);
        }
    }

    /**
     * 获取个人信息
     *
     * @return
     * @throws ServiceException
     */
    @Override
    public ResResult getSelfInfo() throws ServiceException {
        Long id = authComponent.getPrimaryPrincipal(Long.class);
        if (Objects.nonNull(id)) {
            SysUserDO sysUserDO = sysUserDao.getById(id);
            return Objects.nonNull(sysUserDO) ? ResResult.success(sysUserDO) : ResResult.fail();
        }
        return ResResult.fail(ResCode.NOT_LOGIN);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult deleteById(Object id) throws ServiceException {
        boolean flag = sysUserDao.deleteById(id) > 0;
        if (flag) {
            // 删除后同时删除本人角色关联信息
            sysUserRoleMidDao.deleteByUserId(id);
        }
        return flag ? ResResult.success() : ResResult.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult deleteByIds(Object[] ids) throws ServiceException {
        boolean flag = sysUserDao.deleteByIds(ids) > 0;
        if (flag) {
            for (Object id : ids) {
                // 删除后同时删除本人角色关联信息
                sysUserRoleMidDao.deleteByUserId(id);
            }
        }
        return flag ? ResResult.success() : ResResult.fail();
    }

    /**
     * 设置用户角色
     *
     * @param id
     * @param roleIds
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult setUserRole(Long id, Long[] roleIds) throws ServiceException {
        int count;
        /**
         * 判断要设置的角色 id 是否为空,为空则删除当前关联对象;不为空则遍历
         */
        if (JudgeUtil.isNotEmpty(roleIds)) {
            // 保存用户角色关联对象
            count = setUserRoleMidByUserIdAndRoleIdsAndOldRoleIdList(id, roleIds);
        } else {
            // 删除此用户的角色关联对象
            count = sysUserRoleMidDao.deleteByUserId(id);
        }
        return ResResult.success(count);
    }

    /**
     * 设置多个用户的角色
     *
     * @param ids
     * @param roleId
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult setUsersRole(Long[] ids, Long roleId) throws ServiceException {
        int count;
        /**
         * 判断要保存的用户 id 列表是否为空,为空则删除当前关联对象;不为空则遍历
         */
        if (JudgeUtil.isNotEmpty(ids)) {
            // 设置用户角色关联对象
            count = setUserRoleMidByUserIdsAndRoleId(ids, roleId);
        } else {
            // 删除全部用户的此角色id关联对象
            count = sysUserRoleMidDao.deleteByRoleId(roleId);
        }
        return ResResult.success(count);
    }

    /**
     * 设置用户-角色关联对象,通过用户 id 列表与角色 id
     */
    private int setUserRoleMidByUserIdsAndRoleId(Long[] ids, Long roleId) {
        int count;
        /**
         * 获取角色 id 对应的原用户 id 列表
         */
        List<Long> oldUserIdList = sysUserRoleMidDao.listUserIdByRoleId(roleId);
        /**
         * 若为空则添加全部用户 id 的关联,若不为空则判断是否存在
         */
        if (JudgeUtil.isNotEmpty(oldUserIdList)) {
            /**
             * 设置用户角色关联对象,若要设置的用户 id 存在在原用户 id 列表内,则从原用户 id 列表内删除此用户 id;
             * 若要设置的用户 id 不存在原用户 id 列表内,则新增关联对象;遍历完要设置的用户 id 数组后,删除原用户 id 列表剩余的关联对象
             */
            count = setUserRoleMidByUserIdsAndRoleIdAndOldUserIdList(ids, roleId, oldUserIdList);
        } else {
            // 直接保存
            count = saveUserRoleMidByUserIdsAndRoleId(ids, roleId);
        }
        return count;
    }

    /**
     * 设置用户角色关联对象,通过用户 id 与角色 id 列表
     */
    private int setUserRoleMidByUserIdAndRoleIdsAndOldRoleIdList(Long id, Long[] roleIds) {
        int count;
        /**
         * 获取用户 id 对应的原角色 id 列表
         */
        List<Long> oldRoleIdList = sysUserRoleMidDao.listRoleIdByUserId(id);
        /**
         * 若为空则添加全部角色 id 关联,若不为空则判断是否已存在
         */
        if (JudgeUtil.isNotEmpty(oldRoleIdList)) {
            /**
             * 若要设置的角色 id 存在在当前角色 id 列表内,从当前角色 id 列表中删除,
             * 若要设置的角色 id 不存在在当前角色id列表,新增,遍历完后,删除当前角色 ID列表对应的关联表数据
             */
            count = setUserRoleMidByUserIdAndRoleIdsAndOldRoleIdList(id, roleIds, oldRoleIdList);
        } else {
            // 直接保存
            count = saveUserRoleMidByUserIdAndRoleIds(id, roleIds);
        }
        return count;
    }

    /**
     * 保存关联列表,通过用户 id 与角色 id 列表
     */
    private int saveUserRoleMidByUserIdAndRoleIds(Long id, Long[] roleIds) {
        int count = 0;
        for (Long roleId : roleIds) {
            count += saveUserRoleMidDO(id, roleId);
        }
        return count;
    }

    /**
     * 保存关联对象,通过用户 id 列表与角色 id
     */
    private int saveUserRoleMidByUserIdsAndRoleId(Long[] ids, Long roleId) {
        int count = 0;
        for (Long userId : ids) {
            count += saveUserRoleMidDO(userId, roleId);
        }
        return count;
    }

    /**
     * 设置用户角色关联对象,若要设置的角色 id 存在在原角色 id 列表内,则从原角色 id 列表内删除此角色 id;
     * 若要设置的角色 id 不存在原角色 id 列表内,则新增关联对象;遍历完要设置的角色 id 数组后,删除原角色id 列表剩余的关联对象
     */
    private int setUserRoleMidByUserIdAndRoleIdsAndOldRoleIdList(Long id, Long[] roleIds, List<Long> oldRoleIdList) {
        int count = 0;
        for (Long roleId : roleIds) {
            // 若原角色 id 列表中包含要设置的角色 id,则从原角色 id 列表中删除此 id,不进行更新
            if (oldRoleIdList.contains(roleId)) {
                oldRoleIdList.remove(roleId);
            } else {
                count += saveUserRoleMidDO(id, roleId);
            }
        }
        // 循环完后若 oldRoleIdList不为空,则删除对应关联对象
        if (JudgeUtil.isNotEmpty(oldRoleIdList)) {
            for (Long oldRoleId : oldRoleIdList) {
                count += sysUserRoleMidDao.deleteByUserIdAndRoleId(id, oldRoleId);
            }
        }
        return count;
    }

    /**
     * 设置用户角色关联对象,若要设置的用户 id 存在在原用户 id 列表内,则从原用户 id 列表内删除此用户 id;
     * 若要设置的用户 id 不存在原用户 id 列表内,则新增关联对象;遍历完要设置的用户 id 数组后,删除原用户 id 列表剩余的关联对象
     */
    private int setUserRoleMidByUserIdsAndRoleIdAndOldUserIdList(Long[] ids, Long roleId, List<Long> oldUserIdList) {
        int count = 0;
        for (Long userId : ids) {
            // 若原用户 id 列表中包含要设置的用户 id,则从原用户 id 列表中删除此用户 id,不进行更新
            if (oldUserIdList.contains(userId)) {
                oldUserIdList.remove(userId);
            } else {
                count += saveUserRoleMidDO(userId, roleId);
            }
        }
        // 若原用户 id 列表不为空,则删除对应关联对象
        if (JudgeUtil.isNotEmpty(oldUserIdList)) {
            for (Long oldUserId : oldUserIdList) {
                count += sysUserRoleMidDao.deleteByUserIdAndRoleId(oldUserId, roleId);
            }
        }
        return count;
    }

    /**
     * 保存中间表对象
     *
     * @return
     */
    private int saveUserRoleMidDO(Long userId, Long roleId) {
        SysUserRoleMidDO sysUserRoleMidDO = SysUserRoleMidDO.builder()
                .userId(userId)
                .roleId(roleId)
                .createTime(LocalDateTime.now())
                .build();
        return sysUserRoleMidDao.save(sysUserRoleMidDO);
    }
}
