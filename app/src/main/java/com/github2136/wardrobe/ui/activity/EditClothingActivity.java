package com.github2136.wardrobe.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
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

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github2136.picturepicker.activity.CaptureActivity;
import com.github2136.picturepicker.activity.PicturePickerActivity;
import com.github2136.picturepicker.activity.PictureViewActivity;
import com.github2136.util.BitmapUtil;
import com.github2136.util.CollectionsUtil;
import com.github2136.util.CommonUtil;
import com.github2136.util.FileUtil;
import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.BaseActivity;
import com.github2136.wardrobe.model.entity.ClothingInfo;
import com.github2136.wardrobe.model.entity.MediaFile;
import com.github2136.wardrobe.presenter.EditClothingPresenter;
import com.github2136.wardrobe.ui.view.IEditClothingView;
import com.github2136.wardrobe.util.glide.GlideApp;
import com.wefika.flowlayout.FlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class EditClothingActivity extends BaseActivity<EditClothingPresenter> implements IEditClothingView {
    public static final String ARG_CLOTHING = "CLOTHING";
    private static final int REQUEST_SELECT_IMG = 509;
    private static final int REQUEST_CAPTURE = 896;
    private ArrayList<String> mImgs;//原图地址
    private ArrayList<MediaFile> mFileObject;//上传后的File
    private ArrayList<String> mImgsIndex;//图片tag
    private ArrayMap<String, MediaFile> mAVFiles;//传入的图片对象
    private int mSaveIndex;//图片保存
    private int mImgWidth;
    private LayoutInflater mLayoutInflater;
    private int mMargin;
    private int mMaxLimit;
    private int mPicCount = 7;//上传图片上限
    private ClothingInfo mClothingInfo;
    @BindView(R.id.tb_title)
    Toolbar tbTitle;
    @BindView(R.id.sp_type)
    Spinner spType;
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
    protected EditClothingPresenter getPresenter() {
        return new EditClothingPresenter(this, this);
    }

    @Override
    protected int getViewResId() {
        return R.layout.activity_edit_clothing;
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
        mFileObject = new ArrayList<>();
        mImgsIndex = new ArrayList<>();
        mLayoutInflater = getLayoutInflater();
        mMaxLimit = getResources().getDisplayMetrics().widthPixels;
        if (getIntent().hasExtra(ARG_CLOTHING)) {
            mClothingInfo = getIntent().getParcelableExtra(ARG_CLOTHING);
            setTitle("修改");
            List<MediaFile> mMediaFiles = mClothingInfo.getCiPicture();
            mAVFiles = new ArrayMap<>();
            for (int i = 0; i < mMediaFiles.size(); i++) {
                MediaFile mediaFile = mMediaFiles.get(i);
                mImgs.add(mediaFile.getUrl());
                addImgView(mediaFile.getUrl(), mediaFile.getObjectId());

                mAVFiles.put(mediaFile.getUrl(), mediaFile);
            }
            int typeIndex = Arrays.asList(getResources().getStringArray(R.array.arr_clothing_type)).indexOf(mClothingInfo.getCiType());
            spType.setSelection(typeIndex);
            if (mClothingInfo.getCiSeason().contains("春")) {
                cbSpring.setChecked(true);
            }
            if (mClothingInfo.getCiSeason().contains("夏")) {
                cbSummer.setChecked(true);
            }
            if (mClothingInfo.getCiSeason().contains("秋")) {
                cbAutumn.setChecked(true);
            }
            if (mClothingInfo.getCiSeason().contains("冬")) {
                cbWinter.setChecked(true);
            }
            etRemark.setText(mClothingInfo.getCiRemark());
        } else {
            showToast("参数错误");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_clothing, menu);
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
                    showProgressDialog();
                    mSaveIndex = 0;
                    mFileObject = new ArrayList<>();
                    getBitmap();
                }
                break;
            case R.id.menu_delete:
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.dialog_title_prompt)
                        .setMessage("是否删除该记录？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mClothingInfo.setValid(false);
                                ClothingInfo c=new ClothingInfo();
                                c.setObjectId(mClothingInfo.getObjectId());
                                c.setValid(false);
                                mPresenter.deleteClothing(c);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
        return true;
    }

    private void getBitmap() {
        String path = mImgs.get(mSaveIndex);
        if (path.startsWith("http://")) {
            //旧图
            mFileObject.add(mAVFiles.get(path));
            getNext();
        } else {
            //新图
            BitmapUtil.getInstance(path)
                    .rotation()
                    .limit(mMaxLimit)
                    .save(getCacheDir() + File.separator +
                                    "upload" + File.separator +
                                    FileUtil.createFileName(".jpg")
                            , new BitmapUtil.BitmapSaveCallBack() {
                                @Override
                                public void callback(String filePath) {
                                    mPresenter.uploadFile(filePath);
                                }
                            });

        }
    }

    private void getNext() {
        mSaveIndex++;
        if (mImgs.size() > mSaveIndex) {
            getBitmap();
        } else {
            mClothingInfo.setCiType(spType.getSelectedItem().toString());
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
            mClothingInfo.setCiSeason(sb.toString());
            mClothingInfo.setCiRemark(etRemark.getText().toString());
            mClothingInfo.setCiPicture(mFileObject);
            mPresenter.editClothing(mClothingInfo);
        }
    }

    @OnClick(R.id.ib_add)
    public void onViewClicked() {
        if (mImgs.size() < mPicCount) {
            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.dialog_title_prompt)
                    .setItems(new String[]{"拍照", "图片"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0: {
                                    Intent intent = new Intent(mContext, CaptureActivity.class);
                                    startActivityForResult(intent, REQUEST_CAPTURE);
                                }
                                break;
                                case 1: {
                                    Intent intent = new Intent(mContext, PicturePickerActivity.class);
                                    intent.putExtra(PicturePickerActivity.ARG_PICKER_COUNT, mPicCount - mImgs.size());
                                    startActivityForResult(intent, REQUEST_SELECT_IMG);
                                }
                                break;
                            }
                        }
                    }).show();
        } else {
            showToast("最多" + mPicCount + "张图片");
        }
    }

    private void addImgView(final String path, String tag) {
        int index = flImgs.getChildCount() - 1;
        View view = mLayoutInflater.inflate(R.layout.view_add_view, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_add_image);
        if (tag == null) {
            tag = UUID.randomUUID().toString();
        }
        iv.setTag(R.id.tag_add_img, tag);
        mImgsIndex.add(tag);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = (String) v.getTag(R.id.tag_add_img);
                Intent intent = new Intent(mContext, PictureViewActivity.class);
                intent.putStringArrayListExtra(PictureViewActivity.ARG_PICTURES, mImgs);
                intent.putExtra(PictureViewActivity.ARG_CURRENT_INDEX, mImgsIndex.indexOf(t));
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
        GlideApp.with(this)
                .load(path)
                .centerCrop()
                .error(R.drawable.img_picker_fail)
                .placeholder(R.drawable.img_picker_place)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                    ArrayList<String> img = data.getStringArrayListExtra(PicturePickerActivity.ARG_RESULT);
                    mImgs.addAll(img);
                    for (int i = 0; i < img.size(); i++) {
                        String path = img.get(i);
                        addImgView(path, null);
                    }
                }
                break;
                case REQUEST_CAPTURE: {
                    String img = data.getStringExtra(CaptureActivity.ARG_RESULT);
                    mImgs.add(img);
                    addImgView(img, null);
                }
                break;
            }
        }
    }

    @Override
    public void editClothingSuccessful() {
        showToast("修改成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void editClothingFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void deleteClothingSuccessful() {
        showToast("删除成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void deleteClothingFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void uploadFileSuccessful(MediaFile file) {
        mFileObject.add(file);
        getNext();
    }

    @Override
    public void uploadFileFailure(String msg) {
        showToast(msg);
    }
}
