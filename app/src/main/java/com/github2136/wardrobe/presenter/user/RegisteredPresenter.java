package com.github2136.wardrobe.presenter.user;

import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.ui.view.user.IRegisteredView;

/**
 * Created by yb on 2018/5/17.
 */

public class RegisteredPresenter extends BaseMVPPresenter<IRegisteredView> {
    public RegisteredPresenter(AppCompatActivity activity, IRegisteredView view) {
        super(activity, view);
    }

    @Override
    public void cancelRequest() {

    }
}
