package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassInfoModel extends ViewModel {

    private MutableLiveData<Integer> tabViewStatus;

    private MutableLiveData<GroupInfoBean> groupInfoBeanMutableLiveData;

    private MutableLiveData<String[]> pricesLiveData;

    public MutableLiveData<String[]> getPrices() {
        if (pricesLiveData == null) {
            pricesLiveData = new MutableLiveData<>();
        }
        return pricesLiveData;
    }

    public void setPrices(String[] prices) {
        pricesLiveData.setValue(prices);
    }

    public MutableLiveData<GroupInfoBean> getGroupInfoBeanMutableLiveData() {
        if (groupInfoBeanMutableLiveData == null) {
            groupInfoBeanMutableLiveData = new MutableLiveData<>();
        }
        return groupInfoBeanMutableLiveData;
    }


    public void setGroupInfo(GroupInfoBean groupInfoBean) {
        if (groupInfoBeanMutableLiveData == null) {
            groupInfoBeanMutableLiveData = new MutableLiveData<>();
        }
        groupInfoBeanMutableLiveData.setValue(groupInfoBean);

    }

    public MutableLiveData<Integer> getTabViewStatus() {
        if (tabViewStatus == null) {
            tabViewStatus = new MutableLiveData<>();
        }
        return tabViewStatus;
    }

    public void tabViewStatusChange(int isShow) {
        tabViewStatus.setValue(isShow);
    }
}
