package com.jianpei.jpeducation.activitys.school;


import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.school.TopicListAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.school.TopicBean;
import com.jianpei.jpeducation.bean.school.TopicDataBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.TopicListModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TopicListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private TopicListAdapter topicListAdapter;

    private List<TopicBean> topicBeans;

    private int page = 1, pageSize = 10;

    private TopicListModel topicListModel;

    private ArrayList<TopicBean> selectTopicBean;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_topic_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("选择话题");
        tvRight.setText("确定");
        tvRight.setVisibility(View.VISIBLE);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                showLoading("");
                topicListModel.topicData(page, pageSize);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                showLoading("");
                topicListModel.topicData(page, pageSize);
            }
        });


    }

    @Override
    protected void initData() {
        topicBeans = new ArrayList<>();
        selectTopicBean = new ArrayList<>();
        topicListAdapter = new TopicListAdapter(topicBeans);
        topicListAdapter.setMyCheckBoxClickListener(new TopicListAdapter.MyCheckBoxClickListener() {
            @Override
            public void onCheckClick(int position, CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {
                    if (selectTopicBean.size() == 5) {
                        buttonView.setChecked(false);
                        shortToast("最多可选择5个话题");
                        return;
                    }
                    selectTopicBean.add(topicBeans.get(position));
                } else {
                    selectTopicBean.remove(topicBeans.get(position));
                }
                topicBeans.get(position).setSelect(isChecked);
                L.e("size:" + selectTopicBean.size());
            }
        });
        recyclerView.setAdapter(topicListAdapter);

        topicListModel = new ViewModelProvider(this).get(TopicListModel.class);
        topicListModel.getTopicDataBeanLiveData().observe(this, new Observer<TopicDataBean>() {
            @Override
            public void onChanged(TopicDataBean topicDataBean) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                if (page == 1) {
                    topicBeans.clear();
                    topicBeans.add(new TopicBean("", "近期话题", 1));
                    topicBeans.addAll(topicDataBean.getRecently_list());
                    topicBeans.add(new TopicBean("", "热门话题", 1));
                    topicBeans.addAll(topicDataBean.getBase_list());
                } else {
                    topicBeans.addAll(topicDataBean.getBase_list());

                }

                topicListAdapter.notifyDataSetChanged();

            }
        });
        topicListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                shortToast(o);
            }
        });

        showLoading("");
        topicListModel.topicData(page, pageSize);

    }


    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                if (selectTopicBean.size() != 0) {
                    setResult(102, getIntent().putParcelableArrayListExtra("selectTopicBean", selectTopicBean));
                }
                finish();
                break;
        }
    }

}
