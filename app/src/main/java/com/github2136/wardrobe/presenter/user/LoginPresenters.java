package com.github2136.wardrobe.presenter.user;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.UserModel;
import com.github2136.wardrobe.model.entity.UserInfo;
import com.github2136.wardrobe.model.util.HttpCallback;
import com.github2136.wardrobe.ui.view.user.ILoginView;
import com.github2136.wardrobe.util.Constant;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yb on 2018/5/16.
 */

public class LoginPresenters extends BaseMVPPresenter<ILoginView> {
    UserModel mUserModel;

    public LoginPresenters(AppCompatActivity activity, ILoginView view) {
        super(activity, view);
        mUserModel = new UserModel(activity);
    }

    public void login(String username, String password) {
        mView.showProgressDialog();
        mUserModel.login(username, password, new HttpCallback() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        mView.dismissDialog();
                        mView.loginFailure(failedStr);
                    }

                    @Override
                    public void onResponse(Call call, Response response, String bodyStr) {
                        mView.dismissDialog();
                        if (isSuccess(bodyStr)) {
                            UserInfo userInfo = mJsonUtil.getObjectByStr(bodyStr, UserInfo.class);
                            mSpUtil.edit()
                                    .putValue(Constant.SP_SESSION_TOKEN, userInfo.getSessionToken())
                                    .putValue(Constant.SP_OBJECT_ID, userInfo.getObjectId())
                                    .apply();
                            mView.loginSuccessful(userInfo);
                        } else {
                            mView.loginFailure(getFailedStr(bodyStr));
                        }
                    }
                }
        );
    }

    public void autoLogin() {
        mView.showProgressDialog();
        mUserModel.autoLogin(new HttpCallback() {
                                 @Override
                                 public void onFailure(Call call, Exception e) {
                                     mView.dismissDialog();
                                     mView.loginFailure(failedStr);
                                 }

                                 @Override
                                 public void onResponse(Call call, Response response, String bodyStr) {
                                     mView.dismissDialog();
                                     if (isSuccess(bodyStr)) {
                                         UserInfo userInfo = mJsonUtil.getObjectByStr(bodyStr, UserInfo.class);
                                         mSpUtil.edit()
                                                 .putValue(Constant.SP_SESSION_TOKEN, userInfo.getSessionToken())
                                                 .putValue(Constant.SP_OBJECT_ID, userInfo.getObjectId())
                                                 .apply();
                                         mView.loginSuccessful(userInfo);
                                     } else {
                                         mView.loginFailure(getFailedStr(bodyStr));
                                     }
                                 }
                             }
        );
    }

    public boolean isAutoLogin() {
        return mSpUtil.contains(Constant.SP_SESSION_TOKEN);
    }

    @Override
    public void cancelRequest() {
        mUserModel.cancelRequest();
    }
}
