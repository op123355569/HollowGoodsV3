package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 快速适配器列表编辑按钮
 * 适用于变量
 * 请标注于布尔类型的变量上
 * Created by Hollow Goods 2018-06-13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastListEdit {

    /**
     * 图标资源
     * 不设置即使用默认图标
     *
     * @return
     */
    int iconRes() default -1;

}
