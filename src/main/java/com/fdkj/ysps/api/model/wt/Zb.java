package com.fdkj.ysps.api.model.wt;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 招标
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class Zb {
    private String id;

    /**
     * 系统id
     */
    private String fk_xtglid;

    /**
     * 区域编码
     */
    private String fk_qybm;

    /**
     * 添加时间
     */
    private String addtime;

    /**
     * 项目id
     */
    @NotBlank(message = "项目id不能为空")
    private String fk_xmid;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 机构资质
     */
    private String jgzz;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 中标价
     */
    private String zbj;

}
