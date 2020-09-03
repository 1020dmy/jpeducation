package com.jianpei.jpeducation.fragment.elective;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.BannerMainAdapter;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.elective.ElectiveAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.elective.GroupHomeBean;
import com.jianpei.jpeducation.bean.homedata.BannerDataBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.viewmodel.ElectiveModel;
import com.jianpei.jpeducation.viewmodel.MainModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ElectiveFragment extends BaseFragment implements MyItemOnClickListener {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ib_kefu)
    ImageButton ibKefu;
    private MainModel mainModel;
    //banner
    private ArrayList<BannerDataBean> bannerDataBeans;
    //
    private ElectiveModel electiveModel;
    //
    private List<GroupInfoBean> groupDataBeans;
    private ElectiveAdapter electiveAdapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_notifications;
    }

    @Override
    protected void initView(View view) {

        mainModel = new ViewModelProvider(getActivity()).get(MainModel.class);

        electiveModel = new ViewModelProvider(this).get(ElectiveModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    @Override
    protected void initData(Context mContext) {
        bannerDataBeans = new ArrayList<>();
        banner.addBannerLifecycleObserver(this)
                .setBannerRound(30)
                .setAdapter(new BannerMainAdapter(bannerDataBeans, getActivity()))
                .setIndicator(new RectangleIndicator(getActivity()));
        banner.setOnBannerListener(new OnBannerListener<BannerDataBean>() {
            @Override
            public void OnBannerClick(BannerDataBean data, int position) {
                if (data==null)
                    return;
                if ("url".equals(data.getApp_jump_type())){
                    startActivity(new Intent(getActivity(), GuiZeActivity.class)
                            .putExtra("title", data.getTitle())
                            .putExtra("webUrl", data.getUrl()));
                }else if ("group".equals(data.getApp_jump_type())){
                    startActivity(new Intent(getActivity(), ClassInfoActivity.class)
                            .putExtra("groupId", data.getApp_point_id())
                            .putExtra("catId", data.getCat_id()));
                }
            }
        });

        groupDataBeans = new ArrayList<>();
        electiveAdapter = new ElectiveAdapter(groupDataBeans, getActivity());
        electiveAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(electiveAdapter);

        //专业切换
        mainModel.getCatId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showLoading("");
                electiveModel.groupHome(s);

            }
        });
        //首页数据
        electiveModel.getGroupHomeBeanLiveData().observe(this, new Observer<GroupHomeBean>() {
            @Override
            public void onChanged(GroupHomeBean groupHomeBean) {
                dismissLoading();
                bannerDataBeans.clear();
                bannerDataBeans.addAll(groupHomeBean.getBannerData());
                banner.setDatas(bannerDataBeans);
                //
                groupDataBeans.clear();
                groupDataBeans.addAll(groupHomeBean.getGroupData());
                electiveAdapter.notifyDataSetChanged();
            }
        });
        electiveModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });


    }

    @OnClick(R.id.ib_kefu)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), KeFuActivity.class));
    }


    @Override
    public void onItemClick(int position, View view) {
        startActivity(new Intent(getActivity(), ClassInfoActivity.class)
                .putExtra("groupId", groupDataBeans.get(position).getId())
                .putExtra("catId", groupDataBeans.get(position).getCat_id()));


    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
