package com.github2136.wardrobe.model.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.github2136.wardrobe.model.entity.Clothing

/**
 * Created by YB on 2021/10/9
 */
@Dao
interface ClothingDao {
    @Insert
    fun insertClothing(clothing: Clothing): Long

    @RawQuery
    fun queryClothingList(query: SupportSQLiteQuery): MutableList<Clothing>

    @Update
    fun updateClothing(clothing: Clothing): Int

    @Delete
    fun deleteClothing(clothing: Clothing): Int
}