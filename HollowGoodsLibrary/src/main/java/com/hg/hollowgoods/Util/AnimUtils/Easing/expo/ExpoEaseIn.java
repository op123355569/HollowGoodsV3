package com.hg.hollowgoods.Util.AnimUtils.Easing.expo;

import com.hg.hollowgoods.Util.AnimUtils.Easing.BaseEasingMethod;

public class ExpoEaseIn extends BaseEasingMethod {
    public ExpoEaseIn(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return (t == 0) ? b : c * (float) Math.pow(2, 10 * (t / d - 1)) + b;
    }
}
