package com.fdkj.ysps.api.model.system;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 接口返回的用户信息实体类
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class User {
    private String id;
    private String addtime;
    private String fk_qybm;
    private String fk_xtglid;

    private String username;
    private String password;
    private String salt;
    private String fk_jsid;
    private Boolean is_lock;
    private String lxdh;
    private String lxr;

    private String roleType;

    /**
     * 加密狗序列号
     */
    private String jmgxlh;
    
    private String ryhg;
}
