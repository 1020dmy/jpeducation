package com.jianpei.jpeducation.fragment.group;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.vodplayerview.utils.NetWatchdog;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.adapter.ItemOffsetDecoration;
import com.jianpei.jpeducation.adapter.classinfo.ExplanationAdapter;
import com.jianpei.jpeducation.adapter.classinfo.GroupAdapter;
import com.jianpei.jpeducation.adapter.classinfo.TeacherAdapter;
import com.jianpei.jpeducation.base.BasePlayerFragment;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentDataBean;
import com.jianpei.jpeducation.bean.classinfo.TeacherBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.pop.GroupJoinPopup;
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
public class GclassInfoFragment extends BasePlayerFragment implements GroupAdapter.ToJoinGroupListener {


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
    @BindView(R.id.ll_guize)
    LinearLayout llGuize;
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.tv_regiment_des)
    TextView tvRegimentDes;
    //GroupInfoActivity的model
    private ClassInfoModel classInfoModel;
    //自己的额model
    private ClassInfoFModel classInfoFModel;


    private GroupAdapter groupAdapter;
    private List<RegimentBean> regimentBeans;
    //加入团购弹窗
    private GroupJoinPopup groupJoinPopup;
    //团购列表弹窗
    private GroupPopup groupPopup;

//    private String groupId;

    private String pointId, id;

    public GclassInfoFragment(String pointId, String id) {
        this.pointId = pointId;
        this.id = id;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_gclass_info;
    }


    @Override
    protected void initView(View view) {
        if (NetWatchdog.is4GConnected(MyApplication.getInstance())) {
            tvTryListener.setVisibility(View.GONE);
        }

        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);
        classInfoFModel = new ViewModelProvider(getActivity()).get(ClassInfoFModel.class);

        //滑动监听，并回传数据
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            classInfoModel.tabViewStatusChange(scrollY);

        });
        //GroupInfoActivity数据监听
//        classInfoModel.getRegimentInfoBeanMutableLiveData().observe(this, new Observer<RegimentInfoBean>() {
//            @Override
//            public void onChanged(RegimentInfoBean regimentInfoBean) {//获取成功，调用详情接口
//                //获取团购Id
//                groupId = regimentInfoBean.getId();
//                showLoading("");
//                //获取详情数据
//                classInfoFModel.groupInfo(regimentInfoBean.getPoint_id(), regimentInfoBean.getId());
//
//            }
//        });
        //接收更新数据
        classInfoModel.getUpDataLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showLoading("");
                teacherBeans.clear();
                regimentBeans.clear();
                classInfoFModel.groupInfo(pointId, id);
            }
        });

        //详情结果监听
        classInfoFModel.getClassInfoBean().observe(getActivity(), new Observer<ClassInfoBean>() {
            @Override
            public void onChanged(ClassInfoBean classInfoBean) {
                dismissLoading();
                classInfoModel.getClassInfoBeanLiveData().setValue(classInfoBean);
                classInfoFModel.videoUrl(classInfoBean.getVideo_id(), "", classInfoBean.getId());//获取试看视频
                setDatatoView(classInfoBean);//设置页面数据
            }
        });
        //错误监听
        classInfoFModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                if (!TextUtils.isEmpty(o))
                    shortToast(o);
            }
        });
        //视频结果监听
        classInfoFModel.getVideoUrlBeansLiveData().observe(getActivity(), new Observer<VideoUrlBean>() {
            @Override
            public void onChanged(VideoUrlBean videoUrlBean) {
                //试听课程结果
                aliyunPlayerView.setCoverUri(videoUrlBean.getImg());
                //初始化播放器并播放试听课程
                initAliyunPlayerView();
                playVideo(videoUrlBean);

            }
        });
        //团购详情监听
        classInfoFModel.getRegimentBeanLiveData().observe(this, new Observer<RegimentBean>() {
            @Override
            public void onChanged(RegimentBean regimentBean) {
                L.e("========团购详情监听");
                dismissLoading();
                showJoinGroupPop(regimentBean);
            }
        });

        //团购列表监听
        classInfoFModel.getRegimentDataBeanLiveData().observe(this, new Observer<RegimentDataBean>() {
            @Override
            public void onChanged(RegimentDataBean regimentDataBean) {
                dismissLoading();
                showGroupListPop(regimentDataBean);

            }
        });
        rvExplanation.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(10));

        rvItems.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void initData(Context mContext) {
        teacherBeans = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(teacherBeans, getActivity());

        recyclerView.setAdapter(teacherAdapter);
        ///团购
        regimentBeans = new ArrayList<>();
        groupAdapter = new GroupAdapter(regimentBeans, getContext());
        groupAdapter.setToJoinGroupListener(this);
        rvItems.setAdapter(groupAdapter);

        //
        showLoading("");
        //获取详情数据
        classInfoFModel.groupInfo(pointId, id);

    }


    @OnClick({R.id.tv_tryListener, R.id.tv_more, R.id.tv_guize})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tryListener:
                aliyunPlayerView.start();
                tvTryListener.setVisibility(View.GONE);
                aliyunPlayerView.setControlBarCanShow(true);
                break;
            case R.id.tv_more:
                showLoading("");
                classInfoFModel.regimentData(id);
                break;
            case R.id.tv_guize:
                startActivity(new Intent(getActivity(), GuiZeActivity.class).putExtra("webUrl", webUrl).putExtra("title", "拼团规则"));
                break;
        }
    }

    private String originalPrice;
    private String material;

    private TeacherAdapter teacherAdapter;
    private List<TeacherBean> teacherBeans;
    private String webUrl;

    private String joinGroupId;


    protected void setDatatoView(ClassInfoBean classInfoBean) {
        if (classInfoBean == null)
            return;
        webUrl = classInfoBean.getRegiment_rules_url();


        if (classInfoBean.getUser_regiment_info() != null) {
            joinGroupId = classInfoBean.getUser_regiment_info().getId();
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
        //拼团
        if ("0".equals(classInfoBean.getRegiment_info().getRegiment_des())) {
            llPinTuan.setVisibility(View.GONE);
        } else {
            tvRegimentDes.setText(classInfoBean.getRegiment_info().getRegiment_des() + "人正在拼团");
            regimentBeans.clear();
            regimentBeans.addAll(classInfoBean.getRegiment_info().getRegiment_data());
            groupAdapter.notifyDataSetChanged();
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

//        tvClass.setText(classInfoBean.getVideo_time_str());
        tvAging.setText(classInfoBean.getYear_num() + "年");
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


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int type = this.getResources().getConfiguration().orientation;
        if (type == Configuration.ORIENTATION_LANDSCAPE) {
            llClassInfo.setVisibility(View.GONE);
            llContent.setVisibility(View.GONE);
            llServer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            llGuize.setVisibility(View.GONE);
            llPinTuan.setVisibility(View.GONE);
            aliyunPlayerView.setTitleBarCanShow(true);
            //切换到了横屏
        } else if (type == Configuration.ORIENTATION_PORTRAIT) {
            //切换到了竖屏
            llClassInfo.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.VISIBLE);
            llServer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            llGuize.setVisibility(View.VISIBLE);
            llPinTuan.setVisibility(View.VISIBLE);
            aliyunPlayerView.setTitleBarCanShow(false);


        }
        updatePlayerViewMode();
    }


    @Override
    public void onJoinGroupClick(String groupId) {
        if (groupId.equals(joinGroupId)) {
            shortToast("不能参与自己的团");
            return;
        }
        showLoading("");
        classInfoFModel.regimentInfo(groupId);

    }

    protected void showJoinGroupPop(RegimentBean regimentBean) {
        if (groupJoinPopup == null)
            groupJoinPopup = new GroupJoinPopup(getActivity(), regimentBean, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //参团通知调用科目选择
                    classInfoModel.getJoinGroupInfoLiveData().setValue(regimentBean.getId());
                }
            });
        groupJoinPopup.showPop();

    }

    protected void showGroupListPop(RegimentDataBean regimentDataBean) {
        if (groupPopup == null) {
            groupPopup = new GroupPopup(getActivity(), regimentDataBean);
            groupPopup.setToJoinGroupListener(this);
        }
        groupPopup.showPop();

    }
}