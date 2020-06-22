package com.jianpei.jpeducation.adapter.classinfo;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.adapter.classinfo.directory.DirectoryChapterProvider;
import com.jianpei.jpeducation.adapter.classinfo.directory.DirectoryProfessionProvider;
import com.jianpei.jpeducation.adapter.classinfo.directory.DirectorySectionProvider;
import com.jianpei.jpeducation.bean.classinfo.DirectoryChapterBean;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DirectoryAdapter extends BaseNodeAdapter {


    public DirectoryAdapter() {
        super();
        addNodeProvider(new DirectoryProfessionProvider());
        addNodeProvider(new DirectoryChapterProvider());
        addNodeProvider(new DirectorySectionProvider());

    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {

        BaseNode baseNode = list.get(i);

        if (baseNode instanceof DirectoryProfessionBean) {
            return 0;
        } else if (baseNode instanceof DirectoryChapterBean) {
            return 1;
        } else if (baseNode instanceof DirectorySectionBean) {
            return 2;
        }
        return -1;
    }


}
