package com.hg.hollowgoods.Adapter.BaseRecyclerView.Base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.haozhang.lib.SlantedTextView;
import com.hg.hollowgoods.UI.Base.Click.OnAdapterViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.DensityUtils;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.Glide.GlideOptions;
import com.hg.hollowgoods.Util.Glide.GlideUtils;
import com.hg.hollowgoods.Util.XUtils.LoadImageOptions;
import com.hg.hollowgoods.Util.XUtils.XUtils;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hollow Goods on unknown.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder createViewHolder(Context context, View itemView) {
        return new ViewHolder(context, itemView);
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView);
    }

    /**
     * 获取上下文
     *
     * @return Context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId viewId
     * @return <T extends View>
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);

        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**** 以下为辅助方法 *****/

    /**
     * 设置控件高度
     *
     * @param viewId viewId
     * @param dp     dp
     * @return ViewHolder
     */
    public ViewHolder setViewHeight(int viewId, int dp) {

        View view = getView(viewId);
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        vlp.height = DensityUtils.dp2px(mContext, dp);
        view.setLayoutParams(vlp);

        return this;
    }

    public ViewHolder setViewBackgroundTint(int viewId, int color) {

        View view = getView(viewId);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        view.setBackgroundTintList(colorStateList);

        return this;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId viewId
     * @param text   text
     * @return ViewHolder
     */
    public ViewHolder setText(int viewId, String text) {

        TextView tv = getView(viewId);
        tv.setText(text);

        return this;
    }

    /**
     * 为TextView设置字体颜色
     *
     * @param viewId viewId
     * @param color  color
     * @return ViewHolder
     */
    public ViewHolder setTextColor(int viewId, String color) {

        TextView view = getView(viewId);
        view.setTextColor(Color.parseColor(color));

        return this;
    }

    public ViewHolder setText(int viewId, int res) {

        TextView tv = getView(viewId);
        tv.setText(res);

        return this;
    }

    public ViewHolder setTextSize(int viewId, float size) {

        TextView tv = getView(viewId);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

        return this;
    }

    public ViewHolder setTextHint(int viewId, String text) {

        TextView tv = getView(viewId);
        tv.setHint(text);

        return this;
    }

    public ViewHolder setTextHint(int viewId, int res) {

        TextView tv = getView(viewId);
        tv.setHint(res);

        return this;
    }

    public ViewHolder setSlanted(int viewId, int colorRes, int textRes) {
        return setSlanted(viewId, colorRes, mContext.getString(textRes));
    }

    public ViewHolder setSlanted(int viewId, int colorRes, String text) {

        SlantedTextView tv = getView(viewId);
        tv.setText(text);
        tv.setSlantedBackgroundColor(ContextCompat.getColor(mContext, colorRes));

        return this;
    }

    public ViewHolder setNiceSpinnerData(int viewId, String... data) {

        NiceSpinner niceSpinner = getView(viewId);
        List<String> dataSet = new LinkedList<>(Arrays.asList(data));
        niceSpinner.attachDataSource(dataSet);

        return this;
    }

    public ViewHolder setNiceSpinnerClickable(int viewId, boolean clickable) {

        NiceSpinner niceSpinner = getView(viewId);
        niceSpinner.setClickable(clickable);

        return this;
    }

    public ViewHolder setNiceSpinnerCheckedPosition(int viewId, int position) {

        NiceSpinner niceSpinner = getView(viewId);
        niceSpinner.setSelectedIndex(position);

        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {

        ImageView view = getView(viewId);
        view.setImageResource(resId);

        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {

        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);

        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {

        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);

        return this;
    }

    public ViewHolder setImageByUrl(int viewId, LoadImageOptions options) {

        ImageView view = getView(viewId);

        if (TextUtils.isEmpty(options.url)) {
            view.setImageResource(options.failIconRes);
        } else {
            options.view = view;
            XUtils xUtils = new XUtils();
            xUtils.loadImageByUrl(options);
        }

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId  viewId
     * @param options options
     * @return ViewHolder
     */
    public ViewHolder setImageByUrl(int viewId, GlideOptions options) {

        ImageView iv = getView(viewId);

        if (TextUtils.isEmpty(options.getLoadByUrl()) && options.getLoadByRes() == GlideOptions.NO_SETTING_RES) {
            if (options.getRequestOptions().getErrorPlaceholder() != null) {
                iv.setImageDrawable(options.getRequestOptions().getErrorPlaceholder());
            } else {
                iv.setImageResource(options.getRequestOptions().getErrorId());
            }
        } else {
            options.setLoadView(iv);
            options.setGif(!TextUtils.isEmpty(options.getLoadByUrl()) && FileUtils.isImageFileGif(options.getLoadByUrl()));
            GlideUtils.loadImg(mContext, options);
        }

        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {

        View view = getView(viewId);
        view.setBackgroundColor(color);

        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {

        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);

        return this;
    }

    public ViewHolder setCardViewColorRes(int viewId, int colorRes) {

        CardView view = getView(viewId);
        view.setCardBackgroundColor(ContextCompat.getColor(mContext, colorRes));

        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {

        TextView view = getView(viewId);
        view.setTextColor(textColor);

        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {

        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));

        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }

        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {

        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);

        return this;
    }

    public ViewHolder setVisible2(int viewId, boolean visible) {

        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

        return this;
    }

    public ViewHolder linkify(int viewId) {

        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);

        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {

        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }

        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {

        ProgressBar view = getView(viewId);
        view.setProgress(progress);

        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {

        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);

        return this;
    }

    public ViewHolder setMax(int viewId, int max) {

        ProgressBar view = getView(viewId);
        view.setMax(max);

        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {

        RatingBar view = getView(viewId);
        view.setRating(rating);

        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {

        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);

        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {

        View view = getView(viewId);
        view.setTag(tag);

        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {

        View view = getView(viewId);
        view.setTag(key, tag);

        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {

        Checkable view = getView(viewId);
        view.setChecked(checked);

        return this;
    }

    public ViewHolder setClickable(int viewId, boolean clickable) {

        View view = getView(viewId);
        view.setClickable(clickable);

        return this;
    }

    public ViewHolder setEnabled(int viewId, boolean enabled) {

        View view = getView(viewId);
        view.setEnabled(enabled);

        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId, OnViewClickListener listener) {

        View view = getView(viewId);
        view.setOnClickListener(listener);

        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {

        View view = getView(viewId);
        view.setOnTouchListener(listener);

        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, OnViewClickListener listener) {

        View view = getView(viewId);
        view.setOnLongClickListener(listener);

        return this;
    }

    public ViewHolder setAdapterViewItemClickListener(int viewId, OnAdapterViewItemClickListener listener) {

        AdapterView view = getView(viewId);
        view.setOnItemClickListener(listener);

        return this;
    }

    public ViewHolder setAdapterViewItemLongClickListener(int viewId, OnAdapterViewItemClickListener listener) {

        AdapterView view = getView(viewId);
        view.setOnItemLongClickListener(listener);

        return this;
    }

    public ViewHolder setNiceSpinnerOnSelectListener(int viewId, AdapterView.OnItemSelectedListener listener) {

        NiceSpinner niceSpinner = getView(viewId);
        niceSpinner.setOnItemSelectedListener(listener);

        return this;
    }

}
