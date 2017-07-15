package com.linciping.utilrecyclerview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author linciping
 * @time 2017/7/8
 * @Description 支持下拉加载的RecyclerView
 */
public class LoaderRecyclerView extends HeaderAndFooterRecyclerView {

    private OnLoadListener mLoadListener;
    private boolean isLoad = false;
    private LoaderAdapter mAdapter;

    private static final String TAG = "LoaderRecyclerView";

    public LoaderRecyclerView(Context context) {
        super(context);
    }

    public LoaderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoaderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 重写adapter类型
     *
     * @param adapter
     * @param <T>
     */
    public <T extends LoaderAdapter> void setAdapter(T adapter) {
        super.setAdapter(adapter);
        this.mAdapter = adapter;
    }

    /**
     * 设置加载事件
     *
     * @param onLoadListener
     */
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.mLoadListener = onLoadListener;
    }

    /**
     * 控制加载状态
     *
     * @param load
     */
    public void setLoad(boolean load) {
        if (!load && isLoad) {
            if (mAdapter != null) {
                mAdapter.notifyRemoveLoadMore();
            } else {
                Log.e(TAG, "适配器暂未设置");
            }
        }
        this.isLoad = false;
    }

    /**
     * 设置加载视图
     * 如果不设置，则加载默认视图
     *
     * @param loadView
     */
    public void setLoadView(View loadView) {
        if (mAdapter != null) {
            mAdapter.setLoadView(loadView);
        } else {
            Log.e(TAG, "适配器暂未设置");
        }
    }

    /**
     * 获取加载视图
     *
     * @return
     */
    public View getLoadView() {
        if (mAdapter != null) {
            return mAdapter.getLoadView();
        } else {
            return null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /*
        需要改进的地方
        1：优化滑动过程
        2：更改加载视图出现的时间，改进是否加载的判断方式
        */
        if (mAdapter != null) {
            if (getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                if (canLoad(layoutManager)) {
                    int lastPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastPosition == mAdapter.getItemCount() - 1 && !isLoad && mLoadListener != null) {
                        mAdapter.notifyLoadMore();
                        isLoad = true;
                        mLoadListener.onLoad();
                    }
                }
            }
        } else {
            Log.e(TAG, "适配器暂未设置");
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否可以进行加载
     */
    private boolean canLoad(LinearLayoutManager layoutManager) {
        int startPosition = layoutManager.findFirstVisibleItemPosition();
        int endPosition = layoutManager.findLastVisibleItemPosition();
        if (endPosition - startPosition < layoutManager.getItemCount() - 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 加载事件
     */
    public interface OnLoadListener {
        /**
         * 加载
         */
        void onLoad();
    }


    static abstract class LoaderAdapter<VH> extends HeaderAndFooterRecyclerView.RecyclerWrapAdapter {

        static final int LOAD_VIEW = 10004;

        protected boolean mCanLoad = false;
        private View mLoadView;//加载视图
        private long mLoadTime = 2000;
        private long mStartLoadTime;

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) {
                return LOAD_VIEW;
            }
            return super.getItemViewType(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == LOAD_VIEW) {
                if (mLoadView != null) {
                    return new LoadViewHolder(mLoadView);
                } else {
                    View loadView = LayoutInflater.from(parent.getContext()).
                            inflate(com.linciping.utilrecyclerview.R.layout.item_load, parent, false);
                    return new LoadViewHolder(loadView);
                }
            }
            return super.onCreateViewHolder(parent, viewType);
        }

        /**
         * 通知显示加载布局
         */
        private void notifyLoadMore() {
            /*
            需要优化的地方
            1：根据加载的时间，优化加载布局的显示
             */
            mStartLoadTime = System.currentTimeMillis();
//            Logger.e("开始加载的时间:" + String.valueOf(mStartLoadTime));
            mCanLoad = true;
            notifyDataSetChanged();
        }

        /**
         * 关闭加载布局
         */
        private void notifyRemoveLoadMore() {
            /*
            需要优化的地方
            1：根据加载的时间，优化加载布局的显示
             */
            long newTime = System.currentTimeMillis();
            if (newTime - mStartLoadTime < mLoadTime) {
                long loadTime = newTime - mStartLoadTime;
                loadHandler.sendEmptyMessageDelayed(0, mLoadTime - loadTime);
            } else {
                removeLoadMore();
            }
        }

        private Handler loadHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                removeLoadMore();
            }
        };

        /**
         * 停止加载的方法
         */
        private void removeLoadMore() {
            mCanLoad = false;
            notifyDataSetChanged();
        }

        /**
         * 设置加载视图
         *
         * @param loadView
         */
        private void setLoadView(View loadView) {
            this.mLoadView = loadView;
        }

        /**
         * 获取加载视图
         *
         * @return
         */
        private View getLoadView() {
            return mLoadView;
        }
    }

    /**
     * 加载视图
     */
    static class LoadViewHolder extends RecyclerView.ViewHolder {

        public LoadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
