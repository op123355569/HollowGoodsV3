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
import android.widget.TextView;

import com.hg.hollowgoods.Adapter.Dialog.MultiChoiceDialogAdapter;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

import java.util.ArrayList;

/**
 * 单选对话框
 * Created by HG on 2018-01-17.
 */

public class HGMultiChoiceDialog extends HGDialog {

    private String title;
    private Object items;
    private ArrayList<Integer> checkedPositions;
    private int maxCount;

    private RecyclerView result;
    private TextView count;
    private MultiChoiceDialogAdapter adapter;
    private ArrayList<Object> data = new ArrayList<>();

    public HGMultiChoiceDialog(final Context context, Object title, Object items, ArrayList<Integer> checkedPositions, int maxCount, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;
        this.maxCount = maxCount;

        this.title = getValue(title, "");
        this.items = items;
        this.checkedPositions = checkedPositions;

        if (this.checkedPositions == null) {
            this.checkedPositions = new ArrayList<>();
        }

        this.dialog = new AlertDialog
                .Builder(context)
                .setView(R.layout.dialog_multi_choice)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDialogClickListener != null) {
                            onDialogClickListener.onDialogClick(HGMultiChoiceDialog.this.code, false, null);
                        }
                    }
                }).setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDialogClickListener != null) {
                            Bundle data = new Bundle();
                            data.putSerializable(HGParamKey.Positions.getValue(), HGMultiChoiceDialog.this.checkedPositions);
                            onDialogClickListener.onDialogClick(HGMultiChoiceDialog.this.code, true, data);
                        }
                    }
                })
                .create();
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                HGMultiChoiceDialog.this.onDialogDismissListener.onDialogDismiss(HGMultiChoiceDialog.this);
            }
        });

        if (!TextUtils.isEmpty(this.title)) {
            this.dialog.setTitle(this.title);
        }
        this.dialog.setCancelable(true);
        this.dialog.show();

        result = this.dialog.findViewById(R.id.rv_result);
        count = this.dialog.findViewById(R.id.tv_count);

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new LinearLayoutManager(this.context));

        data.addAll(getData());

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

        setCount();
    }

    private void setCount() {

        if (maxCount < 1) {
            count.setText(HGMultiChoiceDialog.this.checkedPositions.size() + "");
        } else {
            count.setText(HGMultiChoiceDialog.this.checkedPositions.size() + "/" + maxCount);
        }
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
