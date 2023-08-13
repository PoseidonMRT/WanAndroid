package com.poseidon.wanandroid.leakcanary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import java.lang.reflect.Field;
import java.util.Map;

public class ViewModelWatcher {
    private static final String TAG = "ViewModelWatcher";
    private static volatile ViewModelWatcher mInstance;

    private ViewModelWatcher() {
    }

    public static ViewModelWatcher getInstance() {
        if (null == mInstance) {
            synchronized (ViewModelWatcher.class) {
                if (null == mInstance) {
                    mInstance = new ViewModelWatcher();
                }
            }
        }
        return mInstance;
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                if (activity instanceof ComponentActivity) {
                    ViewModelProvider viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) activity, new ViewModelProvider.Factory() {
                        @NonNull
                        @Override
                        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                            if (modelClass.isAssignableFrom(ViewModelClearedWatcher.class)) {
                                return (T) (new ViewModelClearedWatcher((ViewModelStoreOwner) activity));
                            }
                            return null;
                        }
                    });
                    viewModelProvider.get(ViewModelClearedWatcher.class);
                }
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

    class ViewModelClearedWatcher extends ViewModel {
        private ViewModelStoreOwner mViewModelStoreOwner;

        public ViewModelClearedWatcher(ViewModelStoreOwner viewModelStoreOwner) {
            this.mViewModelStoreOwner = viewModelStoreOwner;
        }

        @Override
        protected void onCleared() {
            Log.d(TAG, "view model has been cleared!");
            Map<String, ViewModel> map = getMapFromViewModelStore();
            if (map != null && !map.isEmpty()) {
                for (ViewModel viewModel : map.values()) {
                    Log.d(TAG, "viewModel been cleared:" + viewModel.toString());
                    ObjectWatcher.getInstance().watch(viewModel);
                }
            }
            super.onCleared();
        }

        private Map<String, ViewModel> getMapFromViewModelStore() {
            try {
                Class<?> viewModelStoreClass =
                        Class.forName(ViewModelStore.class.getName());
                Field field = viewModelStoreClass.getDeclaredField("mMap");
                field.setAccessible(true);
                return (Map<String, ViewModel>) field.get(mViewModelStoreOwner.getViewModelStore());
            } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}
