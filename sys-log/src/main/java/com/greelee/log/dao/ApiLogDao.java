package com.greelee.log.dao;

import com.greelee.log.model.ApiLogDO;
import com.greelee.tool.component.mvc.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Mapper
public interface ApiLogDao extends BaseDao<ApiLogDO> {
}
