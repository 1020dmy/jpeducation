package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.NoticeDataBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.contract.HomePageContract;
import com.jianpei.jpeducation.repository.HomePageRepositiry;
import com.jianpei.jpeducation.utils.L;

import java.util.ArrayList;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class HomePageModel extends BaseViewModel<HomeDataBean> implements HomePageContract.Model {
    private HomePageRepositiry homePageRepositiry;

    private MutableLiveData<ArrayList<NoticeDataBean>> noticeDatas;

//    private MutableLiveData<DownloadBean> downloadBeanMutableLiveData;


    public HomePageModel() {
        homePageRepositiry = new HomePageRepositiry();
    }


    public MutableLiveData<ArrayList<NoticeDataBean>> getNoticeDatas() {
        if (noticeDatas == null) {
            noticeDatas = new MutableLiveData<>();

        }
        return noticeDatas;
    }

//    public MutableLiveData<DownloadBean> getDownloadBeanMutableLiveData() {
//        if (downloadBeanMutableLiveData == null) {
//            downloadBeanMutableLiveData = new MutableLiveData<>();
//
//        }
//        return downloadBeanMutableLiveData;
//    }

    @Override
    public void getHomeData(String catId) {

        if (TextUtils.isEmpty(catId)) {
            return;
        }
        homePageRepositiry.getHomeData(catId).compose(setThread()).subscribe(new BaseObserver<HomeDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<HomeDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    successData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }


            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                L.e("onFailure:"+e.getMessage());
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }

    @Override
    public void noticeData(String catId) {
        if (TextUtils.isEmpty(catId)) {
            return;
        }
        homePageRepositiry.noticeData(catId).compose(setThread()).subscribe(new BaseObserver<ArrayList<NoticeDataBean>>() {

            @Override
            protected void onSuccees(BaseEntity<ArrayList<NoticeDataBean>> t) throws Exception {
                if (t.isSuccess()) {
//                    successData.setValue(t.getData());
                    if (noticeDatas == null) {
                        noticeDatas = new MutableLiveData<>();
                    }
                    noticeDatas.setValue(t.getData());
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

//    @Override
//    public void getDownloadUrl(String fileId) {
//        if (TextUtils.isEmpty(fileId)) {
//            errData.setValue("文件ID不能为空");
//            return;
//        }
//
//        homePageRepositiry.getDownloadUrl(fileId).compose(setThread()).subscribe(new BaseObserver<DownloadBean>() {
//
//            @Override
//            protected void onSuccees(BaseEntity<DownloadBean> t) throws Exception {
//                if (t.isSuccess()) {
//                    if (downloadBeanMutableLiveData == null) {
//                        downloadBeanMutableLiveData = new MutableLiveData<>();
//                    }
//                    downloadBeanMutableLiveData.setValue(t.getData());
//                } else {
//                    errData.setValue(t.getMsg());
//                }
//
//
//            }
//
//            @Override
//            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                if (isNetWorkError) {
//                    errData.setValue("网络问题！");
//                } else {
//                    errData.setValue(e.getMessage());
//                }
//            }
//        });
//    }
}
