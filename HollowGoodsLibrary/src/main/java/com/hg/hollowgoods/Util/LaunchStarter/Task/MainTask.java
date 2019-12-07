package com.hg.hollowgoods.Util.LaunchStarter.Task;

public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }

}
