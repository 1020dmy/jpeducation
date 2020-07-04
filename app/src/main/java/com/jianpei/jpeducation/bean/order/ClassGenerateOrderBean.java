package com.jianpei.jpeducation.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.jianpei.jpeducation.bean.shop.GroupBean;

import java.util.List;

public class ClassGenerateOrderBean implements Parcelable {

    private GroupInfoBean group_info;

    private OrderInfoBean order_info;

    private List<GroupBean> group_list;

    public List<GroupBean> getGroup_list() {
        return group_list;
    }

    public void setGroup_list(List<GroupBean> group_list) {
        this.group_list = group_list;
    }

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


    public ClassGenerateOrderBean() {
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

    protected ClassGenerateOrderBean(Parcel in) {
        this.group_info = in.readParcelable(GroupInfoBean.class.getClassLoader());
        this.order_info = in.readParcelable(OrderInfoBean.class.getClassLoader());
    }

    public static final Creator<ClassGenerateOrderBean> CREATOR = new Creator<ClassGenerateOrderBean>() {
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
