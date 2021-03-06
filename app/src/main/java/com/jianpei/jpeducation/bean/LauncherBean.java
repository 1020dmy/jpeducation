package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LauncherBean implements Parcelable {

    /**
     * guideList : ["https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/guide1.jpg","https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/guide2.jpg","https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/guide3.jpg"]
     * startingInfo : https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/launcher.jpg
     */

    private String userProtocol;
    private String telephone;
    private ArrayList<String> guideList;
    private ShareDataBean shareData;
    private StartingInfoBean startingInfo;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public ArrayList<String> getGuideList() {
        return guideList;
    }

    public void setGuideList(ArrayList<String> guideList) {
        this.guideList = guideList;
    }

    public String getUserProtocol() {
        return userProtocol;
    }

    public void setUserProtocol(String userProtocol) {
        this.userProtocol = userProtocol;
    }

    public ShareDataBean getShareData() {
        return shareData;
    }

    public void setShareData(ShareDataBean shareData) {
        this.shareData = shareData;
    }

    public StartingInfoBean getStartingInfo() {
        return startingInfo;
    }

    public void setStartingInfo(StartingInfoBean startingInfo) {
        this.startingInfo = startingInfo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userProtocol);
        dest.writeString(this.telephone);
        dest.writeStringList(this.guideList);
        dest.writeParcelable(this.shareData, flags);
        dest.writeParcelable(this.startingInfo, flags);
    }

    public LauncherBean() {
    }

    protected LauncherBean(Parcel in) {
        this.userProtocol = in.readString();
        this.telephone = in.readString();
        this.guideList = in.createStringArrayList();
        this.shareData = in.readParcelable(ShareDataBean.class.getClassLoader());
        this.startingInfo = in.readParcelable(StartingInfoBean.class.getClassLoader());
    }

    public static final Creator<LauncherBean> CREATOR = new Creator<LauncherBean>() {
        @Override
        public LauncherBean createFromParcel(Parcel source) {
            return new LauncherBean(source);
        }

        @Override
        public LauncherBean[] newArray(int size) {
            return new LauncherBean[size];
        }
    };
}
