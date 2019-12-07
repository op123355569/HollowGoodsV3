package com.hg.hollowgoods.Util.LaunchStarter.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Created by Hollow Goods on 2019-11-29.
 */
public class Utils {

    private static String sCurProcessName = null;

    public static boolean isMainProcess(Context context) {
        String processName = getCurProcessName(context);
        if (processName != null && processName.contains(":")) {
            return false;
        }
        return (processName != null && processName.equals(context.getPackageName()));
    }

    private static String getCurProcessName(Context context) {

        String processName = sCurProcessName;

        if (!TextUtils.isEmpty(processName)) {
            return processName;
        }

        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    sCurProcessName = appProcess.processName;
                    return sCurProcessName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        sCurProcessName = getCurProcessNameFromProc();

        return sCurProcessName;
    }

    private static String getCurProcessNameFromProc() {

        BufferedReader cmdlineReader = null;

        try {
            cmdlineReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream("/proc/" + android.os.Process.myPid() + "/cmdline"), StandardCharsets.ISO_8859_1)
            );
            int c;
            StringBuilder processName = new StringBuilder();
            while ((c = cmdlineReader.read()) > 0) {
                processName.append((char) c);
            }
            return processName.toString();
        } catch (Throwable e) {
            // ignore
        } finally {
            if (cmdlineReader != null) {
                try {
                    cmdlineReader.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        return null;
    }

}
