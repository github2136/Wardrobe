package com.github2136.wardrobe.view.activity.clothing.colthing_add

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github2136.wardrobe.base.AppBaseVM
import com.github2136.wardrobe.repository.ClothingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by YB on 2021/10/11
 */
class ClothingAddVM(val app: Application) : AndroidViewModel(app) {
    private val clothingRepository by lazy { ClothingRepository(app) }
    private val _seasonCheckedList = MutableStateFlow(mutableListOf<Int>())
    val seasonCheckedList = _seasonCheckedList.asStateFlow()

    fun addSeasonChecked(i: Int) {
        _seasonCheckedList.value.add(i)
    }

    fun removeSeasonChecked(i: Int) {
        _seasonCheckedList.value.remove(i)
    }
    // private val _type = MutableStateFlow(mutableListOf("外套", "上装", "下装", "内搭", "鞋", "套装", "其他"))
    // val type = _type.asStateFlow()
    //
    // val clothingLD = MutableLiveData<Clothing>().apply { value = Clothing() }
    // val dateLD = MutableLiveData<String>()
    // val addLD = MutableLiveData<String>()
    //
    // val dateCalendar = Calendar.getInstance().apply {
    //     set(Calendar.HOUR_OF_DAY, 0)
    //     set(Calendar.MINUTE, 0)
    //     set(Calendar.SECOND, 0)
    // }
    //
    fun save() {
        // viewModelScope.launch {
        //     dialogLD.value = DialogData(loadingStr)
        //     val parent = FileUtil.getExternalStorageProjectPath(app) + "/.media"
        //     val f = mutableListOf<String>()
        //     clothingLD.value?.apply {
        //         ciPicture.forEach {
        //             val target = File(parent, UUID.randomUUID().toString() + ".jpg")
        //             File(it).copyTo(target)
        //             f.add(target.absolutePath)
        //         }
        //         ciPicture.clear()
        //         ciPicture.addAll(f)
        //     }
        //     clothingRepository.postClothing(clothingLD.value!!)
        //     toastLD.value = "添加成功"
        //     addLD.value = ""
        //     dialogLD.value = null
        // }
    }
}