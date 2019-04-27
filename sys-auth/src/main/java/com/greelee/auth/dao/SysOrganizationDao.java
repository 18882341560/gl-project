package com.greelee.auth.dao;

import com.greelee.auth.model.SysOrganizationDO;
import com.greelee.tool.component.mvc.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Mapper
@Repository
public interface SysOrganizationDao extends BaseDao<SysOrganizationDO> {
    /**
     * 获取机构列表
     * @param parentCorpId
     * @return
     */
    List<SysOrganizationDO> listByParentCorpId(@Param("parentCorpId") Integer parentCorpId);

    /**
     * 判断是否存在
     * @param corpId
     * @return
     */
    boolean isExistByCorpId(@Param("corpId") Integer corpId);

    /**
     * 判断是否有指定状态的父级单位 parentCorpId 的对象
     * @param parentCorpId
     * @return
     */
    boolean isExistByParentCorpId(@Param("parentCorpId") Object parentCorpId);
}
