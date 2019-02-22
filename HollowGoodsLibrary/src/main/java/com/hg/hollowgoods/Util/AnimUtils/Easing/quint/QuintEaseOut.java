package com.hg.hollowgoods.Util.AnimUtils.Easing.quint;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class QuintEaseOut extends BaseEasingMethod {
    public QuintEaseOut(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return c * ((t = t / d - 1) * t * t * t * t + 1) + b;
    }
}
