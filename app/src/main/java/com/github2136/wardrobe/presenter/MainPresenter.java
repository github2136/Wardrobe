package com.github2136.wardrobe.presenter;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.ClothingInfoModel;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.entity.ClothingInfo_;
import com.github2136.wardrobe.model.entity.ClothingType;
import com.github2136.wardrobe.model.entity.ClothingType_;
import com.github2136.wardrobe.model.entity.UserInfo;
import com.github2136.wardrobe.model.util.ErrorResponse;
import com.github2136.wardrobe.model.util.HttpCallback;
import com.github2136.wardrobe.model.util.RequestCallback;
import com.github2136.wardrobe.model.util.Results;
import com.github2136.wardrobe.ui.view.IMainView;
import com.github2136.wardrobe.util.Constant;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.xml.transform.Result;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yb on 2017/7/18.
 */

public class MainPresenter extends BaseMVPPresenter<IMainView> {
    private ClothingInfoModel mClothingInfoModel;

    public MainPresenter(AppCompatActivity activity, IMainView view) {
        super(activity, view);
        mClothingInfoModel = new ClothingInfoModel(activity);
    }

    public void getClothing(int pageNumber) {
        ArrayMap<String, Object> where = new ArrayMap<>();
        where.put(ClothingInfo_.DATA_userId, mSpUtil.getString(Constant.SP_OBJECT_ID));
        where.put(ClothingInfo_.DATA_valid, true);

        mClothingInfoModel.getClothing_rest(
                mJsonUtil.toJsonStr(where),
                pageNumber,
                Constant.PAGE_SIZE,
                "",
                "",
                "",
                new HttpCallback() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        mView.getClothingFailure(failedStr);
                    }

                    @Override
                    public void onResponse(Call call, Response response, String bodyStr) {
                        if (isSuccess(bodyStr)) {
                            Results<ClothingInfo> result = mJsonUtil.getObjectByStr(bodyStr, new TypeToken<Results<ClothingInfo>>() {}.getType());
                            mView.getClothingSuccessful(result.getResults());
                        } else {
                            mView.getClothingFailure(getFailedStr(bodyStr));
                        }
                    }
                });
    }

    @Override
    public void cancelRequest() {
        mClothingInfoModel.cancelRequest();
    }
}
