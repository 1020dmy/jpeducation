package com.jianpei.jpeducation.fragment.home;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.BannerMainAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.MainModel;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;


    private MainModel mainModel;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;

    }

    @Override
    protected void initView(View view) {
        mainModel = new ViewModelProvider(getActivity()).get(MainModel.class);
    }

    @Override
    protected void initData(Context mContext) {

        mainModel.getCatId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                L.e("=======HomeFragment切换了====" + s);
                mainModel.getHomeData(s);
            }
        });

        List<String> messages = new ArrayList<>();
        messages.add("1111");
        messages.add("2222");
        messages.add("3333");
        marqueeView.startWithList(messages);

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.guide_one);
        list.add(R.drawable.guide_two);
        list.add(R.drawable.guide_three);

        banner.addBannerLifecycleObserver(this)
                .setBannerRound(30)
                .setAdapter(new BannerMainAdapter(list))
                .setIndicator(new RectangleIndicator(getActivity()));
//        BannerMainAdapter bannerMainAdapter = new BannerMainAdapter(list);
//        banner.setAdapter(bannerMainAdapter);
//        banner.setBannerRound(30);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
