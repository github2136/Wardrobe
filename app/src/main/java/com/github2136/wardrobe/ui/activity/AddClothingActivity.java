package com.github2136.wardrobe.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github2136.selectimamge.activity.CaptureActivity;
import com.github2136.selectimamge.activity.PhotoViewActivity;
import com.github2136.selectimamge.activity.SelectImageActivity;
import com.github2136.util.BitmapUtil;
import com.github2136.util.CollectionsUtil;
import com.github2136.util.CommonUtil;
import com.github2136.util.FileUtil;
import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseActivity;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.entity.MediaFile;
import com.github2136.wardrobe.presenter.AddClothingPresenter;
import com.github2136.wardrobe.ui.view.IAddClothingView;
import com.wefika.flowlayout.FlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class AddClothingActivity extends BaseActivity<AddClothingPresenter> implements IAddClothingView {
    public static final String ARG_CLOTHING = "CLOTHING";
    private static final int REQUEST_SELECT_IMG = 509;
    private static final int REQUEST_CAPTURE = 896;
    private ArrayList<String> mImgs;//原图地址
    private ArrayList<String> mSaveImgs;//压缩后的图片地址
    private ArrayList<String> mImgsIndex;//图片tag
    private int mSaveIndex;//图片保存
    private int mImgWidth;
    private LayoutInflater mLayoutInflater;
    private int mMargin;
    private int mMaxLimit;
    private ClothingInfo mClothingInfo;
    @BindView(R.id.tb_title)
    Toolbar tbTitle;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.sp_color)
    Spinner spColor;
    @BindView(R.id.cb_spring)
    CheckBox cbSpring;
    @BindView(R.id.cb_summer)
    CheckBox cbSummer;
    @BindView(R.id.cb_autumn)
    CheckBox cbAutumn;
    @BindView(R.id.cb_winter)
    CheckBox cbWinter;
    @BindView(R.id.fl_imgs)
    FlowLayout flImgs;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.ib_add)
    ImageButton ibAdd;

    @Override
    protected AddClothingPresenter getPresenter() {
        return new AddClothingPresenter(this, this);
    }

    @Override
    protected int getViewResId() {
        return R.layout.activity_add_clothing;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setSupportActionBar(tbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMargin = CommonUtil.dp2px(mContext, 4);
        mImgWidth = (getResources().getDisplayMetrics().widthPixels - CommonUtil.dp2px(mContext, 36)) / 4;
        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(mImgWidth, mImgWidth);
        layoutParams.setMargins(mMargin, mMargin, 0, 0);
        ibAdd.setLayoutParams(layoutParams);
        mImgs = new ArrayList<>();
        mSaveImgs = new ArrayList<>();
        mImgsIndex = new ArrayList<>();
        mLayoutInflater = getLayoutInflater();
        mMaxLimit = getResources().getDisplayMetrics().widthPixels;
        if (getIntent().hasExtra(ARG_CLOTHING)) {
            mClothingInfo = getIntent().getParcelableExtra(ARG_CLOTHING);
            setTitle("修改");
        } else {
            setTitle("添加");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_clothing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_ok:
                if (CollectionsUtil.isEmpty(mImgs)) {
                    showToast("请至少添加一张图片");
                } else if (!cbSpring.isChecked() && !cbSummer.isChecked() && !cbAutumn.isChecked() && !cbWinter.isChecked()) {
                    showToast("请至少选择一个季节");
                } else {
                    mSaveIndex = 0;
                    mSaveImgs = new ArrayList<>();
                    getBitmap();
                }
                break;
        }
        return true;
    }

    private void getBitmap() {
        String path = mImgs.get(mSaveIndex);
        BitmapUtil.getInstance(path)
                .rotation()
                .limit(mMaxLimit)
                .save(FileUtil.getExternalStorageProjectPath(mContext) + File.separator + "photo" + File.separator +
                                FileUtil.createFileName(".jpg")
                        , new BitmapUtil.BitmapSaveCallBack() {
                            @Override
                            public void callback(String filePath) {
                                mSaveImgs.add(filePath);
                                mSaveIndex++;
                                if (mImgs.size() > mSaveIndex + 1) {
                                    getBitmap();
                                } else {
                                    ClothingInfo clothingInfo = new ClothingInfo();
                                    clothingInfo.setCiType(spType.getSelectedItem().toString());
                                    clothingInfo.setCiColor(spColor.getSelectedItem().toString());
                                    StringBuilder sb = new StringBuilder();
                                    if (cbSpring.isChecked()) {
                                        sb.append("春,");
                                    }
                                    if (cbSummer.isChecked()) {
                                        sb.append("夏,");
                                    }
                                    if (cbAutumn.isChecked()) {
                                        sb.append("秋,");
                                    }
                                    if (cbWinter.isChecked()) {
                                        sb.append("冬,");
                                    }
                                    if (sb.length() > 1) {
                                        sb.deleteCharAt(sb.length() - 1);
                                    }
                                    clothingInfo.setCiSeason(sb.toString());
                                    clothingInfo.setCiRemark(etRemark.getText().toString());
                                    List<MediaFile> mfs = new ArrayList<>();
                                    for (int i = 0; i < mSaveImgs.size(); i++) {
                                        MediaFile mf = new MediaFile();
                                        mf.setFmPath(mSaveImgs.get(i));
                                        mfs.add(mf);
                                    }
                                    clothingInfo.setMediaFiles(mfs);
                                    clothingInfo.setValid("1");
                                    clothingInfo.setModifyDate(new Date());
                                    mPresenter.saveClothing(clothingInfo);
                                }
                            }
                        });
    }

    @OnClick(R.id.ib_add)
    public void onViewClicked() {
        if (mImgs.size() < 11) {
            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.dialog_title_prompt)
                    .setItems(new String[]{"拍照", "图片"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0: {
                                    Intent intent = new Intent(mContext, CaptureActivity.class);
                                    intent.putExtra(SelectImageActivity.ARG_SELECT_COUNT, 11 - mImgs.size());
                                    startActivityForResult(intent, REQUEST_CAPTURE);
                                }
                                break;
                                case 1: {
                                    Intent intent = new Intent(mContext, SelectImageActivity.class);
                                    intent.putExtra(SelectImageActivity.ARG_SELECT_COUNT, 11 - mImgs.size());
                                    startActivityForResult(intent, REQUEST_SELECT_IMG);
                                }
                                break;
                            }
                        }
                    }).show();
        } else {
            showToast("最多11张图片");
        }
    }

    private void addImgView(final String path) {
        int index = flImgs.getChildCount() - 1;
        View view = mLayoutInflater.inflate(R.layout.view_add_view, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_add_image);
        String tag = UUID.randomUUID().toString();
        iv.setTag(R.id.tag_add_img, tag);
        mImgsIndex.add(tag);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = (String) v.getTag(R.id.tag_add_img);
                Intent intent = new Intent(mContext, PhotoViewActivity.class);
                intent.putStringArrayListExtra(PhotoViewActivity.ARG_PHOTOS, mImgs);
                intent.putExtra(PhotoViewActivity.ARG_CURRENT_INDEX, mImgsIndex.indexOf(t));
                startActivity(intent);
            }
        });
        ImageButton ibDelete = (ImageButton) view.findViewById(R.id.ib_add_delete);
        ibDelete.setTag(tag);
        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.dialog_title_prompt)
                        .setMessage("是否删除该图片")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String t = (String) v.getTag();
                                int index = mImgsIndex.indexOf(t);
                                mImgsIndex.remove(index);
                                mImgs.remove(index);
                                flImgs.removeViewAt(index);
                            }
                        }).setNegativeButton("取消", null)
                        .show();
            }
        });
        Glide.with(this)
                .load(path)
                .centerCrop()
                .into(iv);
        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(mImgWidth, mImgWidth);
        layoutParams.setMargins(mMargin, mMargin, 0, 0);
        view.setLayoutParams(layoutParams);
        flImgs.addView(view, index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_IMG: {
                    ArrayList<String> img = data.getStringArrayListExtra(SelectImageActivity.ARG_RESULT);
                    mImgs.addAll(img);
                    for (int i = 0; i < img.size(); i++) {
                        String path = img.get(i);
                        addImgView(path);
                    }
                }
                break;
                case REQUEST_CAPTURE: {
                    String img = data.getStringExtra(CaptureActivity.ARG_RESULT);
                    mImgs.add(img);
                    addImgView(img);
                }
                break;
            }
        }
    }

    @Override
    public void addClothingSuccessful() {
        showToast("添加成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void addClothingFailure(String msg) {
        showToast(msg);
    }
}
