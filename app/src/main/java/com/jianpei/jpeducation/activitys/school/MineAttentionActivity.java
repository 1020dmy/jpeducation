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
import com.jianpei.jpeducation.adapter.school.MineAttentionAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.school.AttentionBean;
import com.jianpei.jpeducation.bean.school.AttentionDataBean;
import com.jianpei.jpeducation.viewmodel.MineAttentionModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MineAttentionActivity extends BaseActivity {


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

    private MineAttentionAdapter mineAttentionAdapter;
    private List<AttentionBean> mAttentionBeans;

    private ArrayList<AttentionBean> selectAttentionBeans;


    private MineAttentionModel mineAttentionModel;

    private int page = 1, pageSize = 10;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_mine_attention;
    }

    @Override
    protected void initView() {
        tvTitle.setText("选择用户");
        tvRight.setText("确定");
        tvRight.setVisibility(View.VISIBLE);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                showLoading("");
                mineAttentionModel.attentionData(page, pageSize);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                showLoading("");
                mineAttentionModel.attentionData(page, pageSize);
            }
        });

    }

    @Override
    protected void initData() {
        mAttentionBeans = new ArrayList<>();
        selectAttentionBeans=new ArrayList<>();
        mineAttentionAdapter = new MineAttentionAdapter(mAttentionBeans, this);
        mineAttentionAdapter.setMyCheckBoxClickListener(new MineAttentionAdapter.MyCheckBoxClickListener() {
            @Override
            public void onCheckClick(int position, CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    if (selectAttentionBeans.size() == 10) {
                        buttonView.setChecked(false);
                        shortToast("最多可@10个好友");
                        return;
                    }
                    selectAttentionBeans.add(mAttentionBeans.get(position));
                } else {
                    selectAttentionBeans.remove(mAttentionBeans.get(position));
                }
                mAttentionBeans.get(position).setSelect(isChecked);
            }
        });
        recyclerView.setAdapter(mineAttentionAdapter);

        mineAttentionModel = new ViewModelProvider(this).get(MineAttentionModel.class);
        mineAttentionModel.getAttentionDataBeansLiveData().observe(this, new Observer<AttentionDataBean>() {
            @Override
            public void onChanged(AttentionDataBean data) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (page == 1) {
                    mAttentionBeans.clear();
                }
                mAttentionBeans.addAll(data.getData());
                mineAttentionAdapter.notifyDataSetChanged();

            }
        });
        mineAttentionModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);
            }
        });
        showLoading("");
        mineAttentionModel.attentionData(page, pageSize);

    }


    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                if (selectAttentionBeans.size() != 0) {
                    setResult(103, getIntent().putParcelableArrayListExtra("selectAttentionBeans", selectAttentionBeans));
                }
                finish();
                break;
        }
    }
}
