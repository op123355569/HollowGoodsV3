package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;

import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;

import java.util.Calendar;

/**
 * 年月日对话框
 * Created by HG on 2018-01-18.
 */

public class HGDateDialog extends HGDialog {

    private DatePicker datePicker;

    private int year;
    private int month;
    private int date;

    public HGDateDialog(Context context, long timeInMillis, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.code = code;
        this.onDialogDismissListener = onDialogDismissListener;

        Calendar c = Calendar.getInstance();
        if (timeInMillis != HGConstants.DEFAULT_TIME) {
            c.setTimeInMillis(timeInMillis);
        }
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.date = c.get(Calendar.DATE);

        this.dialog = new AlertDialog.Builder(context).setView(R.layout.dialog_date).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onDialogClick(HGDateDialog.this.code, false, null);
                }
            }
        }).setPositiveButton(R.string.sure, (dialog, which) -> {
            if (onDialogClickListener != null) {
                Calendar c1 = Calendar.getInstance();
                c1.set(HGDateDialog.this.year, HGDateDialog.this.month, HGDateDialog.this.date);

                Bundle data = new Bundle();
                data.putInt(HGParamKey.DateYear.getValue(), HGDateDialog.this.year);
                data.putInt(HGParamKey.DateMonth.getValue(), HGDateDialog.this.month + 1);
                data.putInt(HGParamKey.DateDay.getValue(), HGDateDialog.this.date);
                data.putLong(HGParamKey.DateTimeInMillis.getValue(), c1.getTimeInMillis());
                onDialogClickListener.onDialogClick(HGDateDialog.this.code, true, data);
            }
        }).create();
        this.dialog.setOnDismissListener(dialog -> HGDateDialog.this.onDialogDismissListener.onDialogDismiss(HGDateDialog.this));

        this.dialog.show();

        datePicker = this.dialog.findViewById(R.id.datePicker);
        datePicker.init(this.year, this.month, this.date, (view, mYear, mMonth, mDate) -> {
            HGDateDialog.this.year = mYear;
            HGDateDialog.this.month = mMonth;
            HGDateDialog.this.date = mDate;
        });
    }
}
