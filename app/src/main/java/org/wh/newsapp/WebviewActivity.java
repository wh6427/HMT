package org.wh.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by kkk on 2016/5/26.
 */
public class WebviewActivity extends Activity{
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = (WebView) findViewById(R.id.web);
        Intent intent = getIntent();
        String newsUrl = intent.getStringExtra("url");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(newsUrl);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
