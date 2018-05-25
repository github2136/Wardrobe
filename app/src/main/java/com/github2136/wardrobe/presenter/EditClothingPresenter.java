package com.github2136.wardrobe.presenter;

import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;
import com.github2136.wardrobe.model.ClothingInfoModel;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.entity.MediaFile;
import com.github2136.wardrobe.model.util.ErrorResponse;
import com.github2136.wardrobe.model.util.HttpCallback;
import com.github2136.wardrobe.model.util.RequestCallback;
import com.github2136.wardrobe.ui.view.IEditClothingView;

import java.io.FileNotFoundException;

import okhttp3.Call;
import okhttp3.Response;

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
        mClothingInfoModel.editClothing_rest(clothingInfo, new HttpCallback() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        mView.dismissDialog();
                        mView.editClothingFailure(failedStr);
                    }

                    @Override
                    public void onResponse(Call call, Response response, String bodyStr) {
                        mView.dismissDialog();
                        if (isSuccess(bodyStr)) {
                            mView.editClothingSuccessful();
                        } else {
                            mView.editClothingFailure(getFailedStr(bodyStr));
                        }
                    }
                }
        );
    }

    public void deleteClothing(ClothingInfo clothingInfo) {
        mClothingInfoModel.editClothing_rest(clothingInfo, new HttpCallback() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        mView.dismissDialog();
                        mView.deleteClothingFailure(failedStr);
                    }

                    @Override
                    public void onResponse(Call call, Response response, String bodyStr) {
                        mView.dismissDialog();
                        if (isSuccess(bodyStr)) {
                            mView.deleteClothingSuccessful();
                        } else {
                            mView.deleteClothingFailure(getFailedStr(bodyStr));
                        }
                    }
                }
        );
    }

    public void uploadFile(String filePath) {
        final AVFile file;
        try {
            file = AVFile.withAbsoluteLocalPath("Clothing.png", filePath);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setUrl(file.getUrl());
                    mediaFile.setName(file.getName());
                    mediaFile.setObjectId(file.getObjectId());
                    mView.uploadFileSuccessful(mediaFile);
                }
            }, new ProgressCallback() {
                @Override
                public void done(Integer integer) {
                    // 上传进度数据，integer 介于 0 和 100。
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mView.dismissDialog();
            mView.uploadFileFailure(failedStr);
        }
    }

    @Override
    public void cancelRequest() {

    }
}
