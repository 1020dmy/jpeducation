package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LoginJson implements Parcelable {
    private String phone;
    private String pwd;
    private String code;
    private int type;
    private long invitationId;
    private String confirm_pwd;


    public LoginJson(String phone, String pwd, String code, int type, long invitationId,String confirm_pwd) {
        this.phone = phone;
        this.pwd = pwd;
        this.code = code;
        this.type = type;
        this.invitationId = invitationId;
        this.confirm_pwd=confirm_pwd;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(long invitationId) {
        this.invitationId = invitationId;
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
        dest.writeInt(this.type);
        dest.writeLong(this.invitationId);
        dest.writeString(this.confirm_pwd);
    }

    protected LoginJson(Parcel in) {
        this.phone = in.readString();
        this.pwd = in.readString();
        this.code = in.readString();
        this.type = in.readInt();
        this.invitationId = in.readLong();
        this.confirm_pwd = in.readString();
    }

    public static final Creator<LoginJson> CREATOR = new Creator<LoginJson>() {
        @Override
        public LoginJson createFromParcel(Parcel source) {
            return new LoginJson(source);
        }

        @Override
        public LoginJson[] newArray(int size) {
            return new LoginJson[size];
        }
    };
}
