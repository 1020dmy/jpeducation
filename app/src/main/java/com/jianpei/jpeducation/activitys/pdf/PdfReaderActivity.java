package com.jianpei.jpeducation.activitys.pdf;


import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class PdfReaderActivity extends BaseNoStatusActivity {

    @BindView(R.id.iv_statue)
    ImageView ivStatue;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.tv_pageCount)
    TextView tvPageCount;
    @BindView(R.id.tv_page)
    TextView tvPage;

    private MaterialInfoBean materialInfoBean;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_pdf_reader;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(ivStatue);
        materialInfoBean = getIntent().getParcelableExtra("materialInfoBean");
        if (materialInfoBean == null) {
            shortToast("资料读取失败！");
            return;
        }
        tvTitle.setText(materialInfoBean.getTitle());


    }

    @Override
    protected void initData() {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), materialInfoBean.getTitle());
        pdfView.fromFile(file).onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {
                tvPage.setText(page + 1 + "");
                tvPageCount.setText(pageCount + "");

            }
        }).load();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pdfView != null)
            pdfView.recycle();
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


}
