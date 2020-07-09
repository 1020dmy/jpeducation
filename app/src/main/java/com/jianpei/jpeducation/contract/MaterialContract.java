package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MaterialContract {

    interface Repository {

        //1-资料下载url
        Observable<BaseEntity<DownloadBean>> getDownloadUrl(String fileId);

        //1-我的资料
        Observable<BaseEntity<MaterialDataBean>> myMaterialData(int pageIndex, int pageSize);

        //1-资料一级列表
        Observable<BaseEntity<MaterialDataBean>> materialData(String cat_id, int pageIndex, int pageSize);

        //1-资料二级列表
        Observable<BaseEntity<ArrayList<MaterialInfoBean>>> subMaterialData(String cat_id, String class_id, int type);

        //获取数据库中已经下载的资料

        Observable<List<MaterialTitle>> getDownloadCompleteMaterial(List<MaterialTitle> materialTitles);
    }


    interface Model {
        void getDownloadUrl(String fileId);

        void myMaterialData(int pageIndex, int pageSize);

        void materialData(String cat_id, int pageIndex, int pageSize);

        void subMaterialData(String cat_id, String class_id,int type);

        void getDownloadCompleteMaterial(List<MaterialTitle> materialTitles);
    }
}
