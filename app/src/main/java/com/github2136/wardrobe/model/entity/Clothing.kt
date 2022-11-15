package com.github2136.wardrobe.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by YB on 2021/10/9
 * 服装类
 */
@Parcelize
@Entity
data class Clothing(
    @PrimaryKey(autoGenerate = true)
    var ciId: Long = 0,
    var createdAt: Date = Date(),//购买时间
    var ciType: String = "", //服装类型
    var ciSeason: String = "", //季节
    var ciRemark: String = "", //备注
    var ciPicture: MutableList<String> = mutableListOf(), //图片
    var valid: Boolean = true //是否启用
) : Parcelable {
    fun getFirstPic() = ciPicture.first()
}