package com.fdkj.ysps.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author wyt
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String module() default "";

    /**
     * 操作说明
     */
    String desc() default "";

    /**
     * 操作类型
     */
    String optType() default "";
}
