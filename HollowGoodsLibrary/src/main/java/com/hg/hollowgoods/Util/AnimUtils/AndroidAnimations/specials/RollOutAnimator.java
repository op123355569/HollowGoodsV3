package com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials;

import android.view.View;

import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

public class RollOutAnimator extends BaseViewAnimator {

    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0),
                ObjectAnimator.ofFloat(target, "translationX", 0, target.getWidth()),
                ObjectAnimator.ofFloat(target, "rotation", 0, 120)
        );
    }

}
