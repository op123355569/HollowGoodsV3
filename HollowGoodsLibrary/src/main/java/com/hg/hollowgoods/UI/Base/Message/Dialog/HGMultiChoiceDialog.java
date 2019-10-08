package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.Adapter.Dialog.MultiChoiceDialogAdapter;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

import java.util.ArrayList;

/**
 * 单选对话框
 * Created by Hollow Goods 2018-01-17.
 */

class HGMultiChoiceDialog extends HGDialog {

    private ArrayList<Integer> checkedPositions;
    private int maxCount;

    private TextView count;
    private MultiChoiceDialogAdapter adapter;
    private ArrayList<ChoiceItem> data = new ArrayList<>();

    HGMultiChoiceDialog(final Context context, Object title, ArrayList<ChoiceItem> items, ArrayList<Integer> checkedPositions, int maxCount, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;
        this.maxCount = maxCount;

        CharSequence title1 = getValue(title, "");
        this.checkedPositions = checkedPositions;

        if (this.checkedPositions == null) {
            this.checkedPositions = new ArrayList<>();
        }

        this.dialog = new AlertDialog
                .Builder(context)
                .setView(R.layout.dialog_multi_choice)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onDialogClick(HGMultiChoiceDialog.this.code, false, null);
                    }
                }).setPositiveButton(R.string.sure, (dialog, which) -> {
                    if (onDialogClickListener != null) {
                        Bundle data = new Bundle();
                        data.putSerializable(HGParamKey.Positions.getValue(), HGMultiChoiceDialog.this.checkedPositions);
                        onDialogClickListener.onDialogClick(HGMultiChoiceDialog.this.code, true, data);
                    }
                })
                .create();
        this.dialog.setOnDismissListener(dialog -> HGMultiChoiceDialog.this.onDialogDismissListener.onDialogDismiss(HGMultiChoiceDialog.this));

        if (!TextUtils.isEmpty(title1)) {
            this.dialog.setTitle(title1);
        }
        this.dialog.setCancelable(true);
        this.dialog.show();

        RecyclerView result = this.dialog.findViewById(R.id.rv_result);
        count = this.dialog.findViewById(R.id.tv_count);

        if (result != null) {
            result.setHasFixedSize(true);
            result.setItemAnimator(new DefaultItemAnimator());
            result.setLayoutManager(new LinearLayoutManager(this.context));

            if (items != null) {
                data.addAll(items);
            }

            adapter = new MultiChoiceDialogAdapter(this.context, R.layout.item_choice_dialog, data);
            result.setAdapter(adapter);

            adapter.setCheckedPositions(checkedPositions);

            adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false, true, false) {
                @Override
                public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

                    if (adapter.isChecked(position)) {
                        HGMultiChoiceDialog.this.checkedPositions.remove(Integer.valueOf(position));
                        adapter.removeCheckedPosition(position);

                        setCount();
                    } else {
                        if (HGMultiChoiceDialog.this.maxCount < 1) {
                            HGMultiChoiceDialog.this.checkedPositions.add(position);
                            adapter.addCheckedPosition(position);

                            setCount();
                        } else {
                            if (HGMultiChoiceDialog.this.checkedPositions.size() < HGMultiChoiceDialog.this.maxCount) {
                                HGMultiChoiceDialog.this.checkedPositions.add(position);
                                adapter.addCheckedPosition(position);

                                setCount();
                            } else {
                                String tips = context.getString(R.string.is_had_max_count);
                                tips = String.format(tips, HGMultiChoiceDialog.this.maxCount + "");
                                t.error(tips);
                            }
                        }
                    }
                }
            });
            adapter.setOnDescribeClickListener(new OnRecyclerViewItemClickListener(false) {
                @Override
                public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                    showDescribe(view, data.get(position).getItem().toString(), data.get(position).getDescribe());
                }
            });

            setCount();
        }
    }

    private void setCount() {

        if (maxCount < 1) {
            count.setText(String.valueOf(HGMultiChoiceDialog.this.checkedPositions.size()));
        } else {
            String str = HGMultiChoiceDialog.this.checkedPositions.size() + "/" + maxCount;
            count.setText(str);
        }
    }

}
