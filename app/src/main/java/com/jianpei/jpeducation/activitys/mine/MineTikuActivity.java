package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.tiku.WrongQuestionListActivity;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MineTikuActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_cuoti)
    LinearLayout llCuoti;
    @BindView(R.id.ll_jilu)
    LinearLayout llJilu;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.ll_answer)
    LinearLayout llAnswer;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_mine_tiku;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的题库");

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.ll_cuoti, R.id.ll_jilu, R.id.ll_collect, R.id.ll_answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_cuoti:
                startActivity(new Intent(this, WrongQuestionListActivity.class).putExtra("type", 2));
                break;
            case R.id.ll_jilu:
                startActivity(new Intent(this, WrongQuestionListActivity.class).putExtra("type", 1));
                break;
            case R.id.ll_collect:
                startActivity(new Intent(this, WrongQuestionListActivity.class).putExtra("type", 4));
                break;
            case R.id.ll_answer:
                break;
        }
    }
}
