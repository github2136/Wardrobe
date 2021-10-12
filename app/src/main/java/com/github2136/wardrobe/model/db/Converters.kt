package com.github2136.wardrobe.model.db

import androidx.room.TypeConverter
import com.github2136.util.JsonUtil
import com.github2136.util.date
import com.github2136.util.str
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    private val mJsonUtil by lazy { JsonUtil.instance }

    @TypeConverter
    fun fromTimestamp(value: String?) = value?.date()

    @TypeConverter
    fun fromDate(date: Date?) = date?.str()

    @TypeConverter
    fun fromList(value: List<String>) = value.let { mJsonUtil.getGson().toJson(it) }

    @TypeConverter
    fun fromListStr(value: String) = value.let { mJsonUtil.getObjectByStr<List<String>>(it, object : TypeToken<List<String>>() {}.type) }
}