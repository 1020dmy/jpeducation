package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.contract.TryListenerListContract;
import com.jianpei.jpeducation.repository.TryListenerListRepository;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TryListenerListModel extends BaseViewModel implements TryListenerListContract.Model {


    private TryListenerListRepository repository;

    public TryListenerListModel() {
        repository = new TryListenerListRepository();
    }


    private MutableLiveData<List<GroupInfoBean>> groupInfoBeansLiveData;

    public MutableLiveData<List<GroupInfoBean>> getGroupInfoBeansLiveData() {
        if (groupInfoBeansLiveData == null) {
            groupInfoBeansLiveData = new MutableLiveData<>();
        }
        return groupInfoBeansLiveData;
    }

    @Override
    public void groupData(String type, String cat_id) {
        if (TextUtils.isEmpty(cat_id)) {
            return;
        }
        repository.groupData(type, cat_id).compose(setThread()).subscribe(new BaseObserver<List<GroupInfoBean>>() {
            @Override
            protected void onSuccees(BaseEntity<List<GroupInfoBean>> t) throws Exception {
                if (t.isSuccess()) {
                    groupInfoBeansLiveData.setValue(t.getData());
                } else {
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
