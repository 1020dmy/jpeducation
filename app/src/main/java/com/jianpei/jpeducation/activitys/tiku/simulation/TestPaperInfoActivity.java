package com.jianpei.jpeducation.activitys.tiku.simulation;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.tiku.simulation.SimulationExerciseActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.PaperInfoBean;
import com.jianpei.jpeducation.bean.tiku.TestPaperBean;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import butterknife.BindView;
import butterknife.OnClick;

public class TestPaperInfoActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_years)
    TextView tvYears;
    @BindView(R.id.ty_type)
    TextView tyType;
    @BindView(R.id.tv_total_score)
    TextView tvTotalScore;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_people_num)
    TextView tvPeopleNum;
    @BindView(R.id.tv_single_title)
    TextView tvSingleTitle;
    @BindView(R.id.tv_single_content)
    TextView tvSingleContent;
    @BindView(R.id.tv_multiple_title)
    TextView tvMultipleTitle;
    @BindView(R.id.tv_multiple_content)
    TextView tvMultipleContent;
    @BindView(R.id.tv_reply_title)
    TextView tvReplyTitle;
    @BindView(R.id.tv_reply_content)
    TextView tvReplyContent;
    @BindView(R.id.button)
    Button button;
    private AnswerModel answerModel;
    //

    private String paperId;//试卷Id
    private String recordId;//答题记录

    //
    private int restartType;//0添加新试卷，2重做，1继续答题
    private String paperName;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_test_paper_info;
    }

    @Override
    protected void initView() {
        tvTitle.setText("试卷详情");

        answerModel = new ViewModelProvider(this).get(AnswerModel.class);

    }

    @Override
    protected void initData() {
        paperId = getIntent().getStringExtra("paperId");
        recordId = getIntent().getStringExtra("recordId");
        //后去试卷详情结果
        answerModel.getPaperInfoBeanLiveData().observe(this, new Observer<PaperInfoBean>() {
            @Override
            public void onChanged(PaperInfoBean paperInfoBean) {
                dismissLoading();
                setData(paperInfoBean);


            }
        });
        //获取试卷详情失败
        answerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });

        showLoading("");
        answerModel.paperInfo(paperId);

    }

    private void setData(PaperInfoBean paperInfoBean) {
        if (paperInfoBean == null) {
            return;
        }
        restartType = paperInfoBean.getComplete_status();
        paperName = paperInfoBean.getPaper_name();
        //
        tvName.setText(paperName);
        tvYears.setText(paperInfoBean.getTrue_topic_year());
        tyType.setText(paperInfoBean.getPaper_type_str());
        tvTime.setText(paperInfoBean.getAnswer_time() + "分钟");
        tvPeopleNum.setText(paperInfoBean.getAnswer_number());

        tvSingleTitle.setText(paperInfoBean.getSingle_des());
        tvSingleContent.setText(paperInfoBean.getSingle_son_des());

        tvMultipleTitle.setText(paperInfoBean.getMultiple_des());
        tvMultipleContent.setText(paperInfoBean.getMultiple_son_des());

        tvReplyTitle.setText(paperInfoBean.getReply_des());
        tvReplyContent.setText(paperInfoBean.getReply_son_des());
        tvTotalScore.setText(paperInfoBean.getTotal_score() + "分");

        if (restartType == 0) {
            button.setText("开始考试");
        } else if (restartType == 1) {
            button.setText("继续考试");
        } else if (restartType == 2) {
            button.setText("重新考试");
        }
    }


    @OnClick({R.id.iv_back, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.button:
                startActivity(new Intent(this, SimulationExerciseActivity.class)
                        .putExtra("recordId", recordId)
                        .putExtra("paperId", paperId)
                        .putExtra("paperName", paperName)
                        .putExtra("restartType", restartType));
                finish();
                break;
        }
    }
}
