package com.fdkj.ysps.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.fdkj.ysps.api.model.system.User;
import com.fdkj.ysps.api.model.system.Zd;
import com.fdkj.ysps.api.util.DictApi;
import com.fdkj.ysps.base.CusResponseBody;
import com.fdkj.ysps.error.BusinessException;
import com.fdkj.ysps.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典管理
 *
 * @author wyt
 */
@Controller
@RequestMapping("GYFW/CZF_ZDGL")
public class ZdController {
    private static final Logger log = LoggerFactory.getLogger(ZdController.class);

    @Autowired
    private DictApi dictApi;

    /**
     * 跳转到
     *
     * @param request req
     * @param opts    操作权限信息
     * @return res
     * @throws Exception err
     */
    @RequestMapping("Index")
    public ModelAndView index(HttpServletRequest request, @RequestParam(value = "opts", required = false) List<String> opts) throws Exception {
        request.setAttribute("user", dictApi.getUserFromCookie(request));
        request.setAttribute("opts", opts);
        return new ModelAndView("system/zdMgr/zd_index");
    }

    /**
     * 获取字典列表
     *
     * @param request req
     * @param zdm     字典名称
     * @return res
     */
    @RequestMapping("getList")
    @ResponseBody
    public ResponseEntity<CusResponseBody> getList(HttpServletRequest request,
                                                   @RequestParam(value = "zdm", required = false) String zdm,
                                                   @RequestParam(value = "fid", required = false) String fid) {
        try {
            Map<String, Object> reqBody = new HashMap<>();
            if (StringUtils.isNotBlank(zdm)) {
                reqBody.put("zdm", zdm);
            }
            if (StringUtils.isNotBlank(fid)) {
                reqBody.put("fid", fid);
            }

            List<Zd> zdList = dictApi.getZdList(request, reqBody);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("获取字典列表成功", zdList);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取字典列表失败", e);
            throw new BusinessException("获取字典列表失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 获取字典列表
     *
     * @param request req
     * @param zdm     字典名称
     * @return res
     */
    @RequestMapping("getList2")
    @ResponseBody
    public List<Zd> getList2(HttpServletRequest request,
                                                   @RequestParam(value = "zdm", required = false) String zdm,
                                                   @RequestParam(value = "fid", required = false) String fid) {
        try {
            Map<String, Object> reqBody = new HashMap<>();
            if (StringUtils.isNotBlank(zdm)) {
                reqBody.put("zdm", zdm);
            }
            if (StringUtils.isNotBlank(fid)) {
                reqBody.put("fid", fid);
            }

            return dictApi.getZdList(request, reqBody);
        } catch (Exception e) {
            log.error("获取字典列表失败", e);
            throw new BusinessException("获取字典列表失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 获取字典详情
     *
     * @param request req
     * @param id      字典id
     * @return res
     */
    @RequestMapping("getDetail/{id}")
    @ResponseBody
    public ResponseEntity<CusResponseBody> getDetail(HttpServletRequest request,
                                                     @PathVariable("id") String id) {
        try {
            Zd zd = dictApi.getZdDetail(request, id);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("获取字典详情成功", zd);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取字典详情失败", e);
            throw new BusinessException("获取字典详情失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }


    @RequestMapping("toAdd")
    public ModelAndView toAdd(HttpServletRequest request, @RequestParam("fid") String fid) throws Exception {
        //1. 当前登陆用户
        request.setAttribute("user", dictApi.getUserFromCookie(request));
        request.setAttribute("fid", fid);
        return new ModelAndView("system/zdMgr/zd_add");
    }

    @RequestMapping("toEdit/{id}")
    public ModelAndView toEdit(HttpServletRequest request,
                               @PathVariable("id") String id) throws Exception {
        //1. 当前登陆用户
        request.setAttribute("user", dictApi.getUserFromCookie(request));
        //2. 对应的字典信息
        Zd zd = dictApi.getZdDetail(request, id);
        request.setAttribute("zd", zd);
        return new ModelAndView("system/zdMgr/zd_edit");
    }

    /**
     * 添加/编辑字典
     *
     * @param request req
     * @param json    请求体
     * @return res
     */
    @RequestMapping("aeZd/{type}")
    @ResponseBody
    public ResponseEntity<CusResponseBody> aeZd(HttpServletRequest request,
                                                @PathVariable String type,
                                                @RequestBody JSONObject json) {
        try {
            User user = dictApi.getUserFromCookie(request);
            String dateToStr = DateUtils.parseDateToStr("yyyy-MM-dd'T'HH:mm:ss.sss", new Date());
            if("add".equals(type.trim())) {
                json.put("tjr", user.getUsername());
                json.put("tjsj", dateToStr);
            } else {
                json.put("xgr", user.getUsername());
                json.put("xgsj", dateToStr);
            }

            dictApi.aeZd(request, json);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("更新字典成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("更新字典失败", e);
            throw new BusinessException("更新字典失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }
}
