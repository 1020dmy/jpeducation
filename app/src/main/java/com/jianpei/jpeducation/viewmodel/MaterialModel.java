package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;
import com.jianpei.jpeducation.contract.MaterialContract;
import com.jianpei.jpeducation.repository.MaterialRepository;
import com.jianpei.jpeducation.utils.L;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialModel extends BaseViewModel implements MaterialContract.Model {


    private MaterialRepository materialRepository;

    public MaterialModel() {
        materialRepository = new MaterialRepository();
    }


    private MutableLiveData<DownloadBean> downloadBeanMutableLiveData;

    public MutableLiveData<DownloadBean> getDownloadBeanMutableLiveData() {
        if (downloadBeanMutableLiveData == null) {
            downloadBeanMutableLiveData = new MutableLiveData<>();
        }
        return downloadBeanMutableLiveData;
    }

    /**
     * 文件下载
     *
     * @param fileId
     */
    @Override
    public void getDownloadUrl(String fileId) {
        if (TextUtils.isEmpty(fileId)) {
            errData.setValue("文件ID不能为空");
            return;
        }
        materialRepository.getDownloadUrl(fileId).compose(setThread()).subscribe(new BaseObserver<DownloadBean>() {

            @Override
            protected void onSuccees(BaseEntity<DownloadBean> t) throws Exception {
                if (t.isSuccess()) {
                    if (downloadBeanMutableLiveData == null) {
                        downloadBeanMutableLiveData = new MutableLiveData<>();
                    }
                    downloadBeanMutableLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }


            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                L.e("===getDownloadUrl==onFailure==" + e.getMessage());

                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    L.e("=========" + e.getMessage());
                    errData.setValue(e.getMessage());
                }
            }
        });
    }


    /**
     * 我的资料
     *
     * @param pageIndex
     * @param pageSize
     */

    @Override
    public void myMaterialData(int pageIndex, int pageSize) {
        materialRepository.myMaterialData(pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<MaterialDataBean>() {
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

    private MutableLiveData<MaterialDataBean> materialDataBean;

    public MutableLiveData<MaterialDataBean> getMaterialDataBean() {
        if (materialDataBean == null) {
            materialDataBean = new MutableLiveData<MaterialDataBean>();
        }
        return materialDataBean;
    }

    /**
     * 更多资料一级列表
     *
     * @param cat_id
     * @param pageIndex
     * @param pageSize
     */

    @Override
    public void materialData(String cat_id, int pageIndex, int pageSize) {
        if (TextUtils.isEmpty(cat_id)) {
            return;
        }

        materialRepository.materialData(cat_id, pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<MaterialDataBean>() {

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

    private MutableLiveData<ArrayList<com.jianpei.jpeducation.bean.material.MaterialInfoBean>> materialInfoBeans;

    public MutableLiveData<ArrayList<com.jianpei.jpeducation.bean.material.MaterialInfoBean>> getMaterialInfoBeans() {
        if (materialInfoBeans == null) {
            materialInfoBeans = new MutableLiveData<>();
        }
        return materialInfoBeans;
    }

    /**
     * 更多资料二级列表
     *
     * @param cat_id
     * @param class_id
     */

    @Override
    public void subMaterialData(String cat_id, String class_id,int type) {
        if (TextUtils.isEmpty(cat_id) || TextUtils.isEmpty(class_id)) {
            return;
        }


        materialRepository.subMaterialData(cat_id, class_id,type).compose(setThread()).subscribe(new BaseObserver<ArrayList<com.jianpei.jpeducation.bean.material.MaterialInfoBean>>() {

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


    private MutableLiveData<List<MaterialTitle>> localMaterialLiveData;

    public MutableLiveData<List<MaterialTitle>> getLocalMaterialLiveData() {
        if (localMaterialLiveData == null)
            localMaterialLiveData = new MutableLiveData<>();
        return localMaterialLiveData;
    }

    /**
     * 查询已经下载完成的资料
     *
     * @param materialTitles
     */

    @Override
    public void getDownloadCompleteMaterial(List<MaterialTitle> materialTitles) {
        if (materialTitles == null || materialTitles.size() == 0) {
            return;
        }
        materialRepository.getDownloadCompleteMaterial(materialTitles).compose(setThread()).subscribe(new Observer<List<MaterialTitle>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<MaterialTitle> materialTitles) {
                localMaterialLiveData.setValue(materialTitles);

            }

            @Override
            public void onError(Throwable e) {
                errData.setValue(e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        });

    }
}
