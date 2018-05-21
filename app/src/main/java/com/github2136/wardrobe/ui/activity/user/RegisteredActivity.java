package com.github2136.wardrobe.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseActivity;
import com.github2136.wardrobe.model.entity.UserInfo;
import com.github2136.wardrobe.presenter.user.RegisteredPresenter;
import com.github2136.wardrobe.ui.view.user.IRegisteredView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisteredActivity extends BaseActivity<RegisteredPresenter> implements IRegisteredView {
    @BindView(R.id.tb_title)
    Toolbar tbTitle;
    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.tl_username)
    TextInputLayout tlUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.tl_password)
    TextInputLayout tlPassword;
    @BindView(R.id.et_confirm_password)
    TextInputEditText etConfirmPassword;
    @BindView(R.id.tl_confirm_password)
    TextInputLayout tlConfirmPassword;

    @Override
    protected RegisteredPresenter getPresenter() {
        return new RegisteredPresenter(this, this);
    }

    @Override
    protected int getViewResId() {
        return R.layout.activity_registered;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setSupportActionBar(tbTitle);
        setTitle("注册");
        etConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onViewClicked();
                return true;
            }
        });
    }

    @OnClick(R.id.btn_registered)
    public void onViewClicked() {
        tlUsername.setError(null);
        tlPassword.setError(null);
        tlConfirmPassword.setError(null);
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            tlUsername.setError("输入账号");
        } else if (TextUtils.isEmpty(password)) {
            tlPassword.setError("输入密码");
        } else if (TextUtils.equals(password, confirmPassword)) {
            tlConfirmPassword.setError("两次密码不一致");
        } else {
            mPresenter.registered(username, password);
        }
    }

    @Override
    public void registeredSuccessful(UserInfo userInfo) {
        showToast("注册成功");
    }

    @Override
    public void registeredFailure(String msg) {
        showToast(msg);
    }
}
