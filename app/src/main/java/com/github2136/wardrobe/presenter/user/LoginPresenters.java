package com.github2136.wardrobe.presenter.user;

import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.UserModel;
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
    public void login(){}

    @Override
    public void cancelRequest() {

    }
}
