package com.jianpei.jpeducation.activitys.mine.mclass;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassDownloadActivity extends BaseNoStatusActivity {


    @BindView(R.id.iv_statue)
    ImageView ivStatue;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_download;
    }

    @Override
    protected void initView() {
        tvTitle.setText("下载");
        imageButton.setVisibility(View.GONE);
        setTitleViewPadding(ivStatue);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {


    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
