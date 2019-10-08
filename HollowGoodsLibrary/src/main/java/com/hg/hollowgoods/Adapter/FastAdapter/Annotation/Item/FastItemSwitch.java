package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 快速适配器详情项开关
 * 适用于变量
 * 请标注于布尔类型的变量上
 * Created by Hollow Goods 2018-06-13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastItemSwitch {


}
