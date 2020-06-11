package com.jianpei.jpeducation.fragment.info;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.classinfo.TeacherAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassInfoFragment extends BaseFragment {


    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.tv_explanation)
    TextView tvExplanation;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TeacherAdapter teacherAdapter;


    private ClassInfoModel classInfoModel;

    private String htsss = "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  课程名称：建筑物理与建筑设备&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  课程班次：精讲班&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  课程时长：&lt;span lang=&quot;EN-US&quot;&gt;47&lt;/span&gt;节&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  主讲老师：冯玲&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  课程价格&lt;span lang=&quot;EN-US&quot;&gt;900&lt;/span&gt;元&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  课程特色：&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  1、讲：以大纲为引领，真题为主线，得分关键为核心；进一步详细化解不同案例的核心画法及考试关键点。&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  2、做：“讲”、“做”结合手把手教学，口传心授独家作图秘诀，牢固记忆画法要点。&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  3、考：全真模考，实战演练，技法落地，一对一指导作图。&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  4、练：海量案例，稳扎稳打；全方位锁定考试。&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  学员须知：&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  1、本站所有视频版权归北京建培教育咨询有限公司所有&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  1、本站所有视频版权归北京建培教育咨询有限公司所有，禁止翻录！&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  2、网校视频课程为虚拟产品，一经开课，非产品质量问题，不能退费。&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  3、网校所有视频不限制听课次数，一经开课可以无限次听课，一直到当年考试结束七天后关闭。&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;" +
            "&lt;p class=&quot;MsoNormal&quot;&gt;" +
            "  4、网校客服热线：周一到周日，上午&lt;span lang=&quot;EN-US&quot;&gt; 08:30&lt;/span&gt;——&lt;span lang=&quot;EN-US&quot;&gt;12:00&lt;/span&gt;；下午&lt;span lang=&quot;EN-US&quot;&gt;13:00&lt;/span&gt;——&lt;span lang=&quot;EN-US&quot;&gt;20:00&lt;/span&gt;。学员在网络学习过程中如遇到任何疑难问题都可以通过联系建培网校客服来获得帮助，请广大学员注意客服作息时间以便及时获得学习帮助。&lt;span lang=&quot;EN-US&quot;&gt;&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;" +
            "&lt;/p&gt;";

    @Override
    protected int initLayout() {
        return R.layout.fragment_class_info;
    }

    @Override
    protected void initView(View view) {
        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            classInfoModel.tabViewStatusChange(scrollY);

        });

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    protected void initData(Context mContext) {
        Spanned spanned = Html.fromHtml(htsss);
        tvExplanation.setText(Html.fromHtml(spanned.toString()));

        teacherAdapter=new TeacherAdapter();
        recyclerView.setAdapter(teacherAdapter);


    }
}
