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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
    public Observable<BaseEntity<MaterialDataBean>> myMaterialData(int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().myMaterialData(new MaterialDataJson(pageIndex, pageSize)).map(new Function<BaseEntity<MaterialDataBean>, BaseEntity<MaterialDataBean>>() {
            @Override
            public BaseEntity<MaterialDataBean> apply(BaseEntity<MaterialDataBean> materialDataBeanBaseEntity) throws Exception {
                if (materialDataBeanBaseEntity.getData() != null) {
                    List<MaterialTitle> materialTitles = materialDataBeanBaseEntity.getData().getData();
                    if (materialTitles != null && materialTitles.size() > 0) {
                        MyRoomDatabase.getInstance().materialTitleDao().insertMaterialTitle(materialTitles);
                    }
                }
                return materialDataBeanBaseEntity;
            }
        });
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
                            materialInfoBean.setStatus(materialInfoBean1.getStatus());
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
                        MaterialInfoBean materialInfoBean1 = MyRoomDatabase.getInstance().materialInfoDao().getMaterialInfoBean(materialInfoBean.getId());
                        if (materialInfoBean1 != null) {
//                            infoBeanIterator.remove();
                            materialInfoBean.setStatus(materialInfoBean1.getStatus());
                            materialInfoBean.setPath(materialInfoBean1.getPath());


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

    /**
     * 根据materialid数据库查询
     *
     * @param materialid
     * @return
     */
    @Override
    public Observable<MaterialInfoBean> getMaterialInfoBean(String materialid) {
        return Observable.create(new ObservableOnSubscribe<MaterialInfoBean>() {
            @Override
            public void subscribe(ObservableEmitter<MaterialInfoBean> emitter) throws Exception {
                MaterialInfoBean materialInfoBean = MyRoomDatabase.getInstance().materialInfoDao().getMaterialInfoBean(materialid);
                emitter.onNext(materialInfoBean);
            }
        });
    }

    /**
     * 根据cat_id查询
     *
     * @param class_id
     * @return
     */
    @Override
    public Observable<List<MaterialInfoBean>> getMaterialInfoBeans(String class_id) {
        return Observable.create(new ObservableOnSubscribe<List<MaterialInfoBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MaterialInfoBean>> emitter) throws Exception {
                List<MaterialInfoBean> materialInfoBeans = MyRoomDatabase.getInstance().materialInfoDao().getMaterialInfoBeans(class_id);
                L.e("=========subscribe:" + materialInfoBeans.size());
                emitter.onNext(materialInfoBeans);
                emitter.onComplete();
            }
        });
    }

    /**
     * 插入数据库
     *
     * @param materialInfoBean
     * @return
     */
    @Override
    public Observable<String> insertMaterialInfo(MaterialInfoBean materialInfoBean) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                MyRoomDatabase.getInstance().materialInfoDao().insertMaterialInfo(materialInfoBean);
                emitter.onComplete();
            }
        });
    }

    /**
     * 删除
     *
     * @param materialInfoBean
     * @return
     */
    @Override
    public Observable<String> delete(MaterialInfoBean materialInfoBean) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                MyRoomDatabase.getInstance().materialInfoDao().delete(materialInfoBean);
                emitter.onComplete();
            }
        });
    }

    /**
     * 获取数据库中的资料标题
     *
     * @return
     */
    @Override
    public Observable<List<MaterialTitle>> getMaterialTitles() {
        return Observable.create(new ObservableOnSubscribe<List<MaterialTitle>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MaterialTitle>> emitter) throws Exception {
                List<MaterialTitle> materialTitles = MyRoomDatabase.getInstance().materialTitleDao().getAllMaterialTitles();
                emitter.onNext(materialTitles);
                emitter.onComplete();
            }
        });
    }
}
