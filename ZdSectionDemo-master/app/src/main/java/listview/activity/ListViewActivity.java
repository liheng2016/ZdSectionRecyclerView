package listview.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseActivity;
import listview.adapter.PinnedHeaderExpandableAdapter;
import listview.view.PinnedHeaderExpandableListView;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/6/15.
 * Tel：18811793194
 * VersionChange：
 * <p>* 折叠固定头部的ListView
 */
public class ListViewActivity extends BaseActivity {
    private PinnedHeaderExpandableListView explistview;
    private String[][] childrenData = new String[11][5];
    private String[] groupData = new String[11];
    private int expandFlag = -1;//控制列表的展开
    private PinnedHeaderExpandableAdapter adapter;
    private View header;
    private Button bt_click;
    private TextView tv_head;

    boolean isZhankai = false;
    private Button button;

    /**
     * 初始化VIEW
     */
    @Override
    protected void initView() {
        setContentView(R.layout.activity_listview);
        explistview = (PinnedHeaderExpandableListView) findViewById(R.id.explistview);
        button = (Button) findViewById(R.id.bt_ex);
    }

    @Override
    protected void initListener() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isZhankai = !isZhankai;
                if (isZhankai) {
                    explistview.getLayoutParams().height = explistview.getHeight();
                } else {
                    explistview.getLayoutParams().height = 300;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        for (int i = 0; i < groupData.length; i++) {
            groupData[i] = "分组" + i;
        }

        for (int i = 0; i < groupData.length; i++) {
            for (int j = 0; j < 5; j++) {
                childrenData[i][j] = "好友" + i + "-" + j;
            }
        }
        //TODO   添加头布局
        header = View.inflate(this, R.layout.header, null);
        bt_click = (Button) header.findViewById(R.id.bt_click);
        tv_head = (TextView) header.findViewById(R.id.tv_head);


        //设置悬浮头部VIEW
        explistview.setHeaderView(getLayoutInflater().inflate(R.layout.group_head,
                explistview, false));
        adapter = new PinnedHeaderExpandableAdapter(childrenData, groupData, getApplicationContext(), explistview);
        Log.d("11111", "initData: " + childrenData.toString() + "" + groupData.toString());

        explistview.setAdapter(adapter);
//        展开第一个
        explistview.expandGroup(0);
        // 设置被选中的group置于顶端
        explistview.setSelectedGroup(0);
        ViewGroup.LayoutParams layoutParams = explistview.getLayoutParams();
        layoutParams.height=300;
        explistview.setLayoutParams(layoutParams);

  /*
       添加头布局
       if (explistview.getHeaderViewsCount() == 0) {
            explistview.addHeaderView(getLayoutInflater().inflate(R.layout.header,
                    explistview, false));

        }*/


// ToDO  设置选中第一个

        bt_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ListViewActivity.this, "我是head Button", Toast.LENGTH_SHORT).show();

            }
        });


        tv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(ListViewActivity.this, "TextView ", Toast.LENGTH_SHORT).show();


            }
        });


        explistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {


                Toast.makeText(ListViewActivity.this, "子Item点击了" + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        explistview.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Toast.makeText(ListViewActivity.this, "点击了Head" + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        //设置单个分组展开
//        explistview.setOnGroupClickListener(new GroupClickListener());
    }

    //TODO 组点击了

    class GroupClickListener implements OnGroupClickListener {

        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {

            Toast.makeText(ListViewActivity.this, "点击了" + groupPosition, Toast.LENGTH_SHORT).show();
            if (expandFlag == -1) {
                // 展开被选的groupj
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistview.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                explistview.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                explistview.collapseGroup(expandFlag);
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistview.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }
    }
//    public void setListViewHeightBasedOnChildren(ListView listView) {
//
//        ListAdapter listAdapter = listView.getAdapter();
//
//        if (listAdapter == null) {
//            return;
//        }
//
//        int totalHeight = 0;
//
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//
//        params.height = totalHeight
//                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除
//
//        listView.setLayoutParams(params);
//    }



}
