package com.github2136.wardrobe.database.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.github2136.sqliteutil.BaseSQLData;
import com.github2136.wardrobe.database.SQLHelper;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.entity.MediaFile;
import com.github2136.wardrobe.model.entity.MediaFile_;

import java.util.List;

/**
 * Created by yb on 2017/8/18.
 */

public class MediaFileSQL extends BaseSQLData<MediaFile> {
    public MediaFileSQL(Context context) {
        super(context);
    }

    @Override
    public SQLiteOpenHelper getSQLHelper(Context context) {
        return new SQLHelper(context);
    }

//    public List<MediaFile> queryByCiId(String cild) {
//        return query(MediaFile_.DATA_ciId + "=?", new String[]{cild}, null, null, null, null);
//    }
}
