package com.github2136.wardrobe.ui.view;

import com.github2136.wardrobe.base.mvp.IBaseMVPView;
import com.github2136.wardrobe.model.entity.ClothingInfo;

import java.util.List;

/**
 * Created by yubin on 2017/7/18.
 */

public interface IMainView extends IBaseMVPView {
    void getClothingSuccessful(List<ClothingInfo> clothingInfos);

    void getClothingFailure(String msg);
}
