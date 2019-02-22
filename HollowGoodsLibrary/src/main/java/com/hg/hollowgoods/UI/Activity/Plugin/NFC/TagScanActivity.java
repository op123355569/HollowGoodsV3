package com.hg.hollowgoods.UI.Activity.Plugin.NFC;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.EventAction;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

import org.greenrobot.eventbus.EventBus;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * 标签扫描继承基类<br>
 * 子类的initView方法必须return super.initView(view, savedInstanceState)
 */
public abstract class TagScanActivity extends BaseActivity {

    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private NdefMessage mNdefPushMessage;
    private String separator = "-";

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        resolveIntent(getIntent());

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            t.error(R.string.system_not_support_nfc);
            finishMyActivity();
        } else {
            mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            mNdefPushMessage = new NdefMessage(new NdefRecord[]{newTextRecord("Message from HG", Locale.ENGLISH, true)});
        }

        return this;
    }

    /**
     * 创建记录
     *
     * @param text
     * @param locale
     * @param encodeInUtf8
     * @return
     */
    private NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {

        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    /**
     * 显示NFC设置跳转对话框
     */
    private void showWirelessSettingsDialog() {

        new AlertDialog.Builder(this)
                .setMessage(R.string.nfc_disabled)
                .setPositiveButton(R.string.sure, (dialogInterface, which) -> {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, which) -> finish()).show();

        return;
    }

    /**
     * 解析识别结果
     *
     * @param intent
     */
    private void resolveIntent(Intent intent) {

        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            TagScanResult result = null;

            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];

                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // 无法识别类型的标签
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                result = dumpTagData(tag);
            }

            if (result != null) {
                backData(result);
            }
        }
    }

    /**
     * bytes转16进制
     *
     * @param bytes
     * @return
     */
    private String toHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;

            if (b < 0x10) {
                sb.append('0');
            }

            sb.append(Integer.toHexString(b));

            if (i > 0) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    /**
     * bytes转反向16进制
     *
     * @param bytes
     * @return
     */
    private String toReversedHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; ++i) {
            int b = bytes[i] & 0xff;

            if (b < 0x10) {
                sb.append('0');
            }

            sb.append(Integer.toHexString(b));

            if (i < bytes.length - 1) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    /**
     * bytes转10进制
     *
     * @param bytes
     * @return
     */
    private String toDec(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            sb.append(value);

            if (i < bytes.length - 1) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    /**
     * bytes转反向10进制
     *
     * @param bytes
     * @return
     */
    private String toReversedDec(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            sb.append(value);

            if (i > 0) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    /**
     * 接收识别数据
     *
     * @param intent
     */
    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                showWirelessSettingsDialog();
            }
            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
            mAdapter.enableForegroundNdefPush(this, mNdefPushMessage);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
            mAdapter.disableForegroundNdefPush(this);
        }
    }

    /**
     * 转储标签数据
     *
     * @param tag
     * @return
     */
    private TagScanResult dumpTagData(Tag tag) {

        byte[] id = tag.getId();

        TagScanResult tagScanResult = new TagScanResult(
                toHex(id).toUpperCase(),
                toReversedHex(id).toUpperCase(),
                toDec(id),
                toReversedDec(id)
        );

        return tagScanResult;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * 返回识别数据
     *
     * @param result
     */
    private void backData(TagScanResult result) {

        Event event = new Event(EventAction.TagScanResult);
        event.getData().putSerializable(HGConstants.PARAM_KEY_1, result);

        EventBus.getDefault().post(event);
    }

}
