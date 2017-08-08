package com.linciping.utilrecyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linciping.utilrecyclerview.DividerRecyclerView;
import com.linciping.utilrecyclerview.HeaderAndFooterRecyclerView;
import com.linciping.utilrecyclerview.LoaderRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoaderRecyclerView mRvContent;
    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvContent= (LoaderRecyclerView) findViewById(R.id.rv_content);
        list.add("item1");
        list.add("item2");
        list.add("item3");
        list.add("item4");
        list.add("item5");
        list.add("item6");
        list.add("item7");
        list.add("item8");
        list.add("item9");
        list.add("item10");
        list.add("item11");
        list.add("item12");
        list.add("item13");
        list.add("item14");
        list.add("item15");
        StringAdapter myAdapter = new StringAdapter(list,this);
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(myAdapter);
        mRvContent.setOnLoadListener(new LoaderRecyclerView.OnLoadListener() {
            @Override
            public void onLoad() {
                list.add("item16");
                list.add("item17");
                list.add("item18");
                mRvContent.setCanLoad(false);
            }
        });
    }

}
