package com.hg.hollowgoods.Util.AnimUtils.Easing.circ;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class CircEaseIn extends BaseEasingMethod {

    public CircEaseIn(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return -c * ((float) Math.sqrt(1 - (t /= d) * t) - 1) + b;
    }
}
