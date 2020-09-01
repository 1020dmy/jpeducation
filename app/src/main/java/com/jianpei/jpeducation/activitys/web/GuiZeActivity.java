package com.jianpei.jpeducation.activitys.web;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuiZeActivity extends BaseActivity {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webView)
    WebView webView;

    String url;
    String title;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_gui_ze;
    }

    @Override
    protected void initView() {


        url = getIntent().getStringExtra("webUrl");
        title = getIntent().getStringExtra("title");
        L.e("========url:" + url);
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings()
                    .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + ";jianpei/app");
        L.e("========setUserAgentString=" + webView.getSettings().getUserAgentString());
        webView.setWebViewClient(new WebViewClient());
        tvTitle.setText(title);


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