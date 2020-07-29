package com.jianpei.jpeducation.activitys.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.mine.MineDynamicAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.school.MThreadBean;
import com.jianpei.jpeducation.bean.school.MThreadDataBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.viewmodel.SchoolModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineDynamicActivity extends BaseNoStatusActivity {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_fensi)
    TextView tvFensi;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private SchoolModel schoolModel;

    private List<ThreadDataBean> threadDataBeans;

    private MineDynamicAdapter dynamicAdapter;

    private int page = 1, pageSize = 10;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_mine_dynamic;
    }

    @Override
    protected void initView() {
        tvTitle.setText("个人主页");
        tvTitle.setTextColor(getResources().getColor(R.color.transparents));
        setTitleViewPadding(tvStatus);
        tvStatus.setVisibility(View.VISIBLE);
        llTitle.setBackgroundColor(getResources().getColor(R.color.transparents));
        ivBack.setImageResource(R.drawable.ic_back);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        schoolModel = new ViewModelProvider(this).get(SchoolModel.class);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page = 1;
                schoolModel.mThreadData(page, pageSize);


            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page++;
                schoolModel.mThreadData(page, pageSize);
            }
        });
    }

    @Override
    protected void initData() {
        threadDataBeans = new ArrayList<>();
        dynamicAdapter = new MineDynamicAdapter(threadDataBeans, this);
        recyclerView.setAdapter(dynamicAdapter);

        schoolModel.getmThreadDataBeanLiveData().observe(this, new Observer<MThreadDataBean>() {
            @Override
            public void onChanged(MThreadDataBean mThreadDataBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (page == 1) {
                    threadDataBeans.clear();
                    MThreadBean mThreadBean = mThreadDataBean.getData();
                    tvFensi.setText(mThreadBean.getAttention().getAtten_count() + " 粉丝");
                    tvGuanzhu.setText(mThreadBean.getAttention().getU_count() + " 关注");
                }
                threadDataBeans.addAll(mThreadDataBean.getData().getData());
                dynamicAdapter.notifyDataSetChanged();

            }
        });
        schoolModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);

            }
        });


        showLoading("");
        schoolModel.mThreadData(page, pageSize);

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
