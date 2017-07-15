package com.linciping.utilrecyclerviewdemo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linciping.utilrecyclerview.LoadRecyclerView;

import java.util.List;

/**
 * Created by linciping on 2017/5/17.
 */

public class StringAdapter extends LoadRecyclerView.LoadAdapter<StringAdapter.StringViewHolder> {

    private List<String> mStringList;
    private Activity mActivity;

    public StringAdapter(List<String> stringList, Activity activity) {
        mStringList = stringList;
        mActivity = activity;
    }

    @Override
    public StringViewHolder myCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.item_string, parent, false);
        return new StringViewHolder(contentView);
    }

    @Override
    public void myBindViewHolder(StringViewHolder holder, int position) {
        holder.txtContent.setText(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mCanLoad) {
            return mStringList.size() + 1;
        } else {
            return mStringList.size();
        }
    }

    class StringViewHolder extends RecyclerView.ViewHolder {

        private TextView txtContent;

        public StringViewHolder(View itemView) {
            super(itemView);
            txtContent = (TextView) itemView.findViewById(R.id.txt_content);
        }
    }
}
