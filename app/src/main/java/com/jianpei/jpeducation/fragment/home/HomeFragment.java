package com.jianpei.jpeducation.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.jianpei.alyplayer.PlayerActivity;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.login.LoginActivity;
import com.jianpei.jpeducation.adapter.BannerMainAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.button)
    Button button;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {


    }

    @Override
    protected void initData(Context mContext) {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.guide_one);
        list.add(R.drawable.guide_two);
        list.add(R.drawable.guide_three);
        banner.setBannerGalleryMZ(20, 0.8f);

        BannerMainAdapter bannerMainAdapter = new BannerMainAdapter(list);
        banner.setAdapter(bannerMainAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
    }

    @Override
    public void onStop() {
        banner.stop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        banner.destroy();
        super.onDestroy();
    }

    @OnClick({R.id.button, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(getActivity(), PlayerActivity.class));
                break;
            case R.id.login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

        }
    }
}
