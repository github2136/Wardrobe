package com.github2136.wardrobe.view.adapter.clothing

import com.github2136.base.ViewHolderRecyclerView
import com.github2136.basemvvm.loadmore.BaseLoadMoreAdapter
import com.github2136.wardrobe.R
import com.github2136.wardrobe.databinding.ItemClothingBinding
import com.github2136.wardrobe.model.entity.Clothing

/**
 * Created by YB on 2021/10/9
 */
class ClothingAdapter : BaseLoadMoreAdapter<Clothing, ItemClothingBinding>() {
    override fun getLayoutIdByList(viewType: Int) = R.layout.item_clothing

    override fun onBindView(t: Clothing, bind: ItemClothingBinding, holder: ViewHolderRecyclerView, position: Int) {
        bind.data = t
        bind.executePendingBindings()
    }
}