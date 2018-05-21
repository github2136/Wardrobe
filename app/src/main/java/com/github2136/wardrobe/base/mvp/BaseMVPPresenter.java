package com.github2136.wardrobe.base.mvp;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github2136.util.JsonUtil;
import com.github2136.util.SPUtil;
import com.github2136.wardrobe.model.util.ErrorResponse;

import java.util.Set;


/**
 * presenter基础类
 */
public abstract class BaseMVPPresenter<V extends IBaseMVPView> {
    protected V mView;
    protected AppCompatActivity mActivity;
    protected Fragment mFragment;
    protected final String failedStr = "无法连接服务器";
    protected JsonUtil mJsonUtil;
    protected SPUtil mSpUtil;
    protected Handler mHandler;

    public BaseMVPPresenter(AppCompatActivity activity, V view) {
        mActivity = activity;
        initPresenter(view);
    }

    public BaseMVPPresenter(Fragment fragment, V view) {
        mFragment = fragment;
        initPresenter(view);
    }

    private void initPresenter(V view) {
        this.mView = view;
        mJsonUtil = JsonUtil.getInstance();
        mHandler = new Handler(Looper.getMainLooper());
        if (mActivity == null) {
            mSpUtil = SPUtil.getInstance(mFragment.getContext());
        } else {
            mSpUtil = SPUtil.getInstance(mActivity);
        }
    }

    protected boolean isSuccess(String response) {
        ErrorResponse errorResponse = mJsonUtil.getObjectByStr(response, ErrorResponse.class);
        return errorResponse != null && errorResponse.getCode() == null;
    }


    protected String getFailedStr(String response) {
        ErrorResponse errorResponse = mJsonUtil.getObjectByStr(response, ErrorResponse.class);
//        return errorResponse != null && errorResponse.getCode() == null;
        if (response == null) {
            return failedStr;
        } else {
            return errorResponse.getErrorMsg();
        }
    }
    public String getSPString(String key) {
        return mSpUtil.getString(key);
    }

    public boolean getSPBoolean(String key) {
        return mSpUtil.getBoolean(key);
    }

    public float getSPFloat(String key) {
        return mSpUtil.getFloat(key);
    }

    public int getSPInt(String key) {
        return mSpUtil.getInt(key);
    }

    public long getSPLong(String key) {
        return mSpUtil.getLong(key);
    }

    public Set<String> getSPStringSet(String key) {
        return mSpUtil.getStringSet(key);
    }

    public void setSPValue(String key, String strVal) {
        mSpUtil.edit().putValue(key, strVal).apply();
    }

    public void setSPValue(String key, boolean boolVal) {
        mSpUtil.edit().putValue(key, boolVal).apply();
    }

    public void setSPValue(String key, float floatVal) {
        mSpUtil.edit().putValue(key, floatVal).apply();
    }

    public void setSPValue(String key, int intVal) {
        mSpUtil.edit().putValue(key, intVal).apply();
    }

    public void setSPValue(String key, Set<String> setVal) {
        mSpUtil.edit().putValue(key, setVal).apply();
    }

    public void postMain(Runnable runnable) {
        mHandler.post(runnable);
    }

    //取消请求
    public abstract void cancelRequest();
}
