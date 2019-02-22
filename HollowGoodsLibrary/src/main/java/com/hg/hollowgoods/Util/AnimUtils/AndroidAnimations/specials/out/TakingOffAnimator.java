package com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.out;

import android.view.View;

import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.BaseViewAnimator;
import com.hg.hollowgoods.Util.AnimUtils.Easing.Glider;
import com.hg.hollowgoods.Util.AnimUtils.Easing.Skill;
import com.nineoldandroids.animation.ObjectAnimator;

public class TakingOffAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 1, 0))
        );
    }
}
