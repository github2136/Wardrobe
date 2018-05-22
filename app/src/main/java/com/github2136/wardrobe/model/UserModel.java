package com.github2136.wardrobe.model;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPModel;
import com.github2136.wardrobe.model.util.HttpCallback;
import com.github2136.wardrobe.model.util.OKHttpUtil;

/**
 * Created by yb on 2018/5/16.
 */

public class UserModel extends BaseMVPModel {
    private OKHttpUtil mOkHttpUtil;

    public UserModel(AppCompatActivity activity) {
        super(activity);
        mOkHttpUtil = new OKHttpUtil(activity, mTag);
    }

    public void login(String username, String password, HttpCallback callback) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("username", username);
        params.put("password", password);
        mOkHttpUtil.doGetRequest(mBaseUrl, mLogin, params, callback);
    }

    public void registered(String username, String password, HttpCallback callback) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("username", username);
        params.put("password", password);
        mOkHttpUtil.doPostJsonRequest(mBaseUrl, mUser, params, callback);
    }

    @Override
    public void cancelRequest() {
        mOkHttpUtil.cancelCallWithTag(mTag);
    }
}
