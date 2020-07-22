package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.tiku.PaperDataBean;
import com.jianpei.jpeducation.contract.TodayExerciseListContract;
import com.jianpei.jpeducation.repository.TodayExerciseListRepository;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TodayExerciseListModel extends BaseViewModel implements TodayExerciseListContract.Model {

    private TodayExerciseListRepository todayExerciseListRepository;

    public TodayExerciseListModel() {
        todayExerciseListRepository = new TodayExerciseListRepository();
    }

    /**
     * @param pageIndex
     * @param pageSize
     * @param cat_id
     * @param paper_type
     */
    private MutableLiveData<PaperDataBean> paperDataBeanLiveData;

    public MutableLiveData<PaperDataBean> getTestPaperBeanLiveData() {
        if (paperDataBeanLiveData == null)
            paperDataBeanLiveData = new MutableLiveData<>();
        return paperDataBeanLiveData;
    }

    @Override
    public void paperData(int pageIndex, int pageSize, String cat_id, String paper_type) {

        if (TextUtils.isEmpty(cat_id)) {
            return;
        }
        todayExerciseListRepository.paperData(pageIndex, pageSize, cat_id, paper_type).compose(setThread()).subscribe(new BaseObserver<PaperDataBean>() {
            @Override
            protected void onSuccees(BaseEntity<PaperDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    paperDataBeanLiveData.setValue(t.getData());
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
