package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 快速适配器详情项数字选择器
 * 适用于变量
 * Created by HG on 2018-06-13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastItemNumberPicker {

    /**
     * 上限
     * 默认不限
     * 必须填数字
     *
     * @return
     */
    String max() default "";

    /**
     * 下限
     * 默认不限
     * 必须填数字
     *
     * @return
     */
    String min() default "";

    /**
     * 差值
     * 默认1
     * 必须填数字
     *
     * @return
     */
    String dif() default "1";

    /**
     * 数字类型
     * 默认整型
     *
     * @return
     */
    Class<?> type() default Integer.class;

    /**
     * 小数位数
     * 默认0
     *
     * @return
     */
    int pointCount() default 0;

}
