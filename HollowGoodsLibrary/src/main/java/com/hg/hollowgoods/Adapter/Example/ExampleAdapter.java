package com.hg.hollowgoods.Adapter.Example;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.Bean.Example.Example;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;

import java.util.List;

/**
 * Created by HG on 2018-06-06.
 */
public class ExampleAdapter extends CommonAdapter<Example> {

    public ExampleAdapter(Context context, int layoutId, List<Example> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Example item, int position) {

        if (item.getIconRes() == -1) {
            viewHolder.setImageResource(R.id.iv_icon, HGCommonResource.IMAGE_LOADING);
        } else {
            viewHolder.setImageResource(R.id.iv_icon, item.getIconRes());
        }

        viewHolder.setText(R.id.tv_label, item.getLabel());
    }

}
