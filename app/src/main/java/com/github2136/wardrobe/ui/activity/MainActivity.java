package com.github2136.wardrobe.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.github2136.base.BaseLoadMoreRecyclerAdapter;
import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseListActivity;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.presenter.MainPresenter;
import com.github2136.wardrobe.ui.activity.user.LoginActivity;
import com.github2136.wardrobe.ui.adapter.MainAdapter;
import com.github2136.wardrobe.ui.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseListActivity<ClothingInfo, MainPresenter> implements IMainView {
    private static final int REQUEST_ADD = 764;
    private static final int REQUEST_EDIT = 288;
    @BindView(R.id.tb_title)
    Toolbar tbTitle;
    private AlertDialog dialog;

    private CheckBox cbSpring, cbSummer, cbAutumn, cbWinter;
    private CheckBox cbType1, cbType2, cbType3, cbType4, cbType5, cbType6, cbType7, cbType8;

    private List<String> season = new ArrayList<>(), type = new ArrayList<>();

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
        rvContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected BaseLoadMoreRecyclerAdapter<ClothingInfo> getAdapter() {
        return new MainAdapter(mContext, null);
    }

    @Override
    protected void getListData() {
        mPresenter.getClothing(mPageNumber, season, type);
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
            case R.id.menu_title_filter:
                if (dialog == null) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_filter, null);
                    cbSpring = (CheckBox) dialogView.findViewById(R.id.cb_spring);
                    cbSummer = (CheckBox) dialogView.findViewById(R.id.cb_summer);
                    cbAutumn = (CheckBox) dialogView.findViewById(R.id.cb_autumn);
                    cbWinter = (CheckBox) dialogView.findViewById(R.id.cb_winter);

                    cbType1 = (CheckBox) dialogView.findViewById(R.id.cb_ty1);
                    cbType2 = (CheckBox) dialogView.findViewById(R.id.cb_ty2);
                    cbType3 = (CheckBox) dialogView.findViewById(R.id.cb_ty3);
                    cbType4 = (CheckBox) dialogView.findViewById(R.id.cb_ty4);
                    cbType5 = (CheckBox) dialogView.findViewById(R.id.cb_ty5);
                    cbType6 = (CheckBox) dialogView.findViewById(R.id.cb_ty6);
                    cbType7 = (CheckBox) dialogView.findViewById(R.id.cb_ty7);
                    cbType8 = (CheckBox) dialogView.findViewById(R.id.cb_ty8);
                    dialog = new AlertDialog
                            .Builder(this)
                            .setTitle("过滤")
                            .setView(dialogView)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    season.clear();
                                    if (cbSpring.isChecked()) {
                                        season.add("春");
                                    }
                                    if (cbSummer.isChecked()) {
                                        season.add("夏");
                                    }
                                    if (cbAutumn.isChecked()) {
                                        season.add("秋");
                                    }
                                    if (cbWinter.isChecked()) {
                                        season.add("冬");
                                    }
                                    type.clear();
                                    if (cbType1.isChecked()) {
                                        type.add("外套");
                                    }
                                    if (cbType2.isChecked()) {
                                        type.add("上装");
                                    }
                                    if (cbType3.isChecked()) {
                                        type.add("下装");
                                    }
                                    if (cbType4.isChecked()) {
                                        type.add("内搭");
                                    }
                                    if (cbType5.isChecked()) {
                                        type.add("鞋");
                                    }
                                    if (cbType6.isChecked()) {
                                        type.add("套装");
                                    }
                                    if (cbType7.isChecked()) {
                                        type.add("裙装");
                                    }
                                    if (cbType8.isChecked()) {
                                        type.add("其他");
                                    }
                                    srContent.setRefreshing(true);
                                    getFirstPage();
                                }
                            })
                            .create();
                }
                dialog.show();
                break;
            case R.id.menu_title_logout:
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.dialog_title_prompt)
                        .setMessage("确认注销？")
                        .setPositiveButton("注销", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.logout();
                                finish();
                                startActivity(new Intent(mContext, LoginActivity.class));
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
        return true;
    }

    @Override
    protected void itemClick(ClothingInfo clothingInfo, int position) {
        if (!srContent.isRefreshing()) {
            Intent intent = new Intent(mContext, EditClothingActivity.class);
            intent.putExtra(EditClothingActivity.ARG_CLOTHING, clothingInfo);
            startActivityForResult(intent, REQUEST_EDIT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD:
                case REQUEST_EDIT:
                    getFirstPage();
                    break;
            }
        }
    }
}
