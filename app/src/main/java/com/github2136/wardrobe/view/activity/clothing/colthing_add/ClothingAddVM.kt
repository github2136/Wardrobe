package com.github2136.wardrobe.view.activity.clothing.colthing_add

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github2136.wardrobe.repository.ClothingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by YB on 2021/10/11
 */
class ClothingAddVM(val app: Application) : AndroidViewModel(app) {
    private val clothingRepository by lazy { ClothingRepository(app) }
    val seasons = listOf("春", "夏", "秋", "冬")
    val types = listOf("外套", "上装", "下装", "内搭", "鞋", "套装", "其他")

    //List不要使用可变的使用add或remove方法时不会触发重绘，需要使用.value = 赋值触发重绘
    private val _seasonCheckedList = MutableStateFlow(listOf<Int>())
    val seasonCheckedList = _seasonCheckedList.asStateFlow()
    //在viewmodel中添加一个可变对象用来添加
    private var seasonCheckedTempList = mutableListOf<Int>()
    private val _remake = MutableStateFlow("")
    val remake = _remake.asStateFlow()
    private val _expanded = MutableStateFlow(false)
    val expanded = _expanded.asStateFlow()
    private val _optionText = MutableStateFlow("请选择")
    val optionText = _optionText.asStateFlow()
    private val _photoList = MutableStateFlow(listOf<Uri>())
    val photoList = _photoList.asStateFlow()
    init {
        _seasonCheckedList.value = seasonCheckedTempList.toList()
    }

    fun onCheckedChange(index: Int) {
        if (index in seasonCheckedTempList) {
            seasonCheckedTempList.remove(index)
            _seasonCheckedList.value = seasonCheckedTempList.toList()
        } else {
            seasonCheckedTempList.add(index)
            _seasonCheckedList.value = seasonCheckedTempList.toList()
        }
    }

    fun updateRemark(str: String) {
        _remake.value = str
    }

    fun onExpandedChange(expanded: Boolean) {
        _expanded.value = expanded
    }

    fun onOptionSelected(optionText: String) {
        _optionText.value = optionText
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
        viewModelScope.launch {
            Log.e("save",_seasonCheckedList.value.joinToString { it.toString() })
            Log.e("save",_optionText.value)
            Log.e("save",_remake.value)
            _seasonCheckedList.value
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
        }
    }
}