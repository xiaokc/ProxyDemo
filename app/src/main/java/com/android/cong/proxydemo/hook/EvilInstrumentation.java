package com.android.cong.proxydemo.hook;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by xiaokecong on 27/05/2017.
 */

public class EvilInstrumentation extends Instrumentation {


    Instrumentation mBase;

    public EvilInstrumentation(Instrumentation mInstrumentation) {
        this.mBase = mInstrumentation;
    }


    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        // Hook之前
        Log.i("===>xkc","hook之前：who="+who+",contextThread="+contextThread+",token="+token+",target="+target+","
                + "intent="+intent+",requestCode="+requestCode+",options="+options);


        // 开始调用原始的方法, 调不调用随你,但是不调用的话, 所有的startActivity都失效了.
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        try {
            Method execStartActivityMethod = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class,Bundle.class);
            execStartActivityMethod.setAccessible(true);
            return (ActivityResult) execStartActivityMethod.invoke(mBase, who, contextThread,token,target,intent,requestCode,options);

        } catch (Exception e) {
            throw new RuntimeException("do not support!!! pls adapt it!");
        }
    }

}
