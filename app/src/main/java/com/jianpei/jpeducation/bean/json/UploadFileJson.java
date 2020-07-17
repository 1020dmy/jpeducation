package com.jianpei.jpeducation.bean.json;

import okhttp3.MultipartBody;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UploadFileJson {

    private String type;
    private MultipartBody file;

    public UploadFileJson(String type, MultipartBody file) {
        this.type = type;
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultipartBody getFile() {
        return file;
    }

    public void setFile(MultipartBody file) {
        this.file = file;
    }
}
