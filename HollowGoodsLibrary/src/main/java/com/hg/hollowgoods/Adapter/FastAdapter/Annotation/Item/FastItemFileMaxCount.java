package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 快速适配器详情项文件最大数量
 * 适用于变量
 * Created by Hollow Goods 2018-06-13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastItemFileMaxCount {

    /**
     * 最大数量
     * 默认1
     *
     * @return
     */
    int maxCount() default 1;

}
