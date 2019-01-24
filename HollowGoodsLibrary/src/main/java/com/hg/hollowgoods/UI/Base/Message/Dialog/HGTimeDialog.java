package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TimePicker;

import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.R;

import java.util.Calendar;

/**
 * 时间对话框
 * Created by HG on 2018-01-18.
 */

public class HGTimeDialog extends HGDialog {

    private TimePicker timePicker;

    private int hour;
    private int minute;
    private OnDialogDismissListener onDialogDismissListener;

    public HGTimeDialog(Context context, long timeInMillis, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.code = code;
        this.onDialogDismissListener = onDialogDismissListener;

        Calendar c = Calendar.getInstance();
        if (timeInMillis != Constants.DEFAULT_TIME) {
            c.setTimeInMillis(timeInMillis);
        }
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);

        this.dialog = new AlertDialog.Builder(context).setView(R.layout.dialog_time).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onDialogClick(HGTimeDialog.this.code, false, null);
                }
            }
        }).setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onDialogClickListener != null) {
                    Calendar c = Calendar.getInstance();
                    c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), HGTimeDialog.this.hour, HGTimeDialog.this.minute);

                    Bundle data = new Bundle();
                    data.putInt(Constants.VALUE_KEY_HOUR, HGTimeDialog.this.hour);
                    data.putInt(Constants.VALUE_KEY_MINUTE, HGTimeDialog.this.minute);
                    data.putLong(Constants.VALUE_KEY_TIMESTAMPS, c.getTimeInMillis());
                    onDialogClickListener.onDialogClick(HGTimeDialog.this.code, true, data);
                }
            }
        }).create();
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                HGTimeDialog.this.onDialogDismissListener.onDialogDismiss(HGTimeDialog.this);
            }
        });

        this.dialog.show();

        timePicker = this.dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int mHour, int mMinute) {
                HGTimeDialog.this.hour = mHour;
                HGTimeDialog.this.minute = mMinute;
            }
        });
    }
}
