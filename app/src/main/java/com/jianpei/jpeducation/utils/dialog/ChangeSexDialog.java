package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.pop.CommentPopup;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ChangeSexDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;
    private View contentView;
    private LinearLayout llBoy, llGirl;
    private ImageView ivBoy, ivGirl;

    private MyOnItemClickListener myOnItemClickListener;

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public ChangeSexDialog(@NonNull Context context, String sexType) {
        super(context);
        contentView = getLayoutInflater().inflate(R.layout.dialog_userinf_changesex, null);//获取自定义布局
        setView(contentView);
        llBoy = contentView.findViewById(R.id.ll_boy);
        llGirl = contentView.findViewById(R.id.ll_girl);
        ivBoy = contentView.findViewById(R.id.iv_boy);
        ivGirl = contentView.findViewById(R.id.iv_girl);

        llBoy.setOnClickListener(this);
        llGirl.setOnClickListener(this);

        setSex(sexType);
    }

    public void setSex(String sexType) {
        if ("1".equals(sexType)) {
            llBoy.setEnabled(false);
            llGirl.setEnabled(true);
            ivBoy.setEnabled(false);
            ivGirl.setEnabled(true);
        } else {
            llBoy.setEnabled(true);
            llGirl.setEnabled(false);
            ivBoy.setEnabled(true);
            ivGirl.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_boy:
                llBoy.setEnabled(false);
                llGirl.setEnabled(true);
                ivBoy.setEnabled(false);
                ivGirl.setEnabled(true);
                if (myOnItemClickListener != null) {
                    myOnItemClickListener.onChangeSex("1");
                }
                break;
            case R.id.ll_girl:
                llBoy.setEnabled(true);
                llGirl.setEnabled(false);
                ivBoy.setEnabled(true);
                ivGirl.setEnabled(false);
                if (myOnItemClickListener != null) {
                    myOnItemClickListener.onChangeSex("2");
                }
                break;
        }
        dismiss();
    }

    public interface MyOnItemClickListener {
        void onChangeSex(String sexType);
    }
}
