package com.jianpei.jpeducation.activitys.web;


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
import com.jianpei.jpeducation.utils.SpUtils;

import butterknife.BindView;


public class KeFuActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webView)
    WebView webView;

    String url;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {

        tvTitle.setText("在线客服");

        url = SpUtils.getValue("customerServiceUrl");//存储客服地址

        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings()
                    .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient());


    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(url))
            webView.loadUrl(url);
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
