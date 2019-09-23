/*
 * Tencent is pleased to support the open source community by making Tinker available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hg.hollowgoods.Application;

import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerLog;

import org.greenrobot.eventbus.EventBus;

/**
 * 热插件安装监听
 * Created by Hollow Goods on 2019-06-20.
 */
public class HGResultService extends DefaultTinkerResultService {

    @Override
    public void onPatchResult(final PatchResult result) {

        if (result == null) {
            TinkerLog.e("HGResultService", "没结果");
            Event event = new Event(HGEventActionCode.TINKER_NO_RESULT);
            EventBus.getDefault().post(event);
            return;
        }

        if (result.isSuccess) {
            TinkerLog.e("HGResultService", "热补丁已装载！");
            Event event = new Event(HGEventActionCode.TINKER_INSTALL_SUCCESS);
            EventBus.getDefault().post(event);
        }
    }

}
