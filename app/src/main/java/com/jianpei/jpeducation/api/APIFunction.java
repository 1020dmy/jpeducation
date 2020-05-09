package com.jianpei.jpeducation.api;


import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.config.UrlConfig;
import com.jianpei.jpeducation.bean.LoginBean;
import com.jianpei.jpeducation.bean.LoginJson;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface APIFunction {


    /**
     * 登陆接口
     * @param loginJson
     * @return
     */
    @POST(UrlConfig.LOGIN)
    Observable<BaseEntity<LoginBean>> login(@Body LoginJson loginJson);


}
