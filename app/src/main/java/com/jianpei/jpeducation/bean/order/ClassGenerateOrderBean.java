package com.jianpei.jpeducation.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassGenerateOrderBean implements Parcelable {

    private GroupInfoBean group_info;

    private OrderInfoBean order_info;


    public GroupInfoBean getGroup_info() {
        return group_info;
    }

    public void setGroup_info(GroupInfoBean group_info) {
        this.group_info = group_info;
    }

    public OrderInfoBean getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfoBean order_info) {
        this.order_info = order_info;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.group_info, flags);
        dest.writeParcelable(this.order_info, flags);
    }

    public ClassGenerateOrderBean() {
    }

    protected ClassGenerateOrderBean(Parcel in) {
        this.group_info = in.readParcelable(GroupInfoBean.class.getClassLoader());
        this.order_info = in.readParcelable(OrderInfoBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ClassGenerateOrderBean> CREATOR = new Parcelable.Creator<ClassGenerateOrderBean>() {
        @Override
        public ClassGenerateOrderBean createFromParcel(Parcel source) {
            return new ClassGenerateOrderBean(source);
        }

        @Override
        public ClassGenerateOrderBean[] newArray(int size) {
            return new ClassGenerateOrderBean[size];
        }
    };
}
