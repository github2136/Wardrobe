package com.github2136.wardrobe.model.util;

import com.github2136.util.ThreadUtil;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.*;
import okhttp3.Response;

/**
 * Created by yb on 2018/5/18.
 */
public class OKHttpUtil {
    private String mAppId = "c6DtXIFY5bVgUE1PoL2OpADl-gzGzoHsz";
    private String mAppKey = "jvu3zeRhzIHFPqj2oQSnXpoM";
    //    md5( 1453014943466UtOCzqb67d3sN12Kts4URwy8 )
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    ThreadUtil threadUtil;
    private static OKHttpUtil ourInstance = new OKHttpUtil();

    public static OKHttpUtil getInstance() {
        return ourInstance;
    }

    private OKHttpUtil() {
        threadUtil = ThreadUtil.getNewInstance();
    }

    public void get(final String url, final RequestCallback callback) {
        threadUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String timestamp = String.valueOf(getUTCTime());
                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("X-LC-Id", mAppId)
                            .addHeader("X-LC-Sign", getMD5(timestamp + mAppKey) + "," + timestamp)
                            .build();
                    Response response = client.newCall(request).execute();
                    callback.onResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void post(final String url, final String json, final RequestCallback callback) {
        threadUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String timestamp = String.valueOf(getUTCTime());
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("X-LC-Id", mAppId)
                            .addHeader("X-LC-Sign", getMD5(timestamp + mAppKey) + "," + timestamp)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    callback.onResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
