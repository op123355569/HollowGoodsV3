package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;

/**
 * 对话框示例界面
 * Created by Hollow Goods on unknown.
 */

public class Ex9Activity extends BaseActivity {

    private LinearLayout list;
    private EditText input;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_9;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        list = findViewById(R.id.ll_list);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex9);

        return null;
    }

    @Override
    public void setListener() {

        View v;

        for (int i = 0; i < list.getChildCount(); i++) {
            v = list.getChildAt(i);
            v.setId(i + 1);
            v.setOnClickListener(new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    switch (view.getId()) {
                        case 1:
                            showDialog1();
                            break;
                        case 2:
                            showDialog2();
                            break;
                        case 3:
                            showDialog3();
                            break;
                        case 4:
                            showDialog4();
                            break;
                        case 5:
                            showDialog5();
                            break;
                        case 6:
                            showDialog6();
                            break;
                        case 7:
                            showDialog7();
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    private void showDialog1() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_level_1)
                .setMessage(R.string.content)
                .setPositiveButton(R.string.sure, (dialog, which) -> {

                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                })
                .show();
    }

    private boolean[] isChoose = {true, true, true};

    private void showDialog2() {

        final String[] items = getResources().getStringArray(R.array.dialog_example_item_1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_example_tips_go_way)
                .setMultiChoiceItems(items, isChoose, (dialog, which, isChecked) -> {

                })
                .setPositiveButton(R.string.sure, (dialog, which) -> {

                    String tip = getString(R.string.dialog_example_tips_your_choose);
                    for (int i = 0; i < isChoose.length; i++) {
                        if (isChoose[i]) {
                            tip = tip + items[i] + " ";
                        }
                    }
                    baseUI.showShortSnackbar(tip);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                })
                .show();
    }

    private void showDialog3() {

        final String[] items = getResources().getStringArray(R.array.dialog_example_item_1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_example_tips_go_way)
                .setItems(items, (dialog, which) -> baseUI.showShortSnackbar(items[which]))
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                })
                .show();
    }

    private void showDialog4() {

        if (input == null) {
            input = new EditText(this);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_example_tips_go_way)
                .setMessage(R.string.walk_is_best)
                .setView(input)
                .setPositiveButton(R.string.sure, (dialog, which) -> {

                    String tip = getString(R.string.dialog_example_tips_your_choose) + input.getText().toString();
                    baseUI.showShortSnackbar(tip);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                }).setOnDismissListener(dialogInterface -> input = null).show();
    }

    private int choosePosition = 0;

    private void showDialog5() {

        final String[] items = getResources().getStringArray(R.array.dialog_example_item_1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_example_tips_go_way)
                .setSingleChoiceItems(items, choosePosition, (dialog, which) -> choosePosition = which)
                .setPositiveButton(R.string.sure, (dialog, which) -> {

                    String tip = getString(R.string.dialog_example_tips_your_choose) + items[choosePosition];
                    baseUI.showShortSnackbar(tip);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                })
                .show();
    }

    private void showDialog6() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.tips_best));
        progressDialog.setMessage(getString(R.string.is_login_ing));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void showDialog7() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.tips_best));
        progressDialog.setMessage(getString(R.string.is_login_ing));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        new Thread(new Runnable() {

            @Override
            public void run() {

                int i = 0;
                while (i < 100) {
                    try {
                        Thread.sleep(200);
                        // 更新进度条的进度,可以在子线程中更新进度条进度
                        progressDialog.incrementProgressBy(1);
                        // dialog.incrementSecondaryProgressBy(10)//二级进度条更新方式
                        i++;

                    } catch (Exception e) {

                    }
                }
                // 在进度条走完时删除Dialog
                progressDialog.dismiss();

            }
        }).start();
    }

}
