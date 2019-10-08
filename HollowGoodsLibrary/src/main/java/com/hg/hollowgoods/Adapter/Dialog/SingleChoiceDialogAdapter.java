package com.hg.hollowgoods.Adapter.Dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Dialog.ChoiceItem;

import java.util.List;

/**
 * 单选对话框适配器
 * Created by Hollow Goods 2018-07-04.
 */
public class SingleChoiceDialogAdapter extends CommonAdapter<ChoiceItem> {

    private int checkedPosition = -1;
    private OnRecyclerViewItemClickListener onDescribeClickListener;

    public SingleChoiceDialogAdapter(Context context, int layoutId, List<ChoiceItem> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ChoiceItem item, int position) {

        if (item.getItem() instanceof Integer) {
            viewHolder.setVisible(R.id.tv_content, false);
            viewHolder.setVisible(R.id.iv_content, true);
            viewHolder.setVisible2(R.id.iv_flag, position == checkedPosition);

            viewHolder.setImageResource(R.id.iv_content, (Integer) item.getItem());
            viewHolder.setImageResource(R.id.iv_flag, R.drawable.ic_check_main_24dp);

            viewHolder.setCardViewColorRes(R.id.cv_bg, R.color.white);
        } else {
            viewHolder.setVisible(R.id.iv_content, false);
            viewHolder.setVisible(R.id.tv_content, true);
            viewHolder.setVisible2(R.id.iv_flag, position == checkedPosition);

            viewHolder.setText(R.id.tv_content, item.getItem().toString());
            viewHolder.setImageResource(R.id.iv_flag, R.drawable.ic_check_white_24dp);

            viewHolder.setCardViewColorRes(R.id.cv_bg, position == checkedPosition ? R.color.colorAccent : R.color.white);
            viewHolder.setTextColorRes(R.id.tv_content, position == checkedPosition ? R.color.white : R.color.colorAccent);
        }

        if (TextUtils.isEmpty(item.getDescribe())) {
            viewHolder.setVisible(R.id.iv_describe, false);
            viewHolder.setOnClickListener(R.id.iv_describe, null);
        } else {
            viewHolder.setVisible(R.id.iv_describe, true);

            ImageView describe = viewHolder.getView(R.id.iv_describe);

            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(
                    viewHolder.getContext(),
                    checkedPosition == position ? R.color.white : R.color.colorAccent
            ));

            describe.setImageTintList(colorStateList);

            viewHolder.setOnClickListener(R.id.iv_describe, new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    if (onDescribeClickListener != null) {
                        onDescribeClickListener.onRecyclerViewItemClick(view, viewHolder, position);
                    }
                }
            });
        }
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        refreshData(super.mData);
    }

    public void setOnDescribeClickListener(OnRecyclerViewItemClickListener onDescribeClickListener) {
        this.onDescribeClickListener = onDescribeClickListener;
    }
}
