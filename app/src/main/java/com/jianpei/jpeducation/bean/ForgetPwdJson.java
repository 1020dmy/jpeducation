package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/26
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ForgetPwdJson implements Parcelable {


    private String phone;

    private String pwd;
    private String code;

    private String confirm_pwd;


    public ForgetPwdJson(String phone, String pwd, String code, String confirm_pwd) {
        this.phone = phone;
        this.pwd = pwd;
        this.code = code;
        this.confirm_pwd = confirm_pwd;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConfirm_pwd() {
        return confirm_pwd;
    }

    public void setConfirm_pwd(String confirm_pwd) {
        this.confirm_pwd = confirm_pwd;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
        dest.writeString(this.pwd);
        dest.writeString(this.code);
        dest.writeString(this.confirm_pwd);
    }

    protected ForgetPwdJson(Parcel in) {
        this.phone = in.readString();
        this.pwd = in.readString();
        this.code = in.readString();
        this.confirm_pwd = in.readString();
    }

    public static final Creator<ForgetPwdJson> CREATOR = new Creator<ForgetPwdJson>() {
        @Override
        public ForgetPwdJson createFromParcel(Parcel source) {
            return new ForgetPwdJson(source);
        }

        @Override
        public ForgetPwdJson[] newArray(int size) {
            return new ForgetPwdJson[size];
        }
    };
}
