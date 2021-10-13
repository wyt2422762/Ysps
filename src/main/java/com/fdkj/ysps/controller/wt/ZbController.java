package com.fdkj.ysps.controller.wt;

import com.alibaba.fastjson.JSONObject;
import com.fdkj.ysps.annotation.Log;
import com.fdkj.ysps.api.model.system.User;
import com.fdkj.ysps.api.model.system.Zd;
import com.fdkj.ysps.api.model.wt.Xj;
import com.fdkj.ysps.api.model.wt.Zb;
import com.fdkj.ysps.api.util.DictApi;
import com.fdkj.ysps.api.util.XmApi;
import com.fdkj.ysps.api.util.ZbApi;
import com.fdkj.ysps.base.CusResponseBody;
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
@RequestMapping("CZF/WT/ZB")
@Log(module = "委托-招标")
public class ZbController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ZbController.class);

    @Autowired
    private XmApi xmApi;
    @Autowired
    private ZbApi zbApi;
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
        request.setAttribute("cuser", zbApi.getUserFromCookie(request));
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

        return new ModelAndView("wtMgr/zb/zb_index");
    }

    /**
     * 跳转到编辑页面
     *
     * @param request req
     * @param xmid    项目id
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toEdit/{xmid}")
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable String xmid) throws Exception {
        request.setAttribute("cuser", zbApi.getUserFromCookie(request));
        request.setAttribute("xmid", xmid);
        return new ModelAndView("wtMgr/zb/zb_edit");
    }

    /**
     * 根据项目id获取询价单位详情
     *
     * @param request req
     * @param xmid    项目id
     */
    @RequestMapping("getDetailByXmId/{xmid}")
    @ResponseBody
    @Log(module = "招标", desc = "获取招标详情", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getDetailByXmId(HttpServletRequest request, @PathVariable String xmid) {
        try {
            User cuser = zbApi.getUserFromCookie(request);
            Zb zb = zbApi.getXjDetailByXmId(request, xmid);
            CusResponseBody cusResponseBody = CusResponseBody.success("获取招标详情成功", zb);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取询价单位详情失败", e);
            throw new BusinessException("获取询价单位详情失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 获取询价单位详情
     *
     * @param request req
     * @param id      招标id
     */
    @RequestMapping("getDetailById/{id}")
    @ResponseBody
    @Log(module = "招标", desc = "获取招标详情", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getDetailById(HttpServletRequest request, @PathVariable String id) {
        try {
            User cuser = zbApi.getUserFromCookie(request);
            Zb zb = zbApi.getXjDetailById(request, id);
            CusResponseBody cusResponseBody = CusResponseBody.success("获取招标详情成功", zb);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取询价单位详情失败", e);
            throw new BusinessException("获取询价单位详情失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 添加编辑招标
     *
     * @param request req
     * @param json    请求体
     * @return res
     */
    @RequestMapping("aeZb")
    @ResponseBody
    @Log(module = "招标", desc = "添加编辑招标", optType = Constants.OptType.ADD)
    public ResponseEntity<CusResponseBody> aeZb(HttpServletRequest request,
                                                   @RequestBody JSONObject json) {
        try {
            User cuser = xmApi.getUserFromCookie(request);
            zbApi.aeZb(request, json);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("更新招标成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("更新询价单位失败", e);
            throw new BusinessException("更新询价单位失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }


}
