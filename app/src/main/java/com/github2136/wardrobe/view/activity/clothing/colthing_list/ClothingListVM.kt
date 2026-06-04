package com.github2136.wardrobe.view.activity.clothing.colthing_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github2136.wardrobe.R
import com.github2136.wardrobe.model.entity.Clothing
import com.github2136.wardrobe.model.entity.ResultRepo
import com.github2136.wardrobe.repository.ClothingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by YB on 2021/10/9
 */
class ClothingListVM(app: Application) : AndroidViewModel(app) {
    private val clothingInfoRepository by lazy { ClothingRepository(app) }
    val seasonLD = MutableLiveData<MutableList<String>>().apply { value = mutableListOf("春", "夏", "秋", "冬") }
    val typeLD = MutableLiveData<MutableList<String>>().apply { value = app.resources.getStringArray(R.array.arr_clothing_type).toMutableList() }

    private val _items = MutableStateFlow<List<Clothing>>(emptyList())
    val items: StateFlow<List<Clothing>> = _items.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _hasMoreData = MutableStateFlow(true)
    val hasMoreData: StateFlow<Boolean> = _hasMoreData.asStateFlow()
    private var currentPage = 0
    private val pageSize = 20

    private suspend fun loadInitialData() {
        _items.value = emptyList()
        currentPage = 0
        loadNextPage()
    }

    fun loadNextPage() {
        if (_isLoading.value || !_hasMoreData.value) return

        viewModelScope.launch {
            _isLoading.value = true
            val newItems = clothingInfoRepository.getClothingList(seasonLD.value!!, typeLD.value!!, currentPage, pageSize)

            when (newItems) {
                is ResultRepo.Success -> {
                    if (newItems.data.isEmpty()) {
                        _hasMoreData.value = false
                    } else {
                        _items.value = _items.value + newItems.data
                        currentPage++

                        // 检查是否还有更多数据
                        _hasMoreData.value = (newItems.data.size == pageSize)
                    }
                }
                is ResultRepo.Error -> {
                    ""
                }
            }

            _isLoading.value = false
        }
    }

    fun refresh() {
        viewModelScope.launch {
            loadInitialData()
        }
    }

    fun getData() {
        viewModelScope.launch {

            val resultRepo = clothingInfoRepository.getClothingList(seasonLD.value!!, typeLD.value!!, 1, 10)
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