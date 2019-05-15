package com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.FileMode;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemMode;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文件模板
 * Created by Hollow Goods on 2019-05-09.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HGFastItemFile {

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
     * 支持File Customize
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
     * 右侧图标资源
     * 默认 根据mode自动填充
     *
     * @return int
     */
    int rightIconRes() default -1;

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
     * 文件模式
     * 默认打开相机 或 打开相册
     *
     * @return FileMode
     */
    FileMode fileMode() default FileMode.OpenCameraOrAlbum;

    /**
     * 最大数量
     * 默认 1
     * 设置为<1的数值代表无上限
     *
     * @return int
     */
    int maxCount() default 1;

    /**
     * 压缩质量 0-100
     * 默认50
     *
     * @return int
     */
    int quality() default 50;

    /**
     * 文件筛选格式 例如".doc,.docx"
     * 默认空(所有格式都加载)
     *
     * @return String
     */
    String fileFilter() default "";

}
