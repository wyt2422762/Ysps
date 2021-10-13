package com.fdkj.ysps.controller.wt;

import com.alibaba.fastjson.JSONObject;
import com.fdkj.ysps.annotation.Log;
import com.fdkj.ysps.api.model.system.User;
import com.fdkj.ysps.api.model.system.Zd;
import com.fdkj.ysps.api.model.wt.Xj;
import com.fdkj.ysps.api.model.xm.Xm;
import com.fdkj.ysps.api.util.DictApi;
import com.fdkj.ysps.api.util.XjApi;
import com.fdkj.ysps.api.util.XmApi;
import com.fdkj.ysps.base.CusResponseBody;
import com.fdkj.ysps.base.Page;
import com.fdkj.ysps.constant.Constants;
import com.fdkj.ysps.controller.BaseController;
import com.fdkj.ysps.error.BusinessException;
import com.fdkj.ysps.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目controller
 *
 * @author wyt
 */
@Controller
@RequestMapping("CZF/WT/XJ")
@Log(module = "委托-询价")
public class XjController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(XjController.class);

    @Autowired
    private XmApi xmApi;
    @Autowired
    private XjApi xjApi;
    @Autowired
    private DictApi dictApi;

    /**
     * 跳转
     *
     * @param request req
     * @param opts    操作权限
     * @return res
     * @throws Exception err
     */
    @RequestMapping("Index")
    public ModelAndView index(HttpServletRequest request,
                              @RequestParam(value = "opts", required = false) List<String> opts) throws Exception {
        request.setAttribute("cuser", xjApi.getUserFromCookie(request));
        request.setAttribute("opts", opts);
        if (opts != null && !opts.isEmpty()) {
            String s = StringUtils.join(opts, ",");
            request.setAttribute("optsStr", s);
        }
        //获取字典信息
        //项目分类
        Map<String, Object> dictParams = new HashMap<>();
        dictParams.put("fid", Constants.Dict.xmfl);
        List<Zd> dict_xmfl = dictApi.getZdList(request, dictParams);
        request.setAttribute("dict_xmfl", dict_xmfl);

        return new ModelAndView("wtMgr/xj/xj_index");
    }

    /**
     * 跳转询价单位页面
     *
     * @param request req
     * @param id      项目id
     * @param opts    操作权限
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toXjdw/{id}")
    public ModelAndView toXjdw(HttpServletRequest request,
                               @PathVariable String id,
                               @RequestParam(value = "opts", required = false) List<String> opts) throws Exception {
        request.setAttribute("cuser", xjApi.getUserFromCookie(request));
        request.setAttribute("opts", opts);
        if (opts != null && !opts.isEmpty()) {
            String s = StringUtils.join(opts, ",");
            request.setAttribute("optsStr", s);
        }

        JSONObject json = xmApi.getXmDetail(request, id);
        Xm xm = json.getObject("model", Xm.class);

        request.setAttribute("sfyxj", xm.getSfyxj());
        request.setAttribute("xmid", id);

        return new ModelAndView("wtMgr/xj/xj_dw");
    }

    /**
     * 跳转到添加页面
     *
     * @param request req
     * @param xmid    项目id
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toAdd/{xmid}")
    public ModelAndView toAdd(HttpServletRequest request, @PathVariable String xmid) throws Exception {
        request.setAttribute("cuser", xjApi.getUserFromCookie(request));
        request.setAttribute("xmid", xmid);
        return new ModelAndView("wtMgr/xj/xj_add");
    }

    /**
     * 跳转到编辑页面
     *
     * @param request req
     * @param id      询价单位id
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toEdit/{id}")
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable String id) throws Exception {
        request.setAttribute("cuser", xjApi.getUserFromCookie(request));
        request.setAttribute("id", id);
        return new ModelAndView("wtMgr/xj/xj_edit");
    }

    /**
     * 跳转到详情页面
     *
     * @param request req
     * @param id      项目id
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toInfo/{id}")
    public ModelAndView toInfo(HttpServletRequest request, @PathVariable String id) throws Exception {
        request.setAttribute("cuser", xjApi.getUserFromCookie(request));
        request.setAttribute("id", id);
        return new ModelAndView("wtMgr/xj/xj_info");
    }

    /**
     * 获取询价单位列表(分页)
     *
     * @param request req
     * @param xj      请求参数
     * @param page    第几页
     * @param limit   每页显示的记录数
     * @return res
     */
    @RequestMapping("getList")
    @ResponseBody
    @Log(module = "询价", desc = "获取询价单位列表(分页)", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getList(HttpServletRequest request, @Validated Xj xj,
                                                   @RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        try {
            User cuser = xjApi.getUserFromCookie(request);
            Page<Xj> xjList = xjApi.getXjDwList(request, null, xj.toMap(), page, limit);
            CusResponseBody cusResponseBody = CusResponseBody.success("获取询价单位列表成功", xjList);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取询价单位列表失败", e);
            throw new BusinessException("获取询价单位列表失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 获取询价单位详情
     *
     * @param request req
     * @param id      询价单位id
     */
    @RequestMapping("getDetail/{id}")
    @ResponseBody
    @Log(module = "询价", desc = "获取询价单位详情", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getDetail(HttpServletRequest request, @PathVariable String id) {
        try {
            User cuser = xjApi.getUserFromCookie(request);
            Xj xjDwDetail = xjApi.getXjDwDetail(request, id);
            CusResponseBody cusResponseBody = CusResponseBody.success("获取询价单位详情成功", xjDwDetail);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取询价单位详情失败", e);
            throw new BusinessException("获取询价单位详情失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 添加询价单位
     *
     * @param request req
     * @param json    请求体
     * @return res
     */
    @RequestMapping("addXj")
    @ResponseBody
    @Log(module = "询价", desc = "添加询价单位", optType = Constants.OptType.ADD)
    public ResponseEntity<CusResponseBody> addXjDw(HttpServletRequest request,
                                                   @RequestBody JSONObject json) {
        return aeXjDw(request, json);
    }

    /**
     * 编辑询价单位
     *
     * @param request req
     * @param json    请求体
     * @return res
     */
    @RequestMapping("editXj")
    @ResponseBody
    @Log(module = "询价", desc = "编辑询价单位", optType = Constants.OptType.EDIT)
    public ResponseEntity<CusResponseBody> editXjDw(HttpServletRequest request,
                                                    @RequestBody JSONObject json) {
        return aeXjDw(request, json);
    }

    private ResponseEntity<CusResponseBody> aeXjDw(HttpServletRequest request,
                                                   JSONObject json) {
        try {
            User cuser = xmApi.getUserFromCookie(request);
            xjApi.aeXjDw(request, json);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("更新询价单位成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("更新询价单位失败", e);
            throw new BusinessException("更新询价单位失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 删除项目
     *
     * @param request req
     * @param id      询价单位id
     * @return res
     */
    @RequestMapping("delXj/{id}")
    @ResponseBody
    @Log(module = "询价", desc = "删除询价单位", optType = Constants.OptType.DEL)
    public ResponseEntity<CusResponseBody> delXjdw(HttpServletRequest request, @PathVariable String id) {
        try {
            xjApi.delXjDw(request, id);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("删除询价单位成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("删除询价单位失败", e);
            throw new BusinessException("删除询价单位失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 确认询价
     *
     * @param request req
     * @param xmid    项目id
     * @return res
     */
    @RequestMapping("confirmXj/{xmid}")
    @ResponseBody
    @Log(module = "询价", desc = "删除询价单位", optType = Constants.OptType.CONFIRM)
    public ResponseEntity<CusResponseBody> confirmXjdw(HttpServletRequest request, @PathVariable String xmid) {
        try {
            xjApi.completeXjDw(request, xmid);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("确认询价单位成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("确认询价单位失败", e);
            throw new BusinessException("确认询价单位失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }
}
