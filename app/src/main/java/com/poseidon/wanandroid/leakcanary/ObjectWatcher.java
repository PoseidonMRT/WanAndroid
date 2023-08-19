package com.poseidon.wanandroid.leakcanary;

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.util.UUID;
import java.util.WeakHashMap;

public class ObjectWatcher {
    private static final String TAG = "ObjectWatcher";
    private ReferenceQueue mReferenceQueue;
    private WeakHashMap<String, KeyedWeakReference> mReferences;


    private ObjectWatcher() {
        mReferenceQueue = new ReferenceQueue<Object>();
        mReferences = new WeakHashMap<>();


    }

    private static volatile ObjectWatcher mInstance;

    public static ObjectWatcher getInstance() {
        if (mInstance == null) {
            synchronized (ObjectWatcher.class) {
                if (mInstance == null) {
                    mInstance = new ObjectWatcher();
                }
            }
        }
        return mInstance;
    }

    public void watch(Object object) {
        removeWeaklyReachableObjects();
        String key = UUID.randomUUID().toString();
        Log.d(TAG, "watch object:" + object + ",key:" + key);
        KeyedWeakReference weakReference = new KeyedWeakReference(key, object, mReferenceQueue);
        mReferences.put(key, weakReference);

        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                removeWeaklyReachableObjects();
                KeyedWeakReference keyedWeakReference = mReferences.get(key);
                if (keyedWeakReference != null) {
                    // key对应的object可能发生内存泄漏，dump内存堆栈
                    Log.d(TAG, "keyedReference:" + keyedWeakReference.toString());
                    try {
                        Debug.dumpHprofData("/sdcard/Download/tmp.hprof");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }, 5000);
    }

    private void removeWeaklyReachableObjects() {
        KeyedWeakReference keyedWeakReference = null;
        do {
            keyedWeakReference = (KeyedWeakReference) mReferenceQueue.poll();
            Log.d(TAG, "keyedWeakReference:" + keyedWeakReference);
            if (keyedWeakReference != null) {
                mReferences.remove(keyedWeakReference.getKey());
                Log.d(TAG, "object has been destroyed:" + keyedWeakReference.toString());
            }
        } while (keyedWeakReference != null);
    }

}
