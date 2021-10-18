package com.fdkj.ysps.api.model.wt;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打包
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class Db {
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
     * 打包项目名称
     */
    private String dbxmmc;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 机构资质
     */
    private String jgzz;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 中标价
     */
    private String zbj;

    /**
     * 中间表
     */
    private List<DbZjb> zjbs;

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
