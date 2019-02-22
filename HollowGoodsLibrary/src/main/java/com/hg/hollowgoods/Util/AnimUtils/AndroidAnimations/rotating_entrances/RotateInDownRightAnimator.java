package com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_entrances;

import android.view.View;

import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

public class RotateInDownRightAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        float x = target.getWidth() - target.getPaddingRight();
        float y = target.getHeight() - target.getPaddingBottom();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "rotation", 90, 0),
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                ObjectAnimator.ofFloat(target, "pivotX", x, x),
                ObjectAnimator.ofFloat(target, "pivotY", y, y)
        );
    }
}
