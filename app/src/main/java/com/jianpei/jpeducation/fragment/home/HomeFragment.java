package com.jianpei.jpeducation.fragment.home;

import android.content.Context;

import android.content.Intent;
import android.os.Environment;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.pdf.PdfReaderActivity;
import com.jianpei.jpeducation.adapter.BannerMainAdapter;
import com.jianpei.jpeducation.adapter.home.GroupInfoItemBinder;
import com.jianpei.jpeducation.adapter.home.GroupTitleItemBinder;
import com.jianpei.jpeducation.adapter.home.HuoDongItemBinder;
import com.jianpei.jpeducation.adapter.home.MaterialInfoItemBinder;
import com.jianpei.jpeducation.adapter.home.MaterialTitleItemBinder;
import com.jianpei.jpeducation.adapter.home.MateriallTitleTItemBinder;
import com.jianpei.jpeducation.adapter.home.RegimentInfoItemBinder;
import com.jianpei.jpeducation.adapter.home.RegimentTitleItemBinder;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.NoticeDataBean;
import com.jianpei.jpeducation.bean.homedata.BannerDataBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.homedata.GroupTitleBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.bean.homedata.HuoDongDataBean;
import com.jianpei.jpeducation.bean.homedata.MaterialTitleBean;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.bean.homedata.RegimentTitleBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.utils.dialog.IntegralBuyDialog;
import com.jianpei.jpeducation.utils.down.QueueListener;
import com.jianpei.jpeducation.utils.listener.MaterialInfoItemOnClickListener;
import com.jianpei.jpeducation.viewmodel.HomePageModel;
import com.jianpei.jpeducation.viewmodel.IntegralModel;
import com.jianpei.jpeducation.viewmodel.MainModel;
import com.jianpei.jpeducation.viewmodel.MaterialModel;
import com.liulishuo.okdownload.DownloadTask;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;

import java.util.ArrayList;

import butterknife.BindView;


public class HomeFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //课程切换
    private MainModel mainModel;
    //获取资料url
    private MaterialModel materialModel;
    //首页数据
    private HomePageModel homePageModel;
    //积分支付
    private IntegralModel integralModel;
    //banner
    private ArrayList<BannerDataBean> bannerDataBeans;


    private ArrayList<Object> datas;
    private BaseBinderAdapter baseBinderAdapter;

    private MaterialInfoItemBinder materialInfoItemBinder;


    QueueListener queueListener;
    private MaterialInfoItemBinder.MyHolder mMyHolder;
    private String name, nums;

    private IntegralBuyDialog integralBuyDialog;

    private String downloadUrl;


    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        //获取首页数据
        homePageModel = new ViewModelProvider(this).get(HomePageModel.class);
        //获取专业切换
        mainModel = new ViewModelProvider(getActivity()).get(MainModel.class);
        //资料下载
        materialModel = new ViewModelProvider(this).get(MaterialModel.class);
        //积分支付
        integralModel = new ViewModelProvider(this).get(IntegralModel.class);
        //

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bannerDataBeans = new ArrayList<>();
        banner.addBannerLifecycleObserver(this)
                .setBannerRound(30)
                .setAdapter(new BannerMainAdapter(bannerDataBeans, getActivity()))
                .setIndicator(new RectangleIndicator(getActivity()));

        datas = new ArrayList<>();
        materialInfoItemBinder = new MaterialInfoItemBinder();
        materialInfoItemBinder.setMaterialInfoItemOnClickListener(new MaterialInfoItemOnClickListener() {
            @Override
            public void OnItemClick(MaterialInfoItemBinder.MyHolder myHolder, MaterialInfoBean materialInfoBean) {

                if (materialInfoBean.getStatus().equals("2")) {

                    startActivity(new Intent(getActivity(), PdfReaderActivity.class).putExtra("materialInfoBean", materialInfoBean));

                } else {
                    showLoading("");
                    mMyHolder = myHolder;
                    name = materialInfoBean.getTitle();
                    nums = materialInfoBean.getTotal();
                    materialModel.getDownloadUrl(materialInfoBean.getId());
                }


            }
        });
        baseBinderAdapter = new BaseBinderAdapter();
        baseBinderAdapter.addItemBinder(HuoDongDataBean.class, new HuoDongItemBinder(getActivity()))
                .addItemBinder(RegimentTitleBean.class, new RegimentTitleItemBinder())
                .addItemBinder(RegimentInfoBean.class, new RegimentInfoItemBinder(getActivity()))
                .addItemBinder(GroupTitleBean.class, new GroupTitleItemBinder())
                .addItemBinder(GroupInfoBean.class, new GroupInfoItemBinder(getActivity()))
                .addItemBinder(MaterialTitleBean.class, new MaterialTitleItemBinder(getActivity()))
                .addItemBinder(String.class, new MateriallTitleTItemBinder())
                .addItemBinder(MaterialInfoBean.class, materialInfoItemBinder);
        recyclerView.setAdapter(baseBinderAdapter);
        baseBinderAdapter.setList(datas);


    }

    @Override
    protected void initData(Context mContext) {
        //通知数据
        homePageModel.getNoticeDatas().observe(this, new Observer<ArrayList<NoticeDataBean>>() {
            @Override
            public void onChanged(ArrayList<NoticeDataBean> noticeDataBeans) {
                marqueeView.startWithList(noticeDataBeans);
            }
        });
        //首页数据
        homePageModel.getScuucessData().observe(this, new Observer<HomeDataBean>() {
            @Override
            public void onChanged(HomeDataBean data) {
                SpUtils.putString("customerServiceUrl", data.getCustomerServiceUrl());//存储客服地址
                bannerDataBeans.clear();
                bannerDataBeans.addAll(data.getBannerData());
                banner.setDatas(bannerDataBeans);

                datas.clear();
                datas.addAll(data.getHuoDongData());
                if (data.getRegimentData().getData() != null && data.getRegimentData().getData().size() > 0)
                    datas.add(new RegimentTitleBean(data.getRegimentData().getTitle(), data.getRegimentData().getSubtitle()));
                datas.addAll(data.getRegimentData().getData());
                if (data.getGroupData().getData() != null && data.getGroupData().getData().size() > 0)
                    datas.add(new GroupTitleBean(data.getGroupData().getTitle(), data.getGroupData().getSubtitle()));
                datas.addAll(data.getGroupData().getData());

                if (data.getMaterialData().getData() != null && data.getMaterialData().getData().size() > 0) {
                    datas.add(new MaterialTitleBean(data.getMaterialData().getTitle(), data.getMaterialData().getSubtitle()));
                    datas.add(SpUtils.getValue(SpUtils.catName));
                }
                datas.addAll(data.getMaterialData().getData());
                baseBinderAdapter.setList(datas);
            }
        });
        //专业切换
        mainModel.getCatId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                homePageModel.getHomeData(s);//获取首页数据
                homePageModel.noticeData(s);//获取通知数据
            }
        });
        //错误
        homePageModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                shortToast(s);
            }
        });
        //获取资料下载的url
        materialModel.getDownloadBeanMutableLiveData().observe(this, new Observer<DownloadBean>() {
            @Override
            public void onChanged(DownloadBean downloadBean) {
                dismissLoading();
                downloadUrl = downloadBean.getDownloadUrl();
                if ("0".equals(downloadBean.getIs_pay())) {//0不需要积分，直接下载
                    downloadMaterial(downloadBean.getDownloadUrl());
                } else {
                    //要积分，弹窗
                    if (integralBuyDialog == null) {
                        integralBuyDialog = new IntegralBuyDialog(getActivity(), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                integralModel.integrlPay(3, downloadBean.getIntergral_price(), "");

                            }
                        }, name, nums, downloadBean.getIntergral_price(), downloadBean.getUser_intergral());
                    }
                    integralBuyDialog.show();

                }
            }
        });
        materialModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        //积分付款
        integralModel.getIntegrlPayLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                downloadMaterial(downloadUrl);

            }
        });
        integralModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

    }


    public void downloadMaterial(String url) {
        if (queueListener == null) {
            queueListener = new QueueListener();
        }

        DownloadTask task = new DownloadTask.Builder(url, getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))
                .setFilename(name)
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .build();
        queueListener.bind(task, mMyHolder);
        task.enqueue(queueListener);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
