package com.fdkj.ysps.api.model.xm;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 项目审核请求参数
 *
 * @author wyt
 */
@Data
@Accessors(chain = true)
public class XmReview {
    /**
     * 项目id
     */
    @NotBlank(message = "项目id不能为空")
    private String id;

    /**
     * 条件 同意/退回
     */
    @NotBlank(message = "审核条件不能为空")
    private String tj;

    /**
     * 内容
     */
    @NotBlank(message = "审核意见不能为空")
    private String nr;
}
