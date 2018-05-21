package com.github2136.wardrobe.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseActivity;
import com.github2136.wardrobe.presenter.user.LoginPresenters;
import com.github2136.wardrobe.ui.view.user.ILoginView;

public class LoginActivity extends BaseActivity<LoginPresenters> implements ILoginView {
    TextInputLayout tlUsername, tlPassword;
    TextInputEditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected LoginPresenters getPresenter() {
        return new LoginPresenters(this, this);
    }

    @Override
    protected int getViewResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        etUsername = (TextInputEditText) findViewById(R.id.et_username);
        etPassword = (TextInputEditText) findViewById(R.id.et_password);
        tlUsername = (TextInputLayout) findViewById(R.id.tl_username);
        tlPassword = (TextInputLayout) findViewById(R.id.tl_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tlUsername.setError(null);
            tlPassword.setError(null);
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString();
            if (TextUtils.isEmpty(username)) {
                tlUsername.setError("输入账号");
            } else if (TextUtils.isEmpty(password)) {
                tlPassword.setError("输入密码");
            } else {
                mPresenter.login(username, password);
            }
        }
    };

    @Override
    public void loginSuccseeful() {

    }

    @Override
    public void loginFailure(String msg) {

    }
}
