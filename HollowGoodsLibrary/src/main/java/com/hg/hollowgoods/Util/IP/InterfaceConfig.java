package com.hg.hollowgoods.Util.IP;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Dialog.BaseDialog;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口配置
 * Created by HG on 2016-11-21.
 */

public class InterfaceConfig {

    /**
     * IP的位置
     */
    private static final String KEY_POSITION = "ip.position";
    /**
     * 访问类型
     */
    private static final String KEY_NET_TYPE = "net.type";

    /**
     * 命名空间
     */
    public static final String NAME_SPACE = "http://webservice.xhtt.com/";

    /**
     * 协议
     */
    private static final String PROTOCOL = "http://";

    /**
     * IP + 端口
     */
    private static ArrayList<IPConfig> IP = new ArrayList<IPConfig>() {
        {
//            add(new IPConfig("218.93.5.74", "4500"));
//            if (HGSystemConfig.IS_DEBUG_MODEL) {
//                add(new IPConfig("192.168.1.6", "99"));
//                add(new IPConfig("192.168.1.21", "8080"));
//                add(new IPConfig("192.168.1.22", "8080"));
//                add(new IPConfig("192.168.1.25", "8080"));
//            }
        }
    };

    /**
     * 广域网项目名
     */
    private static String PROJECT_NAME_WAN = "";
    /**
     * 局域网项目名
     */
    private static String PROJECT_NAME_LAN = "/LY";

    /**
     * 域名webservice
     */
    private static String REALM_NAME_WEB_SERVICE = "/webservice";
    /**
     * 域名http
     */
    private static String REALM_NAME_HTTP = "/";
    /**
     * 是否是局域网
     */
    private static boolean IS_LAN = true;

    /**
     * IP的位置
     */
    private static int IP_POSITION = 0;

    /**
     * 保存IP + 端口
     *
     * @param context
     */
    private static void saveIP(Context context) {

        new IPDBHelper().save(IP);
        SharedPreferencesUtils.put(context, KEY_POSITION, IP_POSITION);
    }

    /**
     * 获取IP + 端口
     *
     * @return
     */
    private static String getIP() {

        if (TextUtils.isEmpty(IP.get(IP_POSITION).getPort())) {
            return IP.get(IP_POSITION).getIp();
        }

        return IP.get(IP_POSITION).getIp() + ":" + IP.get(IP_POSITION).getPort();
    }

    /**
     * 添加IP配置信息
     *
     * @param ipConfig
     */
    private static void addIP(IPConfig ipConfig) {
        IP.add(ipConfig);
    }

    private static boolean isExist(IPConfig ipConfig) {

        for (int i = 0; i < IP.size(); i++) {
            if (TextUtils.equals(IP.get(i).getIp(), ipConfig.getIp()) && TextUtils.equals(IP.get(i).getPort(), ipConfig.getPort())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取webservice请求头
     *
     * @return
     */
    public static String getRequestHeadWebService() {
        return PROTOCOL + getIP() + (IS_LAN ? PROJECT_NAME_LAN : PROJECT_NAME_WAN) + REALM_NAME_WEB_SERVICE;
    }

    /**
     * 获取Http请求头
     *
     * @return
     */
    public static String getRequestHeadHttp() {
        return PROTOCOL + getIP() + (IS_LAN ? PROJECT_NAME_LAN : PROJECT_NAME_WAN) + REALM_NAME_HTTP;
    }

    /**
     * 初始化IP + 端口
     *
     * @param context
     */
    public static void initIP(Context context, IPConfig... defaultIPs) {

        List<IPConfig> result = new IPDBHelper().findAll();

        if (defaultIPs != null) {
            for (IPConfig t : defaultIPs) {
                if (!isExist(t)) {
                    addIP(t);
                }
            }
        }

        if (result != null) {
            for (IPConfig t : result) {
                if (!isExist(t)) {
                    addIP(t);
                }
            }
        }

        IP_POSITION = (Integer) SharedPreferencesUtils.get(context, KEY_POSITION, 0);
        IS_LAN = (boolean) SharedPreferencesUtils.get(context, KEY_NET_TYPE, HGSystemConfig.IS_DEBUG_MODEL);
    }

    /**
     * 更新IP + 端口
     *
     * @param context
     * @param ip
     * @param port
     */
    public static void updateIP(Context context, String ip, String port) {

        for (int i = 0; i < IP.size(); i++) {
            if (TextUtils.equals(ip, IP.get(i).getIp()) && TextUtils.equals(port, IP.get(i).getPort())) {
                IP_POSITION = i;
                saveIP(context);
                return;
            }
        }

        addIP(new IPConfig(ip, port));
        IP_POSITION = IP.size() - 1;
        saveIP(context);
    }

    /**
     * 获取当前IP
     *
     * @return
     */
    public static String getNowIP() {
        if (IP.size() < IP_POSITION) {
            return "";
        }
        return IP.get(IP_POSITION).getIp();
    }

    /**
     * 获取IP配置集合
     *
     * @return
     */
    public static String[] getIPs() {

        String[] result = new String[IP.size()];

        for (int i = 0; i < IP.size(); i++) {
            result[i] = IP.get(i).getIp() + ":" + IP.get(i).getPort();
        }

        return result;
    }

    /**
     * 获取当前端口
     *
     * @return
     */
    public static String getNowPort() {
        if (IP.size() < IP_POSITION) {
            return "";
        }
        return IP.get(IP_POSITION).getPort();
    }

    /**
     * 获取当前IP位置
     *
     * @return
     */
    public static int getNowIPPosition() {
        return IP_POSITION;
    }

    private AlertDialog ipDialog;
    private EditText ip;
    private EditText port;
    private RadioGroup netType;
    private RadioButton wan;
    private RadioButton lan;
    private View ipHistory;

    private int nowIPPosition;

    public void showIPDialog(final BaseActivity baseActivity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setTitle("IP地址配置").setView(R.layout.dialog_ip_address).setNegativeButton(R.string.cancel, (dialog, which) -> {

        }).setPositiveButton(R.string.sure, (dialog, which) -> {
            InterfaceConfig.updateIP(baseActivity, ip.getText().toString().trim(), port.getText().toString().trim());
            baseActivity.baseUI.showLongSnackbar("您配置的IP地址为:" + InterfaceConfig.getNowIP() + ":" + InterfaceConfig.getNowPort());
        });

        ipDialog = builder.create();
        ipDialog.show();

        ip = ipDialog.findViewById(R.id.et_ip);
        port = ipDialog.findViewById(R.id.et_port);
        netType = ipDialog.findViewById(R.id.rg_netType);
        wan = ipDialog.findViewById(R.id.rb_wan);
        lan = ipDialog.findViewById(R.id.rb_lan);
        ipHistory = ipDialog.findViewById(R.id.tv_ip_history);

        ip.setText(InterfaceConfig.getNowIP());
        port.setText(InterfaceConfig.getNowPort());
        if (IS_LAN) {
            lan.setChecked(true);
        } else {
            wan.setChecked(true);
        }

        ipHistory.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                showIPHistoryDialog(baseActivity);
            }
        });

        netType.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.rb_wan) {
                IS_LAN = false;
            } else if (checkedId == R.id.rb_lan) {
                IS_LAN = true;
            }

            SharedPreferencesUtils.put(baseActivity, KEY_NET_TYPE, IS_LAN);
        });
    }

    private final int DIALOG_CODE = -1000;

    private void showIPHistoryDialog(BaseActivity baseActivity) {

        if (IP.size() < IP_POSITION) {
            t.info("没有历史记录");
            return;
        }

        BaseDialog baseDialog = new BaseDialog(baseActivity);
        final String[] items = InterfaceConfig.getIPs();
        nowIPPosition = InterfaceConfig.getNowIPPosition();

        baseDialog.setOnDialogClickListener((code, result, data) -> {

            if (result) {
                switch (code) {
                    case DIALOG_CODE:
                        int position = data.getInt(Constants.PARAM_KEY_1, -1);
                        if (position != -1) {
                            nowIPPosition = position;
                            String str = items[nowIPPosition];
                            ip.setText(str.substring(0, str.indexOf(":")));
                            port.setText(str.substring(str.indexOf(":") + 1));
                        }
                        break;
                }
            }
        });

        baseDialog.showSingleDialog(R.string.please_choose_ip_address, items, nowIPPosition, DIALOG_CODE);
    }

}
