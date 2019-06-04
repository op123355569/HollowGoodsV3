package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.Adapter.Dialog.SubmitDialogAdapter;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

/**
 * 提交对话框
 * Created by HG on 2019-04-10.
 */

class HGSubmitDialog extends HGDialog {

    private DiscreteScrollView result;
    private TextView tips;
    private TextView cancel;
    private View allFlag;

    private InfiniteScrollAdapter infiniteScrollAdapter;
    private ArrayList<ConfigSubmit> data = new ArrayList<>();
    private int nowPosition = 0;

    HGSubmitDialog(Context context, ArrayList<ConfigSubmit> configSubmits, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;
        if (configSubmits != null) {
            data.addAll(configSubmits);
        }

        this.dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_submit)
                .create();
        this.dialog.setOnDismissListener(dialog -> HGSubmitDialog.this.onDialogDismissListener.onDialogDismiss(HGSubmitDialog.this));

        this.dialog.setCancelable(false);
        this.dialog.show();

        result = this.dialog.findViewById(R.id.dsv_result);
        tips = this.dialog.findViewById(R.id.tv_tips);
        cancel = this.dialog.findViewById(R.id.tv_cancel);
        allFlag = this.dialog.findViewById(R.id.iv_allFlag);

        initView();
    }

    private void initView() {

        if (result != null) {
            // 方向
            result.setOrientation(DSVOrientation.HORIZONTAL);
            // 滚动监听
            result.addOnItemChangedListener((viewHolder, adapterPosition) -> {
                int position = infiniteScrollAdapter.getRealPosition(adapterPosition);
                onItemChanged(position);
            });

            // 创建普通适配器
            SubmitDialogAdapter adapter = new SubmitDialogAdapter(this.context, R.layout.item_submit_dialog, data);
            // 生成无限滚动适配器
            infiniteScrollAdapter = InfiniteScrollAdapter.wrap(adapter);
            // 把无限滚动适配器放入普通适配器，为了获取真实的Position
            adapter.setInfiniteAdapter(infiniteScrollAdapter);
            // 填充适配器
            result.setAdapter(infiniteScrollAdapter);

            // 设置滚动动画时间
            result.setItemTransitionTimeMillis(200);
            result.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());

            onItemChanged(nowPosition);

            adapter.setOnRefreshClickListener(new OnRecyclerViewItemClickListener(false) {
                @Override
                public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                    if (onDialogClickListener != null) {
                        Bundle data = new Bundle();
                        data.putInt(HGParamKey.Position.getValue(), infiniteScrollAdapter.getRealPosition(position));
                        onDialogClickListener.onDialogClick(code, true, data);
                    }
                }
            });
        }

        refreshStep();

        if (cancel != null) {
            cancel.setOnClickListener(new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void onItemChanged(int position) {
        nowPosition = position;
    }

    void refreshDialog(ArrayList<ConfigSubmit> configSubmits) {

        if (configSubmits != null) {
            data.clear();
            data.addAll(configSubmits);

            infiniteScrollAdapter.notifyDataSetChanged();

            refreshStep();
        }
    }

    private void refreshStep() {

        if (tips != null) {
            int i = 0;

            for (ConfigSubmit t : data) {
                switch (t.getStatus()) {
                    case Request:
                    case Wait:
                    case Error:
                        String str = "当前正在提交第" + (i + 1) + "步，共" + data.size() + "步";
                        tips.setText(str);
                        allFlag.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);

                        if (nowPosition != i) {
                            int position = infiniteScrollAdapter.getClosestPosition(i);
                            result.smoothScrollToPosition(position);
                        }
                        return;
                }

                i++;
            }

            String str = "已全部提交完成，共" + data.size() + "步";
            tips.setText(str);
            allFlag.setVisibility(View.VISIBLE);
            result.setVisibility(View.INVISIBLE);
        }
    }

}
