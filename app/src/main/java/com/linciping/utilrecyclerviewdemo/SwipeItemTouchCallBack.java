package com.linciping.utilrecyclerviewdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.List;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

/**
 * Created by linciping on 2017/6/27.
 */

public class SwipeItemTouchCallBack<T> extends ItemTouchHelper.Callback {

    private RecyclerView.Adapter mAdapter;
    private List<T> mResult;

    private static final String TAG = "SwipeItemTouchCallBack";

    public SwipeItemTouchCallBack(RecyclerView.Adapter adapter, List<T> result) {
        mAdapter = adapter;
        mResult = result;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragDirs, swipeDirs;
        dragDirs = 0;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            swipeDirs = 0;
        }
        return makeMovementFlags(dragDirs, swipeDirs);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            Log.e(TAG, String.valueOf(dX));
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }
}
