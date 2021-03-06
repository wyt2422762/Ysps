package com.fdkj.ysps.api.util;

import com.alibaba.fastjson.JSONObject;
import com.fdkj.ysps.api.model.system.User;
import com.fdkj.ysps.api.model.system.Zd;
import com.fdkj.ysps.base.Page;
import com.fdkj.ysps.error.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典接口
 * @author wyt
 */
@Component
public class DictApi extends BaseApi {
    private static final Logger logger = LoggerFactory.getLogger(DictApi.class);

    /**
     * 获取字典列表(分页)
     * @param request  req
     * @param reqBody  请求体
     * @param pageNo   第几页
     * @param pageSize 每页显示的条数
     * @return 字典列表
     * @throws Exception err
     */
    public Page<Zd> getZdList(HttpServletRequest request, Map<String, Object> reqBody, Integer pageNo, Integer pageSize) throws Exception {
        User user = getUserFromCookie(request);
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //请求体
        JSONObject body = new JSONObject();
        body.put("fk_xtglid", user.getFk_xtglid());
        body.putAll(reqBody);

        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);

        //请求参数
        Map<String, Object> params = new HashMap<>(4);
        params.put("page", pageNo == null ? 1 : pageNo);
        params.put("pageNum", pageSize == null ? 10 : pageSize);

        String url = baseUrl + "/api/CZF/CZF_ZDGL_List?page={page}&pageNum={pageNum}";
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, String.class, params);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);

        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("获取字典列表失败，请求url: " + baseUrl + "/api/CZF/CZF_ZDGL_List");
            logger.error("获取字典列表失败，请求参数: " + params);
            logger.error("获取字典列表失败，请求体: " + body.toJSONString());
            logger.error("获取字典列表失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //构造返回信息
        Page<Zd> page = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        Integer totalRecord = jsonObject.getInteger("TotalCount");
        page.setTotalRecord(totalRecord);
        List<Zd> dataList = jsonObject.getJSONArray("Results").toJavaList(Zd.class);
        page.setDataList(dataList);

        return page;
    }

    /**
     * 获取字典列表(全部)
     * @param request  req
     * @param reqBody  请求体
     * @return 字典列表
     * @throws Exception err
     */
    public List<Zd> getZdList(HttpServletRequest request, Map<String, Object> reqBody) throws Exception {
        User user = getUserFromCookie(request);
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //请求体
        JSONObject body = new JSONObject();
        body.put("fk_xtglid", user.getFk_xtglid());
        body.putAll(reqBody);

        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);

        String url = baseUrl + "/api/CZF/CZF_ZDGL_List";
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, String.class);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);

        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("获取字典列表失败，请求url: " + baseUrl + "/api/CZF/CZF_ZDGL_List");
            logger.error("获取字典列表失败，请求体: " + body.toJSONString());
            logger.error("获取字典列表失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //构造返回信息
        return jsonObject.getJSONArray("Results").toJavaList(Zd.class);
    }

    /**
     * 获取字典详情
     *
     * @param request req
     * @param id      字典id
     * @return 银行详情
     * @throws Exception err
     */
    public Zd getZdDetail(HttpServletRequest request, String id) throws Exception {
        User user = getUserFromCookie(request);
        //请求头
        HttpHeaders headers = getHttpHeaders(request);

        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(null, headers);

        //请求参数
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);

        String url = baseUrl + "/api/CZF/CZF_ZDGL_Model?id={id}";
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, String.class, params);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);

        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("获取字典详情失败，请求url: " + baseUrl + "/api/CZF/CZF_ZDGL_Model");
            logger.error("获取字典详情失败，请求参数: " + params);
            logger.error("获取字典详情失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //构造返回信息
        return jsonObject.getJSONObject("Results").toJavaObject(Zd.class);
    }

    /**
     * 更新添加字典
     *
     * @param request req
     * @param body    请求体
     */
    public void aeZd(HttpServletRequest request, JSONObject body) {
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(baseUrl + "/api/CZF/CZF_ZDGL_Update",
                        HttpMethod.POST, requestEntity, String.class);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);
        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("更新添加字典失败，请求url: " + baseUrl + "/api/CZF/CZF_ZDGL_Update");
            logger.error("更新添加字典失败，请求体: " + body.toJSONString());
            logger.error("更新添加字典失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
