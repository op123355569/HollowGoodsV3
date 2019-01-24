package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Widget.RingProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 示例15界面
 * Created by HG on 2018-07-26.
 */
public class Ex15Activity extends BaseActivity {

    private RingProgressBar mRingProgressBar1;
    private RingProgressBar mRingProgressBar2;

    private int mProgress = 0;
    private Timer timer = null;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_15;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        mRingProgressBar1 = findViewById(R.id.mRingProgressBar1);
        mRingProgressBar2 = findViewById(R.id.mRingProgressBar2);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    mRingProgressBar1.setProgress(mProgress);
                    mRingProgressBar2.setProgress(mProgress);
                    mProgress++;
                    if (mProgress > 100) {
                        mProgress = 0;
                    }
                });
            }
        }, 200, 200);

        return null;
    }

    /**
     * ringColor	color	Color ring
     * ringProgressColor	color	Progress of color
     * ringWidth	dimension	Ring width of progress
     * textColor	color	Text color
     * textSize	dimension	Text size
     * max	integer	Max progress
     * textIsShow	boolean	Is display text
     * style	STROKE& FILL	Circle progress style
     */
    @Override
    public void setListener() {

        mRingProgressBar1.setOnProgressListener(() -> t.showShortToast("1完成"));

        mRingProgressBar2.setOnProgressListener(() -> t.showShortToast("2完成"));
    }

    @Override
    protected void onDestroy() {

        timer.cancel();
        timer = null;

        super.onDestroy();
    }

}
