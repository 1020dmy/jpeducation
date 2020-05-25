package com.jianpei.jpeducation.activitys.pdf;


import android.widget.ImageView;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;


import butterknife.BindView;
import butterknife.OnClick;

public class PdfReaderActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_pdf_reader;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
