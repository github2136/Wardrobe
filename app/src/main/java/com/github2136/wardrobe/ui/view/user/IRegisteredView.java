package com.github2136.wardrobe.ui.view.user;

import com.github2136.wardrobe.base.mvp.IBaseMVPView;
import com.github2136.wardrobe.model.entity.UserInfo;

/**
 * Created by yb on 2018/5/17.
 */

public interface IRegisteredView extends IBaseMVPView {
    void registeredSuccessful(  UserInfo userInfo);

    void registeredFailure(String msg);
}
