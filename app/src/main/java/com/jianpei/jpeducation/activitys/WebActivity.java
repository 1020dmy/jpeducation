package com.jianpei.jpeducation.activitys;


import android.os.Build;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webView)
    WebView webView;

    private String url;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(tvStatus);


        url = getIntent().getStringExtra("customerServiceUrl");

        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings()
                    .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient());
        tvTitle.setText("在线客服");


    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(url))
            webView.loadUrl(url);

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();

    }
}
