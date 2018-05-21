package com.github2136.wardrobe.model.util;

/**
 * 错误响应
 * Created by yubin on 2017/8/18.
 */

public class ErrorResponse {
    private Integer code;

    private String error;
    private String errorMsg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error == null ? "" : error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
