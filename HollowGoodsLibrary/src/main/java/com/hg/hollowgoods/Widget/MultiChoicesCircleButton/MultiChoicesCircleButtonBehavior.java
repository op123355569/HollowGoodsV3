package com.hg.hollowgoods.Widget.MultiChoicesCircleButton;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class MultiChoicesCircleButtonBehavior extends CoordinatorLayout.Behavior<MultiChoicesCircleButton> {

    public MultiChoicesCircleButtonBehavior() {
        super();
    }

    public MultiChoicesCircleButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, MultiChoicesCircleButton child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, MultiChoicesCircleButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        if (dyConsumed > 0 || dyUnconsumed > 0) {
            child.hide();
        } else if (dyConsumed < 0 || dyUnconsumed < 0) {
            child.show();
        }
    }
}
