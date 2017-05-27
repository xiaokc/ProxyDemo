package com.android.cong.proxydemo.hook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Instrumentation;
import android.util.Log;

/**
 * Created by xiaokecong on 27/05/2017.
 */

public class HookHelper {
    private volatile static HookHelper mInstance;

    private HookHelper(){}

    public static HookHelper getInstance() {
        if (null == mInstance){
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
            Log.e("===>xkc","ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e("===>xkc","NoSuchMethodException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("===>xkc","IllegalAccessException");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.e("===>xkc","NoSuchFieldException");
        }
    }
}
