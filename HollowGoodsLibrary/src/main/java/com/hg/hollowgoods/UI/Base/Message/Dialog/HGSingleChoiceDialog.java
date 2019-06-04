package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hg.hollowgoods.Adapter.Dialog.SingleChoiceDialogAdapter;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * 单选对话框
 * Created by HG on 2018-01-17.
 */

class HGSingleChoiceDialog extends HGDialog {

    private int checkedPosition;

    private SingleChoiceDialogAdapter adapter;
    private ArrayList<ChoiceItem> data = new ArrayList<>();

    HGSingleChoiceDialog(Context context, Object title, ArrayList<ChoiceItem> items, int checkedPosition, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;

        CharSequence title1 = getValue(title, "");
        this.checkedPosition = checkedPosition;

        this.dialog = new AlertDialog
                .Builder(context)
                .setView(R.layout.dialog_single_choice)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onDialogClick(HGSingleChoiceDialog.this.code, false, null);
                    }
                }).setPositiveButton(R.string.sure, (dialog, which) -> {
                    if (onDialogClickListener != null) {
                        Bundle data = new Bundle();
                        data.putInt(HGParamKey.Position.getValue(), HGSingleChoiceDialog.this.checkedPosition);
                        onDialogClickListener.onDialogClick(HGSingleChoiceDialog.this.code, true, data);
                    }
                })
                .create();
        this.dialog.setOnDismissListener(dialog -> HGSingleChoiceDialog.this.onDialogDismissListener.onDialogDismiss(HGSingleChoiceDialog.this));

        if (!TextUtils.isEmpty(title1)) {
            this.dialog.setTitle(title1);
        }
        this.dialog.setCancelable(true);
        this.dialog.show();

        RecyclerView result = this.dialog.findViewById(R.id.rv_result);

        if (result != null) {
            result.setHasFixedSize(true);
            result.setItemAnimator(new DefaultItemAnimator());
            result.setLayoutManager(new LinearLayoutManager(this.context));

            if (items != null) {
                data.addAll(items);
            }

            adapter = new SingleChoiceDialogAdapter(this.context, R.layout.item_choice_dialog, data);
            result.setAdapter(adapter);

            adapter.setCheckedPosition(checkedPosition);

            adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false, true, false) {
                @Override
                public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                    HGSingleChoiceDialog.this.checkedPosition = position;
                    adapter.setCheckedPosition(position);
                }
            });
            adapter.setOnDescribeClickListener(new OnRecyclerViewItemClickListener(false) {
                @Override
                public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                    showDescribe(view, data.get(position).getItem().toString(), data.get(position).getDescribe());
                }
            });
        }
    }

}
