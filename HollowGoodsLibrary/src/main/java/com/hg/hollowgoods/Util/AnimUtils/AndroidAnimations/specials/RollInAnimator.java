package com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials;

import android.view.View;

import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

public class RollInAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                ObjectAnimator.ofFloat(target, "translationX", -(target.getWidth() - target.getPaddingLeft() - target.getPaddingRight()), 0),
                ObjectAnimator.ofFloat(target, "rotation", -120, 0)
        );
    }
}
