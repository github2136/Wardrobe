package com.github2136.wardrobe.ui.view.user;

import com.github2136.wardrobe.base.mvp.IBaseMVPView;
import com.github2136.wardrobe.model.entity.UserInfo;

/**
 * Created by yb on 2018/5/16.
 */

public interface ILoginView extends IBaseMVPView {
    void loginSuccessful(UserInfo userInfo);

    void loginFailure(String msg);
}
