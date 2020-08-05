package com.jianpei.jpeducation.activitys;


import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
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
import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.TryListenerListAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.TryListenerListModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TryListenerListActivity extends BaseNoStatusActivity {


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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private TryListenerListAdapter tryListenerListAdapter;
    private List<GroupInfoBean> mGroupInfoBeans;


    private TryListenerListModel tryListenerListModel;


    private String cat_id;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_try_listener_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("免费试听");
        setTitleViewPadding(ivStatue);
        initShare();

        cat_id = SpUtils.getValue(SpUtils.catId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                showLoading("");
                tryListenerListModel.groupData("try", cat_id);
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
            }
        });

    }

    @Override
    protected void initData() {
        tryListenerListModel = new ViewModelProvider(this).get(TryListenerListModel.class);
        mGroupInfoBeans = new ArrayList<>();
        tryListenerListAdapter = new TryListenerListAdapter(mGroupInfoBeans, this);
        tryListenerListAdapter.setMyItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                startActivity(new Intent(TryListenerListActivity.this, ClassInfoActivity.class).putExtra("groupInfoBean", mGroupInfoBeans.get(position)));

            }

            @Override
            public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

            }
        });
        recyclerView.setAdapter(tryListenerListAdapter);


        tryListenerListModel.getGroupInfoBeansLiveData().observe(this, new Observer<List<GroupInfoBean>>() {
            @Override
            public void onChanged(List<GroupInfoBean> groupInfoBeans) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                mGroupInfoBeans.clear();
                ;
                mGroupInfoBeans.addAll(groupInfoBeans);
                tryListenerListAdapter.notifyDataSetChanged();
            }
        });
        tryListenerListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                shortToast(o);
            }
        });
        showLoading("");
        tryListenerListModel.groupData("try", cat_id);

    }


    @OnClick({R.id.iv_back, R.id.imageButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.imageButton:
                if (mShareAction != null) {
                    mShareAction.open();
                }
                break;
        }
    }
}
