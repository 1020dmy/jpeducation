package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AccessTokenBean implements Parcelable {


    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeInt(this.expires_in);
        dest.writeString(this.refresh_token);
        dest.writeString(this.openid);
        dest.writeString(this.scope);
    }

    public AccessTokenBean() {
    }

    protected AccessTokenBean(Parcel in) {
        this.access_token = in.readString();
        this.expires_in = in.readInt();
        this.refresh_token = in.readString();
        this.openid = in.readString();
        this.scope = in.readString();
    }

    public static final Parcelable.Creator<AccessTokenBean> CREATOR = new Parcelable.Creator<AccessTokenBean>() {
        @Override
        public AccessTokenBean createFromParcel(Parcel source) {
            return new AccessTokenBean(source);
        }

        @Override
        public AccessTokenBean[] newArray(int size) {
            return new AccessTokenBean[size];
        }
    };
}
