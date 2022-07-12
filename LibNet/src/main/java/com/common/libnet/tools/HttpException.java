package com.common.libnet.tools;

import android.text.TextUtils;


/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public class HttpException extends RuntimeException {

    public HttpException(String message) {
        this.message = message;
    }

    public HttpException(int code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return TextUtils.isEmpty(message) ? "" : message;
    }


    public int getCode() {
        return code;
    }

    private int code;
    private String message;

}
