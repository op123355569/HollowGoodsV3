package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item;

import com.hg.hollowgoods.Adapter.FastAdapter.Constant.FastItemMode;
import com.hg.hollowgoods.Adapter.FastAdapter.Constant.ParamItem;
import com.hg.hollowgoods.Util.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 快速适配器详情项
 * 适用于变量
 * Debug模式自动显示排序号，Release模式自动隐藏排序号
 * Created by Hollow Goods 2018-06-13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastItem {

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
     * 是否需要内容
     * 默认 需要
     *
     * @return boolean
     */
    boolean isNeedContent() default true;

    /**
     * 内容默认值
     *
     * @return String
     */
    String contentHint() default "";

    /**
     * 排序号 必填
     * 同时也是点击事件的区分码
     *
     * @return int
     */
    int sortNumber() default 0;

    /**
     * 翻译数组名称
     * ParamItem类中变量名
     * 区分大小写
     *
     * @return String
     */
    String itemsName() default "";

    /**
     * 左侧图标资源
     * 默认无图标
     *
     * @return int
     */
    int leftIconRes() default -1;

    /**
     * 左侧图标资源名称
     * 默认 空
     *
     * @return String
     */
    String leftIconName() default "";

    /**
     * 左侧图标资源名称源类
     * 默认 ParamItem.class
     *
     * @return Class<?>
     */
    Class<?> leftIconNameClass() default ParamItem.class;

    /**
     * 右侧图标资源
     * 默认 根据mode自动填充
     *
     * @return int
     */
    int rightIconRes() default -1;

    /**
     * 右侧图标资源名称
     * 默认 空
     *
     * @return String
     */
    String rightIconName() default "";

    /**
     * 右侧图标资源名称源类
     * 默认 ParamItem.class
     *
     * @return Class<?>
     */
    Class<?> rightIconNameClass() default ParamItem.class;

    /**
     * 模式
     * 默认 输入模式
     *
     * @return FastItemMode
     */
    FastItemMode mode() default FastItemMode.Input;

    /**
     * 上间距 单位 dp
     * 默认0
     *
     * @return int
     */
    int marginTop() default 0;

    /**
     * 下间距间距 单位 dp
     * 默认0
     *
     * @return int
     */
    int marginBottom() default 0;

    /**
     * 绑定可见性控制的变量名
     *
     * @return String
     */
    String visible() default "";

    /**
     * 是否是日期
     *
     * @return boolean
     */
    boolean isDate() default false;

    /**
     * 日期格式化
     *
     * @return StringUtils.DateFormatMode
     */
    StringUtils.DateFormatMode dateFormatMode() default StringUtils.DateFormatMode.LINE_YMDHMS;

    /**
     * 字体颜色资源变量名
     *
     * @return String
     */
    String textColorResName() default "";

    /**
     * 是否为自定义控件
     * 默认否
     * 适用于int类型
     *
     * @return boolean
     */
    boolean isCustomizeView() default false;

    /**
     * 是否为自定义内容控件
     * 默认否
     * 适用于int类型
     *
     * @return boolean
     */
    boolean isCustomizeContentView() default false;

}
