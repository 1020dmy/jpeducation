package com.jianpei.jpeducation.utils.listener;

import com.jianpei.jpeducation.adapter.home.MaterialInfoItemBinder;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MaterialInfoItemOnClickListener {

    void OnItemClick(MaterialInfoItemBinder.MyHolder myHolder, MaterialInfoBean materialInfoBean);
}
