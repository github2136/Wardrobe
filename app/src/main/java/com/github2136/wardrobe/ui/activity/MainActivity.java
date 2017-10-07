package com.github2136.wardrobe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github2136.base.BaseLoadMoreRecyclerAdapter;
import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseListActivity;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.presenter.MainPresenter;
import com.github2136.wardrobe.ui.adapter.MainAdapter;
import com.github2136.wardrobe.ui.view.IMainView;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseListActivity<ClothingInfo, MainPresenter> implements IMainView {
    private static final int REQUEST_ADD = 764;
    @BindView(R.id.tb_title)
    Toolbar tbTitle;

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    protected int getViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListData(Bundle savedInstanceState) {
        mHasItemClick = true;
        setSupportActionBar(tbTitle);
        setTitle("衣橱");
        srContent.setColorSchemeResources(R.color.primaryColor);
    }

    @Override
    protected BaseLoadMoreRecyclerAdapter<ClothingInfo> getAdapter() {
        return new MainAdapter(mContext, null);
    }

    @Override
    protected void getListData() {
        mPresenter.getClothing(mPageNumber);
    }

    @Override
    public void getClothingSuccessful(List<ClothingInfo> clothingInfos) {
        getDataSuccessful(clothingInfos);
    }

    @Override
    public void getClothingFailure(String msg) {
        getDataFailure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_title_add:
                startActivityForResult(new Intent(mContext, AddClothingActivity.class), REQUEST_ADD);
                break;
        }
        return true;
    }
}
