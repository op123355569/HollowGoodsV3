package com.hg.hollowgoods.UI.Activity.Plugin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;

/**
 * Created by Hollow Goods 2018-07-25.
 */
public class PlayVideoActivity extends BaseActivity {

//    private VideoPlayerView playerView;
//    private RingProgressBar brightnessProgressBar;
//    private ENVolumeView volumeProgressBar;
//    private TextView progressFlag;
//    private TextView progressCurrIndex;
//    private TextView progressMax;
//
//    private ManualPlayer playerManager;
//    private WholeMediaSource wholeMediaSource;
//    private String[] urls = {
//            "http://mp4.vjshi.com/2016-10-21/84bafe60ef0af95a5292f66b9f692504.mp4",
//            "http://mp4.vjshi.com/2018-01-09/40f3f1edc1cc4aacad958f3e8acbf4ce.mp4",
//            "http://mp4.vjshi.com/2015-05-11/1431320221859_786.mp4",
//    };
//    private String[] name = {
//            "超清",
//            "高清",
//            "标清",
//    };

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_play_video;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, "取消该案例 可见源码");

//        playerView = findViewById(R.id.playerView);
//        brightnessProgressBar = findViewById(R.id.brightnessProgressBar);
//        volumeProgressBar = findViewById(R.id.volumeProgressBar);
//        progressFlag = findViewById(R.id.tv_flag);
//        progressCurrIndex = findViewById(R.id.tv_currIndex);
//        progressMax = findViewById(R.id.tv_max);
//
//        baseUI.setCommonTitleViewVisibility(false);
//        baseUI.hideActionBar();
//        baseUI.setStatusBackgroundColor(R.color.black);
//
//        wholeMediaSource = new WholeMediaSource(this, new Data2Source(getApplication()));
//        playerManager = new ManualPlayer(this, wholeMediaSource, playerView);
//
//        //设置视频标题
//        playerView.setTitle("Title");
//        //设置水印图
//        playerView.setExoPlayWatermarkImg(R.mipmap.ic_ex_31_watermark);
//        playerManager.addOnWindowListener(new VideoWindowListener() {
//            @Override
//            public void onCurrentIndex(int currentIndex, int windowCount) {
//                Toast.makeText(getApplication(), currentIndex + "windowCount:" + windowCount, Toast.LENGTH_SHORT).show();
//            }
//        });
//        //设置开始播放进度
//        // exoPlayerManager.setPosition(1000);
//        // exoPlayerManager.setPlayUri(getString(R.string.uri_test_3),getString(R.string.uri_test_h));
//        //    String tes="/storage/emulated/0/DCIM/Camera/VID_20180215_131926.mp4";
//        // exoPlayerManager.setPlayUri(Environment.getExternalStorageDirectory().getAbsolutePath()+"/VID_20170925_154925.mp4");
//        //  test = new String[]{"/storage/emulated/0/DCIM/Camera/VID_20180215_131816.mp4","/storage/emulated/0/DCIM/Camera/VID_20180215_131816.mp4","/storage/emulated/0/DCIM/Camera/VID_20180215_131816.mp4"};
//        //开启线路设置
//        playerManager.setShowVideoSwitch(true);
//        playerManager.setPlaySwitchUri(0, urls, name);
//        //exoPlayerManager.setPlaySwitchUri(0, 0, getString(R.string.uri_test_11), Arrays.asList(test), Arrays.asList(name));
//        // exoPlayerManager.setPlayUri("rtmp://live.hkstv.hk.lxdns.com/live/hks");
//        // exoPlayerManager.setPlaybackParameters(0.5f,0.5f);
//        playerManager.startPlayer();
//        //  exoPlayerManager.startPlayer();
//        //  exoPlayerManager.setPlayUri("http://live.aikan.miguvideo.com/wd_r2/cctv/cctv1hd/1200/01.m3u8");
//        //exoPlayerManager.setPlayUri(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.mp4");
//        //开始启动播放视频
//        //exoPlayerManager.startPlayer();
//        // TestDataBean bean = new TestDataBean();
//        // TestDataBean bean1 = new TestDataBean();
//        //  List<TestDataBean> listss = new ArrayList<>();
//       /* if (Build.VERSION.SDK_INT < 21) {//低版本不支持高分辨视频
//            bean.setUri(getString(R.string.uri_test_3));
//            bean1.setUri(getString(R.string.uri_test_h));
//        } else {
//            //4k 视频
//            //exoPlayerManager.setPlayUri("http://mp4.vjshi.com/2016-07-13/16190d61b7dbddbeb721f1b994fd7424.mp4");
//            bean.setUri("http://mp4.vjshi.com/2016-07-13/16190d61b7dbddbeb721f1b994fd7424.mp4");
//            bean1.setUri("http://mp4.vjshi.com/2017-10-17/b81c7a35932c5bbacdc177534398fe87.mp4");
//        }*/
//        // listss.add(bean);
//        // listss.add(bean1);
//        //exoPlayerManager.setPlayUri(listss);
//        //是否屏蔽进度控件拖拽快进视频（例如广告视频，（不允许用户））
//        //exoPlayerManager.setSeekBarSeek(false);
//        //设置视循环播放
//        //exoPlayerManager.setLooping(10);
//        //隐藏控制布局
//        // exoPlayerManager.hideControllerView();
//        //隐藏进度条
//        // exoPlayerManager.hideSeekBar();
//        //显示进度条
//        //exoPlayerManager.showSeekBar();
//        //是否播放
//        // exoPlayerManager.isPlaying();
//        //设置播放视频倍数  快进播放和慢放播放
//        //exoPlayerManager.setPlaybackParameters(2f,2f);
//        // videoPlayerView.getPreviewImage().setScaleType(ImageView.ScaleType.FIT_XY);
//
////        RequestOptions requestOptions = new RequestOptions()
////                .placeholder(CommonResource.IMAGE_LOADING)
////                .error(CommonResource.IMAGE_LOAD_ERROR)
////                .centerCrop();
////        GlideOptions glideOptions = new GlideOptions(
////                "http://i3.letvimg.com/lc08_yunzhuanma/201707/29/20/49/3280a525bef381311b374579f360e80a_v2_MTMxODYyNjMw/thumb/2_960_540.jpg",
////                playerView.getPreviewImage(),
////                GlideOptions.NO_FADE_IN,
////                requestOptions
////        );
////        GlideUtils.loadImg(this, glideOptions);
//        playerView.getPreviewImage().setImageResource(R.drawable.ic_android_green_24dp);

        return null;
    }

    @Override
    public void setListener() {

//        playerManager.addVideoInfoListener(new VideoInfoListener() {
//
//            @Override
//            public void onPlayStart(long currPosition) {
//
//            }
//
//            @Override
//            public void onLoadingChanged() {
//
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException e) {
//
//            }
//
//            @Override
//            public void onPlayEnd() {
//
//            }
//
//
//            @Override
//            public void isPlaying(boolean playWhenReady) {
//
//            }
//        });
//
//        // 亮度监听
//        playerManager.setOnGestureBrightnessListener(new OnGestureBrightnessListener() {
//            @Override
//            public void setBrightnessPosition(int mMax, int currIndex) {
//                // 显示你的布局
//                playerView.getGestureBrightnessLayout().setVisibility(View.VISIBLE);
//                // 为你布局显示内容自定义内容
//                brightnessProgressBar.setMax(mMax);
//                brightnessProgressBar.setProgress(currIndex);
//            }
//        });
//
//        // 音量监听
//        playerManager.setOnGestureVolumeListener(new OnGestureVolumeListener() {
//            @Override
//            public void setVolumePosition(int mMax, int currIndex) {
//
//                int progress = (int) (currIndex * 1f / mMax * 100);
//
//                // 显示你的布局
//                playerView.getGestureAudioLayout().setVisibility(View.VISIBLE);
//                // 为你布局显示内容自定义内容
//                volumeProgressBar.updateVolumeValue(progress);
//            }
//        });
//
//        // 进度监听
//        playerManager.setOnGestureProgressListener(new OnGestureProgressListener() {
//            @Override
//            public void showProgressDialog(long seekTimePosition, long duration, String seekTime, String totalTime) {
//
//                LogUtils.Log(seekTimePosition);
//                LogUtils.Log(duration);
//                LogUtils.Log(seekTime);
//                LogUtils.Log(totalTime);
//
//                // 显示你的布局
//                playerView.getGestureProgressLayout().setVisibility(View.VISIBLE);
//                // 为你布局显示内容自定义内容
//                progressCurrIndex.setText(seekTime);
//                progressMax.setText(totalTime);
//            }
//
//            @Override
//            public void endGestureProgress(long position) {
//                playerManager.seekTo(position);
//            }
//        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        LogUtils.Log("onResume");
//        playerManager.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        LogUtils.Log("onPause");
//        playerManager.onPause();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        playerManager.onDestroy();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        playerManager.onConfigurationChanged(newConfig);//横竖屏切换
//        super.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (playerManager.onBackPressed()) {
//            finishMyActivity();
//            playerManager.onDestroy();
//        }
//    }

}
