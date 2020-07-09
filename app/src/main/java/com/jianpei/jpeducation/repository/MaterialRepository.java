package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.DownloadJson;
import com.jianpei.jpeducation.bean.SubMaterialDataJson;
import com.jianpei.jpeducation.bean.json.MaterialDataJson;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;
import com.jianpei.jpeducation.contract.MaterialContract;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.L;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialRepository implements MaterialContract.Repository {


    /**
     * 获取下载地址
     *
     * @param fileId
     * @return
     */

    @Override
    public Observable<BaseEntity<DownloadBean>> getDownloadUrl(String fileId) {
        return RetrofitFactory.getInstance().API().getDownloadUrl(new DownloadJson(fileId));
    }

    /**
     * 我的资料
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public Observable<BaseEntity<com.jianpei.jpeducation.bean.material.MaterialDataBean>> myMaterialData(int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().myMaterialData(new MaterialDataJson(pageIndex, pageSize));
    }

    /**
     * 更多资料一级列表
     *
     * @param cat_id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public Observable<BaseEntity<MaterialDataBean>> materialData(String cat_id, int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().materialData(new MaterialDataJson(cat_id, pageIndex, pageSize));
    }

    /**
     * 更多资料二级列表
     *
     * @param cat_id
     * @param class_id
     * @return
     */

    @Override
    public Observable<BaseEntity<ArrayList<MaterialInfoBean>>> subMaterialData(String cat_id, String class_id, int type) {

        if (type == 0) {
            return RetrofitFactory.getInstance().API().subMaterialData(new SubMaterialDataJson(cat_id, class_id)).map(new Function<BaseEntity<ArrayList<MaterialInfoBean>>, BaseEntity<ArrayList<MaterialInfoBean>>>() {
                @Override
                public BaseEntity<ArrayList<MaterialInfoBean>> apply(BaseEntity<ArrayList<MaterialInfoBean>> arrayListBaseEntity) throws Exception {
                    for (MaterialInfoBean materialInfoBean : arrayListBaseEntity.getData()) {
                        MaterialInfoBean materialInfoBean1 = MyRoomDatabase.getInstance().materialInfoDao().getMaterialInfoBean(materialInfoBean.getId());
                        if (materialInfoBean1 != null) {
                            materialInfoBean.setStatus("2");
                        }
                    }
                    return arrayListBaseEntity;
                }
            });
        } else {
            return RetrofitFactory.getInstance().API().subMaterialData(new SubMaterialDataJson(cat_id, class_id)).map(new Function<BaseEntity<ArrayList<MaterialInfoBean>>, BaseEntity<ArrayList<MaterialInfoBean>>>() {
                @Override
                public BaseEntity<ArrayList<MaterialInfoBean>> apply(BaseEntity<ArrayList<MaterialInfoBean>> arrayListBaseEntity) throws Exception {

                    Iterator<MaterialInfoBean> infoBeanIterator = arrayListBaseEntity.getData().iterator();
                    while (infoBeanIterator.hasNext()) {
                        MaterialInfoBean materialInfoBean = infoBeanIterator.next();
                        if (MyRoomDatabase.getInstance().materialInfoDao().getMaterialInfoBean(materialInfoBean.getId()) != null) {
                            infoBeanIterator.remove();
                        }
                    }
                    return arrayListBaseEntity;
                }
            });
        }
    }

    /**
     * 获取已经下载的资料
     *
     * @param materialTitles
     * @return
     */

    @Override
    public Observable<List<MaterialTitle>> getDownloadCompleteMaterial(List<MaterialTitle> materialTitles) {
        return new Observable<List<MaterialTitle>>() {
            @Override
            protected void subscribeActual(Observer<? super List<MaterialTitle>> observer) {
                //查询数据库判断是否已经下载完成
                Iterator<MaterialTitle> iterator = materialTitles.iterator();
                while (iterator.hasNext()) {
                    MaterialTitle materialTitle = iterator.next();
                    List<MaterialInfoBean> materialInfoBeans = MyRoomDatabase.getInstance().materialInfoDao().getMaterialInfoBeans(materialTitle.getId());
                    if (materialInfoBeans == null || materialInfoBeans.size() == 0) {//移除
                        iterator.remove();
                    } else {
                        materialTitle.getChildNode().addAll(materialInfoBeans);
                    }
                }
                observer.onNext(materialTitles);
            }
        };
    }
}
