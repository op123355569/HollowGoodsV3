package com.hg.hollowgoods.Widget.BugView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.Glide.GlideOptions;
import com.hg.hollowgoods.Util.Glide.GlideUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Hollow Goods 2017-06-01.
 */

public class BugAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BugData> mData;
    private ViewHolder mViewHolder;

    public BugAdapter(Context mContext, ArrayList<BugData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {

        private ImageView img;
        private TextView moduleName;
        private TextView describe;

        public void init(View v) {
            img = (ImageView) v.findViewById(R.id.iv_img);
            moduleName = (TextView) v.findViewById(R.id.tv_module_name);
            describe = (TextView) v.findViewById(R.id.tv_describe);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        mViewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_wcatch, null);
            mViewHolder = new ViewHolder();
            mViewHolder.init(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        RequestOptions options = new RequestOptions()
                .placeholder(HGCommonResource.IMAGE_LOADING)
                .error(HGCommonResource.IMAGE_LOAD_ERROR);
        GlideOptions glideOptions = new GlideOptions(new File(mData.get(position).getImgPath()), mViewHolder.img, GlideOptions.NORMAL_FADE_IN, options);
        glideOptions.setThumbnail(0.1f);
        GlideUtils.loadImg(mContext, glideOptions);

        mViewHolder.moduleName.setText(mData.get(position).getModuleName());
        mViewHolder.describe.setText(mData.get(position).getDescribe());

        return convertView;
    }

    public void refreshData(ArrayList<BugData> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

}
