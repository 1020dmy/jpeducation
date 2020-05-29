package com.jianpei.jpeducation.activitys.launcher;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.activitys.SelectDisciplineActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.LauncherBean;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.LauncherModel;

import java.util.ArrayList;

import butterknife.BindView;

public class LauncherActivity extends BaseActivity {

    private LauncherModel launcherModel;
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
        launcherModel = ViewModelProviders.of(this).get(LauncherModel.class);
        launcherModel.getScuucessData().observe(this, new Observer<LauncherBean>() {
            @Override
            public void onChanged(LauncherBean launcherBean) {
                Glide.with(LauncherActivity.this).load(launcherBean.getStartingInfo()).into(imageView);
                start(launcherBean.getGuideList());
            }
        });
        launcherModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                shortToast(s);
                start(null);
            }
        });

    }

    @Override
    protected void initData() {
        launcherModel.appSet();

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
