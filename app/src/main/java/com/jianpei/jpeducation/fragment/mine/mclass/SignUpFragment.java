package com.jianpei.jpeducation.fragment.mine.mclass;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.mclass.ClassPlayerActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mclass.MyClassAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.mclass.ClassDataBean;
import com.jianpei.jpeducation.bean.mclass.MyClassBean;
import com.jianpei.jpeducation.viewmodel.MyClassModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MyClassAdapter myClassAdapter;

    private MyClassModel myClassModel;

    private List<MyClassBean> list;
    private int type = 1;
    private int pageIndex = 1, pageSize = 10;


    @Override
    protected int initLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                showLoading("");
                myClassModel.classData(type, pageIndex, pageSize);

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                showLoading("");
                myClassModel.classData(type, pageIndex, pageSize);
            }
        });

    }

    @Override
    protected void initData(Context mContext) {
        myClassModel = new ViewModelProvider(this).get(MyClassModel.class);
        list = new ArrayList<>();
        myClassAdapter = new MyClassAdapter(getActivity(), list, type);
        myClassAdapter.setMyItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                startActivity(new Intent(getActivity(), ClassPlayerActivity.class).putExtra("myClassBean",list.get(position)));
            }

            @Override
            public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

            }
        });
        recyclerView.setAdapter(myClassAdapter);

        myClassModel.getClassDataBeanLiveData().observe(this, new Observer<ClassDataBean>() {
            @Override
            public void onChanged(ClassDataBean classDataBean) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                if (pageIndex == 1) {
                    list.clear();
                }
                list.addAll(classDataBean.getData());
                myClassAdapter.notifyDataSetChanged();

            }
        });
        myClassModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                shortToast(o);
            }
        });

        showLoading("");
        myClassModel.classData(type, pageIndex, pageSize);

    }
}
