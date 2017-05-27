package com.android.cong.proxydemo;

import com.android.cong.proxydemo.hook.HookHelper;
import com.android.cong.proxydemo.hook.WebViewActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by xiaokecong on 27/05/2017.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HookHelper.getInstance().hookStartActivity();


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof WebViewActivity) {
                    Log.i("===>xkc","WebViewActivity onCreated()");
                    HookHelper.getInstance().traverse(activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
