package com.jianpei.jpeducation.bean.shop;

import android.os.Parcel;
import android.os.Parcelable;

import com.jianpei.jpeducation.bean.order.OrderInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CarInfoBean implements Parcelable {

    private List<GroupBean> group_list;

    private OrderInfoBean  order_info;


    public List<GroupBean> getGroup_list() {
        return group_list;
    }

    public void setGroup_list(List<GroupBean> group_list) {
        this.group_list = group_list;
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
        dest.writeList(this.group_list);
        dest.writeParcelable(this.order_info, flags);
    }

    public CarInfoBean() {
    }

    protected CarInfoBean(Parcel in) {
        this.group_list = new ArrayList<GroupBean>();
        in.readList(this.group_list, GroupBean.class.getClassLoader());
        this.order_info = in.readParcelable(OrderInfoBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CarInfoBean> CREATOR = new Parcelable.Creator<CarInfoBean>() {
        @Override
        public CarInfoBean createFromParcel(Parcel source) {
            return new CarInfoBean(source);
        }

        @Override
        public CarInfoBean[] newArray(int size) {
            return new CarInfoBean[size];
        }
    };
}
