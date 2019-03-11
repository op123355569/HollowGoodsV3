package com.hg.hollowgoods.Application;

import android.app.Application;

import com.hg.hollowgoods.Bean.HGUser;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.IP.IPConfig;
import com.hg.hollowgoods.Util.IP.InterfaceConfig;
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

        InterfaceConfig.IS_SHOW_PROTOCOL = true;
        InterfaceConfig.IS_SHOW_IP = true;
        InterfaceConfig.IS_SHOW_PORT = true;
        InterfaceConfig.IS_SHOW_PROJECT_NAME = true;
        InterfaceConfig.IS_SHOW_REALM_NAME = true;
        InterfaceConfig.IS_SHOW_HISTORY = true;
        HGSystemConfig.IS_NEED_CHECK_SERVER_TIME = false;
        HGSystemConfig.IS_NEED_READ_OFFICE_FILE = false;

        InterfaceConfig.initIP(new IPConfig()
                .setIp("218.93.5.74")
                .setPort("4500")
        );
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
