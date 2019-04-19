package com.hg.hollowgoods.Widget.CommonTitle;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

/**
 * Created by Hollow Goods on 2019-04-19.
 */
public abstract class BaseCommonTitle extends AppBarLayout implements IBaseCommonTitle {

    public final float DEFAULT_TEXT_SIZE = 16f;
    public final float DEFAULT_RIGHT_TEXT_SIZE = 14f;
    public final float DEFAULT_RIGHT_TEXT_MARGIN = 16f;
    public final int DEFAULT_TEXT_COLOR = Color.WHITE;

    public BaseCommonTitle(Context context) {
        this(context, null);
    }

    public BaseCommonTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
