package com.jianpei.jpeducation.fragment.mine.coupon;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CouponAvailableFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;



    @Override
    protected int initLayout() {
        return R.layout.fragment_coupon_available;
    }

    @Override
    protected void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void initData(Context mContext) {

    }
}
