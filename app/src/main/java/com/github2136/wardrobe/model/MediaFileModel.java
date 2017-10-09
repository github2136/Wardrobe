package com.github2136.wardrobe.model;

import android.support.v7.app.AppCompatActivity;

import com.github2136.util.ThreadUtil;
import com.github2136.wardrobe.base.BaseModel;
import com.github2136.wardrobe.database.SQL.MediaFileSQL;
import com.github2136.wardrobe.model.entity.MediaFile;
import com.github2136.wardrobe.model.util.RequestCallback;

import java.util.List;

/**
 * Created by yb on 2017/10/7.
 */

public class MediaFileModel extends BaseModel {
    private MediaFileSQL mMediaFileSQL;

    public MediaFileModel(AppCompatActivity activity) {
        super(activity);
        mMediaFileSQL = new MediaFileSQL(mActivity);
    }

    private void saveMediaFile(final List<MediaFile> mediaFiles, final RequestCallback callback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
//                mMediaFileSQL.insert(mediaFiles).if;
            }
        });

    }

    @Override
    public void cancelRequest() {

    }
}
