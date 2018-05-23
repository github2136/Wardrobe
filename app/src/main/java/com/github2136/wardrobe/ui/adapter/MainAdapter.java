package com.github2136.wardrobe.ui.adapter;

import android.content.Context;

import com.github2136.base.BaseLoadMoreRecyclerAdapter;
import com.github2136.base.ViewHolderRecyclerView;
import com.github2136.wardrobe.R;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.util.Constant;

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
//        ImageView ivImage = viewHolderRecyclerView.getView(R.id.iv_image);
//        Glide.with(mContext)
//                .load(clothingInfo.getMediaFiles().get(0).getFmPath())
//                .transform(new RectCircleTransform(mContext))
//                .into(ivImage);
//        String clothing = "类型：" + clothingInfo.getCiType() + "   颜色：" + clothingInfo.getCiColor();
//        viewHolderRecyclerView.setText(R.id.tv_clothing, clothing);
//        viewHolderRecyclerView.setText(R.id.tv_season, "季节：" + clothingInfo.getCiSeason());
//        viewHolderRecyclerView.setText(R.id.tv_remark, "备注：" + clothingInfo.getCiRemark());
    }
}