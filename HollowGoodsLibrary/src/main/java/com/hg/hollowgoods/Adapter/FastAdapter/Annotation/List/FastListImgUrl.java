package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 快速适配器列表图片
 * 适用于变量
 * Created by Hollow Goods 2018-06-13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastListImgUrl {

    /**
     * 是否加载的是图片
     * 默认 是
     *
     * @return
     */
    boolean isPicture() default true;

}
