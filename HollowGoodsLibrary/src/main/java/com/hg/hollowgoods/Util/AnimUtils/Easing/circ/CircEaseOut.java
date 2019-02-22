package com.hg.hollowgoods.Util.AnimUtils.Easing.circ;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class CircEaseOut extends BaseEasingMethod {

    public CircEaseOut(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return c * (float) Math.sqrt(1 - (t = t / d - 1) * t) + b;
    }
}
