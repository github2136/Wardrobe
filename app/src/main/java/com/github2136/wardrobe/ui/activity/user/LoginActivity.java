package com.github2136.wardrobe.ui.activity.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseActivity;
import com.github2136.wardrobe.model.entity.UserInfo;
import com.github2136.wardrobe.presenter.user.LoginPresenters;
import com.github2136.wardrobe.ui.activity.MainActivity;
import com.github2136.wardrobe.ui.view.user.ILoginView;
import com.github2136.wardrobe.util.Constant;

import butterknife.BindView;
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
        etUsername.setOnEditorActionListener(mOnEditorActionListener);
        etPassword.setOnEditorActionListener(mOnEditorActionListener);
        String username = mPresenter.getSPString(Constant.SP_USER_NAME);
        if (!TextUtils.isEmpty(username)) {
            etUsername.setText(mPresenter.getSPString(Constant.SP_USER_NAME));
            etPassword.requestFocus();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0及以上需要请求权限
            //检查权限
            int permission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                if (mPresenter.isAutoLogin()) {
                    mPresenter.autoLogin();
                }
            } else {
                //请求权限
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
        } else {
            //6.0以下直接自动登录
            if (mPresenter.isAutoLogin()) {
                mPresenter.autoLogin();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //requestCode请求编码
        //permissions请求的权限
        //grantResults授予结果
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 版本兼容
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }
        for (int i = 0, len = permissions.length; i < len; i++) {
            String permission = permissions[i];
            //  拒绝的权限
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                //判断是否点击不再提示
                boolean showRationale = shouldShowRequestPermissionRationale(permission);
                if (!showRationale) {
                    // 用户点击不再提醒，打开设置页让用户开启权限
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                } else {
                    showToast("没有对应权限无法正常使用");
                }
            }
        }
    }

    TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {
                case R.id.et_username:
                    String username = v.getText().toString().trim();
                    return !verifyUsername(username);
                case R.id.et_password:
                    String password = etPassword.getText().toString();
                    if (verifyPassword(password)) {
                        onViewClicked();
                        return false;
                    } else {
                        return true;
                    }
            }
            return false;
        }
    };


    @Override
    public void loginSuccessful(UserInfo userInfo) {
        showToast("登录成功");
        finish();
        startActivity(new Intent(mContext, MainActivity.class));
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
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();
        if (verifyUsername(username) && verifyPassword(password)) {
            mPresenter.login(username, password);
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
}
