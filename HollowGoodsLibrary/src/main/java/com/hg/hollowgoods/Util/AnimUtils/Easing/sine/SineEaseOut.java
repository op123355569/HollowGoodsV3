package com.hg.hollowgoods.Util.AnimUtils.Easing.sine;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class SineEaseOut extends BaseEasingMethod {
    public SineEaseOut(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return c * (float) Math.sin(t / d * (Math.PI / 2)) + b;
    }
}
