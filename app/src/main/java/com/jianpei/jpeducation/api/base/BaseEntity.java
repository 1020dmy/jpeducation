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
    private static String SUCCESS_CODE = "success";//成功的code
    private String msg;
    private long time;
    private T data;
    private String status;


    public boolean isSuccess() {

        return getStatus().equals(SUCCESS_CODE);
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
