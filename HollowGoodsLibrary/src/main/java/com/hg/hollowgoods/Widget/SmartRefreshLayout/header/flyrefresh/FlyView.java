package com.hg.hollowgoods.Widget.SmartRefreshLayout.header.flyrefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.hg.hollowgoods.Widget.SmartRefreshLayout.header.internal.pathview.PathsView;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.util.DensityUtil;

/**
 * 纸飞机视图
 * <p>
 * Created by Hollow Goods on unknown.
 */
public class FlyView extends PathsView {

    public FlyView(Context context) {
        this(context, null);
    }

    public FlyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.parserColors(0xffffffff);
        if (!mPathsDrawable.parserPaths("M2.01,21L23,12 2.01,3 2,10l15,2 -15,2z")) {
            mPathsDrawable.declareOriginal(2, 3, 20, 18);
        }
        int side = DensityUtil.dp2px(25);
        mPathsDrawable.setBounds(0, 0, side, side);
    }
}
