package com.linciping.utilrecyclerviewdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import java.util.Collections;

/**
 * Created by linciping on 2017/7/17.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "SectionDecoration";

    private DecorationCallback mDecorationCallback;

    private TextPaint mTextPaint;

    private Paint mPaint;
    private int topGap;

    private Paint.FontMetrics mFontMetrics;

    public SectionDecoration(Context context, DecorationCallback decorationCallback) {
        mDecorationCallback = decorationCallback;
        Resources resources = context.getResources();
        mPaint = new Paint();
        mPaint.setColor(resources.getColor(R.color.colorAccent));

        mTextPaint = new TextPaint();
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(80);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.getFontMetrics(mFontMetrics);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mFontMetrics = new Paint.FontMetrics();
        topGap = resources.getDimensionPixelOffset(R.dimen.sectioned_top);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        long groupId = mDecorationCallback.getGroupId(position);
        if (groupId < 0) return;
        if (isFirstInGroup(position)) {
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = mTextPaint.getTextSize() + mFontMetrics.descent;

        long preGroupId, groupId = -1;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = mDecorationCallback.getGroupId(position);
            if (groupId < 0 || groupId == preGroupId) continue;

            String textLine = mDecorationCallback.getGroupFirstLine(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                long nextGroupId = mDecorationCallback.getGroupId(position + 1);
                if (nextGroupId != groupId && viewBottom < textY) {//组内最后一个view进入了header
                    textY = viewBottom;
                }
            }
            c.drawRect(left, textY - topGap, right, textY, mPaint);
            c.drawText(textLine, left, textY, mTextPaint);
        }

    }

//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View view = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(view);
//            long groupId = mDecorationCallback.getGroupId(position);
//            String textLine = mDecorationCallback.getGroupFirstLine(position);
//            if (groupId < 0) return;
//            if (isFirstInGroup(position)) {
//                float top = view.getTop() - topGap;
//                float bottom = view.getTop();
//                c.drawRect(left, top, right, bottom, mPaint);
//                c.drawText(textLine, left, bottom, mTextPaint);
//            }
//        }
//    }

    private boolean isFirstInGroup(int position) {
        if (position == 0) {
            return true;
        } else {
            long prevGroupId = mDecorationCallback.getGroupId(position - 1);
            long groupId = mDecorationCallback.getGroupId(position);
            return prevGroupId != groupId;
        }
    }

    public interface DecorationCallback {
        long getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}
