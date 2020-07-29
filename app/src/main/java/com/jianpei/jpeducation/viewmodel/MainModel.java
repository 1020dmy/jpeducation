package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.base.BaseViewModel;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MainModel extends BaseViewModel<String> {


    private MutableLiveData<String> liveDataCatId;//catid更新


    private MutableLiveData<Integer> changeBottomLiveData;

    public MutableLiveData<Integer> getChangeBottomLiveData() {
        if (changeBottomLiveData == null)
            changeBottomLiveData = new MutableLiveData<>();
        return changeBottomLiveData;
    }

    //    private MutableLiveData<String> customerServiceUrlLiveData;//客服地址

//    public MutableLiveData<String> getCustomerServiceUrlLiveData() {
//        if (customerServiceUrlLiveData == null) {
//            customerServiceUrlLiveData = new MutableLiveData<>();
//        }
//        return customerServiceUrlLiveData;
//    }
//
//    public void setCustomerServiceUrlLiveData(String customerServiceUrl) {
//
//        if (customerServiceUrlLiveData == null) {
//            customerServiceUrlLiveData = new MutableLiveData<>();
//        }
//
//        customerServiceUrlLiveData.setValue(customerServiceUrl);
//    }

    //    private MainRepository mainRepository;

//    public MainModel() {
//        mainRepository = new MainRepository();
//    }

    public MutableLiveData<String> getCatId() {
        if (liveDataCatId == null) {
            liveDataCatId = new MutableLiveData<String>();
        }
        return liveDataCatId;
    }

    public void upData(String value) {
        if (liveDataCatId == null) {
            liveDataCatId = new MutableLiveData<String>();
        }
        liveDataCatId.setValue(value);

    }

//    @Override
//    public void getHomeData(String catId) {
//        if (TextUtils.isEmpty(catId)) {
//            return;
//        }
//        mainRepository.getHomeData(catId).compose(setThread()).subscribe(new BaseObserver<HomeDataBean>() {
//
//            @Override
//            protected void onSuccees(BaseEntity<HomeDataBean> t) throws Exception {
//                if (t.isSuccess()) {
//                    successData.setValue(t.getData());
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
//
//    }
}
