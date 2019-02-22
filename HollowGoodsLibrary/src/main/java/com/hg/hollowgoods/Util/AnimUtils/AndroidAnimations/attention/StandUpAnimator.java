package com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention;

import android.view.View;

import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

public class StandUpAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        float x = (target.getWidth() - target.getPaddingLeft() - target.getPaddingRight()) / 2
                + target.getPaddingLeft();
        float y = target.getHeight() - target.getPaddingBottom();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "pivotX", x, x, x, x, x),
                ObjectAnimator.ofFloat(target, "pivotY", y, y, y, y, y),
                ObjectAnimator.ofFloat(target, "rotationX", 55, -30, 15, -15, 0)
        );
    }
}
