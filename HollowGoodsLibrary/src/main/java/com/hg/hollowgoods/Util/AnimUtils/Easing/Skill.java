package com.hg.hollowgoods.Util.AnimUtils.Easing;

import com.hg.hollowgoods.Util.AnimUtils.Easing.back.BackEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.back.BackEaseInOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.back.BackEaseOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.bounce.BounceEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.bounce.BounceEaseInOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.bounce.BounceEaseOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.circ.CircEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.circ.CircEaseInOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.circ.CircEaseOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.cubic.CubicEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.cubic.CubicEaseInOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.cubic.CubicEaseOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.elastic.ElasticEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.elastic.ElasticEaseOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.expo.ExpoEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.expo.ExpoEaseInOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.expo.ExpoEaseOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.linear.Linear;
import com.hg.hollowgoods.Util.AnimUtils.Easing.quad.QuadEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.quad.QuadEaseInOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.quad.QuadEaseOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.quint.QuintEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.quint.QuintEaseInOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.quint.QuintEaseOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.sine.SineEaseIn;
import com.hg.hollowgoods.Util.AnimUtils.Easing.sine.SineEaseInOut;
import com.hg.hollowgoods.Util.AnimUtils.Easing.sine.SineEaseOut;

public enum Skill {

    BackEaseIn(BackEaseIn.class),
    BackEaseOut(BackEaseOut.class),
    BackEaseInOut(BackEaseInOut.class),

    BounceEaseIn(BounceEaseIn.class),
    BounceEaseOut(BounceEaseOut.class),
    BounceEaseInOut(BounceEaseInOut.class),

    CircEaseIn(CircEaseIn.class),
    CircEaseOut(CircEaseOut.class),
    CircEaseInOut(CircEaseInOut.class),

    CubicEaseIn(CubicEaseIn.class),
    CubicEaseOut(CubicEaseOut.class),
    CubicEaseInOut(CubicEaseInOut.class),

    ElasticEaseIn(ElasticEaseIn.class),
    ElasticEaseOut(ElasticEaseOut.class),

    ExpoEaseIn(ExpoEaseIn.class),
    ExpoEaseOut(ExpoEaseOut.class),
    ExpoEaseInOut(ExpoEaseInOut.class),

    QuadEaseIn(QuadEaseIn.class),
    QuadEaseOut(QuadEaseOut.class),
    QuadEaseInOut(QuadEaseInOut.class),

    QuintEaseIn(QuintEaseIn.class),
    QuintEaseOut(QuintEaseOut.class),
    QuintEaseInOut(QuintEaseInOut.class),

    SineEaseIn(SineEaseIn.class),
    SineEaseOut(SineEaseOut.class),
    SineEaseInOut(SineEaseInOut.class),

    Linear(Linear.class);


    private Class easingMethod;

    private Skill(Class clazz) {
        easingMethod = clazz;
    }

    public BaseEasingMethod getMethod(float duration) {
        try {
            return (BaseEasingMethod) easingMethod.getConstructor(float.class).newInstance(duration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Can not init easingMethod instance");
        }
    }
}
