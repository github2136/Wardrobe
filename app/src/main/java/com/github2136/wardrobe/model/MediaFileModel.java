package com.github2136.wardrobe.model;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.github2136.wardrobe.base.mvp.BaseMVPModel;
import com.github2136.wardrobe.database.SQL.MediaFileSQL;
import com.github2136.wardrobe.model.util.HttpCallback;
import com.github2136.wardrobe.model.util.OKHttpUtil;

/**
 * Created by yb on 2017/10/7.
 */

public class MediaFileModel extends BaseMVPModel {
    private OKHttpUtil mOkHttpUtil;
    private MediaFileSQL mMediaFileSQL;

    public MediaFileModel(AppCompatActivity activity) {
        super(activity);
        mMediaFileSQL = new MediaFileSQL(mActivity);
        mOkHttpUtil = new OKHttpUtil(activity, mTag);
    }

//    private void saveMediaFile(final List<MediaFile> mediaFiles, final RequestCallback callback) {
//        ThreadUtil.getInstance().execute(new Runnable() {
//            @Override
//            public void run() {
////                mMediaFileSQL.insert(mediaFiles).if;
//            }
//        });
//    }

    public void getFile_rest(String where, int pagerNumber, int pagerSize, String order, String include, String keys, HttpCallback callback) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("where", where);
        params.put("limit", pagerSize);
        params.put("skip", pagerNumber * pagerSize);
        params.put("order", order);
        params.put("include", include);
        params.put("keys", keys);
        mOkHttpUtil.doGetRequest(mBaseUrl, mCFile, params, callback);
    }

    @Override
    public void cancelRequest() {
        mOkHttpUtil.cancelCallWithTag(mTag);
    }
}
