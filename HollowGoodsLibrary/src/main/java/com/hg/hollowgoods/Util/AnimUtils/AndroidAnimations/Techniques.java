package com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations;

import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.BounceAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.FlashAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.PulseAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.RubberBandAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.ShakeAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.StandUpAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.SwingAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.TadaAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.WaveAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.attention.WobbleAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.bouncing_entrances.BounceInAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.bouncing_entrances.BounceInDownAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.bouncing_entrances.BounceInLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.bouncing_entrances.BounceInRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.bouncing_entrances.BounceInUpAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_entrances.FadeInAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_entrances.FadeInDownAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_entrances.FadeInLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_entrances.FadeInRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_entrances.FadeInUpAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_exits.FadeOutAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_exits.FadeOutDownAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_exits.FadeOutLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_exits.FadeOutRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.fading_exits.FadeOutUpAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.flippers.FlipInXAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.flippers.FlipInYAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.flippers.FlipOutXAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.flippers.FlipOutYAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_entrances.RotateInAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_entrances.RotateInDownLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_entrances.RotateInDownRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_entrances.RotateInUpLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_entrances.RotateInUpRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_exits.RotateOutAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_exits.RotateOutDownLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_exits.RotateOutDownRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_exits.RotateOutUpLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.rotating_exits.RotateOutUpRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders.SlideInDownAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders.SlideInLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders.SlideInRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders.SlideInUpAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders.SlideOutDownAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders.SlideOutLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders.SlideOutRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.sliders.SlideOutUpAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.HingeAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.RollInAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.RollOutAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.in.DropOutAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.in.LandingAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.out.TakingOffAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_entrances.ZoomInAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_entrances.ZoomInDownAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_entrances.ZoomInLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_entrances.ZoomInRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_entrances.ZoomInUpAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_exits.ZoomOutAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_exits.ZoomOutDownAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_exits.ZoomOutLeftAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_exits.ZoomOutRightAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {

    DropOut(DropOutAnimator.class),
    Landing(LandingAnimator.class),
    TakingOff(TakingOffAnimator.class),

    Flash(FlashAnimator.class),
    Pulse(PulseAnimator.class),
    RubberBand(RubberBandAnimator.class),
    Shake(ShakeAnimator.class),
    Swing(SwingAnimator.class),
    Wobble(WobbleAnimator.class),
    Bounce(BounceAnimator.class),
    Tada(TadaAnimator.class),
    StandUp(StandUpAnimator.class),
    Wave(WaveAnimator.class),

    Hinge(HingeAnimator.class),
    RollIn(RollInAnimator.class),
    RollOut(RollOutAnimator.class),

    BounceIn(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInLeft(BounceInLeftAnimator.class),
    BounceInRight(BounceInRightAnimator.class),
    BounceInUp(BounceInUpAnimator.class),

    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),

    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),

    FlipInX(FlipInXAnimator.class),
    FlipOutX(FlipOutXAnimator.class),
    FlipInY(FlipInYAnimator.class),
    FlipOutY(FlipOutYAnimator.class),
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),

    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    SlideInLeft(SlideInLeftAnimator.class),
    SlideInRight(SlideInRightAnimator.class),
    SlideInUp(SlideInUpAnimator.class),
    SlideInDown(SlideInDownAnimator.class),

    SlideOutLeft(SlideOutLeftAnimator.class),
    SlideOutRight(SlideOutRightAnimator.class),
    SlideOutUp(SlideOutUpAnimator.class),
    SlideOutDown(SlideOutDownAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);


    private Class animatorClazz;

    private Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
