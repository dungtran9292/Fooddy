package com.example.hoang.fooddy.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by VT-99 on 13/10/2017.
 */
public class ViewPagerCustom extends ViewPager {

    public ViewPagerCustom(Context context) {
        super(context);
    }

    public ViewPagerCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

}
