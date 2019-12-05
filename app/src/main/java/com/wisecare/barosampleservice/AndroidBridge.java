package com.wisecare.barosampleservice;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.core.app.ActivityCompat;

public class AndroidBridge {

    private String TAG = "AndroidBridge";

    final public Handler handler = new Handler();
    private WebView mWebView;
    private  MainActivity mContext;

    public AndroidBridge(WebView _mWebView, MainActivity _mContext){
        mWebView = _mWebView;
        mContext = _mContext;
    }

    @JavascriptInterface
    public void callFromWebview(final String message){

        //Log.d(TAG, message);
        handler.post(new Runnable() {
            @Override
            public void run() {

                mWebView.loadUrl("javascript:alert('["+ message +"] 라고 로그를 넘김')");

                switch(message){
                    case "App End":
                        finishApp();
                        break;
                    default:
                        Log.d(TAG, "Default");
                }

            }
        });
    }

    private void finishApp(){
        mWebView.clearCache(true);
        mWebView.clearHistory();
        clearCookies(mContext);

        ActivityCompat.finishAffinity(mContext);
        System.exit(0);
    }


    public void clearCookies(Context context){
        Log.d(TAG, "clearCookies");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){
            Log.d(TAG, "clearCookies Version: " + Build.VERSION.SDK_INT );
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }else{
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

}
