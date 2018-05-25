package com.github2136.wardrobe.ui.view;

import com.avos.avoscloud.AVFile;
import com.github2136.wardrobe.base.mvp.IBaseMVPView;
import com.github2136.wardrobe.model.entity.MediaFile;

/**
 * Created by yb on 2017/10/4.
 */

public interface IAddClothingView extends IBaseMVPView {
    void addClothingSuccessful();

    void addClothingFailure(String msg);

    void uploadFileSuccessful(MediaFile file);

    void uploadFileFailure(String msg);
}
