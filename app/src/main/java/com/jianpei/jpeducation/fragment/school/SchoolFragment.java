package com.jianpei.jpeducation.fragment.school;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.MainModel;

public class SchoolFragment extends BaseFragment {


    private MainModel mainModel;

    @Override
    protected int initLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    protected void initView(View view) {
        mainModel = ViewModelProviders.of(getActivity()).get(MainModel.class);
        mainModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                L.e("onChanged:" + s);
            }
        });
    }

    @Override
    protected void initData(Context mContext) {

    }

}
