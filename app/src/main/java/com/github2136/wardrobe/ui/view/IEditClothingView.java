package com.github2136.wardrobe.ui.view;

import com.avos.avoscloud.AVFile;
import com.github2136.wardrobe.base.mvp.IBaseMVPView;
import com.github2136.wardrobe.model.entity.MediaFile;

/**
 * Created by yb on 2017/10/10.
 */

public interface IEditClothingView extends IBaseMVPView {
    void editClothingSuccessful();

    void editClothingFailure(String msg);

    void deleteClothingSuccessful();

    void deleteClothingFailure(String msg);

    void uploadFileSuccessful(MediaFile file);

    void uploadFileFailure(String msg);
}
