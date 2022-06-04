package com.poseidon.lib.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.utils.LinearCurveFit;

import com.poseidon.lib.common.R;

import java.util.List;

public class BottomNavigationBar extends LinearLayout {
    public static final int DEFAULT_FLOATING_DISTANCE = 100;
    private Context mContext;
    private boolean mIsNeedFloating;
    private List<BottomNavigationBarMenuItem> mMenuItems;
    private int mWidth;
    private int mHeight;
    private Path mBgPath;
    private Paint mBgPaint;

    public BottomNavigationBar(Context context) {
        this(context, null);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        mContext = context;
        setBackgroundColor(getResources().getColor(R.color.color_transparent));
        setClipChildren(false);
        setOrientation(HORIZONTAL);
        inflateAttributeSets(attributeSet);
        initBgPaint();
    }

    private void initBgPaint() {
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setStrokeWidth(5);
        mBgPaint.setColor(Color.YELLOW);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void inflateAttributeSets(AttributeSet attributeSet) {
        TypedArray a = mContext.obtainStyledAttributes(attributeSet, R.styleable.BottomNavigationBar, 0, 0);
        try {
            mIsNeedFloating = a.getBoolean(R.styleable.BottomNavigationBar_isShowingFloating, false);
            int menuId = a.getResourceId(R.styleable.BottomNavigationBar_navigationMenu, 0);
            if (menuId != 0) {
                mMenuItems = BottomNavigationBarMenuParser.inflate(mContext, menuId);
            }
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            mWidth = w;
            mHeight = h;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight()+100);
    }

    private void correctBgPath(int childWidth,int floatingIndex) {
        float radius = childWidth / 2.0f;
        mBgPath = new Path();
        mBgPath.moveTo(0, DEFAULT_FLOATING_DISTANCE);
        mBgPath.lineTo(childWidth*floatingIndex,DEFAULT_FLOATING_DISTANCE);
        RectF rectF = new RectF(childWidth*floatingIndex,DEFAULT_FLOATING_DISTANCE-radius,childWidth*(floatingIndex+1),DEFAULT_FLOATING_DISTANCE+radius);
        mBgPath.arcTo(rectF,-180,-180);
        mBgPath.lineTo(mWidth,DEFAULT_FLOATING_DISTANCE);
        mBgPath.lineTo(mWidth,mHeight);
        mBgPath.lineTo(0,mHeight);
        mBgPath.close();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childWidth = mWidth / getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
            if (i == 2) {
                getChildAt(i).layout(childWidth * i, 0, childWidth * (i + 1), childWidth);
                LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
                layoutParams.bottomMargin = 20;
                getChildAt(i).setLayoutParams(layoutParams);
            }else{
                getChildAt(i).layout(childWidth * i, 100, childWidth * (i + 1), mHeight);
            }
        }
        correctBgPath(childWidth,2);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (mBgPath != null && mBgPaint != null) {
            canvas.drawPath(mBgPath,mBgPaint);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mMenuItems != null) {
            for (BottomNavigationBarMenuItem item : mMenuItems) {
                Log.d("ZBTUO", item.toString());
                TextView textView = new TextView(mContext);
                if (item.isTitleVisible()) {
                    textView.setText(item.getTitle());
                } else {
                    textView.setText("");
                }
                textView.setEnabled(item.isItemEnabled());
                textView.setVisibility(item.isItemVisible() ? VISIBLE : GONE);
                Drawable drawable = getResources().getDrawable(item.getIconRes());
                drawable.setBounds(0, 0, 200, 200);
                textView.setCompoundDrawables(null, drawable, null, null);
                textView.setId(item.getItemId());
                textView.setBackgroundColor(getResources().getColor(R.color.color_transparent));
                textView.setSelected(item.isDefaultSelected());
                addView(textView);
            }
        }
    }
}
