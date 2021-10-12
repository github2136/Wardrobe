package com.github2136.wardrobe.repository

import android.content.Context
import androidx.sqlite.db.SimpleSQLiteQuery
import com.github2136.basemvvm.BaseRepository
import com.github2136.wardrobe.model.db.DBHelper
import com.github2136.wardrobe.model.entity.Clothing
import com.github2136.wardrobe.model.entity.ResultRepo


/**
 * Created by YB on 2021/10/9
 */
class ClothingRepository(context: Context) : BaseRepository(context) {
    private val clothingDao by lazy { DBHelper.getInstance(context).clothingDao() }

    /**
     * 服饰列表
     */
    fun getClothingList(season: MutableList<String>, type: MutableList<String>, page: Int, count: Int): ResultRepo<MutableList<Clothing>> {
        try {
            val sb = StringBuilder()
            sb.append("select * from clothing where (")
            season.forEachIndexed { index, s ->
                sb.append("ciSeason like '%$s%'")
                if (index != season.lastIndex) {
                    sb.append(" or ")
                }
            }
            sb.append(") and ciType in (")
            type.forEachIndexed { index, s ->
                sb.append("'$s'")
                if (index != type.lastIndex) {
                    sb.append(",")
                }
            }
            sb.append(")order by ciId desc limit ?,?")
            val query = SimpleSQLiteQuery(sb.toString(), arrayOf((page - 1) * count, count))
            return ResultRepo.Success(clothingDao.queryClothingList(query))
        } catch (e: Exception) {
            //文件提交失败
            return ResultRepo.Error(1, failedStr, e)
        }
    }

    /**
     * 保存
     */
    fun postClothing(clothing: Clothing) = clothingDao.insertClothing(clothing)

    /**
     * 编辑
     */
    fun putClothing(clothing: Clothing) = clothingDao.updateClothing(clothing)

    /**
     * 删除
     */
    fun delClothing(clothing: Clothing) = clothingDao.deleteClothing(clothing)
}