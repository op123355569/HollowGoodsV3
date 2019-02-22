package com.hg.hollowgoods.Util.AnimUtils.Easing.cubic;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class CubicEaseOut extends BaseEasingMethod {

    public CubicEaseOut(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return c * ((t = t / d - 1) * t * t + 1) + b;
    }
}
