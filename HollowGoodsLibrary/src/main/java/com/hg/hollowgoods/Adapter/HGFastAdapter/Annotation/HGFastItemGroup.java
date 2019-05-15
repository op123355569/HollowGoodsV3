package com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分组模板
 * Created by Hollow Goods on 2019-05-14.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HGFastItemGroup {

    /**
     * 排序号 必填
     *
     * @return int
     */
    int sortNumber() default 0;

    /**
     * id 必填
     * 用于区分码点击事件
     *
     * @return int
     */
    int id() default 0;

    /**
     * 上间距 单位 dp
     * 默认0
     *
     * @return int
     */
    int marginTop() default 0;

    /**
     * 左间距 单位 dp
     * 默认0
     *
     * @return int
     */
    int marginLeft() default 0;

    /**
     * 下间距间距 单位 dp
     * 默认0
     *
     * @return int
     */
    int marginBottom() default 0;

    /**
     * 右间距 单位 dp
     * 默认0
     *
     * @return int
     */
    int marginRight() default 0;

    // 特有属性

    /**
     * 组员id集合
     * 默认{}
     *
     * @return int
     */
    int[] groupItemIds() default {};

    /**
     * 是否需要卡片背景
     */
    boolean isNeedCardView() default true;

}
