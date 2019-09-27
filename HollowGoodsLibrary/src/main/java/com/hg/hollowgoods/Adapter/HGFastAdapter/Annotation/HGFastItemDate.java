package com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemMode;
import com.hg.hollowgoods.Util.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日期模板
 * Created by Hollow Goods on 2019-05-10.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HGFastItemDate {

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
     * 模式
     * 默认 Customize
     * 支持SingleChoice Customize
     *
     * @return ItemMode
     */
    ItemMode itemMode() default ItemMode.Customize;

    /**
     * 是否必填
     * 默认非必填
     *
     * @return boolean
     */
    boolean isNotEmpty() default false;

    /**
     * 标签
     *
     * @return String
     */
    String label() default "";

    /**
     * 左侧图标资源
     * 默认无图标
     *
     * @return int
     */
    int leftIconRes() default -1;

    /**
     * 左侧图标资源类型
     * 默认无图标
     * leftIconRes不可用的情况下使用
     *
     * @return String
     */
    String leftIconResType() default "";

    /**
     * 左侧图标资源名称
     * 默认无图标
     * leftIconRes不可用的情况下使用
     *
     * @return String
     */
    String leftIconResName() default "";

    /**
     * 右侧图标资源
     * 默认 根据mode自动填充
     *
     * @return int
     */
    int rightIconRes() default -1;

    /**
     * 右侧图标资源类型
     * 默认无图标
     * rightIconRes不可用的情况下使用
     *
     * @return String
     */
    String rightIconResType() default "";

    /**
     * 右侧图标资源名称
     * 默认无图标
     * rightIconRes不可用的情况下使用
     *
     * @return String
     */
    String rightIconResName() default "";

    /**
     * 绑定可见性控制的变量名
     *
     * @return String
     */
    String visibleName() default "";

    /**
     * 标签字体颜色资源变量名
     *
     * @return String
     */
    String labelTextColorResName() default "";

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

    /**
     * 上下内间距 单位 dp
     * 默认16
     *
     * @return int
     */
    int paddingTopAndBottom() default 16;

    /**
     * 内容默认值
     *
     * @return String
     */
    String contentHint() default "";

    /**
     * 内容字体颜色资源变量名
     *
     * @return String
     */
    String contentTextColorResName() default "";

    /**
     * 单选数组名称
     *
     * @return String
     */
    @Deprecated
    String singleChoiceName() default "";

    /**
     * 单选数组名称类
     *
     * @return Class<?>
     */
    @Deprecated
    Class<?> singleChoiceNameClass() default HGFastItemDate.class;

    // 特有属性

    /**
     * 日期格式化格式
     *
     * @return StringUtils.DateFormatMode
     */
    StringUtils.DateFormatMode dateFormatMode() default StringUtils.DateFormatMode.LINE_YMD;

}
