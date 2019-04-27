package com.greelee.auth.realm;


import com.greelee.auth.dao.SysResourceDao;
import com.greelee.auth.dao.SysRoleDao;
import com.greelee.auth.dao.SysUserDao;
import com.greelee.auth.dao.SysUserRoleMidDao;
import com.greelee.auth.model.SysUserDO;
import com.greelee.auth.properties.SysAuthProperties;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 使用系统登录结合
 */
@Component
public class SysUserRealm extends AuthorizingRealm {
    /**
     * 管理员用户 ID
     */
    public static final Long ADMIN_ID = 0L;

    private SysAuthProperties sysAuthProperties;


    private SysUserDao sysUserDao;

    private SysRoleDao sysRoleDao;

    private SysUserRoleMidDao sysUserRoleMidDao;

    private SysResourceDao sysResourceDao;

    @Autowired
    public SysUserRealm(SysAuthProperties sysAuthProperties, SysUserDao sysUserDao, SysRoleDao sysRoleDao, SysUserRoleMidDao sysUserRoleMidDao, SysResourceDao sysResourceDao) {
        this.sysAuthProperties = sysAuthProperties;
        this.sysUserDao = sysUserDao;
        this.sysRoleDao = sysRoleDao;
        this.sysUserRoleMidDao = sysUserRoleMidDao;
        this.sysResourceDao = sysResourceDao;
        this.setCachingEnabled(sysAuthProperties.isCachingEnabled());
        //开启授权缓存
        this.setAuthorizationCachingEnabled(sysAuthProperties.isAuthorizationCachingEnabled());
        this.setAuthorizationCacheName(sysAuthProperties.getAuthorizationCacheName());
        //开启认证缓存
        this.setAuthenticationCachingEnabled(sysAuthProperties.isAuthenticationCachingEnabled());
        this.setAuthenticationCacheName(sysAuthProperties.getAuthenticationCacheName());
    }

    /**
     * 认证
     *
     * @param authenticationToken token
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /**
         * token 强转(向下转型)为用户名密码 token
         */
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());

        boolean flag = false;
        // 用户 ID
        Long id = null;
        // 姓名
        String name = null;
        /*
         * 1.先判断是否是管理员
         */
        if (Objects.equals(sysAuthProperties.getAdminName(), username) && Objects.equals(sysAuthProperties.getAdminPassword(), password)) {
            id = ADMIN_ID;
            name = "管理员";
            flag = true;
        } else {
            /*
             * 在系统里查询是否存在此用户,邮箱,用户名,手机号,存在则获取AD 域登录名,使用 AD 域登录,AD 域登录未成功则使用系统登录
             */
            SysUserDO loginUserDO = getByKeys(username);
            if (null != loginUserDO) {
                String adAccount = loginUserDO.getAdAccount();

                    /*
                     * 系统验证,此时用户属于系统用户,密码存储在数据库中
                     */
                    if (Objects.equals(adAccount, username)
                            && Objects.equals(loginUserDO.getPassword(), password)
                            && Objects.equals(loginUserDO.getStatus(), SysUserDO.STATUS_OK)) {
                        flag = true;
                    }
                }
                id = loginUserDO.getId();
                name = loginUserDO.getUsername();
        }
        if (flag) {
            /**
             * 使用 userId 作为认证
             */
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
            SimplePrincipalCollection principals = new SimplePrincipalCollection();
            /**
             * 认证主体,设定为user的自增 id
             */
            principals.add(id, getName());
            principals.add(username, getName());
            principals.add(name, getName());
            info.setPrincipals(principals);
            /**
             * 证书
             */
            info.setCredentials(password);
            return info;
        } else {
            throw new UnknownAccountException();
        }
    }

    /**
     * 在系统里查询是否存在此用户,邮箱,用户名,手机号,存在则获取AD 域登录名,使用 AD 域登录,AD 域登录未成功则使用系统登录
     *
     * @param key 关键字
     * @return
     */
    private SysUserDO getByKeys(String key) {
        /**
         * 在系统里查询是否存在此用户,邮箱,用户名,手机号,存在则获取AD 域登录名,使用 AD 域登录,AD 域登录未成功则使用系统登录
         */
        // 查 adAcount
        SysUserDO loginUserDO = sysUserDao.getByAdAccount(key);
        if (Objects.isNull(loginUserDO)) {
            // 查 Mobile
            loginUserDO = sysUserDao.getByMobile(key);
            if (Objects.isNull(loginUserDO)) {
                // 查 Username
                loginUserDO = sysUserDao.getByUsername(key);
                if (Objects.isNull(loginUserDO)) {
                    // 查 email
                    loginUserDO = sysUserDao.getByEmail(key);
                }
            }
        }
        return loginUserDO;
    }

    /**
     * 获取权限
     *
     * @param principals 权限主体,一般是 username
     * @return 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前用户
        Object userId = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (Objects.equals(ADMIN_ID, userId)) {
            info.addStringPermission("*");
        } else {
            //赋予角色
            List<String> roleNameList = sysRoleDao.listRoleNameByUserId(userId);
            info.addRoles(roleNameList);

            //赋予权限
            List<String> permissionList = sysResourceDao.listPermissionByUserId(userId);
            info.addStringPermissions(permissionList);
        }
        return info;
    }


}
