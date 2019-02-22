package com.hg.hollowgoods.Util.AnimUtils.Easing.expo;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class ExpoEaseOut extends BaseEasingMethod {
    public ExpoEaseOut(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return (t == d) ? b + c : c * (-(float) Math.pow(2, -10 * t / d) + 1) + b;
    }
}
