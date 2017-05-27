package com.android.cong.proxydemo.hook;

import com.android.cong.proxydemo.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by xiaokecong on 27/05/2017.
 */

public class WebViewActivity extends Activity {
    private WebView webview;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    HookHelper.getInstance().hookLoadUrl(webview);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("===>xkc", "page start");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("===>xkc", "page finish");
            }
        });

        webview.loadUrl("https://www.baidu.com");

        handler.sendEmptyMessageDelayed(0, 5000);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
