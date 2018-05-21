package com.github2136.wardrobe.presenter;

import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.ClothingInfoModel;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.util.ErrorResponse;
import com.github2136.wardrobe.model.util.RequestCallback;
import com.github2136.wardrobe.ui.view.IAddClothingView;

/**
 * Created by yb on 2017/10/4.
 */

public class AddClothingPresenter extends BaseMVPPresenter<IAddClothingView> {
    private ClothingInfoModel mClothingInfoModel;

    public AddClothingPresenter(AppCompatActivity activity, IAddClothingView view) {
        super(activity, view);
        mClothingInfoModel = new ClothingInfoModel(activity);
    }

    public void saveClothing(ClothingInfo clothingInfo) {
        mClothingInfoModel.saveClothing(clothingInfo, new RequestCallback() {
            @Override
            public void onFailure(Exception e) {
                postMain(new Runnable() {
                    @Override
                    public void run() {
                        mView.dismissDialog();
                        mView.addClothingFailure(failedStr);
                    }
                });
            }

            @Override
            public void onResponse(String response) {
                final ErrorResponse res = mJsonUtil.getObjectByStr(response, ErrorResponse.class);
                postMain(new Runnable() {
                    @Override
                    public void run() {
                        mView.dismissDialog();
//                        if (isSuccess(res)) {
//                            mView.addClothingSuccessful();
//                        } else {
//                            mView.addClothingFailure(getFailedStr(res));
//                        }
                    }
                });

            }
        });
    }

    @Override
    public void cancelRequest() {

    }
}
