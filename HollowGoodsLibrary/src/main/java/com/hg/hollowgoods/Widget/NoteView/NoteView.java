package com.hg.hollowgoods.Widget.NoteView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.hollowgoods.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 右上角消息提醒控件
 * Created by HG on 2018-01-24.
 */

public class NoteView extends FrameLayout {

    private Context context;

    private ImageView icon;
    private TextView label;
    private View notePoint;
    private TextView noteText;

    private NoteMode noteMode = NoteMode.MODE_TEXT;
    private NoteCountShowMode noteCountShowMode = NoteCountShowMode.MODE_99;
    private int noteCount = 0;

    public NoteView(@NonNull Context context) {
        this(context, null);
    }

    public NoteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {

        FrameLayout noteViewLayout = (FrameLayout) View.inflate(context, R.layout.view_note, null);
        addView(noteViewLayout);

        icon = findViewById(R.id.iv_icon);
        label = findViewById(R.id.tv_label);
        notePoint = findViewById(R.id.notePoint);
        noteText = findViewById(R.id.tv_noteText);
    }

    private void refreshNoteCount() {

        if (noteCount > 0) {
            switch (noteMode) {
                case MODE_TEXT:
                    if (notePoint.getVisibility() != GONE) {
                        notePoint.setVisibility(GONE);
                    }
                    if (noteText.getVisibility() != VISIBLE) {
                        showNoteAnim();
                    }
                    if (noteCount < 100) {
                        noteText.setText(noteCount + "");
                    } else {
                        if (noteCountShowMode == NoteCountShowMode.MODE_99) {
                            noteText.setText("99+");
                        } else {
                            noteText.setText(noteCount + "");
                        }
                    }
                    break;
                case MODE_POINT:
                    if (notePoint.getVisibility() != VISIBLE) {
                        showNoteAnim();
                    }
                    if (noteText.getVisibility() != GONE) {
                        noteText.setVisibility(GONE);
                    }
                    break;
            }
        } else {
            switch (noteMode) {
                case MODE_TEXT:
                    if (notePoint.getVisibility() != GONE) {
                        notePoint.setVisibility(GONE);
                    }
                    if (noteText.getVisibility() != GONE) {
                        hideNoteAnim();
                    }
                    break;
                case MODE_POINT:
                    if (noteText.getVisibility() != GONE) {
                        noteText.setVisibility(GONE);
                    }
                    if (notePoint.getVisibility() != GONE) {
                        hideNoteAnim();
                    }
                    break;
            }
        }
    }

    private void showNoteAnim() {

        View animView;

        if (noteMode == NoteMode.MODE_POINT) {
            animView = notePoint;
        } else {
            animView = noteText;
        }

        animView.setVisibility(VISIBLE);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(animView, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(animView, "scaleY", 0f, 1f)
        );
        set.setDuration(300).start();
    }

    private void hideNoteAnim() {

        final View animView;

        if (noteMode == NoteMode.MODE_POINT) {
            animView = notePoint;
        } else {
            animView = noteText;
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(animView, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(animView, "scaleY", 1f, 0f)
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animView.setVisibility(GONE);
            }
        });
        set.setDuration(300).start();
    }

    public NoteView setIconRes(int res) {
        icon.setImageResource(res);
        return this;
    }

    public NoteView setLabel(int res) {
        setLabel(context.getString(res));
        return this;
    }

    public NoteView setLabel(String text) {
        label.setText(text);
        return this;
    }

    public NoteView setLabelTextSize(float size) {
        label.setTextSize(size);
        return this;
    }

    public NoteView setLabelTextColorRes(int res) {
        setLabel(context.getResources().getColor(res));
        return this;
    }

    public NoteView setLabelTextColor(int color) {
        label.setTextColor(color);
        return this;
    }

    public NoteView setNoteMode(NoteMode noteMode) {
        this.noteMode = noteMode;
        refreshNoteCount();
        return this;
    }

    public NoteView setNoteCountShowMode(NoteCountShowMode noteCountShowMode) {
        this.noteCountShowMode = noteCountShowMode;
        return this;
    }

    public NoteView setNoteCount(int noteCount) {
        this.noteCount = noteCount;
        refreshNoteCount();
        return this;
    }

    public NoteView setNeedLabel(boolean isNeed) {
        label.setVisibility(isNeed ? VISIBLE : GONE);
        return this;
    }

    public NoteView setIconSize(int size) {
        ViewGroup.LayoutParams vlp = icon.getLayoutParams();
        vlp.width = size;
        vlp.height = size;
        icon.setLayoutParams(vlp);
        return this;
    }

}
