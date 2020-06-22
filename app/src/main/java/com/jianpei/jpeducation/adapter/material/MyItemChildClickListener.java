package com.jianpei.jpeducation.adapter.material;

import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MyItemChildClickListener {
    void onClick(MaterialInfoBean materialInfoBean, MaterialInfoAdapter.MyHolder myHolder);
}
