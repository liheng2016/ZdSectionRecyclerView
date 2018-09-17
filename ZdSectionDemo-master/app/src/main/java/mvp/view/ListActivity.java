package mvp.view;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import base.BaseActivity;
import entity.ListEntity;
import mvp.adapter.ListAdapter;
import mvp.iview.ListDataView;
import mvp.presenter.ListDataPresenter;
import mvp.presenter.ReqListDataPresenterImpl;
import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/5/10.
 * Tel： 18811793194
 * VersionChange:2.0  XXX
 * 作用： Activity -----用户视图界面
 */

public class ListActivity extends BaseActivity implements ListDataView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe_refresh;
    private ListDataPresenter listDataPresenter;
    private ListAdapter listAdapter;

    @Override
    public void initView() {
        //初始化View控件
        setContentView(R.layout.activity_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //下拉刷新
        initRefresh();
    }

    /**
     * 初始化下拉刷新 Refresh  设置加载ProgressBar转动颜色值
     */
    private void initRefresh() {
        swipe_refresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipe_refresh.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    /**
     * 初始化监听事件
     */
    @Override
    protected void initListener() {

//      注册下拉刷新滑动监听
        swipe_refresh.setOnRefreshListener(this);

    }

    @Override
    protected void initData() {
        /**
         * 创建出Presenter 的实现类  并且将请求服务器的Json串传递过去
         */
        listDataPresenter = new ReqListDataPresenterImpl(this);
        listDataPresenter.reqNetData("showQuestion", 1, "2.0", "18659179669", 1, 150);
    }


    @Override
    public void onRefresh() {

        /**
         * 模拟刷新数据----延时1秒
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 加载最新数据
                if (listDataPresenter != null)
                    listDataPresenter.reqNetData("showQuestion", 1, "2.0", "18659179669", 1, 150);
                //请求最新数据后及时关闭刷新显示
                swipe_refresh.setRefreshing(false);
                Log.d("run", "run: " + Thread.currentThread().getName());

            }
        }, 1000);
    }

    /**
     * 接收ReqListDataPresenterImpl 请求服务器返回的数据，方便更新界面
     *
     * @param listEntity 数据
     * @param flag       true  成功  false 失败
     * @param msg        接收ReqListDataPresenterImpl传递过来的msg
     */
    @Override
    public void onloadData(ListEntity listEntity, boolean flag, String msg) {

        if (flag && listEntity != null) {
            // 创建水平方向的RecyclewView布局管理器，绑定RecyclewView

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            //判空处理 空则创建ListAdapter，与绑定RecyclewView

            if (listAdapter == null) {
                listAdapter = new ListAdapter(listEntity, ListActivity.this);
                recyclerView.setAdapter(listAdapter);
            } else {
                //不为空 ，复用，通知刷新数据
                listAdapter.updateData(listEntity);
            }

        } else {
            /**
             * 失败返回结果
             * 打印msg  吐丝
             */
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
