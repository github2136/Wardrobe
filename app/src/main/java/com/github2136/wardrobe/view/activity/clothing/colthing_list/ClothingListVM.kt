package com.github2136.wardrobe.view.activity.clothing.colthing_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github2136.wardrobe.R
import com.github2136.wardrobe.repository.ClothingRepository
import kotlinx.coroutines.launch

/**
 * Created by YB on 2021/10/9
 */
class ClothingListVM(app: Application): AndroidViewModel(app){
    private val clothingInfoRepository by lazy { ClothingRepository(app) }
    val seasonLD = MutableLiveData<MutableList<String>>().apply { value = mutableListOf("春", "夏", "秋", "冬") }
    val typeLD = MutableLiveData<MutableList<String>>().apply { value = app.resources.getStringArray(R.array.arr_clothing_type).toMutableList() }
    fun getData(){
        viewModelScope.launch {
        val resultRepo = clothingInfoRepository.getClothingList(seasonLD.value!!, typeLD.value!!, adapter.pageIndex, adapter.pageCount)
        }
    }
}

/* : BaseLoadMoreVM<Clothing>(app) {
    private val clothingInfoRepository by lazy { ClothingRepository(app) }
    val seasonLD = MutableLiveData<MutableList<String>>().apply { value = mutableListOf("春", "夏", "秋", "冬") }
    val typeLD = MutableLiveData<MutableList<String>>().apply { value = app.resources.getStringArray(R.array.arr_clothing_type).toMutableList() }
    override fun initData() {
        adapter.pageIndex = 1
        adapter.pageCount = 20
        getData()
    }

    override fun loadMoreData() {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val resultRepo = clothingInfoRepository.getClothingList(seasonLD.value!!, typeLD.value!!, adapter.pageIndex, adapter.pageCount)
            when (resultRepo) {
                is ResultRepo.Success -> {
                    if (adapter.pageIndex == 1) {
                        setData(resultRepo.data)
                    } else {
                        appendData(resultRepo.data)
                    }
                }
                is ResultRepo.Error -> {
                    failedData()
                    toastLD.value = resultRepo.msg
                }
            }
        }
    }

    override fun initAdapter() = ClothingAdapter()
}*/