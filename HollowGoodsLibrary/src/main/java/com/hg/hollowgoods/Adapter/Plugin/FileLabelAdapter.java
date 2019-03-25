package com.hg.hollowgoods.Adapter.Plugin;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.Bean.Plugin.FileSelectorLabel;
import com.hg.hollowgoods.R;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: HollowGoods
 * @date: 2018年09月28日
 */
public class FileLabelAdapter extends CommonAdapter<FileSelectorLabel> {

    public FileLabelAdapter(Context context, int layoutId, List<FileSelectorLabel> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FileSelectorLabel item, int position) {
        if (position == 0) {
            viewHolder.setText(R.id.tv_label, R.string.main_dir);
        } else {
            viewHolder.setText(R.id.tv_label, item.getFile().getName());
        }
    }

}
