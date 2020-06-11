package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.bean.MaterialDataBean;
import com.jianpei.jpeducation.bean.MaterialDataJson;
import com.jianpei.jpeducation.bean.SubMaterialDataJson;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;
import com.jianpei.jpeducation.contract.MaterialListContract;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.L;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialListRepository extends BaseRepository implements MaterialListContract.Repository {

    @Override
    public Observable<BaseEntity<MaterialDataBean>> materialData(String cat_id, int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().materialData(new MaterialDataJson(cat_id, pageIndex, pageSize));
    }

    @Override
    public Observable<BaseEntity<ArrayList<MaterialInfoBean>>> subMaterialData(String cat_id, String class_id) {

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

//        return RetrofitFactory.getInstance().API().subMaterialData(new SubMaterialDataJson(cat_id, class_id));
    }
}
