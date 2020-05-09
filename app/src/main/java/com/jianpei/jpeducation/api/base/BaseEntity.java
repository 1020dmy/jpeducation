package com.jianpei.jpeducation.api.base;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class BaseEntity<T> {
    private static int SUCCESS_CODE = 0;//成功的code
    private int code;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return getCode() == SUCCESS_CODE;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
