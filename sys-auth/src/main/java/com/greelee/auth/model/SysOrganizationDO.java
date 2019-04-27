package com.greelee.auth.model;

import com.greelee.tool.component.mvc.base.PageBean;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 系统机构对象
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysOrganizationDO extends PageBean implements Serializable {
    private static final long serialVersionUID = -3072069300984561645L;
    /**
     * 机构ID
     */
    private Long id;
    /**
     * 单位ID
     */
    private Integer corpId;
    /**
     * 机构名称
     */
    private String corpName;
    /**
     * 父级单位ID
     */
    private Integer parentCorpId;
    /**
     * 排序
     */
    private Integer seq;
    /**
     * 单位负责人
     */
    private String corpManager;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 子资源
     */
    private List<SysOrganizationDO> children;
    /**
     * 父级单位名称
     */
    private String parentCorpName;
}
