package com.hg.hollowgoods.UI.Activity.Plugin.NFC;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;

import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * NFC扫描基界面(被继承使用)<br>
 * 在onCreate方法中，必须调用initNfc()方法
 *
 * @author HG
 */

public abstract class NFCScanActivity extends BaseActivity {

    private static final String TAG_MifareClassic = "android.nfc.tech.MifareClassic";
    private static final String TAG_NfcA = "android.nfc.tech.NfcA";
    public static final String KEY_LONG_READ = "isLongRead";
//    private static final String TAG_NfcB = "android.nfc.tech.NfcB";
//    private static final String TAG_IsoDep = "android.nfc.tech.IsoDep";

    private static final int READ_TAG_MifareClassic = 1;
    private static final int READ_TAG_NFCA = 2;

    private NfcAdapter mNfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private Tag mTagFromIntent;
    private boolean isNFCSupport;
    private long beforeScanTime = 0l;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case READ_TAG_MifareClassic: {
                    readTagMifareClassic();
                }
                break;
                case READ_TAG_NFCA: {
                    readTagNfcA();
                }
                break;
                default:
                    break;
            }
        }
    };

    public void initNfc() {

        // 初始化设备支持NFC功能
        isNFCSupport = true;

        //初始化NfcAdapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            t.showShortToast(R.string.system_not_support_nfc);
            isNFCSupport = false;
        }

        if (isNFCSupport) {
            if (!mNfcAdapter.isEnabled()) {
                t.showShortToast(R.string.open_nfc_switch_first);
                isNFCSupport = false;
            }
        }

        if (isNFCSupport) {

            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                    getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
            ndef.addCategory("*/*");
            // 过滤器
            mFilters = new IntentFilter[]{ndef};
            // 允许扫描的标签类型
            mTechLists = new String[][]{
                    new String[]{MifareClassic.class.getName()},
                    new String[]{NfcA.class.getName()}
            };
        }
    }

    //当设置android:launchMode="singleTop"时调用
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String strAction = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(strAction)) {

//            analyzeTagContent(intent);

            // 播放扫描音效
            playBeepSoundAndVibrate();

            String result = readTagId(intent);
            if (TextUtils.isEmpty(result)) {
                result = "";
            }

            sendResult(result);
        }
    }

    private void sendResult(String result) {

        if (System.currentTimeMillis() - beforeScanTime >= HGSystemConfig.NFC_TWICE_SCAN_TIME) {
            beforeScanTime = System.currentTimeMillis();

            Event event = new Event(HGEventActionCode.NFC_SCAN_RESULT);
            event.getData().putString(HGParamKey.ObjData.getValue(), result);
            EventBus.getDefault().post(event);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        // nfc
        if (isNFCSupport) {
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters, mTechLists);
//        if (isFirst) {
//            String strAction = getIntent().getAction();
//            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(strAction)) {
//                String result = readTagId(getIntent());
//            }
//            isFirst = false;
//        }

            // 初始化扫描音效
            playBeep = true;
            AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                playBeep = false;
            }
            initBeepSound();
            vibrate = true;
        } else {
            return;
        }
    }

    /**
     * 读取标签的ID
     *
     * @param intent
     * @return
     */
    private String readTagId(Intent intent) {

        String tagId = "";

        try {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] tagByte = tagFromIntent.getId();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < tagByte.length; i++) {
                int num = tagByte[i] & 0xff;
                buffer.append(num);
                if (i != tagByte.length - 1) {
                    buffer.append("-");
                }
            }
            tagId = buffer.toString();

            if (HGSystemConfig.IS_DEBUG_MODEL) {
                LogUtils.Log("NFC感应标签的ID为:" + tagId, this.getClass());
            }
        } catch (Exception e) {
            if (HGSystemConfig.IS_DEBUG_MODEL) {
                LogUtils.Log("读取NFC标签异常:" + e.getMessage(), this.getClass());
            }
        }
        return tagId;
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     * 解析标签内容
     *
     * @param intent
     */
    private void analyzeTagContent(Intent intent) {

        // 取出封装在intent中的TAG
        mTagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] techList = mTagFromIntent.getTechList();
        // 查看TAG的类型
        for (String tech : techList) {
            if (HGSystemConfig.IS_DEBUG_MODEL) {
                LogUtils.Log("NFC感应标签类型为:" + tech, this.getClass());
            }
            if (tech.contains(TAG_MifareClassic)) {
                handler.sendEmptyMessage(READ_TAG_MifareClassic);
            } else if (tech.contains(TAG_NfcA)) {
                handler.sendEmptyMessage(READ_TAG_NFCA);
            }
        }

    }

    /**
     * 读取标签类型为NfcA的标签数据
     *
     * @return
     */
    private String readTagNfcA() {
        String tagContent = "";

        NfcA nfcA = NfcA.get(mTagFromIntent);

        try {
            if (nfcA.isConnected())
                nfcA.close();
            nfcA.connect();

            byte[] command = new byte[11];
            byte[] responsebyte = null;

            command[0] = (byte) 0x22; //标志位
            command[1] = (byte) 0x20; //命令位
//            System.arraycopy(UID, 0, command, 2, UID.length);
            command[10] = (byte) 0x1;

            responsebyte = nfcA.transceive(command);

            if (responsebyte != null) {
                tagContent = bytesToHexString(responsebyte);
//                Log.d("intent", "intent response="+toStringHex(data.substring(2, data.length())));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (HGSystemConfig.IS_DEBUG_MODEL) {
                LogUtils.Log("处理NfcA标签类型数据异常:" + e.getMessage(), this.getClass());
            }
        }

        return tagContent;
    }

    /**
     * 读取支持标签类型为MifareClassic的标签内容
     *
     * @return
     */
    private String readTagMifareClassic() {

        String tagContent = "";

        // 读取TAG
        MifareClassic mfc = MifareClassic.get(mTagFromIntent);
        boolean auth = false;
        try {
            String metaInfo = "";
            //Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();//获取TAG的类型
            int sectorCount = mfc.getSectorCount();//获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
                    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";
            for (int j = 0; j < sectorCount; j++) {
                // Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j,
                        MifareClassic.KEY_DEFAULT);
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ":验证成功\n";
                    // 读取扇区中的块
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo += "Block " + bIndex + " : "
                                + bytesToHexString(data) + "\n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ":验证失败\n";
                }
            }
            tagContent = metaInfo;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.Log("处理MifareClassic标签类型数据异常:" + e.getMessage(), this.getClass());
        }

        return tagContent;
    }

    /**
     * 字符序列转换为16进制字符串
     *
     * @param src
     * @return
     */
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    // ------------------------------- 扫描音效处理 -----------------------------------

    private static final float BEEP_VOLUME = 0.50f;

    private static final long VIBRATE_DURATION = 200L;

    private MediaPlayer mediaPlayer;

    private boolean playBeep;
    private boolean vibrate;

    /**
     * 初始化扫描音效
     */
    private void initBeepSound() {

        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {

        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }

    };

    /**
     * 播放音效
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

}
