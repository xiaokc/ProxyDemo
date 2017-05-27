package com.android.cong.proxydemo;

import com.android.cong.proxydemo.hook.HookHelper;

import android.app.Application;

/**
 * Created by xiaokecong on 27/05/2017.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HookHelper.getInstance().hookStartActivity();
    }
}
