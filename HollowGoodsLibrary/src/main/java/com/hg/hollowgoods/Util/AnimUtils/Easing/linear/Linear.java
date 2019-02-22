package com.hg.hollowgoods.Util.AnimUtils.Easing.linear;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class Linear extends BaseEasingMethod {
    public Linear(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return c * t / d + b;
    }
}
