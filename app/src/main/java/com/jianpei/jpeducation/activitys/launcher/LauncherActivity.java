package com.jianpei.jpeducation.activitys.launcher;


import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.activitys.SelectDisciplineActivity;
import com.jianpei.jpeducation.base.BaseModelActivity;
import com.jianpei.jpeducation.bean.LauncherBean;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.LauncherModel;

import java.util.ArrayList;

import butterknife.BindView;

public class LauncherActivity extends BaseModelActivity<LauncherModel, LauncherBean> {

    private int isfirst;
    @BindView(R.id.imageView)
    ImageView imageView;

    private String catId;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView() {
        isfirst = (int) SpUtils.get(SpUtils.ISFirst, 0);
        catId = SpUtils.getValue(SpUtils.catId);
        initViewModel();//初始化viewModel
    }

    @Override
    protected void initData() {
        mViewModel.appSet();
    }

    @Override
    protected void onError(String message) {
        shortToast(message);
        start(null);
    }

    @Override
    protected void onSuccess(LauncherBean data) {
        Glide.with(LauncherActivity.this).load(data.getStartingInfo()).into(imageView);
        start(data.getGuideList());
    }

    public void start(ArrayList<String> images) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isfirst == 0) {
                    startActivity(new Intent(LauncherActivity.this, GuideActivity.class).putStringArrayListExtra("images", images));
                } else {
                    if (TextUtils.isEmpty(catId)) {
                        startActivity(new Intent(LauncherActivity.this, SelectDisciplineActivity.class));
                    } else {
                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    }
                }
                finish();
            }
        }, 2000);
    }
}
