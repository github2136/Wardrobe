package com.github2136.wardrobe.presenter;

import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.ClothingInfoModel;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.util.ErrorResponse;
import com.github2136.wardrobe.model.util.RequestCallback;
import com.github2136.wardrobe.ui.view.IEditClothingView;

/**
 * Created by yb on 2017/10/10.
 */

public class EditClothingPresenter extends BaseMVPPresenter<IEditClothingView> {
    private ClothingInfoModel mClothingInfoModel;

    public EditClothingPresenter(AppCompatActivity activity, IEditClothingView view) {
        super(activity, view);
        mClothingInfoModel = new ClothingInfoModel(activity);
    }

    public void editClothing(ClothingInfo clothingInfo) {
        mClothingInfoModel.editClothing(clothingInfo, new RequestCallback() {
            @Override
            public void onFailure(Exception e) {
                postMain(new Runnable() {
                    @Override
                    public void run() {
                        mView.dismissDialog();
                        mView.editClothingFailure(failedStr);
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
//                            mView.editClothingSuccessful();
//                        } else {
//                            mView.editClothingFailure(getFailedStr(res));
//                        }
                    }
                });
            }
        });
    }

    public void deleteClothing(ClothingInfo clothingInfo) {
        mView.showProgressDialog();
        mClothingInfoModel.deleteClothing(clothingInfo, new RequestCallback() {
            @Override
            public void onFailure(Exception e) {
                postMain(new Runnable() {
                    @Override
                    public void run() {
                        mView.dismissDialog();
                        mView.deleteClothingFailure(failedStr);
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
//                            mView.deleteClothingSuccessful();
//                        } else {
//                            mView.deleteClothingFailure(getFailedStr(res));
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
