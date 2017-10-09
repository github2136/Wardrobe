package com.github2136.wardrobe.presenter;

import android.database.DatabaseUtils;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.app.AppCompatActivity;

import com.github2136.util.DateUtil;
import com.github2136.util.JsonUtil;
import com.github2136.wardrobe.base.BasePresenter;
import com.github2136.wardrobe.model.ClothingInfoModel;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.util.RequestCallback;
import com.github2136.wardrobe.model.util.Response;
import com.github2136.wardrobe.ui.view.IMainView;
import com.github2136.wardrobe.util.Constant;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yubin on 2017/7/18.
 */

public class MainPresenter extends BasePresenter<IMainView> {
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
                Response<List<ClothingInfo>> resp = mJsonUtil.getObjectByStr(response, new TypeToken<Response<List<ClothingInfo>>>() {}.getType());
                if (isSuccess(resp)) {
                    mView.getClothingSuccessful(resp.getData());
                } else {
                    mView.getClothingFailure(getFailedStr(resp));
                }
            }
        });
    }

    @Override
    public void cancelRequest() {
        mClothingInfoModel.cancelRequest();
    }
}
