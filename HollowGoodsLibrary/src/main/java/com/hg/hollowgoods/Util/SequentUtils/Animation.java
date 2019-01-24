package com.hg.hollowgoods.Util.SequentUtils;

import com.hg.hollowgoods.R;

public enum Animation {
    BOUNCE_IN(R.anim.sequent_bounce_in),
    FADE_IN(R.anim.sequent_fade_in),
    FADE_IN_DOWN(R.anim.sequent_fade_in_down),
    FADE_IN_UP(R.anim.sequent_fade_in_up),
    FADE_IN_LEFT(R.anim.sequent_fade_in_left),
    FADE_IN_RIGHT(R.anim.sequent_fade_in_right),
    ROTATE_IN(R.anim.sequent_rotate_in),;

    private int animId;

    Animation(int animId) {
        this.animId = animId;
    }

    public int getAnimId() {
        return this.animId;
    }
}
