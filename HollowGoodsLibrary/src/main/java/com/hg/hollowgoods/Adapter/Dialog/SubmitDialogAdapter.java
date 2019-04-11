package com.hg.hollowgoods.Adapter.Dialog;

import android.content.Context;
import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Dialog.ConfigSubmit;
import com.hg.hollowgoods.Widget.RingProgressBar;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;

import java.util.List;

/**
 * 提交对话框适配器
 * Created by Hollow Goods on 2019-04-10.
 */
public class SubmitDialogAdapter extends CommonAdapter<ConfigSubmit> {

    private InfiniteScrollAdapter infiniteAdapter;
    private OnRecyclerViewItemClickListener onRefreshClickListener;

    public SubmitDialogAdapter(Context context, int layoutId, List<ConfigSubmit> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ConfigSubmit item, int position) {

        viewHolder.setText(R.id.tv_title, "第" + (infiniteAdapter.getRealPosition(position) + 1) + "步" + " " + item.getStepName());

        viewHolder.setVisible(R.id.fl_progress, item.isIndefinite());
        viewHolder.setVisible(R.id.progress, !item.isIndefinite());

        if (!item.isIndefinite()) {
            RingProgressBar progressBar = viewHolder.getView(R.id.progress);
            progressBar.setProgress(item.getProgress());
        }

        String status;
        switch (item.getStatus()) {
            case Wait:
                status = viewHolder.getContext().getString(R.string.submit_dialog_status_wait);
                viewHolder.setVisible(R.id.iv_refresh, false);
                viewHolder.setTextColorRes(R.id.tv_status, R.color.google_yellow);
                if (item.isIndefinite()) {
                    viewHolder.setVisible(R.id.iv_complete, false);
                    viewHolder.setVisible(R.id.iv_error, false);
                }
                viewHolder.setVisible(R.id.iv_wait, true);
                break;
            case Request:
                status = viewHolder.getContext().getString(R.string.submit_dialog_status_request);
                viewHolder.setVisible(R.id.iv_refresh, false);
                viewHolder.setTextColorRes(R.id.tv_status, R.color.google_blue);
                if (item.isIndefinite()) {
                    viewHolder.setVisible(R.id.iv_complete, false);
                    viewHolder.setVisible(R.id.iv_error, false);
                }
                viewHolder.setVisible(R.id.iv_wait, false);
                break;
            case Error:
                status = viewHolder.getContext().getString(R.string.submit_dialog_status_error);
                viewHolder.setVisible(R.id.iv_refresh, true);
                viewHolder.setTextColorRes(R.id.tv_status, R.color.google_red);
                if (item.isIndefinite()) {
                    viewHolder.setVisible(R.id.iv_complete, false);
                    viewHolder.setVisible(R.id.iv_error, true);
                }
                viewHolder.setVisible(R.id.iv_wait, false);
                break;
            case Success:
                status = viewHolder.getContext().getString(R.string.submit_dialog_status_success);
                viewHolder.setVisible(R.id.iv_refresh, false);
                viewHolder.setTextColorRes(R.id.tv_status, R.color.google_green);
                if (item.isIndefinite()) {
                    viewHolder.setVisible(R.id.iv_complete, true);
                    viewHolder.setVisible(R.id.iv_error, false);
                }
                viewHolder.setVisible(R.id.iv_wait, false);
                break;
            default:
                status = viewHolder.getContext().getString(R.string.submit_dialog_status_unknown);
                viewHolder.setVisible(R.id.iv_refresh, false);
                viewHolder.setTextColorRes(R.id.tv_status, R.color.txt_color_light);
                if (item.isIndefinite()) {
                    viewHolder.setVisible(R.id.iv_complete, false);
                    viewHolder.setVisible(R.id.iv_error, true);
                }
                viewHolder.setVisible(R.id.iv_wait, false);
                break;
        }
        // 状态文字
        viewHolder.setText(R.id.tv_status, status);

        viewHolder.setOnClickListener(R.id.iv_refresh, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                if (onRefreshClickListener != null) {
                    onRefreshClickListener.onRecyclerViewItemClick(view, viewHolder, position);
                }
            }
        });
    }

    public void setInfiniteAdapter(InfiniteScrollAdapter infiniteAdapter) {
        this.infiniteAdapter = infiniteAdapter;
    }

    public void setOnRefreshClickListener(OnRecyclerViewItemClickListener onRefreshClickListener) {
        this.onRefreshClickListener = onRefreshClickListener;
    }
}
