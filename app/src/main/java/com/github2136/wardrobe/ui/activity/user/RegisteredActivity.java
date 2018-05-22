package com.github2136.wardrobe.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseActivity;
import com.github2136.wardrobe.model.entity.UserInfo;
import com.github2136.wardrobe.presenter.user.RegisteredPresenter;
import com.github2136.wardrobe.ui.view.user.IRegisteredView;

import butterknife.BindView;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("注册");
        etUsername.setOnEditorActionListener(mOnEditorActionListener);
        etPassword.setOnEditorActionListener(mOnEditorActionListener);
        etConfirmPassword.setOnEditorActionListener(mOnEditorActionListener);
    }

    TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {
                case R.id.et_username:
                    String username = v.getText().toString().trim();
                    return !verifyUsername(username);
                case R.id.et_password:
                    String password = v.getText().toString();
                    return !verifyPassword(password);
                case R.id.et_confirm_password:
                    password = etPassword.getText().toString();
                    String confirmPassword = v.getText().toString();
                    if (verifyConfirmPassword(password, confirmPassword)) {
                        onViewClicked();
                        return false;
                    } else {
                        return true;
                    }
            }
            return false;
        }
    };

    @OnClick(R.id.btn_registered)
    public void onViewClicked() {
        registered();
    }

    private void registered() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (verifyUsername(username) && verifyPassword(password) && verifyConfirmPassword(password, confirmPassword)) {
            mPresenter.registered(username, password);
        }
    }

    private boolean verifyUsername(String username) {
        tlUsername.setError(null);
        if (TextUtils.isEmpty(username)) {
            tlUsername.setError("输入账号");
            return false;
        } else {
            return true;
        }
    }

    private boolean verifyPassword(String password) {
        tlPassword.setError(null);
        if (TextUtils.isEmpty(password)) {
            tlPassword.setError("输入密码");
            return false;
        } else {
            return true;
        }
    }

    private boolean verifyConfirmPassword(String password, String confirmPassword) {
        tlConfirmPassword.setError(null);
        if (TextUtils.equals(password, confirmPassword)) {
            return true;
        } else {
            tlConfirmPassword.setError("两次密码不一致");
            return false;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
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
