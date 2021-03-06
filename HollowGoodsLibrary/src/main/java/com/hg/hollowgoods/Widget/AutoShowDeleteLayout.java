package com.hg.hollowgoods.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hg.hollowgoods.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * 输入框自动显示删除按钮布局
 * Created by HollowGoods
 */

public class AutoShowDeleteLayout extends FrameLayout {

    private ImageView mDelete = null;
    private EditText mEditText = null;

    private int mDeleteSize = 0;
    private int mDeleteMarginRight = 0;
    private boolean mIsMeasure = false;
    private Context mContext = null;
    private Drawable mDeleteImg = null;
    private boolean enable = true;

    public AutoShowDeleteLayout(Context context) {
        this(context, null);
    }

    public AutoShowDeleteLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoShowDeleteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!mIsMeasure) {
            init(mContext);
            mIsMeasure = true;
        }
    }

    private void init(Context context) {

        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i) instanceof EditText) {
                mEditText = (EditText) this.getChildAt(i);
            }
        }

        if (mEditText != null) {
            mDeleteSize = dp2px(mContext, 30f);
            int mDeletePaddingDefault = dp2px(mContext, 7.5f);
            mDeleteMarginRight = 0;
            if (mEditText.getPaddingEnd() > 0) {
                mDeleteMarginRight = mEditText.getPaddingEnd();
            }
            getDeleteDrawable();

            mDelete = new ImageView(context);
            int textColor = mEditText.getCurrentTextColor();
            setDeleteButtonColor(textColor);
            LayoutParams layoutParams = new LayoutParams(mDeleteSize, mDeleteSize, Gravity.CENTER_VERTICAL | Gravity.END);
            layoutParams.setMarginEnd(mDeleteMarginRight);
            layoutParams.bottomMargin = mEditText instanceof MaterialEditText ? 10 : 0;
            mDelete.setLayoutParams(layoutParams);
            mDelete.setPadding(mDeletePaddingDefault, mDeletePaddingDefault, mDeletePaddingDefault, mDeletePaddingDefault);
            mDelete.setVisibility(View.GONE);

            mDelete.setOnClickListener(v -> {
                if (enable) {
                    mEditText.setText("");
                }
            });

            this.addView(mDelete);

            int editTextPaddingEnd = mDeleteSize + mDeleteMarginRight;
            if (mEditText instanceof MaterialEditText) {
                ((MaterialEditText) mEditText).setPaddings(
                        ((MaterialEditText) mEditText).getInnerPaddingLeft(),
                        ((MaterialEditText) mEditText).getInnerPaddingTop(),
                        editTextPaddingEnd,
                        ((MaterialEditText) mEditText).getInnerPaddingBottom()
                );
            } else {
                mEditText.setPadding(
                        mEditText.getPaddingStart(),
                        mEditText.getPaddingTop(),
                        editTextPaddingEnd,
                        mEditText.getPaddingBottom()
                );
            }

            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (mDelete != null) {
                        if (s != null) {
                            String str = s.toString();
                            if (TextUtils.isEmpty(str)) {
                                if (mDelete.getVisibility() != View.GONE) {
                                    mDelete.setVisibility(View.GONE);
                                }
                            } else {
                                if (mDelete.getVisibility() != View.VISIBLE) {
                                    mDelete.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            mDelete.setVisibility(View.GONE);
                        }
                    }
                }
            });

            if (!TextUtils.isEmpty(mEditText.getText().toString())) {
                mDelete.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }

    public void setDeleteButtonColorRes(int colorRes) {

        getDeleteDrawable();
        mDeleteImg.setColorFilter(mContext.getResources().getColor(colorRes), PorterDuff.Mode.SRC_IN);

        if (mDelete != null) {
            mDelete.setImageDrawable(mDeleteImg);
        }
    }

    public void setDeleteButtonColor(int color) {

        getDeleteDrawable();
        mDeleteImg.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        if (mDelete != null) {
            mDelete.setImageDrawable(mDeleteImg);
        }
    }

    public void setDeleteButtonColor(String color) {

        getDeleteDrawable();
        mDeleteImg.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);

        if (mDelete != null) {
            mDelete.setImageDrawable(mDeleteImg);
        }
    }

    public void setDeleteButtonEnable(boolean enable) {
        this.enable = enable;
    }

    private Drawable getDeleteDrawable() {

        if (mDeleteImg == null) {
            Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.btn_delete);
            int width = b.getWidth();
            int height = b.getHeight();
            int[] pixs = new int[width * height];
            b.getPixels(pixs, 0, width, 0, 0, width, height);
            b.recycle();
            b = Bitmap.createBitmap(pixs, width, height, Bitmap.Config.ARGB_8888);
            mDeleteImg = bitmap2Drawable(b);
        }

        return mDeleteImg;
    }

    /**
     * Bitmap转换成Drawable
     *
     * @param bitmap
     * @return
     */
    @SuppressWarnings("deprecation")
    private Drawable bitmap2Drawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }

}
