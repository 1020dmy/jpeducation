package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.MaterialDataBean;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;
import com.jianpei.jpeducation.contract.MaterialListContract;
import com.jianpei.jpeducation.repository.MaterialListRepository;

import java.util.ArrayList;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialListModel extends BaseViewModel implements MaterialListContract.Model {


    private MaterialListRepository materialListRepository;


    private MutableLiveData<MaterialDataBean> materialDataBean;

    private MutableLiveData<ArrayList<MaterialInfoBean>> materialInfoBeans;

    public MaterialListModel() {
        materialListRepository = new MaterialListRepository();
    }

    public MutableLiveData<MaterialDataBean> getMaterialDataBean() {
        if (materialDataBean == null) {
            materialDataBean = new MutableLiveData<MaterialDataBean>();
        }
        return materialDataBean;
    }

    public MutableLiveData<ArrayList<MaterialInfoBean>> getMaterialInfoBeans() {
        if (materialInfoBeans == null) {
            materialInfoBeans = new MutableLiveData<>();
        }
        return materialInfoBeans;
    }

    @Override
    public void materialData(String cat_id, int pageIndex, int pageSize) {

        if (TextUtils.isEmpty(cat_id)) {
            return;
        }

        materialListRepository.materialData(cat_id, pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<MaterialDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<MaterialDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    materialDataBean.setValue(t.getData());
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

    @Override
    public void subMaterialData(String cat_id, String class_id) {

        if (TextUtils.isEmpty(cat_id) || TextUtils.isEmpty(class_id)) {
            return;
        }


        materialListRepository.subMaterialData(cat_id, class_id).compose(setThread()).subscribe(new BaseObserver<ArrayList<MaterialInfoBean>>() {

            @Override
            protected void onSuccees(BaseEntity<ArrayList<MaterialInfoBean>> t) throws Exception {

                if (t.isSuccess()) {
                    materialInfoBeans.setValue(t.getData());
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
