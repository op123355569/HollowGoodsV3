package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Application.IBaseApplication;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.StringUtils;
import com.hg.hollowgoods.Widget.RulerWheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 年月日时分对话框
 * Created by HG on 2018-01-18.
 */

public class HGDateTimeDialog extends HGDialog {

    private RulerWheelView yearView;
    private RulerWheelView monthView;
    private RulerWheelView dateView;
    private RulerWheelView hourView;
    private RulerWheelView minuteView;

    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;

    /**
     * 年月日时分对话框类型
     */
    public enum DateTimeDialogType {
        /**
         * 年
         */
        Y,
        /**
         * 年月日
         */
        YMD,
        /**
         * 年月
         */
        YM,
        /**
         * 时分
         */
        HM,
        /**
         * 年月日时分
         */
        YMD_HM
    }

    HGDateTimeDialog(Context context, long timeInMillis, DateTimeDialogType dateTimeDialogType, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.code = code;
        this.onDialogDismissListener = onDialogDismissListener;

        Calendar c = Calendar.getInstance();
        if (timeInMillis != HGConstants.DEFAULT_TIME) {
            c.setTimeInMillis(timeInMillis);
        } else {
            IBaseApplication baseApplication = ApplicationBuilder.create();
            c.setTimeInMillis(baseApplication.getNowTime());
        }
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.date = c.get(Calendar.DATE);
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);

        this.dialog = new AlertDialog.Builder(context).setView(R.layout.dialog_date_time).setNegativeButton(R.string.cancel, (dialog, which) -> {
            if (onDialogClickListener != null) {
                onDialogClickListener.onDialogClick(HGDateTimeDialog.this.code, false, null);
            }
        }).setPositiveButton(R.string.sure, (dialog, which) -> {
            if (onDialogClickListener != null) {
                Calendar c1 = Calendar.getInstance();
                c1.set(
                        HGDateTimeDialog.this.year,
                        HGDateTimeDialog.this.month,
                        HGDateTimeDialog.this.date,
                        HGDateTimeDialog.this.hour,
                        HGDateTimeDialog.this.minute
                );

                Bundle data = new Bundle();
                data.putInt(HGParamKey.DateYear.getValue(), HGDateTimeDialog.this.year);
                data.putInt(HGParamKey.DateMonth.getValue(), HGDateTimeDialog.this.month + 1);
                data.putInt(HGParamKey.DateDay.getValue(), HGDateTimeDialog.this.date);
                data.putInt(HGParamKey.DateHour.getValue(), HGDateTimeDialog.this.hour);
                data.putInt(HGParamKey.DateMinute.getValue(), HGDateTimeDialog.this.minute);
                data.putLong(HGParamKey.DateTimeInMillis.getValue(), c1.getTimeInMillis());
                onDialogClickListener.onDialogClick(HGDateTimeDialog.this.code, true, data);
            }
        }).create();

        this.dialog.setOnDismissListener(dialog -> HGDateTimeDialog.this.onDialogDismissListener.onDialogDismiss(HGDateTimeDialog.this));

        this.dialog.show();

        yearView = this.dialog.findViewById(R.id.year);
        monthView = this.dialog.findViewById(R.id.month);
        dateView = this.dialog.findViewById(R.id.date);
        hourView = this.dialog.findViewById(R.id.hour);
        minuteView = this.dialog.findViewById(R.id.minute);

        switch (dateTimeDialogType) {
            case YMD:
                hourView.setVisibility(View.GONE);
                minuteView.setVisibility(View.GONE);
                break;
            case HM:
                yearView.setVisibility(View.GONE);
                monthView.setVisibility(View.GONE);
                dateView.setVisibility(View.GONE);
                break;
            case YM:
                dateView.setVisibility(View.GONE);
                hourView.setVisibility(View.GONE);
                minuteView.setVisibility(View.GONE);
                break;
            case Y:
                monthView.setVisibility(View.GONE);
                dateView.setVisibility(View.GONE);
                hourView.setVisibility(View.GONE);
                minuteView.setVisibility(View.GONE);
                break;
        }

        initYear();
        initMonth();
        initDate();
        initHour();
        initMinute();
    }

    private void initMinute() {

        List<String> items = new ArrayList<>();
        int count = 60;
        String endStr = context.getString(R.string.minute);

        for (int i = 0; i < count; i++) {
            items.add(StringUtils.getTen(i) + "");
        }

        minuteView.setAdditionCenterMark(endStr);
        minuteView.setItems(items);
        minuteView.selectIndex(minute);
        minuteView.setOnWheelItemSelectedListener(new RulerWheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(RulerWheelView wheelView, int position) {

            }

            @Override
            public void onWheelItemSelected(RulerWheelView wheelView, int position) {
                minute = position;
            }
        });
    }

    private void initHour() {

        List<String> items = new ArrayList<>();
        int count = 24;
        String endStr = context.getString(R.string.hour);

        for (int i = 0; i < count; i++) {
            items.add(StringUtils.getTen(i) + "");
        }

        hourView.setAdditionCenterMark(endStr);
        hourView.setItems(items);
        hourView.selectIndex(hour);
        hourView.setOnWheelItemSelectedListener(new RulerWheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(RulerWheelView wheelView, int position) {

            }

            @Override
            public void onWheelItemSelected(RulerWheelView wheelView, int position) {
                hour = position;
            }
        });
    }

    private boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    private void initDate() {

        List<String> items = new ArrayList<>();
        int count = 0;
        String endStr = context.getString(R.string.date);

        switch (month + 1) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                count = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                count = 30;
                break;
            case 2:
                if (isLeapYear(year)) {
                    count = 29;
                } else {
                    count = 28;
                }
                break;
        }

        for (int i = 0; i < count; i++) {
            items.add(StringUtils.getTen(1 + i) + "");
        }

        dateView.setAdditionCenterMark(endStr);
        dateView.setItems(items);
        if (date > count) {
            date = count;
        }
        dateView.selectIndex(date - 1);
        dateView.setMaxSelectableIndex(count - 1);
        dateView.setOnWheelItemSelectedListener(new RulerWheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(RulerWheelView wheelView, int position) {

            }

            @Override
            public void onWheelItemSelected(RulerWheelView wheelView, int position) {
                date = position + 1;
            }
        });
    }

    private void initMonth() {

        List<String> items = new ArrayList<>();
        int count = 12;
        String endStr = context.getString(R.string.month);

        for (int i = 0; i < count; i++) {
            items.add(StringUtils.getTen(1 + i) + "");
        }

        monthView.setAdditionCenterMark(endStr);
        monthView.setItems(items);
        monthView.selectIndex(month);
        monthView.setOnWheelItemSelectedListener(new RulerWheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(RulerWheelView wheelView, int position) {

            }

            @Override
            public void onWheelItemSelected(RulerWheelView wheelView, int position) {
                month = position;
                initDate();
            }
        });
    }

    private int startYear = 1900;

    private void initYear() {

        List<String> items = new ArrayList<>();
        int count = Calendar.getInstance().get(Calendar.YEAR) + 100 - this.startYear;
        String endStr = context.getString(R.string.year);

        for (int i = 0; i < count; i++) {
            items.add((startYear + i) + "");
        }

        yearView.setAdditionCenterMark(endStr);
        yearView.setItems(items);
        yearView.selectIndex(this.year - this.startYear);
        yearView.setOnWheelItemSelectedListener(new RulerWheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(RulerWheelView wheelView, int position) {

            }

            @Override
            public void onWheelItemSelected(RulerWheelView wheelView, int position) {
                year = startYear + position;
                initDate();
            }
        });
    }

}
