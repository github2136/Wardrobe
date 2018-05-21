package com.github2136.wardrobe.presenter;

import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.ClothingInfoModel;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.util.ErrorResponse;
import com.github2136.wardrobe.model.util.RequestCallback;
import com.github2136.wardrobe.ui.view.IMainView;
import com.github2136.wardrobe.util.Constant;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by yubin on 2017/7/18.
 */

public class MainPresenter extends BaseMVPPresenter<IMainView> {
    private ClothingInfoModel mClothingInfoModel;

    public MainPresenter(AppCompatActivity activity, IMainView view) {
        super(activity, view);
        mClothingInfoModel = new ClothingInfoModel(activity);
    }

    public void getClothing(int pageNumber) {
        mClothingInfoModel.getClothing(pageNumber, Constant.PAGE_SIZE, new RequestCallback() {
            @Override
            public void onFailure(Exception e) {
                mView.getClothingFailure(failedStr);
            }

            @Override
            public void onResponse(String response) {
//                ErrorResponse<List<ClothingInfo>> resp = mJsonUtil.getObjectByStr(response, new TypeToken<ErrorResponse<List<ClothingInfo>>>() {}.getType());
//                if (isSuccess(resp)) {
//                    mView.getClothingSuccessful(resp.getData());
//                } else {
//                    mView.getClothingFailure(getFailedStr(resp));
//                }
            }
        });
    }

    @Override
    public void cancelRequest() {
        mClothingInfoModel.cancelRequest();
    }
}
