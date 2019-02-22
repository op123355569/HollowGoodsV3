package com.hg.hollowgoods.Util.AnimUtils.Easing.back;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class BackEaseIn extends BaseEasingMethod {

    private float s = 1.70158f;

    public BackEaseIn(float duration) {
        super(duration);
    }

    public BackEaseIn(float duration, float back) {
        this(duration);
        s = back;
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return c * (t /= d) * t * ((s + 1) * t - s) + b;
    }

}
