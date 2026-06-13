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
import kotlin.random.Random

/**
 * Created by YB on 2021/10/9
 */
class ClothingListVM(app: Application) : AndroidViewModel(app) {
    private val clothingInfoRepository by lazy { ClothingRepository(app) }
    private val _season = MutableStateFlow(mutableListOf("春", "夏", "秋", "冬"))
    val season = _season.asStateFlow()

    private val _type = MutableStateFlow(app.resources.getStringArray(R.array.arr_clothing_type).toMutableList())
    val type = _type.asStateFlow()

    private val _items = MutableStateFlow<List<Clothing>>(emptyList()) //列表数据
    val items: StateFlow<List<Clothing>> = _items.asStateFlow()

    private val _isLoading = MutableStateFlow(false) //正在加载更多
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false) //正在刷新数据
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _hasMoreData = MutableStateFlow(true) //是否有更多数据
    val hasMoreData: StateFlow<Boolean> = _hasMoreData.asStateFlow()

    private val _isSuccess = MutableStateFlow(true) //数据获取是否成功
    val isSuccess = _isSuccess.asStateFlow()

    var pageIndex = 1 //页码
    var pageCount = 20 //每页数量

    init {
        // 初始化示例数据
        initData()
    }

    private suspend fun getData() {
        delay(5000)
        val clothings = mutableListOf<Clothing>()
        repeat(pageCount) { i ->
            clothings.add(Clothing(pageIndex * pageCount + i.toLong()))
        }
        val newItems = if (Random.nextInt(10) > 5) {
            ResultRepo.Success(clothings) // clothingInfoRepository.getClothingList(_season.value, _type.value, pageIndex, pageCount)
        } else {
            ResultRepo.Error(0, "失败")
        }

        when (newItems) {
            is ResultRepo.Success -> {
                _isSuccess.value = true
                if (newItems.data.isEmpty()) {
                    _hasMoreData.value = false
                } else {
                    _items.value += newItems.data
                    pageIndex++
                    // 检查是否还有更多数据
                    _hasMoreData.value = (newItems.data.size == pageCount)
                }
            }
            is ResultRepo.Error -> {
                _isSuccess.value = false
            }
        }
    }

    fun initData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            pageIndex = 0
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