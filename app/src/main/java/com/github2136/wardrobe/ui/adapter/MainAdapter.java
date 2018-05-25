package com.github2136.wardrobe.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.github2136.base.BaseLoadMoreRecyclerAdapter;
import com.github2136.base.ViewHolderRecyclerView;
import com.github2136.wardrobe.R;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.util.Constant;
import com.github2136.wardrobe.util.glide.GlideApp;

import java.util.List;

/**
 * Created by yb on 2017/7/18.
 */

public class MainAdapter extends BaseLoadMoreRecyclerAdapter<ClothingInfo> {

    public MainAdapter(Context context, List<ClothingInfo> list) {
        super(context, list);
    }


    @Override
    protected int getPageSize() {
        return Constant.PAGE_SIZE;
    }

    @Override
    public int getLayoutId(int i) {
        return R.layout.item_main;
    }

    @Override
    protected void onBindView(ClothingInfo clothingInfo, ViewHolderRecyclerView viewHolderRecyclerView, int i) {
        ImageView ivImage = viewHolderRecyclerView.getView(R.id.iv_image);
        GlideApp.with(mContext)
                .load(clothingInfo.getCiPicture().get(0).getUrl())
                .error(R.drawable.img_picker_fail)
                .placeholder(R.drawable.img_picker_place)
                .into(ivImage);
        String clothing = "类型：" + clothingInfo.getCiType();
        viewHolderRecyclerView.setText(R.id.tv_clothing, clothing);
        viewHolderRecyclerView.setText(R.id.tv_season, "季节：" + clothingInfo.getCiSeason());
        viewHolderRecyclerView.setText(R.id.tv_remark, "备注：" + clothingInfo.getCiRemark());
    }
}