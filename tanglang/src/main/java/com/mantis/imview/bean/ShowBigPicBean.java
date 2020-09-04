package com.mantis.imview.bean;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.previewlibrary.enitity.IThumbViewInfo;

@SuppressLint("ParcelCreator")
public class ShowBigPicBean implements IThumbViewInfo {
    private String url;  //图片地址

    public ShowBigPicBean(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Rect getBounds() {
        return null;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    protected ShowBigPicBean(Parcel in) {
        this.url = in.readString();
    }

    public static final Creator<ShowBigPicBean> CREATOR = new Creator<ShowBigPicBean>() {
        @Override
        public ShowBigPicBean createFromParcel(Parcel source) {
            return new ShowBigPicBean(source);
        }

        @Override
        public ShowBigPicBean[] newArray(int size) {
            return new ShowBigPicBean[size];
        }
    };
}
