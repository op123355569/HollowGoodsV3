package com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemMode;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.SingleChoiceMode;

import org.xutils.http.HttpMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文字模板
 * Created by Hollow Goods on 2019-05-07.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HGFastItemWord {

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
     * 支持Input SingleChoice Customize
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

    // 特有属性

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
     * 单选模式
     * 默认 Local
     *
     * @return SingleChoiceMode
     */
    SingleChoiceMode singleChoiceMode() default SingleChoiceMode.Local;

    /**
     * 单选数组名称
     *
     * @return String
     */
    String singleChoiceName() default "";

    /**
     * 单选数组名称类
     *
     * @return Class<?>
     */
    Class<?> singleChoiceNameClass() default HGFastItemWord.class;

    /**
     * 网络请求方式
     * 默认 Get
     *
     * @return HttpMethod
     */
    HttpMethod httpMethod() default HttpMethod.GET;

    /**
     * 网络请求参数变量名
     *
     * @return String
     */
    String singleChoiceNetRequestParamName() default "";

    /**
     * 网络请求数据键名称
     * 例如：{"data":[]},此时应填 data
     *
     * @return String
     */
    String singleChoiceNetDataKeyName() default "";

    /**
     * 网络请求数据值名称
     * 例如：{"data":[{"name":"张三"}]},此时应填 name
     *
     * @return String
     */
    String singleChoiceNetDataValueName() default "";

    /**
     * 网络请求数据值的描述名称
     * 例如：{"data":[{"describe":"他是个好人"}]},此时应填 describe
     *
     * @return String
     */
    String singleChoiceNetDataValueDescribeName() default "";

    /**
     * 网络数据接收封装类类型变量名称
     *
     * @return String
     */
    String singleChoiceNetDataTypeName() default "";

}
