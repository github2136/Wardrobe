package com.github2136.wardrobe.model;

import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPModel;
import com.github2136.wardrobe.model.util.OKHttpUtil;
import com.github2136.wardrobe.model.util.RequestCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yb on 2018/5/16.
 */

public class UserModel extends BaseMVPModel {
    OKHttpUtil mOkHttpUtil;

    public UserModel(AppCompatActivity activity) {
        super(activity);
        mOkHttpUtil = OKHttpUtil.getInstance();
    }

    public void login(String username, String password, RequestCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        mOkHttpUtil.get(mBaseUrl + mLogin + getParams(params), callback);
    }

    @Override
    public void cancelRequest() {

    }
}
