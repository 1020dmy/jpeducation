package com.jianpei.jpeducation.fragment.home;

import android.content.Context;

import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.BannerMainAdapter;
import com.jianpei.jpeducation.adapter.home.GroupInfoItemBinder;
import com.jianpei.jpeducation.adapter.home.GroupTitleItemBinder;
import com.jianpei.jpeducation.adapter.home.HuoDongItemBinder;
import com.jianpei.jpeducation.adapter.home.MaterialInfoItemBinder;
import com.jianpei.jpeducation.adapter.home.MaterialTitleItemBinder;
import com.jianpei.jpeducation.adapter.home.MateriallTitleTItemBinder;
import com.jianpei.jpeducation.adapter.home.RegimentInfoItemBinder;
import com.jianpei.jpeducation.adapter.home.RegimentTitleItemBinder;
import com.jianpei.jpeducation.base.BaseModelFragment;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.NoticeDataBean;
import com.jianpei.jpeducation.bean.homedata.BannerDataBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.homedata.GroupTitleBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.bean.homedata.HuoDongDataBean;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;
import com.jianpei.jpeducation.bean.homedata.MaterialTitleBean;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.bean.homedata.RegimentTitleBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.utils.down.AndroidDownloadManager;
import com.jianpei.jpeducation.utils.listener.MaterialInfoItemOnClickListener;
import com.jianpei.jpeducation.viewmodel.HomePageModel;
import com.jianpei.jpeducation.viewmodel.MainModel;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;

import java.util.ArrayList;

import butterknife.BindView;


public class HomeFragment extends BaseModelFragment<HomePageModel, HomeDataBean> {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;


//    @BindView(R.id.rv_huodong)
//    RecyclerView rvHuodong;
//    @BindView(R.id.rv_item)
//    RecyclerView rvItem;
//    @BindView(R.id.rv_try)
//    RecyclerView rvTry;
//    @BindView(R.id.rv_ziliao)
//    RecyclerView rvZiliao;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MainModel mainModel;
    //banner
    private ArrayList<BannerDataBean> bannerDataBeans;
    //活动图片
//    private ArrayList<HuoDongDataBean> huoDongDataBeans;
//    private HuoDongAdapter huoDongAdapter;
    //    团购活动
//    private ArrayList<RegimentInfoBean> regimentInfoBeans;
//    ItemAdapter itemAdapter;
    //    课程试听
//    private ArrayList<GroupInfoBean> groupInfoBeans;
//    TryAdapter tryAdapter;
    //    资料下载
//    private ArrayList<MaterialInfoBean> materialInfoBeans;
//    ZiliaoAdapter ziliaoAdapter;

//    private ArrayList<ProviderMultiEntity> datas;
//    private HomeFragmentAdapter fragmentAdapter;


    private ArrayList<Object> datas;
    private BaseBinderAdapter baseBinderAdapter;

    private MaterialInfoItemBinder materialInfoItemBinder;

    private AlertDialog alertDialog;

    String fileId;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;

    }

    @Override
    protected void initView(View view) {
        mainModel = new ViewModelProvider(getActivity()).get(MainModel.class);
//        rvHuodong.setLayoutManager(new LinearLayoutManager(getActivity()));
        //
//        rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        //
//        rvTry.setLayoutManager(new LinearLayoutManager(getActivity()));
        //
//        rvZiliao.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initViewModel();
        mViewModel.getNoticeDatas().observe(this, new Observer<ArrayList<NoticeDataBean>>() {
            @Override
            public void onChanged(ArrayList<NoticeDataBean> noticeDataBeans) {
                marqueeView.startWithList(noticeDataBeans);
            }
        });
        mViewModel.getDownloadBeanMutableLiveData().observe(this, new Observer<DownloadBean>() {
            @Override
            public void onChanged(DownloadBean downloadBean) {
                dismissLoading();
                AndroidDownloadManager downloadManager = new AndroidDownloadManager(getActivity(), downloadBean.getDownloadUrl());
                downloadManager.download();
            }
        });
    }

    @Override
    protected void initData(Context mContext) {

        mainModel.getCatId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                L.e("=======HomeFragment切换了====" + s);
                shortToast("");
                mViewModel.getHomeData(s);
                mViewModel.noticeData(s);
            }
        });
        bannerDataBeans = new ArrayList<>();
        banner.addBannerLifecycleObserver(this)
                .setBannerRound(30)
                .setAdapter(new BannerMainAdapter(bannerDataBeans, getActivity()))
                .setIndicator(new RectangleIndicator(getActivity()));


        //
//        huoDongDataBeans = new ArrayList<>();
//        huoDongAdapter = new HuoDongAdapter(huoDongDataBeans, getActivity());
//        rvHuodong.setAdapter(huoDongAdapter);
//
//        regimentInfoBeans = new ArrayList<>();
//        itemAdapter = new ItemAdapter(regimentInfoBeans, getActivity());
//        rvItem.setAdapter(itemAdapter);
        //
//        groupInfoBeans = new ArrayList<>();
//        tryAdapter = new TryAdapter(groupInfoBeans, getActivity());
//        rvTry.setAdapter(tryAdapter);
        //
//        materialInfoBeans = new ArrayList<>();
//        ziliaoAdapter = new ZiliaoAdapter(materialInfoBeans);
//        rvZiliao.setAdapter(ziliaoAdapter);

//        TextView textView = new TextView(getActivity());
//        textView.setText("一级建造书");
//        textView.setTextColor(Color.WHITE);
//        textView.setTextSize(15);
//        textView.setGravity(Gravity.CENTER);
//        textView.setBackgroundResource(R.drawable.home_xiazai_bg);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        textView.setLayoutParams(layoutParams);
//        ziliaoAdapter.addHeaderView(textView);
        ///
        datas = new ArrayList<>();
        materialInfoItemBinder = new MaterialInfoItemBinder();
        materialInfoItemBinder.setMaterialInfoItemOnClickListener(new MaterialInfoItemOnClickListener() {
            @Override
            public void OnItemClick(View view, String id) {
                if (id.equals(fileId)) {
                    shortToast("请勿重复下载");
                    return;
                } else {
                    fileId = id;
                }

                if (alertDialog == null) {
                    myDialog();
                } else {
                    alertDialog.show();
                }
            }
        });
        baseBinderAdapter = new BaseBinderAdapter();
        baseBinderAdapter.addItemBinder(HuoDongDataBean.class, new HuoDongItemBinder(getActivity()))
                .addItemBinder(RegimentTitleBean.class, new RegimentTitleItemBinder())
                .addItemBinder(RegimentInfoBean.class, new RegimentInfoItemBinder(getActivity()))
                .addItemBinder(GroupTitleBean.class, new GroupTitleItemBinder())
                .addItemBinder(GroupInfoBean.class, new GroupInfoItemBinder(getActivity()))
                .addItemBinder(MaterialTitleBean.class, new MaterialTitleItemBinder())
                .addItemBinder(String.class, new MateriallTitleTItemBinder())
                .addItemBinder(MaterialInfoBean.class, materialInfoItemBinder);
        recyclerView.setAdapter(baseBinderAdapter);
        baseBinderAdapter.setList(datas);


    }


    @Override
    protected void onError(String message) {
        shortToast(message);

    }

    @Override
    protected void onSuccess(HomeDataBean data) {


        bannerDataBeans.clear();
        bannerDataBeans.addAll(data.getBannerData());
        banner.setDatas(bannerDataBeans);


        datas.clear();
        datas.addAll(data.getHuoDongData());
        datas.add(new RegimentTitleBean(data.getRegimentData().getTitle(), data.getRegimentData().getSubtitle()));
        datas.addAll(data.getRegimentData().getData());
        datas.add(new GroupTitleBean(data.getGroupData().getTitle(), data.getGroupData().getSubtitle()));
        datas.addAll(data.getGroupData().getData());
        datas.add(new MaterialTitleBean(data.getMaterialData().getTitle(), data.getMaterialData().getSubtitle()));
        datas.add(SpUtils.getValue(SpUtils.catName));
        datas.addAll(data.getMaterialData().getData());

        baseBinderAdapter.setList(datas);


        ///
//        huoDongDataBeans.clear();
//        huoDongDataBeans.addAll(data.getHuoDongData());
//        huoDongAdapter.notifyDataSetChanged();
        ///
//        regimentInfoBeans.clear();
//        regimentInfoBeans.addAll(data.getRegimentData().getData());
//        itemAdapter.notifyDataSetChanged();
        ///
//        groupInfoBeans.clear();
//        groupInfoBeans.addAll(data.getGroupData().getData());
//        tryAdapter.notifyDataSetChanged();
        ///
//        materialInfoBeans.clear();
//        materialInfoBeans.addAll(data.getMaterialData().getData());
//        ziliaoAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    protected void myDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_download, null);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        Button btnDownload = view.findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showLoading("");
                L.e("========fileId:"+fileId);
                mViewModel.getDownloadUrl(fileId);


            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Translucent_NoTitle);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager m = window.getWindowManager();
        Point p = new Point();
        m.getDefaultDisplay().getSize(p);
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = p.y; // 高度设置为屏幕的0.5
        params.width = p.x; // 宽度设置为屏幕的0.6
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);


    }


}
