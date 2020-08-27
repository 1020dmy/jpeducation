package com.jianpei.jpeducation.fragment.tiku;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.tiku.RecordListAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordListFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private RecordListAdapter recordListAdapter;
    private int type;
    private String classId;

    public RecordListFragment(int type, String classId) {
        this.type = type;
        this.classId = classId;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_record_list;
    }

    @Override
    protected void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {


            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });


    }

    @Override
    protected void initData(Context mContext) {

        recordListAdapter = new RecordListAdapter();
        recyclerView.setAdapter(recordListAdapter);

    }
}
