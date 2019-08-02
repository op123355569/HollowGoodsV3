package com.hg.hollowgoods.UI.Fragment.Plugin;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;

/**
 * 轮播图助手
 * Created by Hollow Goods on 2019-08-01.
 */
public interface BannerHelper {

    /**
     * 默认轮播图显示的资源组
     * 默认null
     *
     * @return int[]
     */
    default int[] defaultBannerRes() {
        return null;
    }

    /**
     * 绑定轮播图布局<p>
     * 在布局中，一定要申明以下两个id<p>
     * 1. banner_all 用于确定点击事件的范围<p>
     * 2. banner_img 用于填充图片
     *
     * @return int
     */
    @LayoutRes
    int bindBannerItem();

    /**
     * 指示器与顶部的间距<p>
     * 默认0dp
     *
     * @return float 单位dp
     */
    default float indicatorMarginTopDp() {
        return 0F;
    }

    /**
     * 指示器与底部的间距<p>
     * 默认8dp
     *
     * @return float 单位dp
     */
    default float indicatorMarginBottomDp() {
        return 8F;
    }

    /**
     * 指示器与左部的间距<p>
     * 默认0dp
     *
     * @return float 单位dp
     */
    default float indicatorMarginStartDp() {
        return 0F;
    }

    /**
     * 指示器与右部的间距<p>
     * 默认0dp
     *
     * @return float 单位dp
     */
    default float indicatorMarginEndDp() {
        return 0F;
    }

    /**
     * 指示器的位置<p>
     * 默认 垂直方向底部，水平方向居中
     *
     * @return Gravity
     */
    default int indicatorGravity() {
        return Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    }

    /**
     * 轮播图轮播时缩放比例<p>
     * 默认1F 即不进行缩放
     *
     * @return float
     */
    @FloatRange(from = 0.01)
    default float minScale() {
        return 1F;
    }

    /**
     * 轮播图自动轮播间隔时长
     *
     * @return long
     */
    @IntRange(from = 500)
    long bannerPlayDuration();

    /**
     * 获取轮播图数据<p>
     * 该方法主要用于获取网络数据<p>
     * 本地数据可不写改方法的逻辑
     */
    void getData();

    /**
     * Banner点击事件
     *
     * @param view     View
     * @param position int
     */
    void onBannerClick(View view, int position);
}
