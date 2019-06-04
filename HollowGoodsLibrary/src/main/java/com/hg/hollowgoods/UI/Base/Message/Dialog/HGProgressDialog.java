package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Widget.RingProgressBar;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 进度对话框
 * Created by HG on 2018-01-17.
 */

public class HGProgressDialog extends HGDialog {

    private ImageView progressImageView;
    private RingProgressBar progressBar;
    private TextView progressContentView;

    private CharSequence content;
    private CharSequence title;
    private boolean isIndefinite;

    HGProgressDialog(Context context, Object title, Object content, boolean cancelable, boolean isIndefinite, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.isIndefinite = isIndefinite;
        this.code = code;

        this.title = getValue(title, "");
        this.content = getValue(content, "");

        this.dialog = new AlertDialog.Builder(context)
                .setView(isIndefinite ? R.layout.dialog_progress_indefinite : R.layout.dialog_progress_determine)
                .create();
        this.dialog.setOnDismissListener(dialog -> HGProgressDialog.this.onDialogDismissListener.onDialogDismiss(HGProgressDialog.this));

        if (isIndefinite) {
            if (!TextUtils.isEmpty(this.title)) {
                this.dialog.setTitle(this.title);
            }

            this.dialog.setCancelable(cancelable);
            this.dialog.show();

            this.progressContentView = this.dialog.findViewById(R.id.tv_content);

            if (!TextUtils.isEmpty(this.content)) {
                this.progressContentView.setText(this.content);
            }
        } else {
            this.dialog.setCancelable(cancelable);
            this.dialog.show();

            this.progressContentView = this.dialog.findViewById(R.id.tv_content);
            this.progressImageView = this.dialog.findViewById(R.id.iv_progress);
            this.progressBar = this.dialog.findViewById(R.id.progressBar);

            if (!TextUtils.isEmpty(this.content)) {
                this.progressContentView.setText(this.content);
            }
        }
    }

    /**
     * 设置进度
     *
     * @param progress 0-100
     */
    public void setProgress(int progress) {

        if (!isIndefinite) {
            if (progress < 0) {
                progress = -progress;
            }

            if (progress > 100) {
                progress = progress - progress / 100 * 100;
                if (progress == 0) {
                    progress = 100;
                }
            }

            this.progressBar.setProgress(progress);

            if (progress == 100) {
                this.progressContentView.setText(R.string.complete);
                this.progressImageView.setBackgroundResource(R.drawable.ic_progress_complete);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        ObjectAnimator.ofFloat(this.progressBar, "alpha", 1f, 0f),
                        ObjectAnimator.ofFloat(this.progressImageView, "scaleX", 0f, 1f),
                        ObjectAnimator.ofFloat(this.progressImageView, "scaleY", 0f, 1f),
                        ObjectAnimator.ofFloat(this.progressContentView, "scaleX", 0f, 1f),
                        ObjectAnimator.ofFloat(this.progressContentView, "scaleY", 0f, 1f)
                );
                set.setDuration(300).start();
            } else {
                if (this.progressImageView.getBackground() != null) {
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(this.progressBar, "alpha", 0f, 1f),
                            ObjectAnimator.ofFloat(this.progressImageView, "scaleX", 1f, 0f),
                            ObjectAnimator.ofFloat(this.progressImageView, "scaleY", 1f, 0f),
                            ObjectAnimator.ofFloat(this.progressContentView, "scaleX", 1f, 0f),
                            ObjectAnimator.ofFloat(this.progressContentView, "scaleY", 1f, 0f)
                    );
                    set.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            progressImageView.setBackground(null);
                            progressImageView.setScaleX(1f);
                            progressImageView.setScaleY(1f);

                            progressContentView.setText(content);
                            progressContentView.setScaleX(1f);
                            progressContentView.setScaleY(1f);
                        }
                    });
                    set.setDuration(300).start();
                }
            }
        }
    }

    boolean isIndefinite() {
        return isIndefinite;
    }

}
