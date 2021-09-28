package com.fdkj.ysps.api.model.xm;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 项目请求参数
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class XmReq {

    /**
     * 委托时间开始
     */
    private String wtstarttime;
    /**
     * 委托时间结束
     */
    private String wtendtime;

    /**
     * 委托金额开始
     */
    private String wtstartje;

    /**
     * 委托金额结束
     */
    private String wtendje;

    /**
     * 转JSONObject
     *
     * @return res
     */
    public JSONObject toReqJson() {
        return JSONObject.parseObject(JSONObject.toJSONString(this));
    }
}
