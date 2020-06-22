package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.contract.ClassInfoContract;
import com.jianpei.jpeducation.repository.ClassInfoRepository;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassInfoModel extends BaseViewModel implements ClassInfoContract.Model {

    private MutableLiveData<Integer> tabViewStatus;

    private MutableLiveData<GroupInfoBean> groupInfoBeanMutableLiveData;

    private MutableLiveData<String[]> pricesLiveData;

    private ClassInfoRepository classInfoRepository;

    //科目列表
    private MutableLiveData<List<GroupClassBean>>  groupClassBeansLiveData;


    public MutableLiveData<List<GroupClassBean>> getGroupClassBeansLiveData() {
        if(groupClassBeansLiveData==null)
            groupClassBeansLiveData=new MutableLiveData<>();
        return groupClassBeansLiveData;
    }

    public ClassInfoModel() {
        classInfoRepository=new ClassInfoRepository();
    }

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


    /**
     * 获取科目
     * @param groupId
     * @param regimentId
     */
    @Override
    public void groupClass(String groupId, String regimentId) {
        if (TextUtils.isEmpty(groupId)) {
            return;
        }

        classInfoRepository.groupClass(groupId,regimentId).compose(setThread()).subscribe(new BaseObserver<List<GroupClassBean>>(){

            @Override
            protected void onSuccees(BaseEntity<List<GroupClassBean>> t) throws Exception {
                if (t.isSuccess()){
                    groupClassBeansLiveData.setValue(t.getData());
                }else{
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }
}
