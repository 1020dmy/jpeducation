package com.jianpei.jpeducation.adapter.mclass;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DownloadClassAdapter  extends BaseNodeAdapter {


    public DownloadClassAdapter(MyItemOnClickListener myItemOnClickListener) {
        super();
        addNodeProvider(new DownloadClassChapterProvider());
        addNodeProvider(new DownloadClassJieProvider(myItemOnClickListener));
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode baseNode=list.get(i);

        if(baseNode instanceof DirectoryBean){
            return 0;
        }else if(baseNode instanceof ViodBean){
            return 1;
        }
        return -1;
    }
}
