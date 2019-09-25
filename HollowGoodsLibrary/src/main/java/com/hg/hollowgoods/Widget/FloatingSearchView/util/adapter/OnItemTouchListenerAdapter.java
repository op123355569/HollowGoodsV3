package com.hg.hollowgoods.Widget.FloatingSearchView.util.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

public abstract class OnItemTouchListenerAdapter implements RecyclerView.OnItemTouchListener {

    private static final String TAG = "OnItemTouchListenerAdapter";

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
