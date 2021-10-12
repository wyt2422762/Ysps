package com.fdkj.ysps.api.model.xm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 项目信息
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class Xm {
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
     * 项目名称
     */
    private String xmmc;

    /**
     * 项目编号
     */
    private String xmbh;

    /**
     * 项目分类(字典)
     */
    private String xmfl_zd;

    /**
     * 项目性质(字典)
     */
    private String xmxz_zd;

    /**
     * 建设内容
     */
    private String jsbr;

    /**
     * 委托时间
     */
    private String wtsj;

    /**
     * 委托金额
     */
    private String wtje;

    /**
     * 项目实施单位
     */
    private String xmssdw;

    /**
     * 联系人
     */
    private String lxr;

    /**
     * 联系方式
     */
    private String lxfs;

    /**
     * 评审目标及要求
     */
    private String psmbjyq;

    /**
     * 评审结果使用方向
     */
    private String psjgsyfx;

    /**
     * 是否询价
     */
    private String sfxj;

    /**
     * 是否快评项目
     */
    private String sfkpxm;

    /**
     * 受理时间
     */
    private String slsj;

    /**
     * 状态
     */
    private String zt;

    /**
     * 人员工号
     */
    private String rygh;

    /**
     * 当前节点id
     */
    private String dqjdid;

    /**
     * 是否已询价
     */
    private String sfyxj;

    /**
     * 是否已招标
     */
    private String sfyzb;

    /**
     * 附件列表
     */
    private List<XmFj> fjList;

    /**
     * 转JSONObject
     *
     * @return res
     */
    public JSONObject toReqJson() {
        return JSONObject.parseObject(JSONObject.toJSONString(this));
    }
}
