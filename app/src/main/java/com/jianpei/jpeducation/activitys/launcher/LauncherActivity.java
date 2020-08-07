package com.jianpei.jpeducation.activitys.launcher;


import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
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

public class LauncherActivity extends BaseModelActivity<LauncherModel, LauncherBean> {

    private int isfirst;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private String catId;

    private CountDownTimer countDownTimer;

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

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTime.setText(millisUntilFinished / 1000 + "秒");

            }

            @Override
            public void onFinish() {
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
        }.start();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isfirst == 0) {
//                    startActivity(new Intent(LauncherActivity.this, GuideActivity.class).putStringArrayListExtra("images", images));
//                } else {
//                    if (TextUtils.isEmpty(catId)) {
//                        startActivity(new Intent(LauncherActivity.this, SelectDisciplineActivity.class));
//                    } else {
//                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
//                    }
//                }
//                finish();
//            }
//        }, 2000);
    }

    @Override
    protected void onStop() {
        if (countDownTimer != null)
            countDownTimer.cancel();
        countDownTimer = null;
        super.onStop();
    }
}
