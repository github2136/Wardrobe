package com.github2136.wardrobe.presenter.user;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.UserModel;
import com.github2136.wardrobe.model.entity.UserInfo;
import com.github2136.wardrobe.model.util.HttpCallback;
import com.github2136.wardrobe.ui.view.user.IRegisteredView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yb on 2018/5/17.
 */

public class RegisteredPresenter extends BaseMVPPresenter<IRegisteredView> {
    UserModel mUserModel;

    public RegisteredPresenter(AppCompatActivity activity, IRegisteredView view) {
        super(activity, view);
        mUserModel = new UserModel(activity);
    }

    public void registered(String username, String password) {
        mView.showProgressDialog();
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("username", username);
        params.put("password", password);
        mUserModel.registered(params, new HttpCallback() {
            @Override
            public void onFailure(Call call, Exception e) {
                mView.dismissDialog();
                mView.registeredFailure(failedStr);
            }

            @Override
            public void onResponse(Call call, Response response, String bodyStr) {
                mView.dismissDialog();
                if (isSuccess(bodyStr)) {
                    UserInfo userInfo = mJsonUtil.getObjectByStr(bodyStr, UserInfo.class);
                    mView.registeredSuccessful(userInfo);
                } else {
                    mView.registeredFailure(getFailedStr(bodyStr));
                }
            }
        });
    }

    @Override
    public void cancelRequest() {
        mUserModel.cancelRequest();
    }
}
