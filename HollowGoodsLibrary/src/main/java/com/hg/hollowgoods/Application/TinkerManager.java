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

import android.content.Context;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

public class TinkerManager {

    private static boolean isInstalled = false;//是否已经初始化标志位
    private static ApplicationLike mApplicationLike;

    /**
     * 完成Tinker初始化
     *
     * @param applicationLike applicationLike
     */
    public static void installedTinker(ApplicationLike applicationLike) {
        mApplicationLike = applicationLike;
        if (isInstalled) {
            return;
        }

        TinkerInstaller.install(mApplicationLike,
                new HGLoadReporter(getApplicationContext()),
                new HGPatchReporter(getApplicationContext()),
                new HGPatchListener(getApplicationContext()),
                HGResultService.class,
                new UpgradePatch()
        );

        isInstalled = true;
    }

    /**
     * 完成patch文件的加载
     *
     * @param path 补丁文件路径
     */
    public static void loadPatch(String path) {
        if (Tinker.isTinkerInstalled()) {// 是否已经安装过
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 清除补丁
     */
    public static void cleanPatch() {
        if (Tinker.isTinkerInstalled()) {// 是否已经安装过
            TinkerInstaller.cleanPatch(getApplicationContext());
        }
    }

    /**
     * 利用Tinker代理Application 获取应用全局的上下文
     *
     * @return 全局的上下文
     */
    private static Context getApplicationContext() {
        if (mApplicationLike != null)
            return mApplicationLike.getApplication();
        return null;
    }
}
