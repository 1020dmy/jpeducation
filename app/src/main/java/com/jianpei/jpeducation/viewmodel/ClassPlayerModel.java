package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.contract.ClassPlayerContract;
import com.jianpei.jpeducation.repository.ClassPlayerRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassPlayerModel extends BaseViewModel implements ClassPlayerContract.Model {

    private ClassPlayerRepository classPlayerRepository;

    public ClassPlayerModel() {
        classPlayerRepository = new ClassPlayerRepository();
    }

    /**
     * 课程详情
     *
     * @param class_id
     */
    private MutableLiveData<MClassInfoBean> mClassInfoBeanLiveData;

    public MutableLiveData<MClassInfoBean> getmClassInfoBeanLiveData() {
        if (mClassInfoBeanLiveData == null)
            mClassInfoBeanLiveData = new MutableLiveData<>();
        return mClassInfoBeanLiveData;
    }

    @Override
    public void classInfo(String class_id) {
        if (TextUtils.isEmpty(class_id))
            return;
        classPlayerRepository.classInfo(class_id).compose(setThread()).subscribe(new BaseObserver<MClassInfoBean>() {
            @Override
            protected void onSuccees(BaseEntity<MClassInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    mClassInfoBeanLiveData.setValue(t.getData());
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
