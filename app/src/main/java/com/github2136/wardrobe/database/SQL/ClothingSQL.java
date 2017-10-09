package com.github2136.wardrobe.database.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github2136.sqliteutil.BaseSQLData;
import com.github2136.wardrobe.database.SQLHelper;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.entity.ClothingInfo_;
import com.github2136.wardrobe.model.entity.MediaFile;

import java.util.List;
import java.util.UUID;

/**
 * Created by yubin on 2017/8/7.
 */

public class ClothingSQL extends BaseSQLData<ClothingInfo> {
    private MediaFileSQL mMediaFileSQL;

    public ClothingSQL(Context context) {
        super(context);
        mMediaFileSQL = new MediaFileSQL(context);
    }

    @Override
    public SQLiteOpenHelper getSQLHelper(Context context) {
        return new SQLHelper(context);
    }

    public List<ClothingInfo> queryByLimit(int pageNum, int pageSize) {
        return query(ClothingInfo_.DATA_valid + "=?", new String[]{"1"}, null, null, null, String.format("%d,%d", pageNum * pageSize, pageSize));
    }

    public boolean insertClothing(ClothingInfo clothingInfo) {
        boolean success = false;
        String ciId = UUID.randomUUID().toString();
        clothingInfo.setCiId(ciId);
        SQLiteDatabase dbWrite = this.mSQLHelper.getWritableDatabase();
        dbWrite.beginTransaction();
        if (insert(dbWrite, clothingInfo) > 0) {
            int count = 0;
            for (int i = 0; i < clothingInfo.getMediaFiles().size(); i++) {
                MediaFile mediaFile = clothingInfo.getMediaFiles().get(i);
                mediaFile.setCiId(ciId);
                String fmId = UUID.randomUUID().toString();
                mediaFile.setFmId(fmId);
                if (mMediaFileSQL.insert(dbWrite, mediaFile) > 0) {
                    count++;
                }
            }
            if (count == clothingInfo.getMediaFiles().size()) {
                dbWrite.setTransactionSuccessful();
                success = true;
            }
            dbWrite.endTransaction();
        }
        dbWrite.close();
        return success;
    }
}