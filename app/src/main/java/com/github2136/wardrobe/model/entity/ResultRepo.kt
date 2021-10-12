package com.github2136.wardrobe.model.entity

import com.orhanobut.logger.Logger
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by YB on 2020/9/24
 */
sealed class ResultRepo<out R> {
    data class Success<T>(val data: T) : ResultRepo<T>()
    data class Error(val code: Int, val msg: String, val e: Throwable? = null) : ResultRepo<Nothing>() {
        init {
            val w = StringWriter()
            e?.printStackTrace(PrintWriter(w))
            Logger.t("ResultRepo.Error").e("$w")
        }
    }
}