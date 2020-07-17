package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mine.IntegralTaskAdapter;
import com.jianpei.jpeducation.adapter.mine.SigninAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.integral.IntegralInfoBean;
import com.jianpei.jpeducation.bean.integral.IntegralTaskBean;
import com.jianpei.jpeducation.utils.dialog.SiginDialog;
import com.jianpei.jpeducation.viewmodel.IntegralModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IntegralActivity extends BaseNoStatusActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_mingxi)
    TextView tvMingxi;
    @BindView(R.id.tv_sigin)
    TextView tvSigin;
    @BindView(R.id.linearLayout_t)
    LinearLayout linearLayoutT;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_guize)
    TextView tvGuize;


    private SigninAdapter signinAdapter;

    private IntegralTaskAdapter integralTaskAdapter;

    private IntegralModel integralModel;


    private List<IntegralInfoBean.RegistrationInfoBean> list;

    private List<IntegralTaskBean> mIntegralTaskBeans;


    private SiginDialog siginDialog;
    private String url;

    private int type;//4签到，5补签
    private String dayTime;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_integral;
    }

    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvTask.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void initData() {
        integralModel = new ViewModelProvider(this).get(IntegralModel.class);
        //首页信息
        integralModel.getInfoBeanLiveData().observe(this, new Observer<IntegralInfoBean>() {
            @Override
            public void onChanged(IntegralInfoBean integralInfoBean) {
                dismissLoading();
                dayTime = integralInfoBean.getDay_time();
                url = integralInfoBean.getRegistration_rules_url();
                Glide.with(IntegralActivity.this).load(integralInfoBean.getRegistration_image()).into(imageView);
                String str1 = "你已共计签到<font color='#FC6143'>" + integralInfoBean.getRegistration_count() + "天</font>";
                tvTotal.setText(Html.fromHtml(str1));
                tvIntegral.setText(integralInfoBean.getTotal_registration());
                if (1 == integralInfoBean.getDay_status()) {
                    tvSigin.setEnabled(false);
                    tvSigin.setText("已签到");
                }
                list.clear();
                list.addAll(integralInfoBean.getRegistration_info());
                signinAdapter.notifyDataSetChanged();
            }
        });
        //任务列表
        integralModel.getListLiveData().observe(this, new Observer<List<IntegralTaskBean>>() {
            @Override
            public void onChanged(List<IntegralTaskBean> integralTaskBeans) {
                dismissLoading();
                mIntegralTaskBeans.addAll(integralTaskBeans);
                integralTaskAdapter.notifyDataSetChanged();
            }
        });
        //签到
        integralModel.getIntegrlPayLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                if (type == 4) {
                    tvSigin.setText("已签到");
                    tvSigin.setEnabled(false);
                }
                if (siginDialog == null)
                    siginDialog = new SiginDialog(IntegralActivity.this, type);
                siginDialog.show();
                integralModel.integralInfo();
            }
        });
        //错误
        integralModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

        list = new ArrayList<>();
        signinAdapter = new SigninAdapter(list);
        signinAdapter.setMyItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (list.get(position).getIs_sign() == 1) {
                    shortToast("请勿重复签到");
                } else if (list.get(position).getIs_sign() == 2) {
                    type = 5;
                    showLoading("");
                    integralModel.integrlPay(type, list.get(position).getIntegral_price(), list.get(position).getDate());
                } else {
                    type = 4;
                    showLoading("");
                    integralModel.integrlPay(type, "0", list.get(position).getDate());
                }

            }

            @Override
            public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

            }
        });
        recyclerView.setAdapter(signinAdapter);

        mIntegralTaskBeans = new ArrayList<>();
        integralTaskAdapter = new IntegralTaskAdapter(mIntegralTaskBeans, this);
        rvTask.setAdapter(integralTaskAdapter);

        showLoading("");
        integralModel.integralInfo();
        integralModel.integralTask();

    }


    @OnClick({R.id.tv_mingxi, R.id.tv_sigin, R.id.iv_back, R.id.tv_guize})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mingxi:
                startActivity(new Intent(this, IntegralDetailsActivity.class).putExtra("integral", tvIntegral.getText().toString()));
                break;
            case R.id.tv_sigin:
                showLoading("");
                type = 4;
                integralModel.integrlPay(type, "0", dayTime);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_guize:
                startActivity(new Intent(this, GuiZeActivity.class).putExtra("webUrl", url).putExtra("title", "积分规则"));
                break;
        }
    }
}
