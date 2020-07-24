package com.jianpei.jpeducation.activitys.tiku;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.tiku.TodayExerciseAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.PaperDataBean;
import com.jianpei.jpeducation.bean.tiku.TestPaperBean;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.TodayExerciseListModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TodayExerciseListActivity extends BaseActivity implements MyItemOnClickListener {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private TodayExerciseAdapter todayExerciseAdapter;

    private TodayExerciseListModel todayExerciseListModel;

    private List<TestPaperBean> mTestPaperBeans;
    //
    private int page = 1, pageSize = 10;
    private String catId, paperType = "3";

    @Override
    protected int setLayoutView() {
        return R.layout.activity_today_exercise_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("每日一练");

        todayExerciseListModel = new ViewModelProvider(this).get(TodayExerciseListModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                shortToast("");
                page = 1;
                todayExerciseListModel.paperData(page, pageSize, catId, "", "", paperType);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                shortToast("");
                page++;
                todayExerciseListModel.paperData(page, pageSize, catId, "", "", paperType);
            }
        });

    }

    @Override
    protected void initData() {
        catId = SpUtils.getValue(SpUtils.catId);
        mTestPaperBeans = new ArrayList<>();
        todayExerciseAdapter = new TodayExerciseAdapter(mTestPaperBeans);
        todayExerciseAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(todayExerciseAdapter);
        //试卷列表
        todayExerciseListModel.getTestPaperBeanLiveData().observe(this, new Observer<PaperDataBean>() {
            @Override
            public void onChanged(PaperDataBean paperDataBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (page == 1) {
                    mTestPaperBeans.clear();
                }
                mTestPaperBeans.addAll(paperDataBean.getData());
                todayExerciseAdapter.notifyDataSetChanged();
            }
        });
        todayExerciseListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);
            }
        });
        shortToast("");
        todayExerciseListModel.paperData(page, pageSize, catId, "", "", paperType);

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(int position, View view) {
//        startActivity(new Intent(this, TodayAnswerActivity.class).putExtra("testPaperBean",mTestPaperBeans.get(position)).putExtra("source","1"));

        startActivity(new Intent(this, TodayAnswerActivity.class)
                .putExtra("paperId", mTestPaperBeans.get(position).getId())
                .putExtra("source", "1")
                .putExtra("recordId", mTestPaperBeans.get(position).getUser_record_id())
                .putExtra("restartType", mTestPaperBeans.get(position).getUser_is_complete()));

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
