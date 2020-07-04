package com.jianpei.jpeducation.fragment.mine.coupon;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.coupon.CouponAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.viewmodel.UserCouponModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CouponAvailableFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private UserCouponModel userCouponModel;


    private int page = 1;
    private int type = 1;
    private int pageSize = 10;

    private List<CouponDataBean.CouponData> couponDatas;


    private CouponAdapter couponAdapter;


    @Override
    protected int initLayout() {
        return R.layout.fragment_coupon_available;
    }

    @Override
    protected void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page++;
                userCouponModel.couponData(page, pageSize, type);

            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page=1;
                userCouponModel.couponData(page, pageSize, type);

            }
        });


    }

    @Override
    protected void initData(Context mContext) {

        couponDatas = new ArrayList<>();
        couponAdapter = new CouponAdapter(couponDatas);
        recyclerView.setAdapter(couponAdapter);

        userCouponModel = new ViewModelProvider(this).get(UserCouponModel.class);
        userCouponModel.getCouponDataBeanLiveData().observe(this, new Observer<CouponDataBean>() {
            @Override
            public void onChanged(CouponDataBean couponDataBean) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                if (page == 1) {
                    couponDatas.clear();
                }
                couponDatas.addAll(couponDataBean.getData());
                couponAdapter.notifyDataSetChanged();

            }
        });
        userCouponModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                shortToast(o);

            }
        });

        showLoading("");
        userCouponModel.couponData(page, pageSize, type);

    }
}
