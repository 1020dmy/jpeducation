package com.jianpei.jpeducation.view.ninegridelayout;

import android.content.Context;
import android.widget.ImageView;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface ItemImageLongClickListener<T> {
    boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<T> list);
}
