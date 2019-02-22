package com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.in;

import android.view.View;

import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.BaseViewAnimator;
import com.hg.hollowgoods.Util.AnimUtils.Easing.Glider;
import com.hg.hollowgoods.Util.AnimUtils.Easing.Skill;
import com.nineoldandroids.animation.ObjectAnimator;

public class DropOutAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        int distance = target.getTop() + target.getHeight();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                Glider.glide(Skill.BounceEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "translationY", -distance, 0))
        );
    }
}
