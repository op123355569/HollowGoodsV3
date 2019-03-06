package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Widget.ArrowDownloadButton;
import com.hg.hollowgoods.Widget.SubmitButton;

/**
 * @ClassName:动画按钮示例
 * @Description:
 * @author: HollowGoods
 * @date: 2019年2月25日
 */
public class Ex39Activity extends BaseActivity {

    private ArrowDownloadButton download;
    private SubmitButton loading;
    private SubmitButton progress;

    private boolean isDownload = false;
    private int nowProgress = 0;
    private MyTask task;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_39;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex39);

        download = findViewById(R.id.adb_download);
        loading = findViewById(R.id.sb_loading);
        progress = findViewById(R.id.sb_progress);

        return null;
    }

    @Override
    public void setListener() {

        download.setOnStartAnimListener(() -> {

            new Thread(() -> {

                while (true) {
                    runOnUiThread(() -> {
                        nowProgress = nowProgress + 1;
                        download.setProgress(nowProgress);
                    });

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ignored) {

                    }

                    if (nowProgress == 100) {
                        nowProgress = 0;
                        break;
                    }
                }
            }).start();
        });

        download.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {

                if (isDownload) {
                    download.reset();
                } else {
                    download.startAnimating();
                }

                isDownload = !isDownload;
            }
        });

        loading.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                new Handler().postDelayed(() -> loading.doResult(false), 5000);
            }
        });

        progress.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                if (task == null || task.isCancelled()) {
                    task = new MyTask();
                    task.execute();
                }
            }
        });
    }

    private class MyTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            int i = 0;

            while (i <= 100) {
                if (isCancelled()) {
                    return null;
                }

                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {

                }

                i++;
                publishProgress(i);
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if (aBoolean == null) {
                progress.reset();
            }

            progress.doResult(aBoolean);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
        }
    }

}
