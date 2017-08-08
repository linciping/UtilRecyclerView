package com.linciping.utilrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linciping
 * @time 2017/5/17
 * @Description 带头部的RecyclerView
 */
public class HeaderAndFooterRecyclerView extends DividerRecyclerView {

    protected List<View> mHeaderViews, mFooterViews;

    private HeaderAndFooterAdapter mAdapter;

    public HeaderAndFooterRecyclerView(Context context) {
        this(context, null);
    }

    public HeaderAndFooterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderAndFooterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHeaderViews = new ArrayList<>();
        mFooterViews = new ArrayList<>();
    }

    public <T extends HeaderAndFooterAdapter> void setAdapter(T adapter) {
        super.setAdapter(adapter);
        mAdapter = adapter;
        mAdapter.setFooterViews(mFooterViews);
        mAdapter.setHeaderViews(mHeaderViews);
    }

    /**
     * 添加头布局
     *
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderViews.add(view);
        if (mAdapter != null) {
            mAdapter.notifyHeaderViewChanged(mHeaderViews);
        }
    }

    /**
     * 指定添加到头布局
     *
     * @param index
     * @param view
     */
    public void addHeaderView(int index, View view) {
        if (index > mHeaderViews.size()) {
            index = mHeaderViews.size();
        }
        mHeaderViews.add(index, view);
        if (mAdapter != null) {
            mAdapter.notifyHeaderViewChanged(mHeaderViews);
        }
    }

    /**
     * 添加尾布局
     *
     * @param view
     */
    public void addFooterView(View view) {
        mFooterViews.add(view);
        if (mAdapter != null) {
            mAdapter.notifyFooterViewChanged(mFooterViews);
        }
    }

    /**
     * 删除指定视图的头部布局
     *
     * @param v
     */
    public void removeHeaderView(View v) {
        for (int i = 0; i < mHeaderViews.size(); i++) {
            if (mHeaderViews.get(i) == v) {
                removeHeaderView(i);
            }
        }
    }

    /**
     * 删除指定视图的底部布局
     *
     * @param v
     */
    public void removeFooterView(View v) {
        for (int i = 0; i < mFooterViews.size(); i++) {
            if (mFooterViews.get(i) == v) {
                removeFooterView(i);
            }
        }
    }

    /**
     * 根据头部的位置，删除头部布局
     *
     * @param index
     */
    public void removeHeaderView(int index) {
        mHeaderViews.remove(index);
        mAdapter.setHeaderViews(mHeaderViews);
        if (mAdapter != null) {
            mAdapter.notifyHeaderViewChanged(mHeaderViews);
        }
    }

    /**
     * 根据底部的位置，删除底部布局
     *
     * @param index
     */
    public void removeFooterView(int index) {
        mFooterViews.remove(index);
        mAdapter.setFooterViews(mFooterViews);
        if (mAdapter != null) {
            mAdapter.notifyFooterViewChanged(mFooterViews);
        }
    }

    /**
     * 获取指定位置的头部视图
     *
     * @param index
     * @return
     */
    public View getHeaderView(int index) {
        return mHeaderViews.get(index);
    }

    /**
     * 获取指定位置的底部视图
     *
     * @param index
     * @return
     */
    public View getFooterView(int index) {
        return mFooterViews.get(index);
    }

    /**
     * 获取头部视图的数量
     *
     * @return
     */
    public int getHeadersCount() {
        if (CheckUtil.isCollectionEmpty(mHeaderViews)) {
            return mHeaderViews.size();
        }
        return 0;
    }

    /**
     * 获取底部视图的数量
     *
     * @return
     */
    public int getFootersCount() {
        if (CheckUtil.isCollectionEmpty(mFooterViews)) {
            return mFooterViews.size();
        }
        return 0;
    }

    public static abstract class HeaderAndFooterAdapter<VH extends DividerRecyclerView.ViewHolder>
            extends DividerRecyclerView.Adapter<DividerRecyclerView.ViewHolder> {

        private static final int HEADER_VIEW = 10001;
        private static final int FOOTER_VIEW = 10002;
        private static final int NORMAL_VIEW = 10003;

        private List<View> mHeaderViews, mFooterViews;

        private int mHeaderCount, mFooterCount;

        private int mHeaderIndex = 0, mFooterIndex = 0;

        private void setHeaderViews(List<View> headerViews) {
            mHeaderViews = headerViews;
            if (!CheckUtil.isCollectionEmpty(mHeaderViews)) {
                mHeaderCount = mHeaderViews.size();
            }
        }

        private void setFooterViews(List<View> footerViews) {
            mFooterViews = footerViews;
            if (!CheckUtil.isCollectionEmpty(mFooterViews)) {
                mFooterCount = mFooterViews.size();
            }
        }

        private void notifyHeaderViewChanged(List<View> headerViews) {
            mHeaderViews = headerViews;
            mHeaderCount = mHeaderViews.size();
            notifyDataSetChanged();
        }

        private void notifyFooterViewChanged(List<View> footerViews) {
            mFooterViews = footerViews;
            mFooterCount = mFooterViews.size();
            notifyDataSetChanged();
        }

        public int getHeaderCount() {
            return mHeaderCount;
        }

        public int getFooterCount() {
            return mFooterCount;
        }

        /**
         * 内容视图创建ViewHolder
         *
         * @param parent
         * @param viewType
         * @return
         */
        public abstract VH myCreateViewHolder(ViewGroup parent, int viewType);

        /**
         * 绑定数据到内容ViewHolder
         *
         * @param holder
         * @param position
         */
        public abstract void myBindViewHolder(VH holder, int position);


        public abstract int getAdapterItemCount();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == HEADER_VIEW) {
                return new HeaderViewHolder(mHeaderViews.get(mHeaderIndex++));
            } else if (viewType == FOOTER_VIEW) {
                return new HeaderViewHolder(mFooterViews.get(mFooterIndex++));
            } else {
                return myCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public final void onBindViewHolder(ViewHolder holder, int position) {
            int adjPosition = position - mHeaderCount;
            if (getItemViewType(position) == NORMAL_VIEW) {
                myBindViewHolder((VH) holder, adjPosition);
            }
        }

        @Override
        public final int getItemCount() {
            return getAdapterItemCount() + mFooterCount + mHeaderCount;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                // 布局是GridLayoutManager所管理
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                        return (isHeader(position) || isFooter(position)) ? gridLayoutManager.getSpanCount() : 1;
                    }
                });
            }
        }

        /**
         * 判断是否是Header的位置
         * 如果是Header的则返回true否则返回false
         *
         * @param position
         * @return
         */
        private boolean isHeader(int position) {
            return mHeaderCount > 0 && position >= 0 && position < mHeaderCount;
        }

        /**
         * 判断是否是Footer的位置
         * 如果是Footer的位置则返回true否则返回false
         *
         * @param position
         * @return
         */
        private boolean isFooter(int position) {
            return mFooterCount > 0 && position > getAdapterItemCount() + mHeaderCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < mHeaderCount) {
                return HEADER_VIEW;   // 说明是Header所占用的空间
            }
            int adjPosition = position - mHeaderCount;
            int adapterCount = getAdapterItemCount();
            if (adjPosition < adapterCount) {
                return NORMAL_VIEW;
            }
            return FOOTER_VIEW;   // 说明是Footer的所占用的空间
        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
