package com.jianpei.jpeducation.fragment.elective;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.WebActivity;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.viewmodel.MainModel;

import butterknife.BindView;
import butterknife.OnClick;


public class ElectiveFragment extends BaseFragment {

    @BindView(R.id.iv_kefu)
    ImageView ivKefu;
    private MainModel mainModel;


    @Override
    protected int initLayout() {
        return R.layout.fragment_notifications;
    }

    @Override
    protected void initView(View view) {
        mainModel =
                ViewModelProviders.of(getActivity()).get(MainModel.class);
//        mainModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
    }

    @Override
    protected void initData(Context mContext) {


    }

    @OnClick(R.id.iv_kefu)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), WebActivity.class));
    }
}
