package com.hg.hollowgoods.Widget.TanTan;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

import static com.hg.hollowgoods.Widget.TanTan.CardConfig.MAX_SHOW_COUNT;
import static com.hg.hollowgoods.Widget.TanTan.CardConfig.SCALE_GAP;
import static com.hg.hollowgoods.Widget.TanTan.CardConfig.TRANS_Y_GAP;

/**
 * 人人控件Callback
 * Created by Hollow Goods on unknown.
 */

public class RenRenCallback extends ItemTouchHelper.SimpleCallback {

    protected RecyclerView mRv;
    protected List mData;
    protected RecyclerView.Adapter mAdapter;

    public RenRenCallback(RecyclerView rv, RecyclerView.Adapter adapter, List data) {
        this(0,
                ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                rv, adapter, data);
    }

    public RenRenCallback(int dragDirs, int swipeDirs
            , RecyclerView rv, RecyclerView.Adapter adapter, List data) {
        super(dragDirs, swipeDirs);
        mRv = rv;
        mAdapter = adapter;
        mData = data;
    }

    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        return mRv.getWidth() * getSwipeThreshold(viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Log.e("swipecard", "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
        //rollBack(viewHolder);
        //★实现循环的要点
        Object remove = mData.remove(viewHolder.getLayoutPosition());
        mData.add(0, remove);
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //Log.e("swipecard", "onChildDraw()  viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
        //人人影视的效果
        //if (isCurrentlyActive) {
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }
        //对每个ChildView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = childCount - i - 1;
            if (level > 0) {
                child.setScaleX((float) (1 - SCALE_GAP * level + fraction * SCALE_GAP));

                if (level < MAX_SHOW_COUNT - 1) {
                    child.setScaleY((float) (1 - SCALE_GAP * level + fraction * SCALE_GAP));
                    child.setTranslationY((float) (TRANS_Y_GAP * level - fraction * TRANS_Y_GAP));
                } else {
                    //child.setTranslationY((float) (mTranslationYGap * (level - 1) - fraction * mTranslationYGap));
                }
            }
        }
    }
}
