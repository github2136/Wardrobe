package com.github2136.wardrobe.model.db

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String) = Json.decodeFromString<Date>(value)

    @TypeConverter
    fun fromDate(date: Date?) = Json.encodeToString(date)
    //
    @TypeConverter
    fun fromList(value: MutableList<String>?) = Json.encodeToString(value)

    @TypeConverter
    fun fromListStr(value: String) = Json.decodeFromString<MutableList<String>>(value)
}