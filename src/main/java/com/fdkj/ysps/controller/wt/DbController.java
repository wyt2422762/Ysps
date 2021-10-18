package com.fdkj.ysps.controller.wt;

import com.fdkj.ysps.annotation.Log;
import com.fdkj.ysps.api.model.system.User;
import com.fdkj.ysps.api.model.system.Zd;
import com.fdkj.ysps.api.model.wt.Db;
import com.fdkj.ysps.api.model.wt.Xj;
import com.fdkj.ysps.api.util.DbApi;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(DbController.class);

    @Autowired
    private XmApi xmApi;
    @Autowired
    private DbApi dbApi;
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
        request.setAttribute("cuser", dbApi.getUserFromCookie(request));
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

        return new ModelAndView("wtMgr/db/db_index");
    }

    /**
     * 获取打包列表(分页)
     *
     * @param request req
     * @param db      请求参数
     * @param page    第几页
     * @param limit   每页显示的记录数
     * @return res
     */
    @RequestMapping("getList")
    @ResponseBody
    @Log(module = "打包", desc = "获取打包列表(分页)", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getList(HttpServletRequest request, @Validated Db db,
                                                   @RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        try {
            User cuser = dbApi.getUserFromCookie(request);
            Page<Db> dbList = dbApi.getDbList(request, null, db.toMap(), page, limit);
            CusResponseBody cusResponseBody = CusResponseBody.success("获取打包列表成功", dbList);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取打包列表失败", e);
            throw new BusinessException("获取打包列表失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
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
    @Log(module = "打包", desc = "获取打包详情", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getDetail(HttpServletRequest request, @PathVariable String id) {
        try {
            User cuser = dbApi.getUserFromCookie(request);
            Db db = dbApi.getDbDetail(request, id);
            CusResponseBody cusResponseBody = CusResponseBody.success("获取打包详情成功", db);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取打包详情失败", e);
            throw new BusinessException("获取打包详情失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }


}
