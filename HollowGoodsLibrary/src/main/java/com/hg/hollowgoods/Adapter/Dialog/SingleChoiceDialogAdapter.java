package com.hg.hollowgoods.Adapter.Dialog;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.R;

import java.util.List;

/**
 * 单选对话框适配器
 * Created by HG on 2018-07-04.
 */
public class SingleChoiceDialogAdapter extends CommonAdapter<Object> {

    private int checkedPosition = -1;

    public SingleChoiceDialogAdapter(Context context, int layoutId, List<Object> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

        if (item instanceof Integer) {
            viewHolder.setVisible(R.id.tv_content, false);
            viewHolder.setVisible(R.id.iv_content, true);
            viewHolder.setVisible2(R.id.iv_flag, position == checkedPosition);

            viewHolder.setImageResource(R.id.iv_content, (Integer) item);
            viewHolder.setImageResource(R.id.iv_flag, R.drawable.ic_check_main_24dp);

            viewHolder.setCardViewColorRes(R.id.cv_bg, R.color.white);
        } else {
            viewHolder.setVisible(R.id.iv_content, false);
            viewHolder.setVisible(R.id.tv_content, true);
            viewHolder.setVisible2(R.id.iv_flag, position == checkedPosition);

            viewHolder.setText(R.id.tv_content, item.toString());
            viewHolder.setImageResource(R.id.iv_flag, R.drawable.ic_check_white_24dp);

            viewHolder.setCardViewColorRes(R.id.cv_bg, position == checkedPosition ? R.color.colorAccent : R.color.white);
            viewHolder.setTextColorRes(R.id.tv_content, position == checkedPosition ? R.color.white : R.color.colorAccent);
        }
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        refreshData(super.mData);
    }

}
