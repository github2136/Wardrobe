package com.github2136.wardrobe.base

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Created by 44569 on 2026/3/30
 */
abstract class AppBaseVM(app: Application) : AndroidViewModel(app) {
    protected val TAG = this.javaClass.name

    protected val jobs = mutableMapOf<UUID, Job>()
    val loadingStr = "请稍后……"

    //显示dialog
    val dialogLD = MutableLiveData<DialogData>()
    val toastLD = MutableLiveData<String>()
    val titleTextLD = MutableLiveData<String>()
    val leftBtnLD = MutableLiveData<String>()
    val leftImgBtnLD = MutableLiveData<Int>(0)
    val rightBtnLD = MutableLiveData<String>()
    val rightImgBtnLD = MutableLiveData<Int>()
    val handle = Handler(Looper.getMainLooper())

    fun launch(block: suspend (coroutine: CoroutineScope) -> Unit) {
        val uuid = UUID.randomUUID()
        val job = viewModelScope.launch {
            block.invoke(this)
        }
        job.invokeOnCompletion { cause ->
            jobs.remove(uuid)
        }
        jobs[uuid] = job
    }

    //取消请求
    open fun cancelRequest() {
        viewModelScope.launch {
            for (job in jobs) {
                job.value.cancelAndJoin()
            }
            jobs.clear()
        }
    }

    data class DialogData(var msg: String, var cancelable: Boolean = false, var canceledOnTouchOutside: Boolean = false)
}