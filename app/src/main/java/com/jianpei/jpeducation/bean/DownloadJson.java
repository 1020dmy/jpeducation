package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DownloadJson implements Parcelable {

    private String file_id;

    public DownloadJson(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.file_id);
    }

    protected DownloadJson(Parcel in) {
        this.file_id = in.readString();
    }

    public static final Parcelable.Creator<DownloadJson> CREATOR = new Parcelable.Creator<DownloadJson>() {
        @Override
        public DownloadJson createFromParcel(Parcel source) {
            return new DownloadJson(source);
        }

        @Override
        public DownloadJson[] newArray(int size) {
            return new DownloadJson[size];
        }
    };
}
