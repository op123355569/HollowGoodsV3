package com.hg.hollowgoods.Widget.FileRead;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.LogUtils;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * Created by 12457 on 2017/8/29.
 */

public class SuperFileView extends FrameLayout implements TbsReaderView.ReaderCallback {

    private TbsReaderView mTbsReaderView;
    private Context context;

    public SuperFileView(Context context) {
        this(context, null, 0);
    }

    public SuperFileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperFileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTbsReaderView = new TbsReaderView(context, this);
        this.addView(mTbsReaderView, new LinearLayout.LayoutParams(-1, -1));
        this.context = context;
    }

    private OnGetFilePathListener mOnGetFilePathListener;


    public void setOnGetFilePathListener(OnGetFilePathListener mOnGetFilePathListener) {
        this.mOnGetFilePathListener = mOnGetFilePathListener;
    }


    private TbsReaderView getTbsReaderView(Context context) {
        return new TbsReaderView(context, this);
    }

    public void displayFile(File mFile) {

        if (mFile != null && !TextUtils.isEmpty(mFile.toString())) {
            // 增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
            String bsReaderTemp = HGSystemConfig.APP_BASE_PATH + "/Temp/";
            FileUtils.checkFileExist(bsReaderTemp);

            // 加载文件
            Bundle localBundle = new Bundle();
            LogUtils.Log(mFile.toString(), SuperFileView.class);
            localBundle.putString("filePath", mFile.toString());
            localBundle.putString("tempPath", bsReaderTemp);

            if (this.mTbsReaderView == null) {
                this.mTbsReaderView = getTbsReaderView(context);
            }

            boolean bool = this.mTbsReaderView.preOpen(getFileType(mFile.toString()), false);

            if (bool) {
                this.mTbsReaderView.openFile(localBundle);
            }
        } else {
            LogUtils.Log("文件路径无效！", SuperFileView.class);
        }
    }

    /***
     * 获取文件类型
     *
     * @param url
     * @return
     */
    private String getFileType(String url) {

        if (TextUtils.isEmpty(url)) {
            return "";
        }

        int index = url.lastIndexOf(".");

        if (index <= -1) {
            return "";
        }

        return url.substring(index + 1);
    }

    public void show() {
        if (mOnGetFilePathListener != null) {
            mOnGetFilePathListener.onGetFilePath(this);
        }
    }

    /**
     * 将获取File路径的工作，“外包”出去
     */
    public interface OnGetFilePathListener {
        void onGetFilePath(SuperFileView mSuperFileView2);
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        LogUtils.Log("****************************************************" + integer, SuperFileView.class);
    }

    public void onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }

}
