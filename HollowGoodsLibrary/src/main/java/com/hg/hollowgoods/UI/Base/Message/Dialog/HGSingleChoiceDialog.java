package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hg.hollowgoods.Adapter.Dialog.SingleChoiceDialogAdapter;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * 单选对话框
 * Created by HG on 2018-01-17.
 */

public class HGSingleChoiceDialog extends HGDialog {

    private String title;
    private Object items;
    private int checkedPosition;

    private RecyclerView result;
    private SingleChoiceDialogAdapter adapter;
    private ArrayList<Object> data = new ArrayList<>();

    public HGSingleChoiceDialog(Context context, Object title, Object items, int checkedPosition, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;

        this.title = getValue(title, "");
        this.items = items;
        this.checkedPosition = checkedPosition;

        this.dialog = new AlertDialog
                .Builder(context)
                .setView(R.layout.dialog_single_choice)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDialogClickListener != null) {
                            onDialogClickListener.onDialogClick(HGSingleChoiceDialog.this.code, false, null);
                        }
                    }
                }).setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDialogClickListener != null) {
                            Bundle data = new Bundle();
                            data.putInt(HGConstants.PARAM_KEY_1, HGSingleChoiceDialog.this.checkedPosition);
                            onDialogClickListener.onDialogClick(HGSingleChoiceDialog.this.code, true, data);
                        }
                    }
                })
                .create();
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                HGSingleChoiceDialog.this.onDialogDismissListener.onDialogDismiss(HGSingleChoiceDialog.this);
            }
        });

        if (!TextUtils.isEmpty(this.title)) {
            this.dialog.setTitle(this.title);
        }
        this.dialog.setCancelable(true);
        this.dialog.show();

        result = this.dialog.findViewById(R.id.rv_result);
        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new LinearLayoutManager(this.context));

        data.addAll(getData());

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
    }

    private ArrayList<Object> getData() {

        ArrayList<Object> result = new ArrayList<>();

        if (items != null) {
            if (items instanceof String[]) {
                String[] temp = (String[]) items;
                for (String t : temp) {
                    result.add(t);
                }
            } else if (items instanceof Integer[]) {
                Integer[] temp = (Integer[]) items;
                for (Integer t : temp) {
                    result.add(t);
                }
            }
        }

        return result;
    }

}
