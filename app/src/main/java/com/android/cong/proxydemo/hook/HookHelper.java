package com.android.cong.proxydemo.hook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by xiaokecong on 27/05/2017.
 */

public class HookHelper {
    private volatile static HookHelper mInstance;

    private HookHelper() {
    }

    public static HookHelper getInstance() {
        if (null == mInstance) {
            synchronized(HookHelper.class) {
                if (null == mInstance) {
                    mInstance = new HookHelper();
                }
            }
        }
        return mInstance;
    }

    public void hookStartActivity() {
        try {
            // 获取到当前的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 获取到原始的mInstrumentation字段
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

            // 创建代理对象
            Instrumentation evilInstumentation = new EvilInstrumentation(mInstrumentation);

            // 修改，偷梁换柱
            mInstrumentationField.set(currentActivityThread, evilInstumentation);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("===>xkc", "ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e("===>xkc", "NoSuchMethodException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("===>xkc", "IllegalAccessException");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.e("===>xkc", "NoSuchFieldException");
        }
    }

    /**
     * hook WebViewClient
     * 在WebView.setWebViewClient()方法中篡改参数WebViewClient为自定义的WebViewClient
     *
     * @param webView
     */
    public void hookWebViewClient(WebView webView) {
        try {
            Class<?> webViewClass = Class.forName("android.webkit.WebView");
            Method setWebViewClientMethod = webViewClass.getDeclaredMethod("setWebViewClient", WebViewClient.class);
            setWebViewClientMethod.invoke(webView, new MyWebViewClient());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * hook String
     * 在WebView.loadUrl()方法中篡改Url参数
     * @param webView
     */
    public void hookLoadUrl(WebView webView) {
        try {
            Class<?> webViewClass = Class.forName("android.webkit.WebView");
            Method setWebViewClientMethod = webViewClass.getDeclaredMethod("loadUrl", String.class);
            setWebViewClientMethod.invoke(webView, "http://www.vip.com");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void traverse(Activity activity) {
        ViewGroup root = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        final List<WebView> webViews = new ArrayList<>();
        LayoutTraverse.build(new LayoutTraverse.Processor() {
            @Override
            public void process(View view) {
                if (view instanceof WebView) {
                    WebView v = (WebView) view;
                    webViews.add(v);
                }
            }

            @Override
            public void traverseEnd(ViewGroup root) {
                Log.i("===>xkc","webview size:"+webViews.size());
                if (webViews.size() > 0) {
                    WebView webView = webViews.get(0);
                    hookWebViewClient(webView);
                }

            }
        }).traverse(root);

    }
}
