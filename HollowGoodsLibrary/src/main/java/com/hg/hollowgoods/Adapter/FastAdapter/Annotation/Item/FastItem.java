package com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item;

import com.hg.hollowgoods.Adapter.FastAdapter.Constant.FastItemMode;
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
 * Created by HG on 2018-06-13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastItem {

    /**
     * 是否必填
     * 默认非必填
     *
     * @return
     */
    boolean isNotEmpty() default false;

    /**
     * 标签
     *
     * @return
     */
    String label() default "";

    /**
     * 是否需要内容
     * 默认 需要
     *
     * @return
     */
    boolean isNeedContent() default true;

    /**
     * 内容默认值
     *
     * @return
     */
    String contentHint() default "";

    /**
     * 排序号 必填
     * 同时也是点击事件的区分码
     *
     * @return
     */
    int sortNumber() default 0;

    /**
     * 翻译数组名称
     * ParamItem类中变量名
     * 区分大小写
     *
     * @return
     */
    String itemsName() default "";

    /**
     * 左侧图标资源
     * 默认无图标
     *
     * @return
     */
    int leftIconRes() default -1;

    /**
     * 右侧图标资源
     * 默认 根据mode自动填充
     *
     * @return
     */
    int rightIconRes() default -1;

    /**
     * 模式
     * 默认 输入模式
     *
     * @return
     */
    FastItemMode mode() default FastItemMode.Input;

    /**
     * 上间距 单位 dp
     * 默认0
     *
     * @return
     */
    int marginTop() default 0;

    /**
     * 绑定可见性控制的变量名
     *
     * @return
     */
    String visible() default "";

    /**
     * 是否是日期
     *
     * @return
     */
    boolean isDate() default false;

    /**
     * 日期格式化
     *
     * @return
     */
    StringUtils.DateFormatMode dateFormatMode() default StringUtils.DateFormatMode.LINE_YMDHMS;

    /**
     * 字体颜色资源变量名
     *
     * @return
     */
    String textColorResName() default "";

    /**
     * 是否为自定义控件
     * 默认否
     * 适用于int类型
     *
     * @return
     */
    boolean isCustomizeView() default false;

}
