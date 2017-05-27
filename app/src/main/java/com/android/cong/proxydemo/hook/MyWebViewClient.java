package com.android.cong.proxydemo.hook;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by xiaokecong on 27/05/2017.
 */

public class MyWebViewClient extends WebViewClient {
    private final String finishJsCode = "var input="
            + "document.getElementById(\"new-bdvSearchBtn\");input&&input"
            + ".click&&input"
            + ".click();";
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (url.endsWith("baidu.com")) {
//            view.loadUrl("javascript:(function(){" + finishJsCode + "})();");
            view.loadUrl("javascript:(alert(\"hello world\"))");
            Log.i("===>xkc","hook end");
        }

    }
}
