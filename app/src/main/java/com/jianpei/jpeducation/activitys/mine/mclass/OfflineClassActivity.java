package com.jianpei.jpeducation.activitys.mine.mclass;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfflineClassActivity extends BaseNoStatusActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_statue)
    ImageView ivStatue;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_offline_class;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(ivStatue);
        tvTitle.setText("离线课程");
        imageButton.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


}
