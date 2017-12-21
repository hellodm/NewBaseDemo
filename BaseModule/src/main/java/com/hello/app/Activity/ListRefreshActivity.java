package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hello.app.MyView.refresh.PullToRefreshListView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/** listView下拉刷新。上拉加载的activity */
public class ListRefreshActivity extends Activity {


    @InjectView(R.id.list)
    PullToRefreshListView list;

//    @InjectView(R.id.scroll)
//    PullToRefreshScrollView scrollView;


    String[] strs = new String[]{"2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2",
            "2", "2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_refresh);

        ButterKnife.inject(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strs);
        list.setAdapter(adapter);

        list.setPullToRefreshOverScrollEnabled(true);
        list.setMode(PullToRefreshListView.Mode.BOTH);

//        scrollView.setMode(PullToRefreshScrollView.Mode.BOTH);

    }


}
