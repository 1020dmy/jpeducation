package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.school.AttentionDataBean;
import com.jianpei.jpeducation.contract.MineAttentionContract;
import com.jianpei.jpeducation.repository.MineAttentionRepository;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MineAttentionModel extends BaseViewModel implements MineAttentionContract.Model {

    private MineAttentionRepository mineAttentionRepository;

    public MineAttentionModel() {
        mineAttentionRepository = new MineAttentionRepository();
    }

    private MutableLiveData<AttentionDataBean> attentionDataBeansLiveData;

    public MutableLiveData<AttentionDataBean> getAttentionDataBeansLiveData() {
        if (attentionDataBeansLiveData == null)
            attentionDataBeansLiveData = new MutableLiveData<>();
        return attentionDataBeansLiveData;
    }

    @Override
    public void attentionData(int pageIndex, int pageSize) {

        mineAttentionRepository.attentionData(pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<AttentionDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<AttentionDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    attentionDataBeansLiveData.setValue(t.getData());

                } else {
                    errData.setValue(t.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }
}
