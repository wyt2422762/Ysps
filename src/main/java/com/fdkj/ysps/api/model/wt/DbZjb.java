package com.fdkj.ysps.api.model.wt;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 打包中间表
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class DbZjb {
    private String id;

    /**
     * 项目id
     */
    private String fk_xmid;

    /**
     * 打包id
     */
    private String fk_dbid;
}
