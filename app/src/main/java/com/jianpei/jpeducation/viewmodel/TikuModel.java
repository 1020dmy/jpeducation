package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.tiku.PaperHomeBean;
import com.jianpei.jpeducation.contract.TikuContract;
import com.jianpei.jpeducation.repository.TikuRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TikuModel extends BaseViewModel implements TikuContract.Model {


    private TikuRepository tikuRepository;

    public TikuModel() {
        tikuRepository = new TikuRepository();
    }

    /**
     * 题库首页
     *
     * @param cat_id
     */
    private MutableLiveData<PaperHomeBean> paperHomeBeanLiveData;

    public MutableLiveData<PaperHomeBean> getPaperHomeBeanLiveData() {
        if (paperHomeBeanLiveData == null)
            paperHomeBeanLiveData = new MutableLiveData<>();
        return paperHomeBeanLiveData;
    }

    @Override
    public void paperHome(String cat_id) {

        if (TextUtils.isEmpty(cat_id)) {
            return;
        }
        tikuRepository.paperHome(cat_id).compose(setThread()).subscribe(new BaseObserver<PaperHomeBean>() {

            @Override
            protected void onSuccees(BaseEntity<PaperHomeBean> t) throws Exception {
                if (t.isSuccess()) {
                    paperHomeBeanLiveData.setValue(t.getData());
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
