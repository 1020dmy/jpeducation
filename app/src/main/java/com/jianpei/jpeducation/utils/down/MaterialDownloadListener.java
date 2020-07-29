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

    void retry(MaterialInfoBean materialInfoBean);

    void connected(MaterialInfoBean materialInfoBean);

    void progress(MaterialInfoBean materialInfoBean);

    void taskEnd(MaterialInfoBean materialInfoBean);


}
