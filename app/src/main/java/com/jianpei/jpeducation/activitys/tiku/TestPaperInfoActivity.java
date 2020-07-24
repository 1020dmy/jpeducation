package com.jianpei.jpeducation.activitys.tiku;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
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
    private TestPaperBean testPaperBean;
    private PaperInfoBean mPaperInfoBean;

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
        testPaperBean = getIntent().getParcelableExtra("testPaperBean");
        answerModel.getPaperInfoBeanLiveData().observe(this, new Observer<PaperInfoBean>() {
            @Override
            public void onChanged(PaperInfoBean paperInfoBean) {
                dismissLoading();
                mPaperInfoBean = paperInfoBean;
                setData();


            }
        });
        answerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });

        showLoading("");
        answerModel.paperInfo(testPaperBean.getId());

    }

    private void setData() {
        if (testPaperBean == null) {
            shortToast("获取数据失败！");
            return;
        }
        tvName.setText(mPaperInfoBean.getPaper_name());
        tvYears.setText(mPaperInfoBean.getTrue_topic_year());
        tyType.setText(mPaperInfoBean.getPaper_type_str());
        tvTotalScore.setText("");
        tvTime.setText(mPaperInfoBean.getAnswer_time() + "分钟");
        tvPeopleNum.setText(mPaperInfoBean.getAnswer_number());

        tvSingleTitle.setText(mPaperInfoBean.getSingle_des());
        tvSingleContent.setText(mPaperInfoBean.getSingle_son_des());

        tvMultipleTitle.setText(mPaperInfoBean.getMultiple_des());
        tvMultipleContent.setText(mPaperInfoBean.getMultiple_son_des());

        tvReplyTitle.setText(mPaperInfoBean.getReply_des());
        tvReplyContent.setText(mPaperInfoBean.getReply_son_des());
        tvTotalScore.setText(mPaperInfoBean.getTotal_score() + "分");

        if (mPaperInfoBean.getComplete_status() == 0) {
            button.setText("开始考试");
        } else if (mPaperInfoBean.getComplete_status() == 1) {
            button.setText("继续考试");
        } else if (mPaperInfoBean.getComplete_status() == 2) {
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
                startActivity(new Intent(this, SimulationExerciseActivity.class).putExtra("paperInfoBean", mPaperInfoBean).putExtra("recordId", testPaperBean.getUser_record_id()));
                finish();
                break;
        }
    }
}
