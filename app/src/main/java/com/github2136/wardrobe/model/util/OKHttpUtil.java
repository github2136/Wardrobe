package com.github2136.wardrobe.model.util;

import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;


import com.github2136.util.JsonUtil;
import com.github2136.util.SPUtil;
import com.github2136.util.ThreadUtil;
import com.github2136.wardrobe.util.Constant;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKHTTP请求类
 */
public class OKHttpUtil {
    private String mAppId = "c6DtXIFY5bVgUE1PoL2OpADl-gzGzoHsz";
    private String mAppKey = "jvu3zeRhzIHFPqj2oQSnXpoM";
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private SPUtil mSpUtil;
    private OkHttpClient mOkHttpClient;
    Handler mHandler;
    WeakReference<AppCompatActivity> weakActivity;
    WeakReference<Fragment> weakFragment;
    WeakReference<Service> weakService;
    String mTag;
    ThreadUtil threadUtil;

    public OKHttpUtil(AppCompatActivity activity, String tag) {
        weakActivity = new WeakReference<>(activity);
        init(tag, activity);
    }

    public OKHttpUtil(Fragment fragment, String tag) {
        weakFragment = new WeakReference<>(fragment);
        init(tag, fragment.getContext());
    }

    public OKHttpUtil(Service service, String tag) {
        weakService = new WeakReference<>(service);
        init(tag, service);
    }

    private void init(String tag, Context context) {
        mTag = tag;
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
        threadUtil = ThreadUtil.getInstance();
        mSpUtil = SPUtil.getInstance(context);
    }

    /**
     * GET请求
     */
    public void doGetRequest(final String url,
                             final String method,
                             final ArrayMap<String, Object> params,
                             final HttpCallback callback) {
        threadUtil.execute(new Runnable() {
            @Override
            public void run() {
                StringBuilder urlSb = new StringBuilder(url + method);
                if (params != null && !params.isEmpty()) {
                    urlSb.append("?");
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        String value = entry.getValue().toString();
                        if (!TextUtils.isEmpty(value)) {
                            urlSb.append(entry.getKey());
                            urlSb.append("=");
                            urlSb.append(entry.getValue());
                            urlSb.append("&");
                        }
                    }
                    urlSb.deleteCharAt(urlSb.length() - 1);
                }
                String timestamp = String.valueOf(getUTCTime());

                Request.Builder builder = new Request.Builder()
                        .url(urlSb.toString())
                        .addHeader("X-LC-Id", mAppId)
                        .addHeader("X-LC-Sign", getMD5(timestamp + mAppKey) + "," + timestamp)
                        .tag(mTag);
                if (mSpUtil.contains(Constant.SP_SESSION_TOKEN)) {
                    builder.addHeader("X-LC-Session", mSpUtil.getString(Constant.SP_SESSION_TOKEN));
                }
                Request request = builder.build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    callback.onFailure(call, e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        final String bodyStr = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
//                                    try {
//                                        if (response.isSuccessful()) {
                                    callback.onResponse(call, response, bodyStr);
//                                        } else {
//                                            callback.onFailure(call, new RuntimeException("http status:" + response.code()));
//                                        }
//                                    } catch (IOException e) {
//                                        callback.onFailure(call, e);
//                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
    }

//    /**
//     * POST请求Form
//     */
//    public void doPostFormRequest(final String url,
//                                  final String method,
//                                  final ArrayMap<String, Object> params,
//                                  final HttpCallback callback) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FormBody.Builder body = new FormBody.Builder();
//                if (params != null && !params.isEmpty()) {
//                    for (Map.Entry<String, Object> entry : params.entrySet()) {
//                        body.add(entry.getKey(), entry.getValue().toString());
//                    }
//                }
//                Request request = new Request.Builder()
//                        .url(url + method)
//                        .tag(mTag)
//                        .post(body.build())
//                        .build();
//
//                Call call = mOkHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(final Call call, final IOException e) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (!isViewGone()) {
//                                    callback.onFailure(call, e);
//                                }
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(final Call call, final Response response) throws IOException {
//                        final String bodyStr = response.body().string();
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (!isViewGone()) {
////                                    try {
//                                        if (response.isSuccessful()) {
//                                            callback.onResponse(call, response, bodyStr);
//                                        } else {
//                                            callback.onFailure(call, new RuntimeException("http status:" + response.code()));
//                                        }
////                                    } catch (IOException e) {
////                                        callback.onFailure(call, e);
////                                    }
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        }).start();
//    }

    /**
     * POST请求Json
     */
    public void doPostJsonRequest(final String url,
                                  final String method,
                                  final ArrayMap<String, Object> params,
                                  final HttpCallback callback) {
        threadUtil.execute(new Runnable() {
            @Override
            public void run() {

                String json = "";
                if (params != null && !params.isEmpty()) {
                    json = JsonUtil.getInstance().getGson().toJson(params);
                }
                RequestBody body = RequestBody.create(JSON, json);
                String timestamp = String.valueOf(getUTCTime());

                Request.Builder builder = new Request.Builder()
                        .url(url + method)
                        .addHeader("X-LC-Id", mAppId)
                        .addHeader("X-LC-Sign", getMD5(timestamp + mAppKey) + "," + timestamp)
                        .tag(mTag)
                        .post(body);
                if (mSpUtil.contains(Constant.SP_SESSION_TOKEN)) {
                    builder.addHeader("X-LC-Session", mSpUtil.getString(Constant.SP_SESSION_TOKEN));
                }
                Request request = builder.build();

                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    callback.onFailure(call, e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        final String bodyStr = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
//                                    try {
//                                        if (response.isSuccessful()) {
                                    callback.onResponse(call, response, bodyStr);
//                                        } else {
//                                            callback.onFailure(call, new RuntimeException("http status:" + response.code()));
//                                        }
//                                    } catch (IOException e) {
//                                        callback.onFailure(call, e);
//                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * 界面已销毁
     */
    private boolean isViewGone() {
        Fragment fragment = null;
        AppCompatActivity activity = null;
        if (weakFragment != null) {
            fragment = weakFragment.get();
        } else if (weakActivity != null) {
            activity = weakActivity.get();
        }

        if (fragment != null) {
            return fragment.isDetached();
        } else if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return activity.isFinishing() || activity.isDestroyed();
            } else {
                return activity.isFinishing();
            }
        } else {
            return false;
        }
    }

    /**
     * 取消请求
     */
    public void cancelCallWithTag(String tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }

    /**
     * 获取UTC时间
     *
     * @return
     */
    private long getUTCTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        return cal.getTimeInMillis();
    }

    private String getMD5(String msg) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(msg.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}