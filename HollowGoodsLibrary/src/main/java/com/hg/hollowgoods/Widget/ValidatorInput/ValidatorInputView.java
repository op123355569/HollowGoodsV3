package com.hg.hollowgoods.Widget.ValidatorInput;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Fragment.Proxy.ProxyConfig;
import com.hg.hollowgoods.UI.Fragment.Proxy.ProxyHelper;
import com.hg.hollowgoods.Util.RegexUtils;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item.Validator;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.ValidatorType;
import com.hg.hollowgoods.voice.VoiceListener;
import com.hg.hollowgoods.voice.VoiceUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;

/**
 * 验证输入控件
 * Created by HollowGoods on 2018-08-15.
 */
public class ValidatorInputView extends LinearLayout {

    private LinearLayout topView;
    private LinearLayout bottomView;

    private EditText input;
    private ImageView clearButton;
    private View line;

    private View valueView;
    private TextView minValue;
    private TextView lineValue;
    private TextView maxValue;
    private TextView labelValue;
    private TextView nowLength;
    private TextView slashLength;
    private TextView minLength;
    private TextView lineLength;
    private TextView maxLength;
    private TextView labelLength;
    private TextView tips;
    private View voiceView;
    private ImageView voice;
    private ProgressBar progressBar;

    private ArrayList<Validator> validator = new ArrayList<>();
    private Long lMinLength = null;
    private Long lMaxLength = null;
    private Double dMinValue = null;
    private Double dMaxValue = null;

    private int inputTextColor;
    private int inputTextColorHint;
    private int inputTextSize;
    private int inputLineFocusColor;
    private int inputLineFocusNoColor;
    private int inputLineRightColor;
    private int inputLineWrongColor;

    private VoiceUtils voiceUtils;
    private BaseActivity baseActivity;

    public ValidatorInputView(@NonNull Context context) {
        this(context, null);
    }

    public ValidatorInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValidatorInputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        init();
    }

    private void initAttr(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ValidatorInputView);

            inputTextColor = a.getColor(R.styleable.ValidatorInputView_input_textColor, ContextCompat.getColor(getContext(), R.color.txt_color_dark));
            inputTextColorHint = a.getColor(R.styleable.ValidatorInputView_input_textColorHint, ContextCompat.getColor(getContext(), R.color.txt_color_light));
            inputTextSize = a.getDimensionPixelSize(R.styleable.ValidatorInputView_input_textSize, 14);
            inputLineFocusColor = a.getColor(R.styleable.ValidatorInputView_input_lineFocusColor, ContextCompat.getColor(getContext(), R.color.colorAccent));
            inputLineFocusNoColor = a.getColor(R.styleable.ValidatorInputView_input_lineFocusNoColor, ContextCompat.getColor(getContext(), R.color.txt_color_light));
            inputLineRightColor = a.getColor(R.styleable.ValidatorInputView_input_lineRightColor, ContextCompat.getColor(getContext(), R.color.google_green));
            inputLineWrongColor = a.getColor(R.styleable.ValidatorInputView_input_lineWrongColor, ContextCompat.getColor(getContext(), R.color.google_red));

            a.recycle();
        }
    }

    private void init() {

        topView = (LinearLayout) View.inflate(getContext(), R.layout.validator_input_view_top, null);
        bottomView = (LinearLayout) View.inflate(getContext(), R.layout.validator_input_view_bottom, null);

        input = topView.findViewById(R.id.et_input);
        clearButton = topView.findViewById(R.id.iv_delete);
        line = topView.findViewById(R.id.line);
        voiceView = topView.findViewById(R.id.fl_voice);
        voice = topView.findViewById(R.id.iv_voice);
        progressBar = topView.findViewById(R.id.progressBar);

        valueView = bottomView.findViewById(R.id.ll_valueView);
        minValue = bottomView.findViewById(R.id.tv_minValue);
        lineValue = bottomView.findViewById(R.id.tv_lineValue);
        maxValue = bottomView.findViewById(R.id.tv_maxValue);
        labelValue = bottomView.findViewById(R.id.tv_labelValue);
        nowLength = bottomView.findViewById(R.id.tv_nowLength);
        slashLength = bottomView.findViewById(R.id.tv_slashLength);
        minLength = bottomView.findViewById(R.id.tv_minLength);
        lineLength = bottomView.findViewById(R.id.tv_lineLength);
        maxLength = bottomView.findViewById(R.id.tv_maxLength);
        labelLength = bottomView.findViewById(R.id.tv_labelLength);
        tips = bottomView.findViewById(R.id.tv_tips);

        this.setOrientation(VERTICAL);
        this.addView(topView);
        this.addView(bottomView);

        initVoice();
        initViewStyle();
        setClearButtonColor();
        initValidator();
        setListener();
    }

    private void initVoice() {

        voiceUtils = new VoiceUtils(getContext());

        if (VoiceUtils.isIncludeSDK()) {
            voiceView.setVisibility(VISIBLE);
            voiceUtils.setVoiceListener(new VoiceListener() {
                @Override
                public void onStart() {
                    voice.setVisibility(GONE);
                    progressBar.setVisibility(VISIBLE);
                }

                @Override
                public void onEnd() {
                    voice.setVisibility(VISIBLE);
                    progressBar.setVisibility(GONE);
                }

                @Override
                public void onResult(String text) {
                    input.append(text);
                }
            });
            voice.setOnClickListener(new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    if (baseActivity != null) {
                        ProxyHelper.create(baseActivity).requestProxy(
                                new ProxyConfig()
                                        .setPermissions(voiceUtils.permissions)
                                        .setRequestCode(12000)
                                        .setOnProxyRequestPermissionsResult((isAgreeAll, requestCode, permissions, isAgree) -> {
                                            if (isAgreeAll) {
                                                voiceUtils.setParam(false);
                                                voiceUtils.start();
                                            }
                                        })
                        );
                    }
                }
            });
        } else {
            voiceView.setVisibility(GONE);
        }
    }

    private void initViewStyle() {

        input.setTextColor(inputTextColor);
        input.setHintTextColor(inputTextColorHint);
        input.setTextSize(inputTextSize);

        updateUIMode(UIMode.NormalNoFocus, "");
    }

    private void initValidator() {

        lMinLength = null;
        lMaxLength = null;
        dMinValue = null;
        dMaxValue = null;

        // 验证规则
        if (validator != null) {
            String strMinValue = "";
            String strMaxValue = "";
            String strMinLength = "";
            String strMaxLength = "";

            for (Validator t : validator) {
                if (t.getType() == ValidatorType.MIN_VALUE) {
                    // 最小值
                    strMinValue = t.getItem().toString();
                    dMinValue = Double.valueOf(strMinValue);
                } else if (t.getType() == ValidatorType.MAX_VALUE) {
                    // 最大值
                    strMaxValue = t.getItem().toString();
                    dMaxValue = Double.valueOf(strMaxValue);
                } else if (t.getType() == ValidatorType.MIN_LENGTH) {
                    // 最小长度
                    strMinLength = t.getItem().toString();
                    lMinLength = Long.valueOf(strMinLength);
                } else if (t.getType() == ValidatorType.MAX_LENGTH) {
                    // 最大长度
                    strMaxLength = t.getItem().toString();
                    lMaxLength = Long.valueOf(strMaxLength);
                }
            }

            if (!TextUtils.isEmpty(strMinValue) && !TextUtils.isEmpty(strMaxValue)) {
                double dMin = Double.valueOf(strMinValue);
                double dMax = Double.valueOf(strMaxValue);

                if (dMin == (long) dMin) {
                    minValue.setText(((long) dMin) + "");
                } else {
                    minValue.setText(strMinValue);
                }

                if (dMax == (long) dMax) {
                    maxValue.setText(((long) dMax) + "");
                } else {
                    maxValue.setText(strMaxValue);
                }

                valueView.setVisibility(View.VISIBLE);
                minValue.setVisibility(View.VISIBLE);
                lineValue.setVisibility(View.VISIBLE);
                maxValue.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(strMinValue)) {
                double dMin = Double.valueOf(strMinValue);
                if (dMin == (long) dMin) {
                    minValue.setText(((long) dMin) + "+");
                } else {
                    minValue.setText(strMinValue + "+");
                }

                valueView.setVisibility(View.VISIBLE);
                minValue.setVisibility(View.VISIBLE);
                lineValue.setVisibility(View.GONE);
                maxValue.setVisibility(View.GONE);
            } else if (!TextUtils.isEmpty(strMaxValue)) {
                double dMax = Double.valueOf(strMaxValue);
                if (dMax == (long) dMax) {
                    maxValue.setText(((long) dMax) + "");
                } else {
                    maxValue.setText(strMaxValue);
                }

                valueView.setVisibility(View.VISIBLE);
                minValue.setVisibility(View.GONE);
                lineValue.setVisibility(View.GONE);
                maxValue.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(strMinLength) && !TextUtils.isEmpty(strMaxLength)) {
                minLength.setText(lMinLength.toString());
                maxLength.setText(lMaxLength.toString());

                slashLength.setVisibility(View.VISIBLE);
                minLength.setVisibility(View.VISIBLE);
                lineLength.setVisibility(View.VISIBLE);
                maxLength.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(strMinLength)) {
                minLength.setText(lMinLength.toString() + "+");

                slashLength.setVisibility(View.VISIBLE);
                minLength.setVisibility(View.VISIBLE);
                lineLength.setVisibility(View.GONE);
                maxLength.setVisibility(View.GONE);
            } else if (!TextUtils.isEmpty(strMaxLength)) {
                maxLength.setText(lMaxLength.toString());

                slashLength.setVisibility(View.VISIBLE);
                minLength.setVisibility(View.GONE);
                lineLength.setVisibility(View.GONE);
                maxLength.setVisibility(View.VISIBLE);
            }
        }

        checkLength(getText().length());
        checkValue(getText());
        if (isInputRight()) {
            updateUIMode(UIMode.NormalNoFocus, "");
        }
    }

    private void setListener() {

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                long length = s.length();
                String value = s.toString();
                nowLength.setText(length + "");

                checkLength(length);
                checkValue(value);
                if (isInputRight()) {
                    updateUIMode(UIMode.NormalFocus, "");
                }
            }
        });

        clearButton.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                input.setText("");
            }
        });

        input.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                showView(clearButton, true);
                if (isInputRight()) {
                    updateUIMode(UIMode.NormalFocus, "");
                }
            } else {
                hideView(clearButton, true);
                if (isInputRight()) {
                    updateUIMode(UIMode.NormalNoFocus, "");
                }
            }
        });
    }

    private void showView(View view, boolean isNeedAnim) {

        if (view.getVisibility() != VISIBLE) {
            view.setVisibility(VISIBLE);

            if (isNeedAnim) {
                AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                        ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
                );
                set.setDuration(300).start();
            }
        }
    }

    private void hideView(View view, boolean isNeedAnim) {

        if (view.getVisibility() != GONE) {
            if (isNeedAnim) {
                AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                        ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f)
                );
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(GONE);
                    }
                });
                set.setDuration(300).start();
            } else {
                view.setVisibility(GONE);
            }
        }
    }

    private void setClearButtonColor() {
        int textColor = input.getCurrentTextColor();
        ColorStateList colorStateList = ColorStateList.valueOf(textColor);
        clearButton.setImageTintList(colorStateList);
    }

    private void checkValue(String value) {

        if (dMinValue != null && dMaxValue != null) {
            if (!RegexUtils.isRealNumber1(value)) {
                setWrongValue();
            } else {
                double dValue = Double.valueOf(value);
                if (dValue >= dMinValue && dValue <= dMaxValue) {
                    setNormalValue();
                } else {
                    setWrongValue();
                }
            }
        } else if (dMinValue != null) {
            if (!RegexUtils.isRealNumber1(value)) {
                setWrongValue();
            } else {
                double dValue = Double.valueOf(value);
                if (dValue >= dMinValue) {
                    setNormalValue();
                } else {
                    setWrongValue();
                }
            }
        } else if (dMaxValue != null) {
            if (!RegexUtils.isRealNumber1(value)) {
                setWrongValue();
            } else {
                double dValue = Double.valueOf(value);
                if (dValue <= dMaxValue) {
                    setNormalValue();
                } else {
                    setWrongValue();
                }
            }
        } else {
            setNormalValue();
        }
    }

    private void setNormalValue() {
        labelValue.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_dark));
        minValue.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_normal));
        lineValue.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_normal));
        maxValue.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_normal));
    }

    private void setWrongValue() {
        labelValue.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
        minValue.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
        lineValue.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
        maxValue.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
    }

    private void checkLength(long length) {

        if (lMinLength != null && lMaxLength != null) {
            if (length >= lMinLength.longValue() && length <= lMaxLength.longValue()) {
                setNormalLength();
            } else {
                setWrongLength();
            }
        } else if (lMinLength != null) {
            if (length >= lMinLength.longValue()) {
                setNormalLength();
            } else {
                setWrongLength();
            }
        } else if (lMaxLength != null) {
            if (length <= lMaxLength.longValue()) {
                setNormalLength();
            } else {
                setWrongLength();
            }
        }
    }

    private void setNormalLength() {
        labelLength.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_dark));
        nowLength.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_normal));
        slashLength.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_normal));
        minLength.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_normal));
        lineLength.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_normal));
        maxLength.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_color_normal));
    }

    private void setWrongLength() {
        labelLength.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
        nowLength.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
        slashLength.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
        minLength.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
        lineLength.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
        maxLength.setTextColor(ContextCompat.getColor(getContext(), R.color.google_red));
    }

    private enum UIMode {
        Wrong,
        Right,
        NormalFocus,
        NormalNoFocus,
    }

    /**** 暴露的方法 ****/

    /**
     * 设置验证项
     *
     * @param validator
     */
    public void setValidator(Validator... validator) {

        this.validator.clear();

        if (validator != null) {
            for (Validator t : validator) {
                this.validator.add(t);
            }
        }

        initValidator();
    }

    /**
     * 输入是否正确
     *
     * @return
     */
    public boolean isInputRight() {

        boolean isValid;

        for (int i = 0; i < validator.size(); i++) {
            isValid = validator.get(i).isValid(getText());

            if (lMinLength == null && TextUtils.isEmpty(getText()) && !isValid) {
                switch (validator.get(i).getType()) {
                    case ValidatorType.EMAIL:
                    case ValidatorType.EMPTY:
                    case ValidatorType.EQUAL:
                    case ValidatorType.BEGIN:
                    case ValidatorType.END:
                    case ValidatorType.PHONE:
                    case ValidatorType.CONTAINS:
                        isValid = true;
                        break;
                }
            }
            if (!isValid) {
                updateUIMode(UIMode.Wrong, validator.get(i).getErrorMessage());
                return false;
            }
        }

        updateUIMode(UIMode.Right, "");

        return true;
    }

    private void updateUIMode(UIMode uiMode, String msg) {

        tips.setText(msg);

        switch (uiMode) {
            case Wrong:
                line.setBackgroundColor(inputLineWrongColor);
                break;
            case Right:
                line.setBackgroundColor(inputLineRightColor);
                break;
            case NormalFocus:
                line.setBackgroundColor(inputLineFocusColor);
                break;
            case NormalNoFocus:
                line.setBackgroundColor(inputLineFocusNoColor);
                break;
        }
    }

    public String getText() {
        return input.getText().toString();
    }

    public void setText(CharSequence text) {
        input.setText(text);
    }

    public void setHint(CharSequence hint) {
        input.setHint(hint);
    }

    public EditText getInputView() {
        return input;
    }

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }
}
