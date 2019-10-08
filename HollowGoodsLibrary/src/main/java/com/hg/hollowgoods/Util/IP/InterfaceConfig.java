package com.hg.hollowgoods.Util.IP;

import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Dialog.BaseDialog;
import com.hg.hollowgoods.UI.Base.Message.Dialog.ChoiceItem;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口配置
 * Created by Hollow Goods 2016-11-21.
 */

public class InterfaceConfig {

    public static boolean IS_SHOW_PROTOCOL = true;
    public static boolean IS_SHOW_IP = true;
    public static boolean IS_SHOW_PORT = true;
    public static boolean IS_SHOW_PROJECT_NAME = true;
    public static boolean IS_SHOW_REALM_NAME = true;
    public static boolean IS_SHOW_HISTORY = true;

    /**
     * IP配置集合
     */
    private static ArrayList<IPConfig> IP = new ArrayList<>();

    /**
     * 保存IP配置
     */
    private static void saveIP() {
        new IPDBHelper().save(IP);
    }

    /**
     * 添加IP配置信息
     *
     * @param ipConfig ipConfig
     */
    private static void addIP(IPConfig ipConfig) {
        if (!isExist(ipConfig)) {
            IP.add(ipConfig);
        }
    }

    private static boolean isExist(IPConfig ipConfig) {
        return getIPConfigPosition(ipConfig) != -1;
    }

    private static int getIPConfigPosition(IPConfig ipConfig) {

        for (int i = 0; i < IP.size(); i++) {
            if (IPConfig.equals(ipConfig, IP.get(i))) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 初始化IP配置
     */
    public static void initIP(IPConfig... defaultIPs) {

        List<IPConfig> result = new IPDBHelper().findAll();

        if (result != null) {
            for (IPConfig t : result) {
                addIP(t);
            }
        }

        if (defaultIPs != null) {
            for (IPConfig t : defaultIPs) {
                addIP(t);
            }
        }

    }

    public static IPConfig getNowIPConfig() {

        IPConfig ipConfig = null;

        if (IP.size() == 0) {
            ipConfig = new IPConfig();
        }

        for (IPConfig t : IP) {
            if (t.isChecked()) {
                ipConfig = t;
                break;
            }
        }

        if (ipConfig == null) {
            IP.get(0).setChecked(true);
            saveIP();

            ipConfig = IP.get(0);
        }

        return IPConfig.copy(ipConfig);
    }

    private AlertDialog ipDialog;
    private EditText protocol;
    private EditText ip;
    private EditText port;
    private EditText projectName;
    private EditText realmName;
    private TextView pre;
    private View ipHistory;
    private IPConfig ipConfig;

    public void showIPDialog(BaseActivity baseActivity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setTitle("IP地址配置").setView(R.layout.dialog_ip_address).setNegativeButton(R.string.cancel, (dialog, which) -> {

        }).setPositiveButton(R.string.sure, (dialog, which) -> {

            addIP(ipConfig);

            int position = getIPConfigPosition(ipConfig);
            for (IPConfig t : IP) {
                t.setChecked(false);
            }
            IP.get(position).setChecked(true);

            saveIP();
            baseActivity.baseUI.showLongSnackbar("当前地址："
                    + ipConfig.getRequestUrl()
            );

            Event event = new Event(HGEventActionCode.IP_CONFIG_CHANGED);
            EventBus.getDefault().post(event);
        });

        ipDialog = builder.create();
        ipDialog.show();

        protocol = ipDialog.findViewById(R.id.et_protocol);
        ip = ipDialog.findViewById(R.id.et_ip);
        port = ipDialog.findViewById(R.id.et_port);
        projectName = ipDialog.findViewById(R.id.et_projectName);
        realmName = ipDialog.findViewById(R.id.et_realmName);
        pre = ipDialog.findViewById(R.id.tv_pre);
        ipHistory = ipDialog.findViewById(R.id.tv_ip_history);

        View protocolView = ipDialog.findViewById(R.id.asdl_protocol);
        View ipView = ipDialog.findViewById(R.id.asdl_ip);
        View portView = ipDialog.findViewById(R.id.asdl_port);
        View projectNameView = ipDialog.findViewById(R.id.asdl_projectName);
        View realmNameView = ipDialog.findViewById(R.id.asdl_realmName);

        protocolView.setVisibility(IS_SHOW_PROTOCOL ? View.VISIBLE : View.GONE);
        ipView.setVisibility(IS_SHOW_IP ? View.VISIBLE : View.GONE);
        portView.setVisibility(IS_SHOW_PORT ? View.VISIBLE : View.GONE);
        projectNameView.setVisibility(IS_SHOW_PROJECT_NAME ? View.VISIBLE : View.GONE);
        realmNameView.setVisibility(IS_SHOW_REALM_NAME ? View.VISIBLE : View.GONE);
        ipHistory.setVisibility(IS_SHOW_HISTORY ? View.VISIBLE : View.GONE);

        refreshIPDialog();

        protocol.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setProtocol(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        ip.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setIp(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        port.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setPort(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        projectName.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setProjectName(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        realmName.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setRealmName(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        ipHistory.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                showIPHistoryDialog(baseActivity);
            }
        });
    }

    private void refreshIPDialog() {

        ipConfig = getNowIPConfig();

        protocol.setText(ipConfig.getProtocol());
        ip.setText(ipConfig.getIp());
        port.setText(ipConfig.getPort());
        projectName.setText(ipConfig.getProjectName());
        realmName.setText(ipConfig.getRealmName());

        if (isShowAll()) {
            pre.setText(ipConfig.getRequestUrl());
        } else {
            pre.setText(ipConfig.getRequestHead());
        }

    }

    private final int DIALOG_CODE = -1000;

    private void showIPHistoryDialog(BaseActivity baseActivity) {

        if (IP.size() == 0) {
            t.info("没有历史记录");
            return;
        }

        BaseDialog baseDialog = new BaseDialog(baseActivity);
        ArrayList<ChoiceItem> items = new ArrayList<>();
        int checkedPosition = -1;
        boolean isShowAll = isShowAll();

        for (int i = 0; i < IP.size(); i++) {
            items.add(new ChoiceItem(isShowAll ? IP.get(i).getRequestUrl() : IP.get(i).getRequestHead()));
            if (IP.get(i).isChecked()) {
                checkedPosition = i;
            }
        }

        baseDialog.setOnDialogClickListener((code, result, data) -> {

            if (result) {
                switch (code) {
                    case DIALOG_CODE:
                        int position = data.getInt(HGParamKey.Position.getValue(), -1);
                        if (position != -1) {
                            for (IPConfig t : IP) {
                                t.setChecked(false);
                            }
                        }
                        IP.get(position).setChecked(true);
                        refreshIPDialog();
                        break;
                }
            }
        });

        baseDialog.showSingleDialog(R.string.please_choose_ip_address, items, checkedPosition, DIALOG_CODE);
    }

    private class TextWatcherAdapter implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private static boolean isShowAll() {
        return IS_SHOW_PROTOCOL
                && IS_SHOW_IP
                && IS_SHOW_PORT
                && IS_SHOW_PROJECT_NAME
                && IS_SHOW_REALM_NAME;
    }

}
