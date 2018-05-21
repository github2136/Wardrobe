package com.github2136.wardrobe.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseActivity;
import com.github2136.wardrobe.model.entity.UserInfo;
import com.github2136.wardrobe.presenter.user.LoginPresenters;
import com.github2136.wardrobe.ui.activity.AddClothingActivity;
import com.github2136.wardrobe.ui.view.user.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenters> implements ILoginView {
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
        setSupportActionBar(tbTitle);
        setTitle("登录");
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onViewClicked();
                return true;
            }
        });
    }

    @Override
    public void loginSuccessful(UserInfo userInfo) {
        showToast("登录成功");
    }

    @Override
    public void loginFailure(String msg) {
        showToast(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_title_registered:
                startActivity(new Intent(mContext, RegisteredActivity.class));
                break;
        }
        return true;
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
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
}
