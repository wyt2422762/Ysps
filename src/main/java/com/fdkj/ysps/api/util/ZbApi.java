package com.fdkj.ysps.api.util;

import com.alibaba.fastjson.JSONObject;
import com.fdkj.ysps.api.model.wt.Zb;
import com.fdkj.ysps.error.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 招标接口
 *
 * @author wyt
 */
@Component
public class ZbApi extends BaseApi {
    private static final Logger logger = LoggerFactory.getLogger(ZbApi.class);

    /**
     * 获取招标详情(根据招标id)
     *
     * @param request req
     * @param id      招标id
     * @return res
     */
    public Zb getXjDetailById(HttpServletRequest request, String id) throws Exception {
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //请求体
        JSONObject body = new JSONObject();
        //body.put("fk_xtglid", user.getFk_xtglid());

        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);

        //请求参数
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);

        String url = baseUrl + "/api/CZF/YS_ZBXM_Model?id={id}";
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, String.class, params);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);

        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("获取招标详情失败，请求url: " + baseUrl + "/api/CZF/YS_ZBXM_Model");
            logger.error("获取招标详情失败，请求体: " + body.toJSONString());
            logger.error("获取招标详情失败，请求参数: " + params);
            logger.error("获取招标详情失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return jsonObject.getObject("Results", Zb.class);
    }

    /**
     * 获取招标详情(根据项目id)
     *
     * @param request req
     * @param xmId    项目id
     * @return res
     */
    public Zb getXjDetailByXmId(HttpServletRequest request, String xmId) throws Exception {
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //请求体
        JSONObject body = new JSONObject();
        //body.put("fk_xtglid", user.getFk_xtglid());

        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);

        //请求参数
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", xmId);

        String url = baseUrl + "/api/CZF/YS_ZBXM_Model_xmid?id={id}";
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, String.class, params);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);

        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("获取招标详情失败，请求url: " + baseUrl + "/api/CZF/YS_ZBXM_Model_xmid");
            logger.error("获取招标详情失败，请求体: " + body.toJSONString());
            logger.error("获取招标详情失败，请求参数: " + params);
            logger.error("获取招标详情失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return jsonObject.getObject("Results", Zb.class);
    }

    /**
     * 更新添加招标
     *
     * @param request req
     * @param body    请求体
     */
    public void aeZb(HttpServletRequest request, JSONObject body) {
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(baseUrl + "/api/CZF/YS_ZBXM_Update",
                        HttpMethod.POST, requestEntity, String.class);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);
        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("更新添加招标失败，请求url: " + baseUrl + "/api/CZF/YS_ZBXM_Update");
            logger.error("更新添加招标失败，请求体: " + body.toJSONString());
            logger.error("更新添加招标失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
