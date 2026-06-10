package com.github2136.wardrobe.view.activity.clothing.colthing_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github2136.wardrobe.R
import com.github2136.wardrobe.model.entity.Clothing
import com.github2136.wardrobe.model.entity.ResultRepo
import com.github2136.wardrobe.repository.ClothingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by YB on 2021/10/9
 */
class ClothingListVM(app: Application) : AndroidViewModel(app) {
    private val clothingInfoRepository by lazy { ClothingRepository(app) }
    private val _season = MutableStateFlow(mutableListOf("春", "夏", "秋", "冬"))
    val season = _season.asStateFlow()

    private val _type = MutableStateFlow(app.resources.getStringArray(R.array.arr_clothing_type).toMutableList())
    val type = _type.asStateFlow()

    private val _items = MutableStateFlow<List<Clothing>>(emptyList())
    val items: StateFlow<List<Clothing>> = _items.asStateFlow()

    private val _isLoading = MutableStateFlow(false) //正在加载更多
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false) //刷新获取新数据
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _hasMoreData = MutableStateFlow(true) //是否有更多数据
    val hasMoreData: StateFlow<Boolean> = _hasMoreData.asStateFlow()
    private var currentPage = 0
    private val pageSize = 50

    init {
        // 初始化示例数据
        initData()
    }

    private suspend fun getData() {
        delay(5000)
        val clothings = mutableListOf<Clothing>()
        repeat(20) { i ->
            clothings.add(Clothing(currentPage * pageSize + i.toLong()))
        }
        val newItems = ResultRepo.Success(clothings) // clothingInfoRepository.getClothingList(_season.value, _type.value, currentPage, pageSize)

        when (newItems) {
            is ResultRepo.Success -> {
                if (newItems.data.isEmpty()) {
                    _hasMoreData.value = false
                } else {
                    _items.value += newItems.data
                    currentPage++
                    // 检查是否还有更多数据
                    _hasMoreData.value = (newItems.data.size == pageSize)
                }
            }
            is ResultRepo.Error -> {
                ""
            }
        }
    }

    fun initData() {
        viewModelScope.launch {
            _hasMoreData.value = true
            _isRefreshing.value = true
            currentPage = 0
            getData()
            _isRefreshing.value = false
        }
    }

    fun loadMoreData() {
        viewModelScope.launch {
            _isLoading.value = true
            getData()
            _isLoading.value = false
        }
    }


    fun refreshTet() {
        viewModelScope.launch {

            _isRefreshing.value = true
            delay(5000)
            _isRefreshing.value = false
        }
    }


    // fun getData() {
    //     viewModelScope.launch {
    //
    //         val resultRepo = clothingInfoRepository.getClothingList(season.value, type.value, 1, 10)
    //     }
    // }
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