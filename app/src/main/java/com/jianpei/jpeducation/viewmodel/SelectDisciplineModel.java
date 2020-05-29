package com.jianpei.jpeducation.viewmodel;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.bean.DisciplinesBean;
import com.jianpei.jpeducation.contract.SelectDisciplineContract;
import com.jianpei.jpeducation.repository.SelectDisciplineRepository;

import java.util.ArrayList;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SelectDisciplineModel extends BaseViewModel<ArrayList<DisciplinesBean>> implements SelectDisciplineContract.Model {


    protected SelectDisciplineRepository selectDisciplineRepository;

    public SelectDisciplineModel() {
        selectDisciplineRepository = new SelectDisciplineRepository();
    }

    @Override
    public void getCourseData() {


        selectDisciplineRepository.getCourseData().compose(setThread()).subscribe(new BaseObserver<ArrayList<DisciplinesBean>>() {

            @Override
            protected void onSuccees(BaseEntity<ArrayList<DisciplinesBean>> t) throws Exception {

                if (t.isSuccess()) {
                    successData.setValue(t.getData());
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
