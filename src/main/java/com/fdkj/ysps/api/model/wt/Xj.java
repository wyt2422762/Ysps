package com.fdkj.ysps.api.model.wt;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * 询价
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class Xj {
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
     * 项目id
     */
    @NotBlank(message = "项目id不能为空")
    private String fk_xmid;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 机构资质
     */
    private String jgzz;

    /**
     * 联系人
     */
    private String lxr;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 报价
     */
    private String bj;

    /**
     * 是否中标
     */
    private String sfzb = "否";

    /**
     * 转map
     * @return res
     * @throws Exception err
     */
    public Map<String, Object> toMap() throws Exception {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(this);
        Map<String,Object> resultMap=new HashMap<>(descriptors.length);
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            if("class".equals(name)){
                continue;
            }
            Object val = propertyUtilsBean.getNestedProperty(this, name);
            if(val != null && StringUtils.isNotBlank(val.toString())){
                resultMap.put(name,val);
            }
        }
        return resultMap;
    }
}
