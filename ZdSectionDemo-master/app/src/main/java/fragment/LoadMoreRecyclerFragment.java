package fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import base.TestBaseFragment;
import customview.LoadMoreWrapper;
import entity.Category;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.CategoryViewBinder;
import mulitbinder.ViewBinder;
import pullrefresh.XctPtrLayout;
import pullrefresh.XctRefreshLayout;
import utils.ColorUtils;
import utils.HkStockUtil;
import utils.UIUtils;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.R;

/**
 * 名称： 加载更多的RecyclerView
 * <p>
 * Created by niudong on 2018/11/07
 * Tel：18811793194
 * VersionChange：mthq1.0
 */
public class LoadMoreRecyclerFragment extends TestBaseFragment implements LoadMoreWrapper.OnLoadMoreListener {

    private MultiTypeAdapter mAdapter;
    private Items items = new Items();
    private RecyclerView mRecyclerView;
    private View mHeadPayView;
    private TextView mHeadTvContent;
    private View mChildContentTitlte;
    private LoadMoreWrapper mLoadMoreAdapter;

    private int currPage = 1;
    private XctPtrLayout mPullDownView;
    private List<Category> firstList;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_loadmore_recycler;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRecyclerView = view.findViewById(R.id.load_more_recyclerview);
        mPullDownView = (XctPtrLayout) view.findViewById(R.id.pull);
        //正在为您准备数据.....
        mPullDownView.emptyViewLoading(null);
        //加载失败支持点击刷新
        mPullDownView.setEmptyClickRefresh(true);
        mAdapter = new MultiTypeAdapter(items);
        //TODO 绑定多个条目
        mAdapter.register(View.class, new ViewBinder());
        mAdapter.register(Category.class, new CategoryViewBinder());
        //TODO 头部View
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mHeadPayView = LayoutInflater.from(getContext()).inflate(R.layout.pay_head_layout, null);
        //TODO  子View内容标题
        mChildContentTitlte = LayoutInflater.from(getContext()).inflate(R.layout.hk_daxin_detail_title_content, mRecyclerView, false);
        mHeadTvContent = (TextView) mChildContentTitlte.findViewById(R.id.tv_head_content);
        //TODO 加载更多的包装容器
        mLoadMoreAdapter = new LoadMoreWrapper(mAdapter);
        mLoadMoreAdapter.setLoadMoreEnable(true);
        mRecyclerView.setAdapter(mLoadMoreAdapter);
        //监听回调
        mLoadMoreAdapter.setOnLoadMoreListener(this);
    }

    @Override
    protected String getFragmentTitle() {
        return "让我们一起憧憬明天吧";
    }

    @Override
    protected void initListener() {
        super.initListener();
        //下拉刷新
        mPullDownView.setPtrHandler(new XctRefreshLayout.DefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                currPage=1;
                items.clear();
                items.add(mHeadPayView);
                items.add(addTitlte("我是标题一"));
                //List
                items.addAll(firstList);
                mPullDownView.refreshComplete();
                mAdapter.notifyDataSetChanged();
                mLoadMoreAdapter.setLoadMoreEnable(true);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mRecyclerView, header);
            }
        });
    }

public void requestNotify(){
try {
    currPage=1;
    items.clear();
    items.add(mHeadPayView);
    items.add(addTitlte("我来自点击通知刷新数据哦"));
    //List
    items.addAll(firstList);
    mPullDownView.refreshComplete();
    mAdapter.notifyDataSetChanged();
    mLoadMoreAdapter.setLoadMoreEnable(true);
}catch (Exception e)
{
    Log.d("",""+e);

}}
    @Override
    protected void initData() {
        super.initData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullDownView.showContentView(true);
            }
        }, 300);
        //初始化数据
        firstList = HkStockUtil.getInstance().getPopListData();
        //默认添加15条数据
        items.clear();
        items.add(mHeadPayView);
        items.add(addTitlte("我是1"));
        //List
        items.addAll(firstList);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 添加孩子标题
     */
    private TextView addTitlte(String value) {
        TextView textView = new TextView(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(40));
        textView.setLayoutParams(layoutParams);
        textView.setPadding(20,0,0,0);
        textView.setTextColor(ColorUtils.COLOR_WHITE);
        textView.setBackgroundColor(ColorUtils.COLOR_TIELE_BG);
        textView.setGravity(Gravity.CENTER | Gravity.LEFT);
        textView.setText(value);
        return textView;
    }


    @Override
    public void onLoadMore() {
        final boolean isHasMore = items.size() <= 100;
        if (isHasMore) {
            MyApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currPage++;
                    mLoadMoreAdapter.loadMoreComplete(isHasMore);
                    items.add(addTitlte(getLoadMoreTitle(currPage)));
                    items.addAll(getLoadMoreData(currPage));
                    mAdapter.notifyDataSetChanged();
                }
            }, 1200);

        } else {
            mLoadMoreAdapter.setLoadMoreEnable(false);
            mLoadMoreAdapter.loadMoreComplete();
        }
        //List
    }

    private String getLoadMoreTitle(int currPage) {
        String data = "你好";
        switch (currPage) {
            case 1:
                data = "我是" + currPage;
                break;
            case 2:
                data = "我是" + currPage;
                break;
            case 3:
                data = "我是" + currPage;
                break;
            case 4:
                data = "我是" + currPage;
                break;
            default:
                break;
        }
        return data;
    }

    /**
     * 构造随机数据
     *
     * @param currPage
     */

    private List<Category> getLoadMoreData(int currPage) {
        List<Category> data = null;
        switch (currPage) {
            case 1:
                data = HkStockUtil.getInstance().getPopList2Data();
                break;
            case 2:
                data = HkStockUtil.getInstance().getPopList3Data();
                break;
            case 3:
                data = HkStockUtil.getInstance().getList4Data();
                break;
            case 4:
                data = HkStockUtil.getInstance().getPopList2Data();
                break;
            default:
                break;
        }
        return data;
    }
}
