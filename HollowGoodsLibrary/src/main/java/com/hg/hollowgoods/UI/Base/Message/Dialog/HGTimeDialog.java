package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TimePicker;

import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;

import java.util.Calendar;

/**
 * 时间对话框
 * Created by HG on 2018-01-18.
 */
@Deprecated
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
        if (timeInMillis != HGConstants.DEFAULT_TIME) {
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
        }).setPositiveButton(R.string.sure, (dialog, which) -> {
            if (onDialogClickListener != null) {
                Calendar c1 = Calendar.getInstance();
                c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), HGTimeDialog.this.hour, HGTimeDialog.this.minute);

                Bundle data = new Bundle();
                data.putInt(HGParamKey.DateHour.getValue(), HGTimeDialog.this.hour);
                data.putInt(HGParamKey.DateMinute.getValue(), HGTimeDialog.this.minute);
                data.putLong(HGParamKey.DateTimeInMillis.getValue(), c1.getTimeInMillis());
                onDialogClickListener.onDialogClick(HGTimeDialog.this.code, true, data);
            }
        }).create();
        this.dialog.setOnDismissListener(dialog -> HGTimeDialog.this.onDialogDismissListener.onDialogDismiss(HGTimeDialog.this));

        this.dialog.show();

        timePicker = this.dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);

        timePicker.setOnTimeChangedListener((view, mHour, mMinute) -> {
            HGTimeDialog.this.hour = mHour;
            HGTimeDialog.this.minute = mMinute;
        });
    }
}
