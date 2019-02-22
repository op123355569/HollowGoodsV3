package com.hg.hollowgoods.Adapter.Example;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.XUtils.LoadImageOptions;
import com.hg.hollowgoods.Util.XUtils.XUtils;

import java.util.ArrayList;

/**
 * 示例2适配器
 * Created by HG on 2018-06-07.
 */
public class Ex2Adapter extends BaseAdapter {

    private Context context = null;
    private ArrayList<String> url = null;
    private ViewHolder viewHolder = null;

    public Ex2Adapter(Context context, ArrayList<String> url) {
        this.context = context;
        this.url = url;
    }

    private class ViewHolder {
        ImageView img = null;
    }

    @Override
    public int getCount() {
        return url == null ? 0 : url.size();
    }

    @Override
    public Object getItem(int position) {
        return url == null ? null : url.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_ex_2, null);

            viewHolder = new ViewHolder();
            viewHolder.img = convertView.findViewById(R.id.iv_img);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LoadImageOptions options = new LoadImageOptions(
                viewHolder.img,
                url.get(position),
                HGCommonResource.IMAGE_LOADING,
                HGCommonResource.IMAGE_LOAD_ERROR,
                120,
                120,
                0,
                false,
                ImageView.ScaleType.CENTER_CROP
        );
        new XUtils().loadImageByUrl(options);

        return convertView;
    }

}
