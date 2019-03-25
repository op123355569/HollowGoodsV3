package com.hg.hollowgoods.Adapter.BaseAdapterView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.haozhang.lib.SlantedTextView;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.UI.Base.Click.OnAdapterViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
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
 * 万能适配器ViewHolder
 * Created by HG
 */
public class ViewHolder {

    private final SparseArray<View> mViews;
    protected int mPosition;
    private View mConvertView;
    private Context mContext;
    protected int mLayoutId;

    public ViewHolder(Context context, View itemView, ViewGroup parent, int position) {
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArray<>();
        mConvertView.setTag(this);
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
     * 拿到一个ViewHolder对象
     *
     * @param context     context
     * @param convertView convertView
     * @param parent      parent
     * @param layoutId    layoutId
     * @param position    position
     * @return ViewHolder
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {

        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            ViewHolder holder = new ViewHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;

            return holder;
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;

            return holder;
        }
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
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

    public int getLayoutId() {
        return mLayoutId;
    }

    public void updatePosition(int position) {
        mPosition = position;
    }

    public int getItemPosition() {
        return mPosition;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId viewId
     * @param text   text
     * @return ViewHolder
     */
    public ViewHolder setText(int viewId, String text) {

        TextView view = getView(viewId);
        view.setText(text);

        return this;
    }

    /**
     * 为TextView设置字体颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public ViewHolder setTextColor(int viewId, String color) {

        TextView view = getView(viewId);
        view.setTextColor(Color.parseColor(color));

        return this;
    }

    /**
     * 为TextView设置字体颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public ViewHolder setTextColor(int viewId, int color) {

        TextView view = getView(viewId);
        view.setTextColor(color);

        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {

        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));

        return this;
    }

    public ViewHolder setText(int viewId, int res) {
        TextView tv = getView(viewId);
        tv.setText(res);
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
        tv.setSlantedBackgroundColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    public ViewHolder setNiceSpinnerData(int viewId, String... data) {
        NiceSpinner niceSpinner = getView(viewId);
        List<String> dataset = new LinkedList<>(Arrays.asList(data));
        niceSpinner.attachDataSource(dataset);
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

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {

        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {

        ImageView view = getView(viewId);
        view.setImageBitmap(bm);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param options
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, LoadImageOptions options) {

        ImageView iv = getView(viewId);

        if (TextUtils.isEmpty(options.url)) {
            iv.setImageResource(options.failIconRes);
        } else {
            options.view = iv;
            XUtils xUtils = new XUtils();
            xUtils.loadImageByUrl(options);
        }

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param options
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, GlideOptions options) {

        ImageView iv = getView(viewId);

        if (TextUtils.isEmpty(options.getLoadByUrl())) {
            if (options.getRequestOptions() == null) {
                iv.setImageResource(HGCommonResource.IMAGE_LOAD_ERROR);
            } else {
                if (options.getRequestOptions().getErrorPlaceholder() != null) {
                    iv.setImageDrawable(options.getRequestOptions().getErrorPlaceholder());
                } else {
                    iv.setImageResource(options.getRequestOptions().getErrorId());
                }
            }
        } else {
            options.setLoadView(iv);
            options.setGif(!TextUtils.isEmpty(options.getLoadByUrl()) && FileUtils.isImageFileGif(options.getLoadByUrl()));
            GlideUtils.loadImg(mContext, options);
        }

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setHeadImageByUrl(int viewId, String url) {

        ImageView view = getView(viewId);

        if (TextUtils.isEmpty(url)) {
            view.setImageResource(HGCommonResource.NO_IMAGE_HEAD);
        } else {
            XUtils xUtils = new XUtils();
            LoadImageOptions options = new LoadImageOptions(view, url, HGCommonResource.NO_IMAGE_HEAD,
                    HGCommonResource.NO_IMAGE_HEAD, 50, 50, 0, false, ScaleType.CENTER_CROP);
            xUtils.loadImageByUrl(options);
        }

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setRoundHeadImageByUrl(int viewId, String url) {

        ImageView view = getView(viewId);

        if (TextUtils.isEmpty(url)) {
            view.setImageResource(HGCommonResource.NO_IMAGE_HEAD);
        } else {
            XUtils xUtils = new XUtils();
            LoadImageOptions options = new LoadImageOptions(view, url, HGCommonResource.NO_IMAGE_HEAD,
                    HGCommonResource.NO_IMAGE_HEAD, 50, 50, 0, true, ScaleType.CENTER_CROP);
            xUtils.loadImageByUrl(options);
        }

        return this;
    }

    public ViewHolder setViewBackground(int id, int color) {
        getView(id).setBackgroundColor(color);
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
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

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
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
