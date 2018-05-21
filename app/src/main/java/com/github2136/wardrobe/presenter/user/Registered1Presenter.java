package com.github2136.wardrobe.presenter.user;

import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.ui.view.user.IRegisteredView;

/**
 * Created by yb on 2018/5/17.
 */

public class Registered1Presenter extends BaseMVPPresenter<IRegisteredView> {
    public Registered1Presenter(AppCompatActivity activity, IRegisteredView view) {
        super(activity, view);
    }

    @Override
    public void cancelRequest() {

    }
}
