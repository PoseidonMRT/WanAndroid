package com.poseidon.wanandroid.leakcanary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class FragmentWatcher {
    private static final String TAG = "FragmentWatcher";
    private static volatile FragmentWatcher mInstance;
    private FragmentWatcher(){

    }
    public static FragmentWatcher getInstance(){
        if (null == mInstance) {
            synchronized (FragmentWatcher.class) {
                if (null == mInstance) {
                    mInstance = new FragmentWatcher();
                }
            }
        }
        return mInstance;
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                watchFragment(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    private void watchFragment(Activity activity) {
        if (isClassAvailable("androidx.fragment.app.Fragment") && activity instanceof FragmentActivity) {
            FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
            fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                    super.onFragmentViewDestroyed(fm, f);
                }

                @Override
                public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                    super.onFragmentDestroyed(fm, f);
                }
            },true);
        }

        if (isClassAvailable("android.support.v4.app.Fragment")) {
            if (activity instanceof FragmentActivity) {
                FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
                fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                        super.onFragmentViewDestroyed(fm, f);
                    }

                    @Override
                    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                        super.onFragmentDestroyed(fm, f);
                    }
                },true);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.registerFragmentLifecycleCallbacks(new android.app.FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentViewDestroyed(android.app.FragmentManager fm, android.app.Fragment f) {
                    super.onFragmentViewDestroyed(fm, f);
                }

                @Override
                public void onFragmentDestroyed(android.app.FragmentManager fm, android.app.Fragment f) {
                    super.onFragmentDestroyed(fm, f);
                }
            },true);
        }
    }

    private boolean isClassAvailable(String classname) {
        try {
            Class.forName(classname);
            return true;
        }catch (Throwable e) {
            return false;
        }
    }
}
