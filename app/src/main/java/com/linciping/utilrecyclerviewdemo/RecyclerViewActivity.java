package com.linciping.utilrecyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRvContent;

    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRvContent = (RecyclerView) findViewById(R.id.rv_content);
        mDatas.add("item1");
        mDatas.add("otem2");
        mDatas.add("item3");
        mDatas.add("ptem4");
        mDatas.add("etem5");
        mDatas.add("item6");
        mDatas.add("item7");
        mDatas.add("ytem8");
        mDatas.add("item9");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");
        mDatas.add("ztem10");

        Collections.sort(mDatas);

        MyAdapter myAdapter = new MyAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.addItemDecoration(new SectionDecoration(this, new SectionDecoration.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(mDatas.get(position).charAt(0));
            }

            @Override
            public String getGroupFirstLine(int position) {
                return mDatas.get(position).substring(0, 1).toUpperCase();
            }
        }));
        mRvContent.setAdapter(myAdapter);
    }


    class MyAdapter extends RecyclerView.Adapter<NormalViewHolder> {

        @Override
        public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalViewHolder(getLayoutInflater().inflate(R.layout.item_normal, parent, false));
        }

        @Override
        public void onBindViewHolder(NormalViewHolder holder, int position) {
            holder.txtContent.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        TextView txtContent;

        public NormalViewHolder(View itemView) {
            super(itemView);
            txtContent = (TextView) itemView.findViewById(R.id.txt_content);
        }
    }
}
