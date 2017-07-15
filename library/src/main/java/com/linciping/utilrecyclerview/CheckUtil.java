package com.linciping.utilrecyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.ListAdapter;

import java.util.Collection;
import java.util.Map;

/**
 * Created by linciping on 2016/12/13.
 */

public class CheckUtil {

    /**
     * 判断集合对象是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isCollectionEmpty(Collection<?> collection) {
        return !(collection != null && collection.size() > 0);
    }

    /**
     * 判断Map对象是否为空
     *
     * @param map
     * @return
     */
    public static boolean isMapEmpty(Map<?, ?> map) {
        return !(map != null && map.size() > 0);
    }

    /**
     * 字符串是否为空
     *
     * @param content
     * @return
     */
    public static boolean isStringEmpty(String content) {
        return content == null || content.isEmpty();
    }

    /**
     * activity是否处于活动状态
     *
     * @param activity
     * @return
     */
    public static boolean activityIsActive(Activity activity) {
        return activity != null && !activity.isFinishing();
    }

    /**
     * adapter是否为空
     *
     * @param adapter
     * @return
     */
    public static boolean isEmptyAdapter(RecyclerView.Adapter adapter) {
        return !(adapter != null && adapter.getItemCount() > 0);
    }

    /**
     * adapter是否为空
     *
     * @param listAdapter
     * @return
     */
    public static boolean isEmptyAdapter(ListAdapter listAdapter) {
        return !(listAdapter != null && listAdapter.getCount() > 0);
    }


    /**
     * SparseArray是否为空
     *
     * @param sparseArray
     * @return
     */
    public static boolean isSparseArrayEmpty(SparseArray<?> sparseArray) {
        if (sparseArray != null && sparseArray.size() > 0) {
            return false;
        } else {
            return true;
        }
    }


}
