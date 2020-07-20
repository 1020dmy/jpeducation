package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.elective.GroupHomeBean;
import com.jianpei.jpeducation.contract.ElectiveContract;
import com.jianpei.jpeducation.repository.ElectiveRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ElectiveModel extends BaseViewModel implements ElectiveContract.Model {


    private ElectiveRepository electiveRepository;

    public ElectiveModel() {
        electiveRepository = new ElectiveRepository();
    }


    /**
     * 选课数据
     *
     * @param cat_id
     */

    private MutableLiveData<GroupHomeBean> groupHomeBeanLiveData;


    public MutableLiveData<GroupHomeBean> getGroupHomeBeanLiveData() {
        if (groupHomeBeanLiveData == null)
            groupHomeBeanLiveData = new MutableLiveData<>();
        return groupHomeBeanLiveData;
    }

    @Override
    public void groupHome(String cat_id) {


        if (TextUtils.isEmpty(cat_id))
            return;
        electiveRepository.groupHome(cat_id).compose(setThread()).subscribe(new BaseObserver<GroupHomeBean>() {

            @Override
            protected void onSuccees(BaseEntity<GroupHomeBean> t) throws Exception {
                if (t.isSuccess()) {
                    groupHomeBeanLiveData.setValue(t.getData());
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
