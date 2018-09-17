package recycleview.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import recycleview.adapter.RecyItemAdapter;
import recycleview.view.RecycleViewDivider;
import recycleview.view.RefreshRecyclerView;
import utils.ToastUtils;
import view.niudong.com.demo.R;


/**
 * 加头加脚的RecyclerView
 */
public class RefreshRecyActivity extends BaseActivity implements RefreshRecyclerView.OnRefreshListener,
        RefreshRecyclerView.OnLoadMoreListener, RecyItemAdapter.OnItemClickListener {

    private RefreshRecyclerView recycler_view;
    private List<String> list_data = new ArrayList<>();
    private RecyItemAdapter refreshAdapter;

    public int pageNum = 30;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_refresh);
        recycler_view = (RefreshRecyclerView) findViewById(R.id.rv_news);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.head_image, null);
        recycler_view.addSwitchImageView(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        refreshAdapter = new RecyItemAdapter(null, getApplicationContext());
        recycler_view.setLayoutManager(linearLayoutManager);
//       添加分割线
        recycler_view.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.VERTICAL, R.drawable.custom_recycler_view_divier, false));
        recycler_view.setAdapter(refreshAdapter);
    }

    @Override
    protected void initListener() {
        //设置下拉刷新的监听
        recycler_view.setOnRefreshListener(this);
        //设置上拉加载的监听
        recycler_view.setOnLoadMoreListener(this);

        refreshAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {
        addData();
        bindData();
    }

    private void addData() {
        for (int i = 0; i < pageNum; i++) {
            list_data.add((66626 + i) + "");

        }
    }


    private void bindData() {

        if (refreshAdapter != null) {
            refreshAdapter.setData(list_data);

//            refreshAdapter.notifyDataSetChanged();
        }
// TODO  加头加脚哦
//  setHeaderView(recycler_view);
//        setFooterView(recycler_view);

    }

    @Override

    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                recycler_view.hideHeaderView(true);
            }
        }, 1500);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recycler_view.hideFooterView();
            }
        }, 1500);
    }

    @Override
    public void onClick(String data) {
        ToastUtils.showToast(this, data);
    }

/*    private void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.header, view, false);
        refreshAdapter.setHeaderView(header);
    }

    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(this).inflate(R.layout.footer_view, view, false);
        refreshAdapter.setFooterView(footer);
    }*/


}
