package com.github2136.wardrobe.model;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPModel;
import com.github2136.wardrobe.database.SQL.ClothingSQL;
import com.github2136.wardrobe.database.SQL.MediaFileSQL;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.util.HttpCallback;
import com.github2136.wardrobe.model.util.OKHttpUtil;
import com.github2136.wardrobe.util.Constant;

//import com.github2136.wardrobe.model.util.RequestCallback;

/**
 * 服装
 * Created by yb on 2017/7/18.
 */

public class ClothingInfoModel extends BaseMVPModel {
    private ClothingSQL mClothingDao;
    private MediaFileSQL mMediaFileSQL;
    private OKHttpUtil mOkHttpUtil;

    public ClothingInfoModel(AppCompatActivity activity) {
        super(activity);
        mClothingDao = new ClothingSQL(mActivity);
        mMediaFileSQL = new MediaFileSQL(mActivity);
        mOkHttpUtil = new OKHttpUtil(activity, mTag);
    }
//
//    public void saveClothing(final ClothingInfo clothingInfo, final RequestCallback callback) {
//        ThreadUtil.getInstance().execute(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
////                        if (mClothingDao.insertClothing(clothingInfo)) {
////                            ErrorResponse response = new ErrorResponse();
////                            response.setRequestCode(ErrorResponse.CODE_SUCCESSFUL);
////                            callback.onResponse(mJsonUtil.toJsonStr(response));
////                        } else {
////                            ErrorResponse response = new ErrorResponse();
////                            response.setRequestCode(ErrorResponse.CODE_FAILURE);
////                            callback.onResponse(mJsonUtil.toJsonStr(response));
////                        }
//                    }
//                }
//        );
//    }

//    public void editClothing(final ClothingInfo clothingInfo, final RequestCallback callback) {
//        ThreadUtil.getInstance().execute(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
////                        if (mClothingDao.editClothing(clothingInfo)) {
////                            ErrorResponse response = new ErrorResponse();
////                            response.setRequestCode(ErrorResponse.CODE_SUCCESSFUL);
////                            callback.onResponse(mJsonUtil.toJsonStr(response));
////                        } else {
////                            ErrorResponse response = new ErrorResponse();
////                            response.setRequestCode(ErrorResponse.CODE_FAILURE);
////                            callback.onResponse(mJsonUtil.toJsonStr(response));
////                        }
//                    }
//                }
//        );
//    }

//    public void deleteClothing(final ClothingInfo clothingInfo, final RequestCallback callback) {
//        ThreadUtil.getInstance().execute(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
////                        if (mClothingDao.updateByPrimaryKey(clothingInfo)) {
////                            ErrorResponse response = new ErrorResponse();
////                            response.setRequestCode(ErrorResponse.CODE_SUCCESSFUL);
////                            callback.onResponse(mJsonUtil.toJsonStr(response));
////                        } else {
////                            ErrorResponse response = new ErrorResponse();
////                            response.setRequestCode(ErrorResponse.CODE_FAILURE);
////                            callback.onResponse(mJsonUtil.toJsonStr(response));
////                        }
//                    }
//                }
//        );
//    }

//    public void getClothing(final int pagerNumber, final int pagerSize, final RequestCallback callback) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                final List<ClothingInfo> clothingInfos = mClothingDao.queryByLimit(pagerNumber, pagerSize);
//                for (ClothingInfo clothingInfo : clothingInfos) {
////                    clothingInfo.setMediaFiles(mMediaFileSQL.queryByCiId(clothingInfo.getCiId()));
//                }
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
////                        ErrorResponse<List<ClothingInfo>> response = new ErrorResponse<>();
////                        response.setRequestCode(ErrorResponse.CODE_SUCCESSFUL);
////                        response.setData(clothingInfos);
////                        callback.onResponse(mJsonUtil.toJsonStr(response));
//                    }
//                });
//            }
//        }).start();
//    }

    public void getClothing_rest(String where, int pagerNumber, int pagerSize, String order, String include, String keys, HttpCallback callback) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("where", where);
        params.put("limit", pagerSize);
        params.put("skip", pagerNumber * pagerSize);
        params.put("order", order);
        params.put("include", include);
        params.put("keys", keys);
        mOkHttpUtil.doGetRequest(mBaseUrl, mCClothingInfo, params, callback);
    }

    public void saveClothing_rest(final ClothingInfo clothingInfo, final HttpCallback callback) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("userId", mSpUtil.getString(Constant.SP_OBJECT_ID));
        params.put("ciType", clothingInfo.getCiType());
        params.put("ciColor", clothingInfo.getCiColor());
        params.put("ciSeason", clothingInfo.getCiSeason());
        params.put("ciRemark", clothingInfo.getCiRemark());
        params.put("ciPicture", clothingInfo.getCiPicture());
        params.put("valid", clothingInfo.getValid());
        params.put("viewSeq", clothingInfo.getViewSeq());
        mOkHttpUtil.doPostJsonRequest(mBaseUrl, mCClothingInfo, params, callback);
    }
    public void editClothing_rest(final ClothingInfo clothingInfo, final HttpCallback callback) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("userId", mSpUtil.getString(Constant.SP_OBJECT_ID));
        params.put("ciType", clothingInfo.getCiType());
        params.put("ciColor", clothingInfo.getCiColor());
        params.put("ciSeason", clothingInfo.getCiSeason());
        params.put("ciRemark", clothingInfo.getCiRemark());
        params.put("ciPicture", clothingInfo.getCiPicture());
        params.put("valid", clothingInfo.getValid());
        params.put("viewSeq", clothingInfo.getViewSeq());
        mOkHttpUtil.doPutJsonRequest(mBaseUrl, mCClothingInfo + "/" + clothingInfo.getObjectId(), params, callback);
    }

    @Override
    public void cancelRequest() { }
}
