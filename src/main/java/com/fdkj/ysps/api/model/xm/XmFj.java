package com.fdkj.ysps.api.model.xm;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 项目附件
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class XmFj {

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
     * 项目信息id
     */
    private String fk_xmid;

    /**
     * 附件名称
     */
    private String fjmc;

    /**
     * 附件地址
     */
    private String fjdz;

    /**
     * 附件类型
     */
    private String fjlx_zd;

    /**
     * 上传类型
     */
    private String sclx;
}
