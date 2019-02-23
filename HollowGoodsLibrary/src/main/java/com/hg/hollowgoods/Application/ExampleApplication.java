package com.hg.hollowgoods.Application;

import android.app.Application;

import com.hg.hollowgoods.Bean.HGUser;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.LogUtils;

/**
 * 示例Application
 * Created by HG on 2018-03-22.
 */
public class ExampleApplication extends BaseApplication {

    @Override
    public Application initAppContext() {
        return this;
    }

    @Override
    public void initAppDataBeforeDB() {
        HGSystemConfig.init(this);
    }

    @Override
    public void initAppDataAfterDB() {
//        InterfaceConfig.initIP(this,
//                new IPConfig("218.93.5.74", "4500")
//        );
        LogUtils.init(this);
        BaseApplication baseApplication = create();
        baseApplication.setCrashHandlerUsername("HollowGoods");
    }

    /**
     * 用户登录信息
     */
    private HGUser user = null;

    // **** Get&Set ****//

    public HGUser getUser() {
        return user;
    }

    public void setUser(HGUser user) {
        this.user = user;
    }

}
