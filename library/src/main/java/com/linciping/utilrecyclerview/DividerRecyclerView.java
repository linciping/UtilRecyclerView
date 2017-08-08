package com.linciping.utilrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


/**
 * @author linciping
 * @time 2017/5/17
 * @Description 带分割线的RecyclerView
 */
public class DividerRecyclerView extends RecyclerView {

    private int mDividerHeight;
    private int mDividerColor = Color.GRAY;


    public DividerRecyclerView(Context context) {
        this(context, null);
    }

    public DividerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, com.linciping.utilrecyclerview.R.styleable.DividerRecyclerView, defStyle, 0);
        mDividerHeight = array.getDimensionPixelSize(com.linciping.utilrecyclerview.R.styleable.DividerRecyclerView_dividerHeight, 3);
        mDividerColor = array.getColor(com.linciping.utilrecyclerview.R.styleable.DividerRecyclerView_dividerColor, Color.parseColor("#eeeeee"));
        if (mDividerColor != 0) {
            addItemDecoration(new DividerItemDecoration());
        }
        array.recycle();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    private class DividerItemDecoration extends ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager != null) {
                if (layoutManager instanceof GridLayoutManager) {
                    outRect.bottom = mDividerHeight;
                    outRect.right = mDividerHeight;
                } else if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                        outRect.bottom = mDividerHeight;
                    } else if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                        outRect.right = mDividerHeight;
                    }
                }
            } else {
                outRect.bottom = mDividerHeight;
            }

        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state) {
            LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager != null) {
                if (layoutManager instanceof GridLayoutManager) {
                    gridDivider(c, parent);
                } else if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                        normalDivider(c, parent);
                    } else if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                        normalHorDivider(c, parent);
                    }
                }
            } else {
                normalDivider(c, parent);
            }

        }

        /**
         * 水平分割线
         *
         * @param c
         * @param parent
         */
        private void normalDivider(Canvas c, RecyclerView parent) {
            Paint paint = new Paint();
            paint.setColor(mDividerColor);
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + mDividerHeight;
                c.drawRect(left, top, right, bottom, paint);
            }
        }

        /**
         * 垂直分割线
         *
         * @param c
         * @param parent
         */
        private void normalHorDivider(Canvas c, RecyclerView parent) {
            Paint paint = new Paint();
            paint.setColor(mDividerColor);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                View view = linearLayoutManager.findViewByPosition(i);
                float left = view.getRight();
                float right = view.getRight() + mDividerHeight;
                float top = view.getTop();
                float bottom = view.getBottom();
                c.drawRect(left, top, right, bottom, paint);
            }
        }

        /**
         * 垂直分割线
         *
         * @param c
         * @param parent
         */
        private void gridDivider(Canvas c, RecyclerView parent) {
            int childCount = parent.getChildCount();
            Paint paint = new Paint();
            paint.setColor(mDividerColor);
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            for (int i = 0; i < childCount; i++) {
                View view = gridLayoutManager.findViewByPosition(i);
                float left = view.getRight();
                float right = view.getRight() + mDividerHeight;
                float top = view.getTop();
                float bottom = view.getBottom();
                c.drawRect(left, top, right, bottom, paint);

                left = view.getLeft();
                right = view.getLeft() + view.getWidth();
                top = view.getBottom();
                bottom = view.getBottom() + mDividerHeight;
                c.drawRect(left, top, right, bottom, paint);
            }

        }
    }
}
