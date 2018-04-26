package com.anshi.healthdeyi.view.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.anshi.healthdeyi.R;
import com.anshi.healthdeyi.utils.Constants;
import com.anshi.healthdeyi.utils.StatubarUtils;
import com.anshi.healthdeyi.utils.StatusBarUtils;
import com.anshi.healthdeyi.view.base.BaseActivity;
import com.anshi.healthdeyi.view.self.ProgressWebView;

/**
 *
 * Created by yulu on 2018/4/2.
 */

public class WebActivity extends BaseActivity {
    private ProgressWebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        String url = getIntent().getStringExtra("url");
        if (getIntent().getAction().equals("self")){
            mWebView.loadUrl(Constants.WEB_FORMATION_URL+"?id="+url);
        }else {
            mWebView.loadUrl(url);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebView = (ProgressWebView) findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 关闭缩放
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        // 设定缩放控件隐藏
        settings.setDisplayZoomControls(false);
        // 设置加载进来的页面自适应手机屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatubarUtils.setStatusTextColor(true,this);
    }
}
