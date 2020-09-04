package com.jianpei.jpeducation.fragment.tiku;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
import com.jianpei.jpeducation.activitys.login.LoginActivity;
import com.jianpei.jpeducation.activitys.tiku.simulation.SimulationExerciseListActivity;
import com.jianpei.jpeducation.activitys.tiku.daily.TodayExerciseListActivity;
import com.jianpei.jpeducation.activitys.tiku.WrongQuestionListActivity;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.adapter.BannerMainAdapter;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.tiku.RecommendClassAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.homedata.BannerDataBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.tiku.PaperHomeBean;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.MainModel;
import com.jianpei.jpeducation.viewmodel.TikuModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TikuFragment extends BaseFragment implements MyItemOnClickListener {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_seven)
    TextView tvSeven;
    @BindView(R.id.tv_eight)
    TextView tvEight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private TikuModel tikuModel;

    //    private List<RecommendClassBean> recommendClassBeans;
    private RecommendClassAdapter recommendClassAdapter;

    private List<GroupInfoBean> groupDataBeans;

    //
    private MainModel mainModel;

    private String catId;
    //banner
    private ArrayList<BannerDataBean> bannerDataBeans;
    private BannerMainAdapter bannerMainAdapter;


    @Override
    protected int initLayout() {
        return R.layout.fragment_tiku;
    }

    @Override
    protected void initView(View view) {
        tikuModel = new ViewModelProvider(this).get(TikuModel.class);
        mainModel = new ViewModelProvider(getActivity()).get(MainModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                tikuModel.paperHome(catId);

            }
        });


    }

    @Override
    protected void initData(Context mContext) {
        groupDataBeans = new ArrayList<>();
        recommendClassAdapter = new RecommendClassAdapter(groupDataBeans, getActivity());
        recommendClassAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(recommendClassAdapter);
        //
        bannerDataBeans = new ArrayList<>();
        bannerMainAdapter=new BannerMainAdapter(bannerDataBeans, getActivity());
        bannerMainAdapter.setMainModel(mainModel);
        banner.addBannerLifecycleObserver(this)
                .setBannerRound(30)
                .setAdapter(bannerMainAdapter)
                .setIndicator(new RectangleIndicator(getActivity()));

        //专业切换
        mainModel.getCatId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showLoading("");
                catId = s;
                tikuModel.paperHome(s);

            }
        });
        //题库首页数据
        tikuModel.getPaperHomeBeanLiveData().observe(this, new Observer<PaperHomeBean>() {
            @Override
            public void onChanged(PaperHomeBean paperHomeBean) {
                refreshLayout.finishRefresh();
                dismissLoading();
                //
                bannerDataBeans.clear();
                bannerDataBeans.addAll(paperHomeBean.getBannerData());
                banner.setDatas(bannerDataBeans);
                //
                groupDataBeans.clear();
                groupDataBeans.addAll(paperHomeBean.getGroupData());
                recommendClassAdapter.notifyDataSetChanged();


            }
        });
        tikuModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                dismissLoading();
                shortToast(o);

            }
        });


    }

    @OnClick({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four, R.id.tv_five, R.id.tv_six, R.id.tv_seven, R.id.tv_eight})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(SpUtils.getValue(SpUtils.ID))) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        switch (view.getId()) {
            case R.id.tv_one:
                startActivity(new Intent(getActivity(), TodayExerciseListActivity.class));
                break;
            case R.id.tv_two:
                break;
            case R.id.tv_three:
                startActivity(new Intent(getActivity(), SimulationExerciseListActivity.class).putExtra("paper_type", "1"));
                break;
            case R.id.tv_four:
                startActivity(new Intent(getActivity(), SimulationExerciseListActivity.class).putExtra("paper_type", "2"));
                break;
            case R.id.tv_five:
                startActivity(new Intent(getActivity(), WrongQuestionListActivity.class).putExtra("type", 2));
                break;
            case R.id.tv_six:
                startActivity(new Intent(getActivity(), WrongQuestionListActivity.class).putExtra("type", 1));
                break;
            case R.id.tv_seven:
                startActivity(new Intent(getActivity(), WrongQuestionListActivity.class).putExtra("type", 4));
                break;
            case R.id.tv_eight:
                break;
        }
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
