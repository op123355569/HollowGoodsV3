package com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Item.ItemHGFastItemWord;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义模板
 * Created by Hollow Goods on 2019-05-09.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HGFastItemCustomize {

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
     * 绑定可见性控制的变量名
     *
     * @return String
     */
    String visibleName() default "";

    // 特有属性

    /**
     * 布局类型 必填
     *
     * @return int
     */
    int itemType() default 0;

    /**
     * 布局类
     *
     * @return Class<?>
     */
    Class<? extends ItemViewDelegate<CommonBean>> itemClass() default ItemHGFastItemWord.class;

}
