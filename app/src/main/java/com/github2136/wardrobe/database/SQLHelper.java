package com.github2136.wardrobe.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github2136.sqliteutil.TableUtil;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.entity.ClothingType;
import com.github2136.wardrobe.model.entity.MediaFile;


/**
 * Created by yb on 2017/3/28.
 */

public class SQLHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "wardrobe.db";
    public static int DB_VERSION = 1;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableUtil.getCreateSQL(ClothingInfo.class));
        db.execSQL(TableUtil.getCreateSQL(ClothingType.class));
        db.execSQL(TableUtil.getCreateSQL(MediaFile.class));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:

            case 2:

        }
    }
}
