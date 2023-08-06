package com.poseidon.wanandroid.leakcanary;

import android.app.Service;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class ServiceWatcher {
    private static final String TAG = "ServiceWatcher";
    private static final int STOP_SERVICE = 116;
    private Map<IBinder, Service> mActivityThreadServices;

    private ServiceWatcher() {

    }

    private static volatile ServiceWatcher mInstance;

    public static ServiceWatcher getInstance() {
        if (mInstance == null) {
            synchronized (ServiceWatcher.class) {
                if (mInstance == null) {
                    mInstance = new ServiceWatcher();
                }
            }
        }
        return mInstance;

    }

    public void init() {
        hookCallbackInMH();
        hookServicesInActivityThread();
        hookActivityManager();
    }

    private void hookCallbackInMH() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            // currentActivityThread是一个static函数所以可以直接invoke，不需要带实例参数
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);


            // 获取mH Handler对象
            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            Handler handler = (Handler) mH.get(currentActivityThread);

            // 获取Handler中Callback对象并赋值
            Field callBack = Handler.class.getDeclaredField("mCallback");
            callBack.setAccessible(true);

            callBack.set(handler, new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {
                    Log.d(TAG, "handleMessage: msg " + msg);
                    if (msg.what == STOP_SERVICE) {
                        IBinder token = (IBinder) msg.obj;
                        Service service = findServiceFromActivityThreadServices(token);
                        Log.d(TAG, "stop service is:" + service.toString());
                    }

                    return false;
                }
            });
        } catch (ClassNotFoundException | NoSuchFieldException | InvocationTargetException |
                 NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void hookServicesInActivityThread() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            // currentActivityThread是一个static函数所以可以直接invoke，不需要带实例参数
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            Field mServices = activityThreadClass.getDeclaredField("mServices");
            mServices.setAccessible(true);
            mActivityThreadServices = (Map<IBinder, Service>) mServices.get(currentActivityThread);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void hookActivityManager() {
        try {
            Object defaultSingleton = null;
            if (Build.VERSION.SDK_INT >= 26) {//1
                Class<?> activityManageClazz =
                        Class.forName("android.app.ActivityManager");
                Field field = activityManageClazz.getDeclaredField("IActivityManagerSingleton");
                field.setAccessible(true);
                //获取activityManager中的IActivityManagerSingleton字段
                defaultSingleton = field.get(null);
            } else {
                Class<?> activityManagerNativeClazz =
                        Class.forName("android.app.ActivityManagerNative");
                //获取ActivityManagerNative中的gDefault字段
                Field field = activityManagerNativeClazz.getDeclaredField("gDefault");
                field.setAccessible(true);
                defaultSingleton = field.get(null);
            }

            Class<?> singletonClazz = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClazz.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            //获取iActivityManager
            Object iActivityManager = mInstanceField.get(defaultSingleton);
            Class<?> iActivityManagerClazz =
                    Class.forName("android.app.IActivityManager");

            Object proxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerClazz},
                    new IActivityManagerProxy(iActivityManager));

            mInstanceField.set(defaultSingleton, proxy);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private Service findServiceFromActivityThreadServices(IBinder token) {
        if (mActivityThreadServices == null) {
            hookServicesInActivityThread();
        }
        return mActivityThreadServices.get(token);
    }
}
