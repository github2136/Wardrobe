package com.github2136.wardrobe.vm.clothing

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github2136.basemvvm.BaseVM
import com.github2136.util.FileUtil
import com.github2136.wardrobe.model.entity.Clothing
import com.github2136.wardrobe.repository.ClothingRepository
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

/**
 * Created by YB on 2021/10/11
 */
class ClothingEditVM(val app: Application) : BaseVM(app) {
    private val clothingRepository by lazy { ClothingRepository(app) }

    val clothingLD = MutableLiveData<Clothing>()
    val dateLD = MutableLiveData<String>()
    val addLD = MutableLiveData<String>()

    val dateCalendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    fun edit() {
        viewModelScope.launch {
            dialogLD.value = DialogData(loadingStr)
            val parent = FileUtil.getExternalStorageProjectPath(app) + "/.media"
            val f = mutableListOf<String>()
            clothingLD.value?.apply {
                ciPicture.forEach {
                    val target = File(parent, UUID.randomUUID().toString() + ".jpg")
                    val mediaFile = File(it)
                    if (mediaFile.parent != parent) {
                        mediaFile.copyTo(target)
                        f.add(target.absolutePath)
                    } else {
                        f.add(mediaFile.absolutePath)
                    }
                }
                ciPicture.clear()
                ciPicture.addAll(f)
            }
            clothingRepository.putClothing(clothingLD.value!!)
            toastLD.value = "修改成功"
            addLD.value = ""
            dialogLD.value = null
        }
    }

    fun del() {
        viewModelScope.launch {
            dialogLD.value = DialogData(loadingStr)
            clothingRepository.delClothing(clothingLD.value!!)
            toastLD.value = "删除成功"
            addLD.value = ""
            dialogLD.value = null
        }
    }
}