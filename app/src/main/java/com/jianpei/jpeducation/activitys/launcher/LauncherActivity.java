package com.jianpei.jpeducation.activitys.launcher;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LauncherActivity extends BaseModelActivity<LauncherModel, LauncherBean> {

    private int isfirst;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private String catId;

    private CountDownTimer countDownTimer;

    private LauncherBean launcherBean;

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
        tvTime.setVisibility(View.VISIBLE);
        start();
    }

    @Override
    protected void onSuccess(LauncherBean data) {
        launcherBean = data;
        tvTime.setVisibility(View.VISIBLE);
        Glide.with(LauncherActivity.this).load(data.getStartingInfo().getImage_path()).into(imageView);
        start();
    }

    public void start() {
        int countdown = launcherBean.getStartingInfo().getCountdown();
        if (countdown <= 0)
            countdown = 3;
        countDownTimer = new CountDownTimer(countdown * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                judgeActiviyt();
            }
        }.start();
    }

    @Override
    protected void onStop() {
        if (countDownTimer != null)
            countDownTimer.cancel();
        countDownTimer = null;
        super.onStop();
    }


    @OnClick(R.id.tv_time)
    public void onViewClicked() {
        countDownTimer.cancel();
        judgeActiviyt();

    }

    protected void judgeActiviyt() {
        if (isfirst == 0) {
            startActivity(new Intent(LauncherActivity.this, GuideActivity.class).putStringArrayListExtra("images", launcherBean.getGuideList()));
            finish();
        } else if (TextUtils.isEmpty(catId)) {
            startActivity(new Intent(LauncherActivity.this, SelectDisciplineActivity.class));
            finish();
        } else {
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
            finish();
        }

    }
}
