package com.wisecare.barosampleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 없애기
        setContentView(R.layout.activity_main);

        String siteUrl = this.getResources().getString(R.string.site_url);

        mWebView = (WebView)findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용 가능하도록 설정
        mWebView.loadUrl(siteUrl);
        mWebView.setWebChromeClient(new WebChromeClient()); // 웹뷰에서 크롭 실행 가능하도록 설정
        mWebView.setWebViewClient(new WebViewCleintClass()); // 새창열기 없이 웹뷰내에서 다시 열기

        AndroidBridge bridge = new AndroidBridge(mWebView, MainActivity.this);
        mWebView.addJavascriptInterface(bridge, "Android");


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){// 뒤로가기 버튼 이벤트 처리
        if((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private class WebViewCleintClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            Log.d("check URL", url);
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

}
