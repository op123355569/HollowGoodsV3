package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 快速适配器列表标题
 * 适用于变量
 * Created by HG on 2018-06-13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastListTitle {

    /**
     * 翻译数组名称
     * ParamItem类中变量名
     * 区分大小写
     *
     * @return
     */
    String itemsName() default "";

}
