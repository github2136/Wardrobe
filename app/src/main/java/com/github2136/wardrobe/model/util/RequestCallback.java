package com.github2136.wardrobe.model.util;

/**
 * Created by yb on 2017/1/5.
 */

public interface RequestCallback {
    void onFailure(Exception e);

    void onResponse(String response);
}
