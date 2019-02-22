package com.hg.hollowgoods.Util.AnimUtils.Easing.quad;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class QuadEaseOut extends BaseEasingMethod {
    public QuadEaseOut(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return -c * (t /= d) * (t - 2) + b;
    }
}
