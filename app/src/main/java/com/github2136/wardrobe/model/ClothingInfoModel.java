package com.github2136.wardrobe.model;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.github2136.util.ThreadUtil;
import com.github2136.wardrobe.base.BaseModel;
import com.github2136.wardrobe.database.SQL.ClothingSQL;
import com.github2136.wardrobe.database.SQL.MediaFileSQL;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.util.Response;
import com.github2136.wardrobe.model.util.RequestCallback;

import java.util.List;
import java.util.Random;

/**
 * 服装
 * Created by yubin on 2017/7/18.
 */

public class ClothingInfoModel extends BaseModel {
    private ClothingSQL mClothingDao;
    private MediaFileSQL mMediaFileSQL;

    public ClothingInfoModel(AppCompatActivity activity) {
        super(activity);
        mClothingDao = new ClothingSQL(mActivity);
        mMediaFileSQL = new MediaFileSQL(mActivity);
    }

    public void saveClothing(final ClothingInfo clothingInfo, final RequestCallback callback) {
        ThreadUtil.getInstance().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        if (mClothingDao.insertClothing(clothingInfo)) {
                            Response response = new Response();
                            response.setRequestCode(Response.CODE_SUCCESSFUL);
                            callback.onResponse(mJsonUtil.toJsonStr(response));
                        }else{
                            Response response = new Response();
                            response.setRequestCode(Response.CODE_FAILURE);
                            callback.onResponse(mJsonUtil.toJsonStr(response));
                        }
                    }
                }
        );
    }

    public void getClothing(final int pagerNumber, final int pagerSize, final RequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final List<ClothingInfo> clothingInfos = mClothingDao.queryByLimit(pagerNumber, pagerSize);
                for (ClothingInfo clothingInfo : clothingInfos) {
                    clothingInfo.setMediaFiles(mMediaFileSQL.queryByCiId(clothingInfo.getCiId()));
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Response<List<ClothingInfo>> response = new Response<>();
                        if (new Random().nextBoolean()) {
                            response.setRequestCode(Response.CODE_SUCCESSFUL);
                        } else {
                            response.setRequestCode(Response.CODE_FAILURE);
                        }
                        if (new Random().nextBoolean()) {
                            response.setData(clothingInfos);
                        }
                        callback.onResponse(mJsonUtil.toJsonStr(response));
                    }
                });
            }
        }).start();
    }

    public void addClothing(ClothingInfo clothingInfo, RequestCallback callback) {
        boolean result = mClothingDao.insert(clothingInfo);
        Response response = new Response();
        if (result) {
            response.setRequestCode(Response.CODE_SUCCESSFUL);
        } else {
            response.setRequestCode(Response.CODE_FAILURE);
        }
        callback.onResponse(mJsonUtil.toJsonStr(response));
    }

    public void addClothing(List<ClothingInfo> clothingInfos, RequestCallback callback) {
        boolean result = mClothingDao.insert(clothingInfos);
        Response response = new Response();
        if (result) {
            response.setRequestCode(Response.CODE_SUCCESSFUL);
        } else {
            response.setRequestCode(Response.CODE_FAILURE);
        }
        callback.onResponse(mJsonUtil.toJsonStr(response));
    }

    @Override
    public void cancelRequest() { }
}
