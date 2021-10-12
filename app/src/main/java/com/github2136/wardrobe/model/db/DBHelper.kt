package com.github2136.wardrobe.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github2136.util.FileUtil
import com.github2136.wardrobe.common.Constants
import com.github2136.wardrobe.model.entity.Clothing
import java.io.File


@Database(
        entities = [
            Clothing::class
        ],
        version = 1
)
@TypeConverters(Converters::class)
abstract class DBHelper : RoomDatabase() {

    abstract fun clothingDao(): ClothingDao

    companion object {
        @Volatile
        private var instance: DBHelper? = null

        fun getInstance(context: Context): DBHelper {
            if (instance == null) {
                synchronized(DBHelper::class) {
                    if (instance == null) {
                        val f = File(FileUtil.getExternalStorageProjectPath(context) + "/.db", Constants.SQLITE_NAME)
                        if (!f.parentFile.exists()) {
                            f.parentFile.mkdirs()
                        }
                        instance = Room.databaseBuilder(context, DBHelper::class.java, f.absolutePath)
//                            .addMigrations(object : Migration(1, 2) {
//                                override fun migrate(database: SupportSQLiteDatabase) {
//                                    database.execSQL("CREATE TABLE Animal (_Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Id TEXT NOT NULL, MediaUrl TEXT NOT NULL, MediaType INTEGER NOT NULL, PhotoDate TEXT NOT NULL, PhotoDevice TEXT NOT NULL, PhotoDeviceId TEXT NOT NULL, WZName TEXT NOT NULL, WZType TEXT NOT NULL, WZNumber TEXT NOT NULL, Temperature TEXT NOT NULL, PhotoName TEXT NOT NULL, PhotoQuality TEXT NOT NULL, WZCategory TEXT NOT NULL, WZGender TEXT NOT NULL, Remind TEXT NOT NULL, ZStatus INTEGER NOT NULL, SourceType INTEGER NOT NULL, SourceInfo TEXT NOT NULL, JDName TEXT NOT NULL, JDTime TEXT NOT NULL, JDAIScore TEXT NOT NULL, UserId INTEGER NOT NULL, AdminId INTEGER NOT NULL, CDate TEXT NOT NULL, SiteId TEXT NOT NULL, Location TEXT NOT NULL, Altitude TEXT NOT NULL, _uploaded INTEGER NOT NULL);")
//                                    database.execSQL("CREATE TABLE Plant (_Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Id TEXT NOT NULL, PlantName TEXT NOT NULL, PlantImage TEXT NOT NULL, PlantVideo TEXT NOT NULL, PlantType TEXT NOT NULL, PlantQX TEXT NOT NULL, ZBType TEXT NOT NULL, ProtectionLevel TEXT NOT NULL, IsRareWZ INTEGER NOT NULL, IsForeignWZ INTEGER NOT NULL, GridId TEXT NOT NULL, Location TEXT NOT NULL, Altitude TEXT NOT NULL, LocationPX TEXT NOT NULL, LocationPW TEXT NOT NULL, LocationPD TEXT NOT NULL, Soil TEXT NOT NULL, Sunshine TEXT NOT NULL, Ewzzc TEXT NOT NULL, Eqljg TEXT NOT NULL, Ezqsl TEXT NOT NULL, Enljg TEXT NOT NULL, Ezqmd TEXT NOT NULL, Eysgxzk TEXT NOT NULL, Eglfs TEXT NOT NULL, Eglqd TEXT NOT NULL, CJName TEXT NOT NULL, CJTime TEXT NOT NULL, UserId INTEGER NOT NULL, AdminId INTEGER NOT NULL, CDate TEXT NOT NULL, ZStatus INTEGER NOT NULL, Remind TEXT NOT NULL, SiteId TEXT NOT NULL, JDName TEXT NOT NULL, JDTime TEXT NOT NULL, _uploaded INTEGER NOT NULL);")
//                                }
//                            })
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return instance!!
        }
    }
}