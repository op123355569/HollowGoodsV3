package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;

import com.hg.hollowgoods.Constant.HGConstants;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 基本对话框
 * Created by HG on 2017/6/4.
 */

public class BaseDialog {

    private Context context;
    private OnDialogClickListener onDialogClickListener;
    private OnDialogDismissListener onDialogDismissListener;

    /**
     * 对话框
     */
    private ArrayList<HGDialog> hgDialogs;

    public BaseDialog(Context context) {
        this.context = context;
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public void setOnDialogDismissListener(OnDialogDismissListener onDialogDismissListener) {
        this.onDialogDismissListener = onDialogDismissListener;
    }

    /**
     * 显示进度对话框
     *
     * @param title        标题（系统对话框专用）
     * @param content      对话框提示文字
     * @param cancelable   是否可以用返回键关闭对话框
     * @param isIndefinite 是否是不确定进度
     * @param code         code
     */
    public void showProgressDialog(Object title, Object content, boolean cancelable, boolean isIndefinite, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGProgressDialog dialog = new HGProgressDialog(context, title, content, cancelable, isIndefinite, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示进度对话框（不确定进度）
     *
     * @param content 对话框提示文字
     * @param code    code
     */
    public void showProgressDialog(Object content, int code) {
        showProgressDialog(null, content, true, true, code);
    }

    /**
     * 设置进度
     *
     * @param progress 0-100
     * @param code     code
     */
    public void setProgress(int progress, int code) {

        if (hgDialogs != null) {
            HGProgressDialog progressDialog = null;
            for (int i = 0; i < hgDialogs.size(); i++) {
                if (hgDialogs.get(i) instanceof HGProgressDialog && hgDialogs.get(i).code == code) {
                    progressDialog = (HGProgressDialog) hgDialogs.get(i);
                    break;
                }
            }

            if (progressDialog != null && !progressDialog.isIndefinite()) {
                progressDialog.setProgress(progress);
            }
        }
    }

    /**
     * 显示对话框
     *
     * @param title        标题字样
     * @param tip          提示字样
     * @param noButtonTxt  取消按钮字样
     * @param yesButtonTxt 确定按钮字样
     * @param cancelable   是否可以用返回键关闭对话框
     * @param code         区别码
     */
    public void showAlertDialog(Object title, Object tip, Object noButtonTxt, Object yesButtonTxt, boolean cancelable, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGAlertDialog dialog = new HGAlertDialog(context, title, tip, noButtonTxt, yesButtonTxt, cancelable, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示对话框
     *
     * @param title      标题字样
     * @param tip        提示字样
     * @param cancelable 是否可以用返回键关闭对话框
     * @param code       区别码
     */
    public void showAlertDialog(Object title, Object tip, boolean cancelable, int code) {
        showAlertDialog(title, tip, null, null, cancelable, code);
    }

    /**
     * 显示对话框
     *
     * @param title 标题字样
     * @param tip   提示字样
     * @param code  code
     */
    public void showAlertDialog(Object title, Object tip, int code) {
        showAlertDialog(title, tip, null, null, true, code);
    }

    /**
     * 显示警告对话框
     *
     * @param tip          提示字样
     * @param yesButtonTxt 确定按钮字样
     * @param cancelable   是否可以用返回键关闭对话框
     * @param code         code
     */
    public void showWarningDialog(Object tip, Object yesButtonTxt, boolean cancelable, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGWarningDialog dialog = new HGWarningDialog(context, tip, yesButtonTxt, cancelable, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示警告对话框
     *
     * @param tip        提示字样
     * @param cancelable 是否可以用返回键关闭对话框
     * @param code       code
     */
    public void showWarningDialog(Object tip, boolean cancelable, int code) {
        showWarningDialog(tip, null, cancelable, code);
    }

    /**
     * 显示警告对话框
     *
     * @param tip  提示字样
     * @param code code
     */
    public void showWarningDialog(Object tip, int code) {
        showWarningDialog(tip, null, true, code);
    }

    /**
     * 显示提示对话框
     *
     * @param title      提示主题
     * @param tip        提示文字
     * @param cancelable 是否可以用返回键关闭对话框
     * @param code       code
     */
    public void showTipDialog(Object title, Object tip, boolean cancelable, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGTipDialog dialog = new HGTipDialog(context, title, tip, cancelable, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示提示对话框
     *
     * @param title 提示主题
     * @param tip   提示文字
     * @param code  code
     */
    public void showTipDialog(Object title, Object tip, int code) {
        showTipDialog(title, tip, true, code);
    }

    /**
     * 显示输入对话框
     *
     * @param hint      hint
     * @param value     value
     * @param inputType inputType
     * @param code      code
     */
    @Deprecated
    public void showInputDialog(Object hint, Object value, int inputType, final int code, final int minSize, final int maxSize) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGInputDialog dialog = new HGInputDialog(context, hint, value, inputType, code, minSize, maxSize, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示输入对话框
     *
     * @param value value
     * @param code  code
     */
    @Deprecated
    public void showInputDialog(Object value, int code) {
        showInputDialog(null, value, HGConstants.INPUT_TYPE_DEFAULT, code, HGConstants.INPUT_SIZE_DEFAULT, HGConstants.INPUT_SIZE_DEFAULT);
    }

    /**
     * 显示新输入对话框
     *
     * @param configInput configInput
     * @param code        code
     */
    public void showInputDialog(ConfigInput configInput, final int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGInputNewDialog dialog = new HGInputNewDialog(context, configInput, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示年月日时分对话框
     *
     * @param timeInMillis timeInMillis
     * @param code         code
     */
    public void showDateTimeDialog(long timeInMillis, HGDateTimeDialog.DateTimeDialogType dateTimeDialogType, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGDateTimeDialog dialog = new HGDateTimeDialog(context, timeInMillis, dateTimeDialogType, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    public void showSingleDialog(Object title, Object items, int checkedPosition, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGSingleChoiceDialog dialog = new HGSingleChoiceDialog(context, title, items, checkedPosition, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    public void showMultiDialog(Object title, Object items, ArrayList<Integer> checkedPositions, int maxCount, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGMultiChoiceDialog dialog = new HGMultiChoiceDialog(context, title, items, checkedPositions, maxCount, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示年月日对话框
     *
     * @param timeInMillis timeInMillis
     * @param code         code
     */
    @Deprecated
    public void showDateDialog(long timeInMillis, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGDateDialog dialog = new HGDateDialog(context, timeInMillis, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示年月日对话框
     *
     * @param year  year
     * @param month 1-12
     * @param date  date
     * @param code  code
     */
    @Deprecated
    public void showDateDialog(int year, int month, int date, int code) {
        Calendar c = Calendar.getInstance();
        if (year < 1900) {
            year = 1900;
        }
        if (month < 1 || month > 12) {
            month = 1;
        }
        if (date < 1 || date > 31) {
            date = 1;
        }
        c.set(year, month - 1, date);
        showDateDialog(c.getTimeInMillis(), code);
    }

    /**
     * 显示年月日对话框
     *
     * @param code code
     */
    @Deprecated
    public void showDateDialog(int code) {
        showDateDialog(HGConstants.DEFAULT_TIME, code);
    }

    /**
     * 显示时间对话框
     *
     * @param timeInMillis timeInMillis
     * @param code         code
     */
    @Deprecated
    public void showTimeDialog(long timeInMillis, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGTimeDialog dialog = new HGTimeDialog(context, timeInMillis, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    /**
     * 显示时间对话框
     *
     * @param hour   hour
     * @param minute minute
     * @param code   code
     */
    @Deprecated
    public void showTimeDialog(int hour, int minute, int code) {
        Calendar c = Calendar.getInstance();
        if (hour < 0 || hour > 23) {
            hour = 0;
        }
        if (minute < 0 || minute > 59) {
            minute = 0;
        }
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        showTimeDialog(c.getTimeInMillis(), code);
    }

    /**
     * 显示时间对话框
     *
     * @param code code
     */
    @Deprecated
    public void showTimeDialog(int code) {
        showTimeDialog(HGConstants.DEFAULT_TIME, code);
    }

    /**
     * 显示提交对话框
     *
     * @param code code
     */
    public void showSubmitDialog(ArrayList<ConfigSubmit> configSubmits, int code) {

        if (hgDialogs == null) {
            hgDialogs = new ArrayList<>();
        }

        HGSubmitDialog dialog = new HGSubmitDialog(context, configSubmits, code, dialog1 -> {
            hgDialogs.remove(dialog1);
            if (onDialogDismissListener != null) {
                onDialogDismissListener.onDialogDismiss(dialog1);
            }
            if (dialog1.isCloseAll) {
                closeAllDialog();
            }
        });
        dialog.setOnDialogClickListener(onDialogClickListener);
        hgDialogs.add(0, dialog);
    }

    public void refreshSubmitDialog(ArrayList<ConfigSubmit> configSubmits, int code) {

        if (hgDialogs != null) {
            HGSubmitDialog submitDialog = null;

            for (int i = 0; i < hgDialogs.size(); i++) {
                if (hgDialogs.get(i) instanceof HGSubmitDialog && hgDialogs.get(i).code == code) {
                    submitDialog = (HGSubmitDialog) hgDialogs.get(i);
                    break;
                }
            }

            if (submitDialog != null) {
                submitDialog.refreshDialog(configSubmits);
            }
        }
    }

    /**
     * 关闭最上层的进度对话框
     */
    public void closeDialog() {

        if (hgDialogs != null && hgDialogs.size() > 0) {
            hgDialogs.get(0).closeDialog();
        }
    }

    /**
     * 关闭对话框，通过code
     */
    public void closeDialog(int code) {

        if (hgDialogs != null && hgDialogs.size() > 0) {
            for (int i = 0; i < hgDialogs.size(); i++) {
                if (hgDialogs.get(i).code == code) {
                    hgDialogs.get(i).closeDialog();
                    break;
                }
            }
        }
    }

    /**
     * 关闭所有进度对话框
     */
    public void closeAllDialog() {
        if (hgDialogs != null && hgDialogs.size() > 0) {
            hgDialogs.get(0).closeAllDialog();
        }
    }

}
