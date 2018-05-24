package com.github2136.wardrobe.base;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVOSCloud;
import com.github2136.wardrobe.BuildConfig;
import com.github2136.wardrobe.util.CrashHandler;

import java.util.ArrayList;

/**
 * Created by yb on 2016/2/23.
 */
public class BaseApplication extends Application {
    private ArrayList<AppCompatActivity> mActivitys;

    @Override
    public void onCreate() {
        super.onCreate();
        mActivitys = new ArrayList<>();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.setCustomCrashHanler(getApplicationContext());
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, BuildConfig.APP_ID, BuildConfig.APP_KEY);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void addActivity(AppCompatActivity act) {
        this.mActivitys.add(act);
    }

    public void removeActivity(AppCompatActivity act) {
        if (mActivitys.contains(act)) {
            mActivitys.remove(act);
        }
    }

    public void finallyAll() {
        for (int i = 0, len = mActivitys.size(); i < len; i++) {
            AppCompatActivity act = mActivitys.get(i);
            act.finish();
        }
    }
}