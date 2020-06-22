package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class WxLoginJson implements Parcelable {


    private String auth_type;

    private String refreshToken;

    private String expiration;

    private String screen_name;

    private String access_token;

    private String city;

    private String gender;

    private String openid;

    private String province;

    private String iconurl;

    public WxLoginJson(String auth_type,String refreshToken, String expiration, String screen_name, String access_token, String city, String gender, String openid, String province, String iconurl) {
       this.auth_type=auth_type;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        this.screen_name = screen_name;
        this.access_token = access_token;
        this.city = city;
        this.gender = gender;
        this.openid = openid;
        this.province = province;
        this.iconurl = iconurl;
    }

    public String getAuth_type() {
        return auth_type;
    }

    public void setAuth_type(String auth_type) {
        this.auth_type = auth_type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.auth_type);
        dest.writeString(this.refreshToken);
        dest.writeString(this.expiration);
        dest.writeString(this.screen_name);
        dest.writeString(this.access_token);
        dest.writeString(this.city);
        dest.writeString(this.gender);
        dest.writeString(this.openid);
        dest.writeString(this.province);
        dest.writeString(this.iconurl);
    }

    protected WxLoginJson(Parcel in) {
        this.auth_type = in.readString();
        this.refreshToken = in.readString();
        this.expiration = in.readString();
        this.screen_name = in.readString();
        this.access_token = in.readString();
        this.city = in.readString();
        this.gender = in.readString();
        this.openid = in.readString();
        this.province = in.readString();
        this.iconurl = in.readString();
    }

    public static final Creator<WxLoginJson> CREATOR = new Creator<WxLoginJson>() {
        @Override
        public WxLoginJson createFromParcel(Parcel source) {
            return new WxLoginJson(source);
        }

        @Override
        public WxLoginJson[] newArray(int size) {
            return new WxLoginJson[size];
        }
    };
}
