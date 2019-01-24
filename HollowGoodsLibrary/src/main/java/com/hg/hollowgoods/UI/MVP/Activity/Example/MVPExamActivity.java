package com.hg.hollowgoods.UI.MVP.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hg.hollowgoods.Bean.User;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseMVPActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

/**
 * @ClassName:
 * @Description:
 * @author: 马禛
 * @date: 2019年01月16日
 */
public class MVPExamActivity extends BaseMVPActivity<MVPExamPresenter> implements MVPExamContract.View {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView txt;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mvp_exam;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void setListener() {

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_login);
        txt = findViewById(R.id.tv_txt);

        login.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                mPresenter.login(username.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public MVPExamPresenter createPresenter() {
        return new MVPExamPresenter();
    }

    @Override
    public void showProgressDialog() {
        baseUI.baseDialog.showProgressDialog("登录中，请稍候……", 6666);
    }

    @Override
    public void hideProgressDialog() {
        baseUI.baseDialog.closeDialog(6666);
    }

    @Override
    public void onSuccess(User user) {

        t.showShortToast("登录成功");

        txt.setText("");
        txt.append("用户名：");
        txt.append(user.getUsername());
        txt.append("\n\n");
        txt.append("密码：");
        txt.append(user.getPassword());
    }

}
