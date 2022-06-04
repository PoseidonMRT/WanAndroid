package com.poseidon.lib.common.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.res.ResourcesCompat;

import com.poseidon.lib.common.R;

public class BottomNavigationBarMenuItem {
    static final int NO_ID = 0;
    private static final int defaultItemId = NO_ID;
    private static final boolean defaultItemChecked = false;
    private static final boolean defaultNeedShowTitle = true;
    private static final boolean defaultItemVisible = true;
    private static final boolean defaultItemEnabled = true;
    private CharSequence mTitle;
    private int mItemId;
    private int mIconRes;
    private boolean mDefaultSelected = false;
    private boolean mItemVisible;
    private boolean mItemEnabled;
    private int mIconWidth;
    private int mIconHeight;
    private boolean mTitleVisible;
    public BottomNavigationBarMenuItem(Context context,AttributeSet attrs) {
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
                R.styleable.BottomNavigationMenuItem);

        mItemId = a.getResourceId(R.styleable.BottomNavigationMenuItem_android_id, defaultItemId);
        mTitle = a.getText(R.styleable.BottomNavigationMenuItem_android_title);
        mIconRes = a.getResourceId(R.styleable.BottomNavigationMenuItem_android_icon,0);
        mDefaultSelected = a.getBoolean(R.styleable.BottomNavigationMenuItem_defaultSelected, defaultItemChecked);
        mItemVisible = a.getBoolean(R.styleable.BottomNavigationMenuItem_android_visible, defaultItemVisible);
        mItemEnabled = a.getBoolean(R.styleable.BottomNavigationMenuItem_android_enabled, defaultItemEnabled);
        mIconWidth = (int) a.getDimension(R.styleable.BottomNavigationMenuItem_iconWidth, R.dimen.default_navigation_menu_item_icon_width);
        mIconHeight = (int) a.getDimension(R.styleable.BottomNavigationMenuItem_iconHeight, R.dimen.default_navigation_menu_item_icon_height);
        mTitleVisible = a.getBoolean(R.styleable.BottomNavigationMenuItem_titleVisible, defaultNeedShowTitle);

        a.recycle();
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public int getItemId() {
        return mItemId;
    }

    public int getIconRes() {
        return mIconRes;
    }

    public boolean isDefaultSelected() {
        return mDefaultSelected;
    }

    public boolean isItemVisible() {
        return mItemVisible;
    }

    public boolean isItemEnabled() {
        return mItemEnabled;
    }

    public float getIconWidth() {
        return mIconWidth;
    }

    public float getIconHeight() {
        return mIconHeight;
    }

    public boolean isTitleVisible() {
        return mTitleVisible;
    }

    @Override
    public String toString() {
        return "BottomNavigationBarMenuItem{" +
                "mTitle=" + mTitle +
                ", mItemId=" + mItemId +
                ", mIconRes=" + mIconRes +
                ", mDefaultSelected=" + mDefaultSelected +
                ", mItemVisible=" + mItemVisible +
                ", mItemEnabled=" + mItemEnabled +
                ", mIconWidth=" + mIconWidth +
                ", mIconHeight=" + mIconHeight +
                ", mTitleVisible=" + mTitleVisible +
                '}';
    }
}
