package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;

import java.util.List;

import io.reactivex.Observable;

public interface ClassInfoContract {

    interface Repository {

        Observable<BaseEntity<List<GroupClassBean>>> groupClass(String groupId, String regimentId);

    }


    interface Model {
        void groupClass(String groupId, String regimentId);

    }
}
