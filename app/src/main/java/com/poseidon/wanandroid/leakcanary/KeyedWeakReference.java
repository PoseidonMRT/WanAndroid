package com.poseidon.wanandroid.leakcanary;

import androidx.annotation.NonNull;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class KeyedWeakReference extends WeakReference {
    private String mKey;

    public KeyedWeakReference(String key, Object referent, ReferenceQueue q) {
        super(referent, q);
        mKey = key;
    }

    public String getKey() {
        return mKey;
    }

    @NonNull
    @Override
    public String toString() {
        return "KeyedWeakReference{ mKey=" + mKey + ",Object=" + get() + " }";
    }
}
