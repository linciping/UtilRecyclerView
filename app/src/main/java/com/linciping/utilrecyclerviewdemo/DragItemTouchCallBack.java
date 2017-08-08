package com.linciping.utilrecyclerviewdemo;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

/**
 * Created by linciping on 2017/6/27.
 */

public class DragItemTouchCallBack<T> extends ItemTouchHelper.Callback {

    private List<T> mResults;

    public DragItemTouchCallBack(List<T> results) {
        mResults = results;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragDirs, swipeDirs;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragDirs = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeDirs = 0;
        } else {
            dragDirs = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            swipeDirs = 0;
        }
        return makeMovementFlags(dragDirs, swipeDirs);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int startPosition = viewHolder.getAdapterPosition();
        int endPosition = target.getAdapterPosition();
        if (startPosition < endPosition) {
            for (int i = startPosition; i < endPosition; i++) {
                Collections.swap(mResults, i, i + 1);
            }
        } else {
            for (int i = startPosition; i > endPosition; i--) {
                Collections.swap(mResults, i, i - 1);
            }
        }
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        adapter.notifyItemMoved(startPosition, endPosition);
        return true;
    }

    //当长按选中item的时候（拖拽开始的时候）调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);

    }

    //当手指松开的时候（拖拽完成的时候）调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
