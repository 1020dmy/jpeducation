package com.jianpei.jpeducation.fragment.group;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.adapter.ItemOffsetDecoration;
import com.jianpei.jpeducation.adapter.classinfo.ExplanationAdapter;
import com.jianpei.jpeducation.adapter.classinfo.TeacherAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;
import com.jianpei.jpeducation.bean.classinfo.TeacherBean;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.dialog.GroupDialog;
import com.jianpei.jpeducation.utils.pop.GroupPopup;
import com.jianpei.jpeducation.viewmodel.ClassInfoFModel;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class GclassInfoFragment extends BaseFragment {


    @BindView(R.id.aliyunPlayerView)
    AliyunVodPlayerView aliyunPlayerView;
    @BindView(R.id.tv_tryListener)
    TextView tvTryListener;
    @BindView(R.id.tv_now_price)
    TextView tvNowPrice;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_handsel)
    TextView tvHandsel;
    @BindView(R.id.ll_handsel)
    LinearLayout llHandsel;
    @BindView(R.id.tv_teachers)
    TextView tvTeachers;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_aging)
    TextView tvAging;
    @BindView(R.id.tv_nums)
    TextView tvNums;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_more)
    TextView tvMore;
    //    @BindView(R.id.civ_head)
//    CircleImageView civHead;
//    @BindView(R.id.tv_name)
//    TextView tvName;
//    @BindView(R.id.tv_nums)
//    TextView tvNums;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.tv_join)
//    TextView tvJoin;
//    @BindView(R.id.civ_head)
//    CircleImageView civHead;
//    @BindView(R.id.tv_name)
//    TextView tvName;
//    @BindView(R.id.tv_nums)
//    TextView tvNums;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.tv_join)
//    TextView tvJoin;
    @BindView(R.id.tv_guize)
    TextView tvGuize;
    @BindView(R.id.tv_server)
    TextView tvServer;
    @BindView(R.id.tv_guarantee)
    TextView tvGuarantee;
    @BindView(R.id.ll_server)
    LinearLayout llServer;
    @BindView(R.id.rv_explanation)
    RecyclerView rvExplanation;
    @BindView(R.id.ll_classInfo)
    LinearLayout llClassInfo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.tv_item)
    TextView tvItem;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.ll_pinTuan)
    LinearLayout llPinTuan;
    //GroupInfoActivity的model
    private ClassInfoModel classInfoModel;
    //自己的额model
    private ClassInfoFModel classInfoFModel;


    @Override
    protected int initLayout() {
        return R.layout.fragment_gclass_info;
    }

    @Override
    protected void initView(View view) {
        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);
        classInfoFModel = new ViewModelProvider(getActivity()).get(ClassInfoFModel.class);

        //滑动监听，并回传数据
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            classInfoModel.tabViewStatusChange(scrollY);

        });
        //GroupInfoActivity数据监听
        classInfoModel.getRegimentInfoBeanMutableLiveData().observe(this, new Observer<RegimentInfoBean>() {
            @Override
            public void onChanged(RegimentInfoBean regimentInfoBean) {//获取成功，调用详情接口
                showLoading("");
                //获取详情数据
                classInfoFModel.groupInfo(regimentInfoBean.getPoint_id(), regimentInfoBean.getId());

            }
        });
        //详情结果监听
        classInfoFModel.getClassInfoBean().observe(getActivity(), new Observer<ClassInfoBean>() {
            @Override
            public void onChanged(ClassInfoBean classInfoBean) {
                dismissLoading();
//                classInfoFModel.videoUrl(classInfoBean.getVideo_id(), "");//获取试看视频
                setDatatoView(classInfoBean);//设置页面数据
            }
        });
        //错误监听
        classInfoFModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        rvExplanation.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(10));
    }

    @Override
    protected void initData(Context mContext) {
        teacherBeans = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(teacherBeans, getActivity());

        recyclerView.setAdapter(teacherAdapter);

    }

    GroupPopup groupPopup;
    private List<RegimentBean> regimentBeans;

    @OnClick({R.id.tv_tryListener, R.id.tv_more, R.id.tv_guize})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tryListener:
                break;
            case R.id.tv_more:
                if (groupPopup == null) {
                    groupPopup = new GroupPopup(getActivity(), regimentBeans);
                }
                L.e("regimentBeans:"+regimentBeans.size());
                groupPopup.showPop();
                break;
            case R.id.tv_guize:
                startActivity(new Intent(getActivity(), GuiZeActivity.class).putExtra("webUrl", webUrl));
                break;
        }
    }

    private String originalPrice;
    private String material;

    private TeacherAdapter teacherAdapter;
    private List<TeacherBean> teacherBeans;
    private String webUrl;


    protected void setDatatoView(ClassInfoBean classInfoBean) {
        if (classInfoBean == null)
            return;
        webUrl = classInfoBean.getRegiment_rules_url();
        if (regimentBeans == null) {
            regimentBeans = new ArrayList<>();
        }
        L.e("setDatatoView:"+classInfoBean.getRegiment_info().getRegiment_data().size());

        regimentBeans.addAll(classInfoBean.getRegiment_info().getRegiment_data());
        if(regimentBeans.size()==0){
            llPinTuan.setVisibility(View.GONE);
        }
        //如果没有活动价格，隐藏原价
        if (classInfoBean.getHuod_price_info() == null) {
            tvPrice.setVisibility(View.INVISIBLE);
            tvNowPrice.setText(classInfoBean.getOriginal_price_info());
        } else {
            tvNowPrice.setText(classInfoBean.getHuod_price_info());
            originalPrice = classInfoBean.getOriginal_price_info();
            tvPrice.setText("原价：" + originalPrice);
            tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        tvItem.setText(classInfoBean.getRegiment_people());
        tvNums.setText("已拼" + classInfoBean.getRegiment_num() + "件");

//        classInfoModel.setPrices(new String[]{classInfoBean.getHuod_price_info(), classInfoBean.getOriginal_price_info(),classInfoBean.getMaterial_des()});
        tvTitle.setText(classInfoBean.getTitle());
        //班级描述
        if (classInfoBean.getSub_title() == null) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setText(classInfoBean.getSub_title());
        }
        //是否有赠送资料
        if (classInfoBean.getMaterial_des() == null) {
            llHandsel.setVisibility(View.GONE);
        } else {
            material = classInfoBean.getMaterial_des();
            tvHandsel.setText(material);
        }
        //教师
        StringBuilder stringBuilder = new StringBuilder();
        for (String teacherNum : classInfoBean.getTeacher_names()) {
            stringBuilder.append(teacherNum + " ");
        }
        String teachers = stringBuilder.toString();
        stringBuilder.replace(0, stringBuilder.length(), "");
        stringBuilder.reverse();
        stringBuilder = null;
        tvTeachers.setText(teachers);

        tvClass.setText(classInfoBean.getVideo_time_str());
        tvAging.setText(classInfoBean.getEnd_time_str());
        tvPeople.setText(classInfoBean.getBuy_num() + "人已报名");

        //服务保障
        tvServer.setText(classInfoBean.getOur_service());
        tvGuarantee.setText(classInfoBean.getOur_guarantee());
        //班级说明
        if (classInfoBean.getContent() != null) {
            ExplanationAdapter explanationAdapter = new ExplanationAdapter(classInfoBean.getContent(), getActivity());
            rvExplanation.setAdapter(explanationAdapter);
        }
        //教师列表
        teacherBeans.addAll(classInfoBean.getTeachers());
        if (teacherBeans.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            teacherAdapter.notifyDataSetChanged();
        }
    }
}