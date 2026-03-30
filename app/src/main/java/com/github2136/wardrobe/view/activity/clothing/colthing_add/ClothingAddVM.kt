package com.github2136.wardrobe.view.activity.clothing.colthing_add

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github2136.wardrobe.base.AppBaseVM
import com.github2136.wardrobe.model.entity.Clothing
import com.github2136.wardrobe.repository.ClothingRepository
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

/**
 * Created by YB on 2021/10/11
 */
class ClothingAddVM(val app: Application) : AppBaseVM(app) {
    private val clothingRepository by lazy { ClothingRepository(app) }

    val clothingLD = MutableLiveData<Clothing>().apply { value = Clothing() }
    val addLD = MutableLiveData<String>()

    fun save() {
        viewModelScope.launch {
            dialogLD.value = DialogData(loadingStr)
            val parent = FileUtil.getExternalStorageProjectPath(app) + "/.media"
            val f = mutableListOf<String>()
            clothingLD.value?.apply {
                ciPicture.forEach {
                    val target = File(parent, UUID.randomUUID().toString() + ".jpg")
                    File(it).copyTo(target)
                    f.add(target.absolutePath)
                }
                ciPicture.clear()
                ciPicture.addAll(f)
            }
            clothingRepository.postClothing(clothingLD.value!!)
            toastLD.value = "添加成功"
            addLD.value = ""
            dialogLD.value = null
        }
    }
}