package com.github2136.wardrobe.ui.view;

import com.github2136.base.mvp.IBaseMVPView;

/**
 * Created by yb on 2017/10/4.
 */

public interface IAddClothingView extends IBaseMVPView {
    void addClothingSuccessful();

    void addClothingFailure(String msg);
}
