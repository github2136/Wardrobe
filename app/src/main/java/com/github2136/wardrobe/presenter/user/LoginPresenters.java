package com.github2136.wardrobe.presenter.user;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.UserModel;
import com.github2136.wardrobe.model.util.RequestCallback;
import com.github2136.wardrobe.ui.view.user.ILoginView;

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
        mUserModel.login(username, password, new RequestCallback() {
            @Override
            public void onFailure(Exception e) {
                Log.e("login", e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (isSuccess(response)) {

                }
//                ErrorResponse<String> res = mJsonUtil.getObjectByStr(response, new TypeToken<ErrorResponse<String>>() {}.getType());
//                if (res!=null) {
//                    if (res.getCode()==211) {
//
//                    }
//                }

                Log.e("login", response);
            }
        });
    }

    @Override
    public void cancelRequest() {

    }
}
