package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 快速适配器列表自动编号
 * 适用于类
 * Created by HG on 2018-06-13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastListAutoNumber {

    /**
     * 背景颜色
     *
     * @return
     */
    int backgroundColorRes() default -1;

}
