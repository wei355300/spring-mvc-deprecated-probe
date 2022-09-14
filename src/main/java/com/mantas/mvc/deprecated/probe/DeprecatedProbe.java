package com.mantas.mvc.deprecated.probe;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface DeprecatedProbe {

    /**
     * 标记描述
     */
    String value() default "";

    /**
     * 版本标记
     * 可设置为项目的版本号
     */
    String version() default "";

    /**
     * namespace: 用于区分多项目的情况下, 存在重复的情况
     */
    String ns() default "";
}
