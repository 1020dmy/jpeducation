package com.jianpei.jpeducation.bean.classinfo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RegimentInfoBean implements Parcelable {


    /**
     * regiment_des : 21
     * regiment_data : [{"id":"1","start_time":"1591681306","end_time":"1591781306","create_time":"1591681306","user_id":"2414","huod_id":"42","is_source":"1","user_name":"15801080541","img":"http://localhost/Source/pc/images/shiimg/a15.png","num_people":"5","remaining_number":4,"remaining_time":99441}]
     */

    private String regiment_des;
    private List<RegimentBean> regiment_data;

    public String getRegiment_des() {
        return regiment_des;
    }

    public void setRegiment_des(String regiment_des) {
        this.regiment_des = regiment_des;
    }

    public List<RegimentBean> getRegiment_data() {
        return regiment_data;
    }

    public void setRegiment_data(List<RegimentBean> regiment_data) {
        this.regiment_data = regiment_data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.regiment_des);
        dest.writeList(this.regiment_data);
    }

    public RegimentInfoBean() {
    }

    protected RegimentInfoBean(Parcel in) {
        this.regiment_des = in.readString();
        this.regiment_data = new ArrayList<RegimentBean>();
        in.readList(this.regiment_data, RegimentBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<RegimentInfoBean> CREATOR = new Parcelable.Creator<RegimentInfoBean>() {
        @Override
        public RegimentInfoBean createFromParcel(Parcel source) {
            return new RegimentInfoBean(source);
        }

        @Override
        public RegimentInfoBean[] newArray(int size) {
            return new RegimentInfoBean[size];
        }
    };
}
