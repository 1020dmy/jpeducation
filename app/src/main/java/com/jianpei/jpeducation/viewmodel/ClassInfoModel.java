package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.classinfo.ImputedPriceBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.contract.ClassInfoContract;
import com.jianpei.jpeducation.repository.ClassInfoRepository;
import com.jianpei.jpeducation.utils.L;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassInfoModel extends BaseViewModel implements ClassInfoContract.Model {

    //tabview状态
    private MutableLiveData<Integer> tabViewStatus;
    //
    private MutableLiveData<GroupInfoBean> groupInfoBeanMutableLiveData;
    //价格回传
//    private MutableLiveData<String[]> pricesLiveData;

    private ClassInfoRepository classInfoRepository;

    //科目列表
    private MutableLiveData<List<GroupClassBean>> groupClassBeansLiveData;

    //团购参数
    private MutableLiveData<RegimentInfoBean> regimentInfoBeanMutableLiveData;

    //选择参团数据回传
    private MutableLiveData<String> joinGroupInfoLiveData;


    public MutableLiveData<String> getJoinGroupInfoLiveData() {
        if (joinGroupInfoLiveData == null)
            joinGroupInfoLiveData = new MutableLiveData<>();
        return joinGroupInfoLiveData;
    }


    private MutableLiveData<ClassInfoBean> classInfoBeanLiveData;

    public MutableLiveData<ClassInfoBean> getClassInfoBeanLiveData() {
        if (classInfoBeanLiveData == null)
            classInfoBeanLiveData = new MutableLiveData<>();
        return classInfoBeanLiveData;
    }


    //购买课程下单/计算价格结果（生成订单）

    private MutableLiveData<MIneOrderInfoBean> classGenerateOrderBeanLiveData;


    public MutableLiveData<MIneOrderInfoBean> getClassGenerateOrderBeanLiveData() {

        if (classGenerateOrderBeanLiveData == null) {
            classGenerateOrderBeanLiveData = new MutableLiveData<>();
        }
        return classGenerateOrderBeanLiveData;
    }

    public MutableLiveData<List<GroupClassBean>> getGroupClassBeansLiveData() {
        if (groupClassBeansLiveData == null)
            groupClassBeansLiveData = new MutableLiveData<>();
        return groupClassBeansLiveData;
    }

    //计算价格
    private MutableLiveData<ImputedPriceBean> imputedPriceBeanLiveData;


    public MutableLiveData<ImputedPriceBean> getImputedPriceBeanLiveData() {
        if (imputedPriceBeanLiveData == null)
            imputedPriceBeanLiveData = new MutableLiveData<>();
        return imputedPriceBeanLiveData;
    }

    public ClassInfoModel() {
        classInfoRepository = new ClassInfoRepository();
    }

//    public MutableLiveData<String[]> getPrices() {
//        if (pricesLiveData == null) {
//            pricesLiveData = new MutableLiveData<>();
//        }
//        return pricesLiveData;
//    }

//    public void setPrices(String[] prices) {
//        pricesLiveData.setValue(prices);
//    }
    ////

    public MutableLiveData<GroupInfoBean> getGroupInfoBeanMutableLiveData() {
        if (groupInfoBeanMutableLiveData == null) {
            groupInfoBeanMutableLiveData = new MutableLiveData<>();
        }
        return groupInfoBeanMutableLiveData;
    }


    public void setGroupInfo(GroupInfoBean groupInfoBean) {
        if (groupInfoBeanMutableLiveData == null) {
            groupInfoBeanMutableLiveData = new MutableLiveData<>();
        }
        groupInfoBeanMutableLiveData.setValue(groupInfoBean);

    }

    ///
    public MutableLiveData<Integer> getTabViewStatus() {
        if (tabViewStatus == null) {
            tabViewStatus = new MutableLiveData<>();
        }
        return tabViewStatus;
    }

    public void tabViewStatusChange(int isShow) {
        tabViewStatus.setValue(isShow);
    }
    ////


    public MutableLiveData<RegimentInfoBean> getRegimentInfoBeanMutableLiveData() {
        if (regimentInfoBeanMutableLiveData == null) {
            regimentInfoBeanMutableLiveData = new MutableLiveData<>();
        }
        return regimentInfoBeanMutableLiveData;
    }

    public void setRegimentInfoBeanMutableLiveData(RegimentInfoBean regimentInfoBean) {
        if (regimentInfoBeanMutableLiveData == null) {
            regimentInfoBeanMutableLiveData = new MutableLiveData<>();
        }
        regimentInfoBeanMutableLiveData.setValue(regimentInfoBean);
    }

    /**
     * 获取科目
     *
     * @param groupId
     * @param regimentId
     */
    @Override
    public void groupClass(String groupId, String regimentId) {
        if (TextUtils.isEmpty(groupId)) {
            return;
        }

        classInfoRepository.groupClass(groupId, regimentId).compose(setThread()).subscribe(new BaseObserver<List<GroupClassBean>>() {

            @Override
            protected void onSuccees(BaseEntity<List<GroupClassBean>> t) throws Exception {
                if (t.isSuccess()) {
                    groupClassBeansLiveData.setValue(t.getData());
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


    /**
     * 1-购买课程下单/计算价格
     *
     * @param goods_type
     * @param group_id
     * @param coupon_id
     * @param order_id
     * @param class_ids
     * @param suites_ids
     * @param regiment_id
     * @param gather_id
     */
    @Override
    public void classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, List<String> class_ids, List<String> suites_ids, String regiment_id, String gather_id) {
        if (TextUtils.isEmpty(goods_type)) {
            return;
        }
        if (TextUtils.isEmpty(group_id)) {
            return;
        }

        if (class_ids.size() == 0 && suites_ids.size() == 0) {
            errData.setValue("请选择要购买的课程");
            return;
        }

        String classIds = "";
        String suitesIds = "";

        StringBuilder stringBuilder = new StringBuilder();
        if (class_ids.size() != 0) {
            for (String classID : class_ids) {
                stringBuilder.append(classID);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            classIds = stringBuilder.toString();
        }
        if (suites_ids.size() != 0) {
            stringBuilder.delete(0, stringBuilder.length());
            for (String suiteId : suites_ids) {
                stringBuilder.append(suiteId);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            suitesIds = stringBuilder.toString();

        }
        stringBuilder.delete(0, stringBuilder.length() - 1);
        stringBuilder = null;

//        L.e("classIds:" + classIds + ",suitesIds:" + suitesIds);


        classInfoRepository.classGenerateOrder(goods_type, group_id, coupon_id, order_id, classIds, suitesIds, regiment_id, gather_id).compose(setThread()).subscribe(new BaseObserver<MIneOrderInfoBean>() {

            @Override
            protected void onSuccees(BaseEntity<MIneOrderInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    classGenerateOrderBeanLiveData.setValue(t.getData());
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

    @Override
    public void imputedPrice(String group_id, List<String> class_ids, List<String> suites_ids, String regiment_id) {
        if (class_ids.size() == 0 && suites_ids.size() == 0) {
            errData.setValue("请选择至少一门课程");
            return;
        }

        String classIds = "";
        String suitesIds = "";


        StringBuilder stringBuilder = new StringBuilder();
        if (class_ids.size() != 0) {
            for (String classID : class_ids) {
                stringBuilder.append(classID);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            classIds = stringBuilder.toString();
        }
        if (suites_ids.size() != 0) {
            stringBuilder.delete(0, stringBuilder.length());
            for (String suiteId : suites_ids) {
                stringBuilder.append(suiteId);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            suitesIds = stringBuilder.toString();

        }
        stringBuilder.reverse();
        stringBuilder = null;

        L.e("classIds:" + classIds + ",suitesIds:" + suitesIds);

        classInfoRepository.imputedPrice(group_id, classIds, suitesIds, regiment_id).compose(setThread()).subscribe(new BaseObserver<ImputedPriceBean>() {

            @Override
            protected void onSuccees(BaseEntity<ImputedPriceBean> t) throws Exception {
                if (t.isSuccess()) {
                    imputedPriceBeanLiveData.setValue(t.getData());
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

    /**
     * 加入购物车
     *
     * @param group_id
     * @param class_ids
     * @param suites_ids
     */

    @Override
    public void insertCar(String group_id, List<String> class_ids, List<String> suites_ids) {
        if (class_ids.size() == 0 && suites_ids.size() == 0) {
            errData.setValue("请选择至少一门课程");
            return;
        }
        String classIds = "";
        String suitesIds = "";
        StringBuilder stringBuilder = new StringBuilder();
        if (class_ids.size() != 0) {
            for (String classID : class_ids) {
                stringBuilder.append(classID);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            classIds = stringBuilder.toString();
        }
        if (suites_ids.size() != 0) {
            stringBuilder.delete(0, stringBuilder.length());
            for (String suiteId : suites_ids) {
                stringBuilder.append(suiteId);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            suitesIds = stringBuilder.toString();

        }
        stringBuilder.reverse();
        stringBuilder = null;

        classInfoRepository.insertCar(group_id, classIds, suitesIds).compose(setThread()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {

                errData.setValue(t.getMsg());

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
