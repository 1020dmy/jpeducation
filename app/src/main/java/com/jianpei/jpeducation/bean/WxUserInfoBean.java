package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class WxUserInfoBean implements Parcelable {


    /**
     * openid : OPENID
     * nickname : NICKNAME
     * sex : 1
     * province : PROVINCE
     * city : CITY
     * country : COUNTRY
     * headimgurl : http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0
     * privilege : ["PRIVILEGE1","PRIVILEGE2"]
     * unionid :  o6_bmasdasdsad6_2sgVt7hMZOPfL
     */

    private String openid;
    private String nickname;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<String> privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.openid);
        dest.writeString(this.nickname);
        dest.writeInt(this.sex);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.headimgurl);
        dest.writeString(this.unionid);
        dest.writeStringList(this.privilege);
    }

    public WxUserInfoBean() {
    }

    protected WxUserInfoBean(Parcel in) {
        this.openid = in.readString();
        this.nickname = in.readString();
        this.sex = in.readInt();
        this.province = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.headimgurl = in.readString();
        this.unionid = in.readString();
        this.privilege = in.createStringArrayList();
    }

    public static final Parcelable.Creator<WxUserInfoBean> CREATOR = new Parcelable.Creator<WxUserInfoBean>() {
        @Override
        public WxUserInfoBean createFromParcel(Parcel source) {
            return new WxUserInfoBean(source);
        }

        @Override
        public WxUserInfoBean[] newArray(int size) {
            return new WxUserInfoBean[size];
        }
    };
}
