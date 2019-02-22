package com.hg.hollowgoods.Util.AnimUtils.Easing.cubic;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class CubicEaseIn extends BaseEasingMethod {

    public CubicEaseIn(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return c * (t /= d) * t * t + b;
    }
}
