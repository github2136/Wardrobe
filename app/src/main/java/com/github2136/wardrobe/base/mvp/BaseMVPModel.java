package com.github2136.wardrobe.base.mvp;

import android.app.Service;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github2136.util.CollectionsUtil;
import com.github2136.util.CommonUtil;
import com.github2136.util.JsonUtil;
import com.github2136.util.SPUtil;

import java.util.Map;


/**
 * model基础类
 */
public abstract class BaseMVPModel {
    protected String mBaseUrl = "https://leancloud.cn:443/1.1/";
    protected String mLogin = "login";
    protected String mUser = "users";
    protected String mCClothingType = "classes/ClothingType";
    protected String mCClothingInfo = "classes/ClothingInfo";
    protected String mCFile= "classes/_File";
    protected AppCompatActivity mActivity;
    protected Fragment mFragment;
    protected Service mService;

    protected String mTag;
    protected SPUtil mSpUtil;
    protected JsonUtil mJsonUtil;

    public BaseMVPModel(AppCompatActivity activity) {
        mActivity = activity;
        mTag = activity.getClass().getSimpleName();
        initMode();
    }

    public BaseMVPModel(Fragment fragment) {
        mFragment = fragment;
        mTag = fragment.getClass().getSimpleName();
        initMode();
    }

    public BaseMVPModel(Service service) {
        mService = service;
        mTag = service.getClass().getSimpleName();
        initMode();
    }

    private void initMode() {
        mJsonUtil = JsonUtil.getInstance();
        if (mActivity != null) {
            mSpUtil = SPUtil.getInstance(mActivity);
        } else if (mFragment != null) {
            mSpUtil = SPUtil.getInstance(mFragment.getContext());
        } else if (mService != null) {
            mSpUtil = SPUtil.getInstance(mService);
        }
    }

    public String getParams(Map<String, Object> params) {
        StringBuilder sbStr = new StringBuilder("?");
        if (CollectionsUtil.isNotEmpty(params)) {
            for (Map.Entry<String, Object> par : params.entrySet()) {
                sbStr.append(par.getKey());
                sbStr.append("=");
                sbStr.append(par.getValue());
                sbStr.append("&");
            }
        }
        sbStr.deleteCharAt(sbStr.length() - 1);
        return sbStr.toString();
    }

    public abstract void cancelRequest();
}