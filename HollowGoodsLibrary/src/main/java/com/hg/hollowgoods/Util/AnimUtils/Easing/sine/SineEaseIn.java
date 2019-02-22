package com.hg.hollowgoods.Util.AnimUtils.Easing.sine;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class SineEaseIn extends BaseEasingMethod {

    public SineEaseIn(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return -c * (float) Math.cos(t / d * (Math.PI / 2)) + c + b;
    }
}
