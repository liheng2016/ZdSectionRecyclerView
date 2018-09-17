package listview.activity;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import listview.adapter.HeadFootAdapter;
import listview.view.PullToRefreshListView;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/6/16.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：下拉刷新和加载更多的ListView
 */

public class ListViewAddHeadFootActivity extends BaseActivity {
    private PullToRefreshListView listView;
    private List<String> list = new ArrayList<>();
    private HeadFootAdapter headFootAdapter;
    private Button button;

    boolean isZhankai = false;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_head_foot);
        listView = (PullToRefreshListView) findViewById(R.id.tv_head_foot_list);
        button = (Button) findViewById(R.id.bt_ex);
    }


    @Override
    protected void initData() {
        addData();
        if (headFootAdapter == null) {
            headFootAdapter = new HeadFootAdapter(list, getApplicationContext());
            listView.setAdapter(headFootAdapter);
            if (!isZhankai) {
                listView.getLayoutParams().height = 300;
            }
            listView.setOnRefreshingListener(new PullToRefreshListView.OnRefreshingListener() {

                @Override
                public void onRefreshing() {
                    reloadData();
                }

                @Override
                public void onLoadMore() {
                    loadMore();
                }
            });
            listView.setOnItemClickListener(onItemClickListener);

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isZhankai = !isZhankai;
                if (isZhankai) {
                    listView.getLayoutParams().height = listView.getHeight();
                } else {
                    listView.getLayoutParams().height = 300;
                }
                headFootAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void initListener() {

    }

    private void addData() {
        for (int i = 0; i < 30; i++) {
            list.add((66666 + i) + "");

        }
    }

    ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String itemAtPosition = (String) adapterView.getItemAtPosition(i);

            Toast.makeText(ListViewAddHeadFootActivity.this, "" + itemAtPosition + "position" + i, Toast.LENGTH_LONG).show();
        }
    };


    /**
     * 重新联网获取数据
     */
    protected void reloadData() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                list.add(0, "刷新出来的数据");
                headFootAdapter.notifyDataSetChanged();
                listView.onRefreshComplete();
            }
        }, 2000);    // 模拟联网的耗时操作。  3秒钟后会调用run方法中的代码
    }

    /**
     * 联网加载更多数据
     */
    protected void loadMore() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                list.add("加载更多数据");
                headFootAdapter.notifyDataSetChanged();
                listView.onLoadmoreComplete();
            }
        }, 2000);    // 模拟联网的耗时操作。  3秒钟后会调用run方法中的代码
    }

}
