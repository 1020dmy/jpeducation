package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.json.GroupInfoJson;
import com.jianpei.jpeducation.contract.ClassInfoContract;

import java.util.List;

import io.reactivex.Observable;

public class ClassInfoRepository extends BaseRepository implements ClassInfoContract.Repository {

    @Override
    public Observable<BaseEntity<List<GroupClassBean>>> groupClass(String groupId, String regimentId) {
        return RetrofitFactory.getInstance().API().groupClass(new GroupInfoJson(groupId,regimentId));
    }
}
