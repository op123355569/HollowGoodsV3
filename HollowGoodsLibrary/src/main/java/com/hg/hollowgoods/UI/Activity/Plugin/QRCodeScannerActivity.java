package com.hg.hollowgoods.UI.Activity.Plugin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.FormatUtils;
import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Util.QRCodeUtils.CameraManager;
import com.hg.hollowgoods.Util.QRCodeUtils.CaptureActivityHandler;
import com.hg.hollowgoods.Util.QRCodeUtils.InactivityTimer;
import com.hg.hollowgoods.Util.QRCodeUtils.Util.AnimationUtils;
import com.hg.hollowgoods.Util.QRCodeUtils.Util.BeepUtils;
import com.hg.hollowgoods.Util.QRCodeUtils.Util.QRCodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 二维码扫描界面
 */
public class QRCodeScannerActivity extends BaseActivity {

    /**** 整体根布局 ****/
    private RelativeLayout mContainer;
    /**** 扫描框根布局 ****/
    private RelativeLayout mCropLayout;
    /**** 闪光灯 按钮 ****/
    private ImageView light;
    /**** 扫描网格 ****/
    private ImageView scanLine;

    private InactivityTimer inactivityTimer;
    /**** 扫描处理 ****/
    private CaptureActivityHandler handler;
    /**** 扫描边界的宽度 ****/
    private int mCropWidth = 0;
    /**** 扫描边界的高度 ****/
    private int mCropHeight = 0;
    /**** 是否有预览 ****/
    private boolean hasSurface;
    /**** 扫描成功后是否震动 ****/
    private boolean vibrate = true;
    /**** 闪光灯开启状态 ****/
    private boolean mFlashing = true;
    private String title = "";

    private final int SCAN_LINE_COLOR_RES = R.color.colorAccent;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_qr_code_scanner;
    }

    @Override
    public void initIntentData(Intent intent) {
        title = intent.getStringExtra(HGParamKey.Title.getValue());
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        mContainer = findViewById(R.id.capture_containter);
        mCropLayout = findViewById(R.id.capture_crop_layout);
        light = findViewById(R.id.light);
        scanLine = findViewById(R.id.capture_scan_line);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON,
                TextUtils.isEmpty(title) ? R.string.title_activity_ex23 : title
        );

        // 扫描动画初始化
        initScannerAnimation();
        // 初始化 CameraManager
        CameraManager.init(this);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        return null;
    }

    @Override
    public void setListener() {

        light.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                light();
            }
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {

        super.onResume();

        SurfaceView surfaceView = findViewById(R.id.capture_preview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();

        if (hasSurface) {
            // Camera初始化
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (!hasSurface) {
                        hasSurface = true;
                        initCamera(holder);
                    }
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    hasSurface = false;

                }
            });
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }

        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {

        inactivityTimer.shutdown();

        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }

        super.onDestroy();
    }

    private void initScannerAnimation() {

        Bitmap bitmapScanBox = BitmapFactory.decodeResource(getResources(), R.mipmap.qr_capture);
        bitmapScanBox = QRCodeUtils.makeTintBitmap(bitmapScanBox, ContextCompat.getColor(this, SCAN_LINE_COLOR_RES));
        mCropLayout.setBackground(FormatUtils.bitmap2Drawable(bitmapScanBox));

        Bitmap bitmapScanLine = BitmapFactory.decodeResource(getResources(), R.mipmap.qr_scan_ray);
        bitmapScanLine = QRCodeUtils.makeTintBitmap(bitmapScanLine, ContextCompat.getColor(this, SCAN_LINE_COLOR_RES));
        scanLine.setImageBitmap(bitmapScanLine);

        AnimationUtils.ScaleUpDowm(scanLine);
    }

    public void setCropWidth(int cropWidth) {
        mCropWidth = cropWidth;
        CameraManager.FRAME_WIDTH = mCropWidth;
    }

    public void setCropHeight(int cropHeight) {
        this.mCropHeight = cropHeight;
        CameraManager.FRAME_HEIGHT = mCropHeight;
    }

    private void light() {

        if (mFlashing) {
            mFlashing = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            mFlashing = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {

        try {
            CameraManager.get().openDriver(surfaceHolder);
            Point point = CameraManager.get().getCameraResolution();
            AtomicInteger width = new AtomicInteger(point.y);
            AtomicInteger height = new AtomicInteger(point.x);
            int cropWidth = mCropLayout.getWidth() * width.get() / mContainer.getWidth();
            int cropHeight = mCropLayout.getHeight() * height.get() / mContainer.getHeight();
            setCropWidth(cropWidth);
            setCropHeight(cropHeight);
        } catch (IOException | RuntimeException ioe) {
            return;
        }

        if (handler == null) {
            handler = new CaptureActivityHandler(QRCodeScannerActivity.this);
        }
    }

    public void handleDecode(Result result) {

        inactivityTimer.onActivity();
        // 扫描成功之后的振动与声音提示
        BeepUtils.playBeep(this, vibrate);

        String result1 = result.getText();
        Log.e("二维码/条形码 扫描结果", result1);

        backData(result);
    }

    public Handler getHandler() {
        return handler;
    }

    private void backData(Result result) {

        BarcodeFormat type = result.getBarcodeFormat();
        String realContent = result.getText();

        StringBuilder msg = new StringBuilder();

        if (BarcodeFormat.QR_CODE.equals(type)) {
            msg.append("二维码扫描结果");
        } else if (BarcodeFormat.EAN_13.equals(type)) {
            msg.append("条形码扫描结果");
        } else {
            msg.append("扫描结果");
        }

        msg.append("\n");
        msg.append(realContent);

        LogUtils.Log(msg);

        Event event = new Event(HGEventActionCode.QR_CODE_SCAN_RESULT);
        event.getData().putString(HGParamKey.ObjData.getValue(), realContent);
        EventBus.getDefault().post(event);

        finishMyActivity();

//        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();
//
//        if (handler != null) {
//            // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
//            handler.sendEmptyMessage(R.id.restart_preview);
//        }
    }

}