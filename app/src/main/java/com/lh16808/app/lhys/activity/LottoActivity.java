package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.marco.ApiConfig;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.other.ShowBannerInfo;


public class LottoActivity extends BaseActivity {

    private WebView mWebView;
    private ShowBannerInfo mShowBannerInfo;
    private ProgressBar mProgressBar;
    String baseUrl;
    private String title;

    public static void start(Context context) {
        Intent starter = new Intent(context, LottoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initLoadData() {
        Intent intent = getIntent();
        String web_key = intent.getStringExtra("web_key");
        title = intent.getStringExtra("web_title");
        if (TextUtils.isEmpty(title)) {
            if (TextUtils.isEmpty(web_key)) {
                baseUrl = ApiConfig.getBaseUrl(ApiConfig.KaiJianRQURL);
            } else {
                baseUrl = ApiConfig.getBaseUrl(ApiConfig.KaiJianFXURL);
            }
        } else {
            baseUrl = web_key;
        }
    }

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_lotto_banner);
        if (!TextUtils.isEmpty(title)) {
            TextView viewById = (TextView) findViewById(R.id.tool_bar_title);
            viewById.setText(title);
        }
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_lotto_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lotto);
        mWebView = (WebView) findViewById(R.id.web_lotto);
        mProgressBar = (ProgressBar) findViewById(R.id.pro_lotto);
        mProgressBar.setMax(100);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadData(Constants.errorHtml, "text/html; charset=UTF-8", null);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() != View.VISIBLE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mWebView.loadUrl(baseUrl);
    }


    @Override
    protected void loadData() {

    }


}
