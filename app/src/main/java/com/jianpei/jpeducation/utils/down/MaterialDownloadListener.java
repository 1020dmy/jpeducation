package com.jianpei.jpeducation.utils.down;

import com.jianpei.jpeducation.bean.material.MaterialInfoBean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MaterialDownloadListener {
    void taskStart(MaterialInfoBean materialInfoBean);

    void repeat(MaterialInfoBean materialInfoBean);

    void retry(MaterialInfoBean materialInfoBean);

    void connected(MaterialInfoBean materialInfoBean);

    void progress(MaterialInfoBean materialInfoBean,long currentOffset, long totalLength);

//    void taskEnd(MaterialInfoBean materialInfoBean);

    void onError(String errMsg);

    void onSuccess(MaterialInfoBean materialInfoBean);


}
