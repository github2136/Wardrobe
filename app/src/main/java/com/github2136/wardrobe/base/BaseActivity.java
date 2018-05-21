package com.github2136.wardrobe.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.base.mvp.IBaseMVPView;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Activity基础类
 * Created by yubin on 2016/2/23.
 */
public abstract class BaseActivity<P extends BaseMVPPresenter> extends AppCompatActivity implements IBaseMVPView {
    protected P mPresenter;
    protected final String TAG = this.getClass().getName();
    protected BaseApplication mApp;
    protected Context mContext;
    protected ProgressDialog mProgressDialog;
    protected InputMethodManager im;
    protected Handler mHandler;
    protected Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (BaseApplication) getApplication();
        mApp.addActivity(this);
        mContext = this;
        setContentView(getViewResId());
        ButterKnife.bind(this);
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mHandler = new Handler(this);
        mPresenter = getPresenter();
        initData(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        mApp.removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(null);
    }

    @Override
    public void showProgressDialog(@StringRes int resId) {
        showProgressDialog(getString(resId));
    }

    @Override
    public void showProgressDialog(String msg) {
        String message;
        if (!TextUtils.isEmpty(msg)) {
            message = msg;
        } else {
            message = "请稍候……";
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isFinishing() && !isDestroyed()) {
                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
            }
        } else {
            if (!isFinishing()) {
                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
            }
        }
    }

    @Override
    public void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing() && !isFinishing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showToastLong(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }


    ///////////////////////////////////////////////////////////////////////////
    // Handler
    ///////////////////////////////////////////////////////////////////////////
    static class Handler extends android.os.Handler {
        WeakReference<BaseActivity> weakReference;

        Handler(BaseActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = weakReference.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    protected void handleMessage(Message msg) { }

    ///////////////////////////////////////////////////////////////////////////
    // Touch
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideKeyboard();
        }
        return super.onTouchEvent(event);
    }

    /**
     * 隐藏键盘
     */
    protected void hideKeyboard() {
        if (getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager
                    .HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 延迟关闭
     *
     * @param delayMillis
     */
    public void finish(int delayMillis) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delayMillis);
    }

    //获得presenter
    protected abstract P getPresenter();

    //布局ID
    protected abstract int getViewResId();

    //初始化
    protected abstract void initData(Bundle savedInstanceState);

    //取消请求
    public void cancelRequest() {}
}
