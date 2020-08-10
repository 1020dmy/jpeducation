package com.jianpei.jpeducation.bean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class VersionDetectBean {


    /**
     * app_version : 1.4.1
     * is_mandatory : 0
     * file_path :
     * hint :
     */

    private String app_version;
    private String is_mandatory;
    private String file_path;
    private String hint;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getIs_mandatory() {
        return is_mandatory;
    }

    public void setIs_mandatory(String is_mandatory) {
        this.is_mandatory = is_mandatory;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
