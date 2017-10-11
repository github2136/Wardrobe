package com.github2136.wardrobe.ui.view;

import com.github2136.base.mvp.IBaseMVPView;

/**
 * Created by yb on 2017/10/10.
 */

public interface IEditClothingView extends IBaseMVPView {
    void editClothingSuccessful();

    void editClothingFailure(String msg);

    void deleteClothingSuccessful();

    void deleteClothingFailure(String msg);
}
