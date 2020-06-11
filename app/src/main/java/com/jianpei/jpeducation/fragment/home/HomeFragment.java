package com.jianpei.jpeducation.fragment.home;

import android.content.Context;

import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.jianpei.jpeducation.base.BaseFragment;
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
import com.jianpei.jpeducation.viewmodel.MaterialModel;
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

    private MainModel mainModel;
    private MaterialModel materialModel;
    private HomePageModel homePageModel;
    //banner
    private ArrayList<BannerDataBean> bannerDataBeans;


    private ArrayList<Object> datas;
    private BaseBinderAdapter baseBinderAdapter;

    private MaterialInfoItemBinder materialInfoItemBinder;

    private AlertDialog alertDialog;

    String downUrl;

    private MaterialInfoBean mmaterialInfoBean;
    private int mposition;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int pro = msg.arg1;
            if (pro == -1) {
                mmaterialInfoBean.setStatus("3");
            } else {
                mmaterialInfoBean.setProgress(pro);
            }
            if (pro == 100) {
                mmaterialInfoBean.setStatus("2");
                L.e("下载完成，开始存入数据库");
//                MyRoomDatabase.getInstance().materialInfoDao().insertMaterialInfo(mmaterialInfoBean);
            }
            baseBinderAdapter.notifyItemChanged(mposition);
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        //获取首页数据
        homePageModel = new ViewModelProvider(getActivity()).get(HomePageModel.class);
        //获取专业切换
        mainModel = new ViewModelProvider(getActivity()).get(MainModel.class);
        //资料下载，支付相关
        materialModel = new ViewModelProvider(getActivity()).get(MaterialModel.class);

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
            public void OnItemClick(View view, int position) {
                mposition = position;
                MaterialInfoBean materialInfoBean = (MaterialInfoBean) datas.get(position);
                mmaterialInfoBean = materialInfoBean;
                mmaterialInfoBean.setStatus("1");
                baseBinderAdapter.notifyItemChanged(position);

                materialModel.getDownloadUrl(mmaterialInfoBean.getId());

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
                if ("1".equals(downloadBean.getIs_pay())) {//0不需要积分
                    AndroidDownloadManager downloadManager = new AndroidDownloadManager(getActivity(), downloadBean.getDownloadUrl());
                    downloadManager.download(handler);
                } else {//要积分
                    if (alertDialog == null) {
                        myDialog(downloadBean.getIntergral_price(), downloadBean.getUser_intergral());
                    } else {
                        alertDialog.show();
                    }
                }

            }
        });
        //积分付款
        materialModel.getIntegrlPayLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                AndroidDownloadManager downloadManager = new AndroidDownloadManager(getActivity(), downUrl);
                downloadManager.download(handler);
            }
        });

        materialModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                shortToast(o);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    protected void myDialog(String intergral_price, String user_intergral) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_download, null);
        TextView tvXjifen = view.findViewById(R.id.tv_xjifen);
        TextView textView = view.findViewById(R.id.tv_jifen);
        tvXjifen.setText(intergral_price);
        textView.setText("您现在有" + user_intergral + "积分");
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
                materialModel.integrlPay(intergral_price);
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
