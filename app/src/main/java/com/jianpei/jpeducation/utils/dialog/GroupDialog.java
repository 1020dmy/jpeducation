package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;

import java.util.List;

public class GroupDialog extends AlertDialog {
    private List<RegimentBean> regimentBeans;
    private View contentView;

    public GroupDialog(@NonNull Context context, List<RegimentBean> regimentBeans) {
        super(context);
        this.regimentBeans=regimentBeans;


        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_group,
                null, false);
        setContentView(contentView);


    }


}
