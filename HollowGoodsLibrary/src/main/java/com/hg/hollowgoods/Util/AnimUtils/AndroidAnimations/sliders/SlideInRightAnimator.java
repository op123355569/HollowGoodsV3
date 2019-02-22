package com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders;

import android.view.View;
import android.view.ViewGroup;

import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

public class SlideInRightAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        ViewGroup parent = (ViewGroup) target.getParent();
        int distance = parent.getWidth() - target.getLeft();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                ObjectAnimator.ofFloat(target, "translationX", distance, 0)
        );
    }
}
