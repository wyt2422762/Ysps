package com.fdkj.ysps.api.util;

import com.alibaba.fastjson.JSONObject;
import com.fdkj.ysps.api.model.system.User;
import com.fdkj.ysps.api.model.xm.Xm;
import com.fdkj.ysps.base.Page;
import com.fdkj.ysps.error.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目基本信息接口
 *
 * @author wyt
 */
@Component
public class XmApi extends BaseApi {
    private static final Logger logger = LoggerFactory.getLogger(XmApi.class);

    /**
     * 获取项目信息列表(分页)
     *
     * @param request   req
     * @param reqParams 请求参数
     * @param reqBody   请求体
     * @param pageNo    第几页
     * @param pageSize  每页显示多少条
     * @return res
     * @throws Exception err
     */
    public Page<Xm> getXmList(HttpServletRequest request, Map<String, Object> reqParams, Map<String, Object> reqBody, Integer pageNo, Integer pageSize) throws Exception {
        User user = getUserFromCookie(request);
        //请求头
        HttpHeaders headers = getHttpHeaders(request);

        //请求体
        JSONObject body = new JSONObject();
        body.put("fk_xtglid", user.getFk_xtglid());
        if (reqBody != null && !reqBody.isEmpty()) {
            body.putAll(reqBody);
        }

        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);

        //请求参数
        Map<String, Object> params = new HashMap<>(4);
        params.put("page", pageNo == null ? 1 : pageNo);
        params.put("pageNum", pageSize == null ? 10 : pageSize);

        String url = baseUrl + "/api/CZF/YS_XMXX_List";

        if (reqParams != null && !reqParams.isEmpty()) {
            params.putAll(reqParams);
        }

        List<String> paramList = new ArrayList<>();
        params.forEach((key, value) -> {
            paramList.add(key + "={" + key + "}");
        });

        if (!paramList.isEmpty()) {
            url += ("?" + String.join("&", paramList));
        }

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class, params);

        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);

        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("获取项目列表失败，请求url: " + baseUrl + "/api/CZF/YS_XMXX_List");
            logger.error("获取项目列表失败，请求参数: " + params);
            logger.error("获取项目列表失败，请求体: " + body.toJSONString());
            logger.error("获取项目列表失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //构造返回信息
        Page<Xm> page = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        Integer totalRecord = jsonObject.getInteger("TotalCount");
        page.setTotalRecord(totalRecord);
        List<Xm> dataList = jsonObject.getJSONArray("Results").toJavaList(Xm.class);
        page.setDataList(dataList);

        return page;
    }

    /**
     * 获取项目信息列表(分页)
     *
     * @param request   req
     * @param reqParams 请求参数
     * @param reqBody   请求体
     * @return res
     * @throws Exception err
     */
    public List<Xm> getXmListAll(HttpServletRequest request, Map<String, Object> reqParams, Map<String, Object> reqBody) throws Exception {
        User user = getUserFromCookie(request);
        //请求头
        HttpHeaders headers = getHttpHeaders(request);

        //请求体
        JSONObject body = new JSONObject();
        body.put("fk_xtglid", user.getFk_xtglid());
        if (reqBody != null && !reqBody.isEmpty()) {
            body.putAll(reqBody);
        }

        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);

        //请求参数
        //请求参数
        Map<String, Object> params = new HashMap<>(4);

        String url = baseUrl + "/api/CZF/YS_XMXX_List";

        if (reqParams != null && !reqParams.isEmpty()) {
            params.putAll(reqParams);
        }

        List<String> paramList = new ArrayList<>();
        params.forEach((key, value) -> {
            paramList.add(key + "={" + key + "}");
        });

        if (!paramList.isEmpty()) {
            url += ("?" + String.join("&", paramList));
        }

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class, params);

        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);

        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("获取项目列表失败，请求url: " + baseUrl + "/api/CZF/YS_XMXX_List");
            logger.error("获取项目列表失败，请求参数: " + params);
            logger.error("获取项目列表失败，请求体: " + body.toJSONString());
            logger.error("获取项目列表失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //构造返回信息
        return jsonObject.getJSONArray("Results").toJavaList(Xm.class);
    }

    /**
     * 获取项目详情
     *
     * @param request req
     * @param id      项目id
     * @return res
     */
    public JSONObject getXmDetail(HttpServletRequest request, String id) throws Exception {
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

        String url = baseUrl + "/api/CZF/YS_XMXX_Model?id={id}";
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, String.class, params);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);

        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("获取项目详情失败，请求url: " + baseUrl + "/api/CZF/YS_XMXX_Model");
            logger.error("获取项目详情失败，请求体: " + body.toJSONString());
            logger.error("获取项目详情失败，请求参数: " + params);
            logger.error("获取项目详情失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return jsonObject.getJSONObject("Results");
    }

    /**
     * 更新添加项目
     *
     * @param request req
     * @param body    请求体
     */
    public void aeXm(HttpServletRequest request, JSONObject body) {
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(baseUrl + "/api/CZF/YS_XMXX_Update",
                        HttpMethod.POST, requestEntity, String.class);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);
        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("更新添加项目失败，请求url: " + baseUrl + "/api/CZF/YS_XMXX_Update");
            logger.error("更新添加项目失败，请求体: " + body.toJSONString());
            logger.error("更新添加项目失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * 删除项目
     *
     * @param request req
     * @param id    项目id
     */
    public void delXm(HttpServletRequest request, String id) {
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(null, headers);
        //参数
        Map<String, String> params = new HashMap<>(1);
        params.put("id", id);
        //请求
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(baseUrl + "/api/CZF/YS_XMXX_Del?id={id}",
                        HttpMethod.POST, requestEntity, String.class, params);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);
        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("删除项目失败，请求url: " + baseUrl + "/api/CZF/YS_XMXX_Del");
            logger.error("删除项目失败，请求参数: " + params);
            logger.error("删除项目失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * 提交项目
     *
     * @param request req
     * @param id    项目id
     */
    public void tjXm(HttpServletRequest request, String id) {
        //请求头
        HttpHeaders headers = getHttpHeaders(request);
        //组装请求体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(null, headers);
        //参数
        Map<String, String> params = new HashMap<>(1);
        params.put("id", id);
        //请求
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(baseUrl + "/api/CZF/YS_XMXX_TJ?id={id}",
                        HttpMethod.POST, requestEntity, String.class, params);
        String responseEntityBody = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntityBody);
        boolean success = jsonObject.getBooleanValue("Success");
        if (!success) {
            logger.error("提交项目失败，请求url: " + baseUrl + "/api/CZF/YS_XMXX_TJ");
            logger.error("提交项目失败，请求参数: " + params);
            logger.error("提交项目失败，返回内容: " + responseEntityBody);
            throw new BusinessException(jsonObject.getString("Message"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
