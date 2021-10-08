package com.fdkj.ysps.controller.xm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fdkj.ysps.annotation.Log;
import com.fdkj.ysps.api.model.system.User;
import com.fdkj.ysps.api.model.system.Zd;
import com.fdkj.ysps.api.model.xm.Xm;
import com.fdkj.ysps.api.model.xm.XmFj;
import com.fdkj.ysps.api.model.xm.XmReq;
import com.fdkj.ysps.api.model.xm.XmReview;
import com.fdkj.ysps.api.util.DictApi;
import com.fdkj.ysps.api.util.XmApi;
import com.fdkj.ysps.base.CusResponseBody;
import com.fdkj.ysps.base.Page;
import com.fdkj.ysps.config.BusConfig;
import com.fdkj.ysps.constant.Constants;
import com.fdkj.ysps.controller.BaseController;
import com.fdkj.ysps.error.BusinessException;
import com.fdkj.ysps.utils.DateUtils;
import com.fdkj.ysps.utils.StringUtils;
import com.fdkj.ysps.utils.file.FileUploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目controller
 *
 * @author wyt
 */
@Controller
@RequestMapping("CZF/XMGL")
@Log(module = "项目")
public class XmController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(XmController.class);

    @Autowired
    private XmApi xmApi;
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
        request.setAttribute("cuser", xmApi.getUserFromCookie(request));
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

        return new ModelAndView("xmMgr/xm_index");
    }

    /**
     * 跳转
     *
     * @param request req
     * @param zt      状态
     * @param opts    操作权限
     * @return res
     * @throws Exception err
     */
    @RequestMapping("{zt}/Index")
    public ModelAndView indexZt(HttpServletRequest request, @PathVariable String zt,
                                @RequestParam(value = "opts", required = false) List<String> opts) throws Exception {
        request.setAttribute("cuser", xmApi.getUserFromCookie(request));
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

        //状态
        if (StringUtils.isNotBlank(zt)) {
            request.setAttribute("zt", zt);
        }

        return new ModelAndView("xmMgr/xm_index");
    }

    /**
     * 跳转到添加页面
     *
     * @param request req
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toAdd")
    public ModelAndView toAdd(HttpServletRequest request) throws Exception {
        request.setAttribute("cuser", xmApi.getUserFromCookie(request));
        //获取字典信息
        //项目分类
        Map<String, Object> dictParams = new HashMap<>();
        dictParams.put("fid", Constants.Dict.xmfl);
        List<Zd> dict_xmfl = dictApi.getZdList(request, dictParams);
        request.setAttribute("dict_xmfl", dict_xmfl);
        dictParams.clear();
        //项目性质
        dictParams.put("fid", Constants.Dict.xmxz);
        List<Zd> dict_xmxz = dictApi.getZdList(request, dictParams);
        request.setAttribute("dict_xmxz", dict_xmxz);
        return new ModelAndView("xmMgr/xm_add");
    }

    /**
     * 跳转到编辑页面
     *
     * @param request req
     * @param id      项目id
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toEdit/{id}")
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable String id) throws Exception {
        request.setAttribute("cuser", xmApi.getUserFromCookie(request));
        request.setAttribute("id", id);
        //获取字典信息
        //项目分类
        Map<String, Object> dictParams = new HashMap<>();
        dictParams.put("fid", Constants.Dict.xmfl);
        List<Zd> dict_xmfl = dictApi.getZdList(request, dictParams);
        request.setAttribute("dict_xmfl", dict_xmfl);
        dictParams.clear();
        //项目性质
        dictParams.put("fid", Constants.Dict.xmxz);
        List<Zd> dict_xmxz = dictApi.getZdList(request, dictParams);
        request.setAttribute("dict_xmxz", dict_xmxz);
        return new ModelAndView("xmMgr/xm_edit");
    }

    /**
     * 跳转到编辑页面
     *
     * @param request req
     * @param id      项目id
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toInfo/{id}")
    public ModelAndView toInfo(HttpServletRequest request, @PathVariable String id) throws Exception {
        request.setAttribute("cuser", xmApi.getUserFromCookie(request));
        request.setAttribute("id", id);
        //获取字典信息
        //项目分类
        Map<String, Object> dictParams = new HashMap<>();
        dictParams.put("fid", Constants.Dict.xmfl);
        List<Zd> dict_xmfl = dictApi.getZdList(request, dictParams);
        request.setAttribute("dict_xmfl", dict_xmfl);
        dictParams.clear();
        //项目性质
        dictParams.put("fid", Constants.Dict.xmxz);
        List<Zd> dict_xmxz = dictApi.getZdList(request, dictParams);
        request.setAttribute("dict_xmxz", dict_xmxz);
        return new ModelAndView("xmMgr/xm_info");
    }

    /**
     * 跳转到审核页面1
     * @param request req
     * @param id 项目id
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toReview1/{id}")
    public ModelAndView toReview1(HttpServletRequest request, @PathVariable String id) throws Exception {
        request.setAttribute("id", id);
        return new ModelAndView("xmMgr/xm_review1");
    }

    /**
     * 跳转到审核页面2
     * @param request req
     * @param id 项目id
     * @return res
     * @throws Exception err
     */
    @RequestMapping("toReview2/{id}")
    public ModelAndView toReview2(HttpServletRequest request, @PathVariable String id) throws Exception {
        request.setAttribute("id", id);
        return new ModelAndView("xmMgr/xm_review2");
    }

    /**
     * 获取方案列表(分页)
     *
     * @param request req
     * @param xmReq   请求参数
     * @param xm      请求体
     * @param page    第几页
     * @param limit   每页显示的记录数
     * @return res
     */
    @RequestMapping("getList")
    @ResponseBody
    @Log(module = "项目", desc = "获取方案列表(分页)", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getList(HttpServletRequest request, XmReq xmReq, Xm xm,
                                                   @RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        try {
            User cuser = xmApi.getUserFromCookie(request);
            Page<Xm> xmList = xmApi.getXmList(request, xmReq.toReqJson(), xm.toReqJson(), page, limit);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("获取项目列表成功", xmList);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取项目列表失败", e);
            throw new BusinessException("获取项目列表失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 获取项目编号
     *
     * @param request req
     * @return res
     */
    @RequestMapping("getXmbh")
    @ResponseBody
    @Log(module = "项目", desc = "获取项目编号", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getXmBh(HttpServletRequest request) {
        try {
            String xmbh;

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String nowTime = sf.format(new Date());

            Page<Xm> xmList = xmApi.getXmList(request, null, null, 1, 1);
            List<Xm> dataList = xmList.getDataList();
            if (dataList != null && !dataList.isEmpty()) {
                Xm xm = dataList.get(0);
                String xmbh_pre = xm.getXmbh();
                //获取最新项目编号的日期
                String date = xmbh_pre.substring(0, 10);
                //如果是新的一天的就直接变成0001
                if (!nowTime.equals(date)) {
                    xmbh = nowTime + "-00001";
                } else {//如果不是新的一天则递增
                    DecimalFormat df = new DecimalFormat("00000");
                    xmbh = nowTime + "-" + df.format(1 + Integer.parseInt(xmbh_pre.substring(11, xmbh_pre.length())));
                }
            } else {
                xmbh = nowTime + "-00001";
            }
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("获取项目编号成功", xmbh);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取项目编号失败", e);
            throw new BusinessException("获取项目编号失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 获取项目详情
     *
     * @param request req
     * @param id      项目id
     * @return res
     */
    @RequestMapping("getDetail/{id}")
    @ResponseBody
    @Log(module = "项目", desc = "获取项目详情", optType = Constants.OptType.SELECT)
    public ResponseEntity<CusResponseBody> getDetail(HttpServletRequest request, @PathVariable String id) {
        try {
            JSONObject xmDetail = xmApi.getXmDetail(request, id);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("获取项目详情成功", xmDetail);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取项目详情失败", e);
            throw new BusinessException("获取项目详情失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 添加项目
     *
     * @param request req
     * @param json    请求体
     * @return res
     */
    @RequestMapping("addXm")
    @ResponseBody
    @Log(module = "项目", desc = "添加项目", optType = Constants.OptType.ADD)
    public ResponseEntity<CusResponseBody> addXm(HttpServletRequest request,
                                                 @RequestBody JSONObject json) {
        return aeXm(request, json);
    }

    /**
     * 编辑项目
     *
     * @param request req
     * @param json    请求体
     * @return res
     */
    @RequestMapping("editXm")
    @ResponseBody
    @Log(module = "项目", desc = "添加项目", optType = Constants.OptType.EDIT)
    public ResponseEntity<CusResponseBody> editXm(HttpServletRequest request,
                                                  @RequestBody JSONObject json) {
        return aeXm(request, json);
    }

    private ResponseEntity<CusResponseBody> aeXm(HttpServletRequest request,
                                                 JSONObject json) {
        try {
            User cuser = xmApi.getUserFromCookie(request);
            String dateToStr = DateUtils.parseDateToStr("yyyy-MM-dd'T'HH:mm:ss.sss", new Date());

            //项目基本信息
            Xm model = json.getObject("model", Xm.class);
            //项目附件
            List<XmFj> fjList = json.getJSONArray("list").toJavaList(XmFj.class);

            //判断项目名称是否存在
            boolean res = checkXmmc(request, model.getXmmc(), model.getId());
            if (!res) {
                throw new BusinessException("项目名称已存在", HttpStatus.BAD_REQUEST.value());
            }

            if (StringUtils.isBlank(model.getFk_xtglid())) {
                model.setFk_xtglid(cuser.getFk_xtglid());
            }
            if (StringUtils.isBlank(model.getAddtime())) {
                model.setAddtime(dateToStr);
            }
            if (StringUtils.isBlank(model.getWtsj())) {
                model.setWtsj(dateToStr);
            } else {
                model.setWtsj(DateUtils.parseDateToStr("yyyy-MM-dd'T'HH:mm:ss.sss", DateUtils.dateTime("yyyy-MM-dd", model.getWtsj())));
            }

            if (fjList != null && !fjList.isEmpty()) {
                for (XmFj xmFj : fjList) {
                    //xmFj.setFk_xmid(model.getId());
                    xmFj.setFk_xtglid(cuser.getFk_xtglid());
                    //附件id
                    //xmFj.setId(IdUtils.randomUUID());
                }
            }

            JSONObject jsonReq = new JSONObject();
            jsonReq.put("model", JSONObject.parseObject(JSONObject.toJSONString(model)));
            jsonReq.put("list", JSONArray.parseArray(JSONArray.toJSONString(fjList)));

            xmApi.aeXm(request, jsonReq);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("更新项目成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("更新项目失败", e);
            throw new BusinessException("更新项目失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 删除项目
     *
     * @param request req
     * @param id      项目id
     * @return res
     */
    @RequestMapping("delXm/{id}")
    @ResponseBody
    @Log(module = "项目", desc = "删除项目", optType = Constants.OptType.DEL)
    public ResponseEntity<CusResponseBody> del(HttpServletRequest request, @PathVariable String id) {
        try {
            xmApi.delXm(request, id.trim());
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("删除项目成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("删除项目失败", e);
            throw new BusinessException("删除项目失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 审核项目
     *
     * @param request  req
     * @param xmReview 审核参数
     * @return res
     */
    @RequestMapping("reviewXm")
    @ResponseBody
    @Log(module = "项目", desc = "审核项目", optType = Constants.OptType.REVIEW)
    public ResponseEntity<CusResponseBody> review(HttpServletRequest request, @Validated @ModelAttribute XmReview xmReview) {
        try {
            xmApi.reviewXm(request, xmReview);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("审核项目成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("审核项目失败", e);
            throw new BusinessException("审核项目失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 上传文件
     *
     * @param file 文件(多个)
     * @param type 文件类型
     * @return res
     */
    @RequestMapping("upload")
    @ResponseBody
    @Log(module = "项目", desc = "上传附件", optType = Constants.OptType.UPLOAD)
    public ResponseEntity<CusResponseBody> uploadFile(MultipartFile file, String type) {
        try {
            // 上传文件路径
            String filePath = BusConfig.getUploadBaseDir() + File.separator + "xm";

            //文件名
            String fileName = file.getOriginalFilename();
            // 上传并返回新文件路劲
            String fileNamePath = FileUploadUtils.upload(filePath, file);
            //构造附件实体类
            XmFj xmFj = new XmFj();
            xmFj.setFjmc(fileName).setFjdz(fileNamePath).setFjlx_zd(type);

            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("上传成功", xmFj);
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("上传失败", e);
            throw new BusinessException("上传失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }

    /**
     * 检查项目名称是否存在(true 不存在 false 存在)
     *
     * @param request req
     * @param xmmc    项目名称
     * @return res
     */
    private boolean checkXmmc(HttpServletRequest request, String xmmc, String id) throws Exception {
        boolean res = true;
        //更具项目名称请求数据
        JSONObject toReqJson = new Xm().setXmmc(xmmc).toReqJson();
        List<Xm> xmListAll = xmApi.getXmListAll(request, null, toReqJson);
        if (xmListAll != null && !xmListAll.isEmpty()) {
            for (Xm xm : xmListAll) {
                if (xm.getXmmc().equals(xmmc) && !xm.getId().equals(id)) {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 提交项目
     *
     * @param request req
     * @param id      项目id
     * @return res
     */
    @RequestMapping("tjXm/{id}")
    @ResponseBody
    @Log(module = "项目", desc = "提交审核", optType = Constants.OptType.TO_REVIEW)
    public ResponseEntity<CusResponseBody> tj(HttpServletRequest request, @PathVariable String id) {
        try {
            xmApi.tjXm(request, id);
            //构造返回数据
            CusResponseBody cusResponseBody = CusResponseBody.success("提交成功");
            return new ResponseEntity<>(cusResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("提交失败", e);
            throw new BusinessException("提交失败: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }
}
