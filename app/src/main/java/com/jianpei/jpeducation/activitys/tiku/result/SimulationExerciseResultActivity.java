package com.jianpei.jpeducation.activitys.tiku.result;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
import com.jianpei.jpeducation.activitys.tiku.simulation.SimulationParsingActivity;
import com.jianpei.jpeducation.activitys.tiku.simulation.TestPaperInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.tiku.RecommendClassAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.tiku.EvaluationBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SimulationExerciseResultActivity extends BaseActivity implements MyItemOnClickListener {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_use_time)
    TextView tvUseTime;
    @BindView(R.id.tv_total_fraction)
    TextView tvTotalFraction;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.tv_fraction)
    TextView tvFraction;
    @BindView(R.id.tv_correct)
    TextView tvCorrect;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_notDone)
    TextView tvNotDone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_multipleChoice_score)
    TextView tvMultipleChoiceScore;
    @BindView(R.id.tv_answerQuestion_score)
    TextView tvAnswerQuestionScore;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_back_list)
    TextView tvBackList;
    @BindView(R.id.tv_again)
    TextView tvAgain;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    //
    private AnswerModel answerModel;
    //    private PaperEvaluationBean paperEvaluationBean;
    private String recordId;//答题记录
    private String paperId;//试卷ID
    private String paperName;//试卷名称
    private String source;//1正常答题，2收藏，4本卷错题，3错题集,5全部解析，6本卷解答题

    //
    private List<GroupInfoBean> groupDataBeans;
    private RecommendClassAdapter recommendClassAdapter;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_simulation_exercise_result;
    }

    @Override
    protected void initView() {
        tvTitle.setText("考试结果");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.info_black_share);

        recordId = getIntent().getStringExtra("recordId");
        paperId = getIntent().getStringExtra("paperId");
        paperName = getIntent().getStringExtra("paperName");

        answerModel = new ViewModelProvider(this).get(AnswerModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void initData() {

        PaperEvaluationBean paperEvaluationBean = getIntent().getParcelableExtra("paperEvaluationBean");

        //交卷
        answerModel.getPaperEvaluationBeanLiveData().observe(this, new Observer<PaperEvaluationBean>() {
            @Override
            public void onChanged(PaperEvaluationBean paperEvaluationBean) {
                setData(paperEvaluationBean);
            }
        });

        if (paperEvaluationBean == null) {
            answerModel.paperEvaluation(recordId, "1");
        } else {
            setData(paperEvaluationBean);
        }


    }

    protected void setData(PaperEvaluationBean paperEvaluationBean) {
        if (paperEvaluationBean == null) {
            shortToast("测试结果获取失败！");
            return;
        }
        EvaluationBean evaluationBean = paperEvaluationBean.getEvaluation();
        if (evaluationBean == null) {
            shortToast("测试结果获取失败！");
            return;
        }
        tvName.setText(paperName);
        //时间
        tvUseTime.setText(evaluationBean.getAnswering_time_str());
        tvTotalFraction.setText(evaluationBean.getPaper_score() + "分");
        tvTotalTime.setText(evaluationBean.getAnswer_time_str());
        //统计
        tvFraction.setText(evaluationBean.getTotal_score() + "");
        tvCorrect.setText(evaluationBean.getSuccess_num() + "");
        tvError.setText(evaluationBean.getFail_num() + "");
        tvNotDone.setText(evaluationBean.getUnsolved_num() + "");
        //
        tvTime.setText(evaluationBean.getCreate_time_str());
        //判分
        tvMultipleChoiceScore.setText(evaluationBean.getChoose_score() + "分");
        tvAnswerQuestionScore.setText(evaluationBean.getAnswer_score() + "分");
        //
        if (TextUtils.isEmpty(evaluationBean.getScore_assessment())) {
            tvTip.setVisibility(View.GONE);
        } else {
            tvTip.setText(evaluationBean.getScore_assessment());
        }
        //推荐课程
        if (evaluationBean.getGroupData() != null && evaluationBean.getGroupData().size() > 0) {
            if (groupDataBeans == null)
                groupDataBeans = new ArrayList<>();
            groupDataBeans.addAll(evaluationBean.getGroupData());
            if (recommendClassAdapter == null) {
                recommendClassAdapter = new RecommendClassAdapter(groupDataBeans, this);
                recommendClassAdapter.setMyItemOnClickListener(this);
            }
            recyclerView.setAdapter(recommendClassAdapter);
            recommendClassAdapter.notifyDataSetChanged();
        }


    }

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.tv_back_list, R.id.tv_again, R.id.tv_answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back_list:
                finish();
                break;
            case R.id.iv_right:
                if (mShareAction == null)
                    initShare();
                mShareAction.open();
                break;
            case R.id.tv_again:
                source = "1";
                startActivity(new Intent(this, TestPaperInfoActivity.class)
                        .putExtra("paperId", paperId)
                        .putExtra("recordId", recordId));
                finish();
                break;
            case R.id.tv_answer:
                source = "5";
                startActivity(new Intent(this, SimulationParsingActivity.class)
                        .putExtra("paperId", paperId)
                        .putExtra("recordId", recordId)
                        .putExtra("source", source)
                        .putExtra("paperName", paperName));
                break;
        }
    }

    @Override
    public void onItemClick(int position, View view) {
        startActivity(new Intent(this, ClassInfoActivity.class)
                .putExtra("groupId", groupDataBeans.get(position).getId())
                .putExtra("catId", groupDataBeans.get(position).getCat_id()));
    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
