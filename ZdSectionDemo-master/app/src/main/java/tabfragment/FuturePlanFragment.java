package tabfragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;

import java.util.List;

import adapter.InforMationPagerAdapter;
import base.TestBaseFragment;
import customview.ViewPagerScroll;
import entity.ItemOneBean;
import entity.ItemTwoBean;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.HkDaXinOneProvider;
import mulitbinder.HkDaXinTwoProvider;
import mulitbinder.ViewBinder;
import utils.HkStockUtil;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * 名称：测试 列表实现效果
 * Created by niudong on 2018/5/29.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class FuturePlanFragment extends TestBaseFragment {

    //VIew  列表
    private RecyclerView mRecyclerView;
    //头部文字View
    private View mHeadView;
    //头部文字
    private View mContentViewPager;
    //其他情况
    private MultiTypeAdapter mAdapter;
    private Items mData = new Items();
    private XTabLayout infoTab;
    private ViewPagerScroll mViewPager;
    private String mTab[];

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_market;
    }

    @Override
    protected String getFragmentTitle() {
        return "让我们一起憧憬明天吧";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        //TODO  RecyclerView 初始
        mRecyclerView = (RecyclerView) view.findViewById(R.id.muilt_recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MultiTypeAdapter(mData);
        mAdapter.register(ItemOneBean.class, new HkDaXinOneProvider());
        mAdapter.register(ItemTwoBean.class, new HkDaXinTwoProvider());
        mAdapter.register(View.class, new ViewBinder());
        mRecyclerView.setAdapter(mAdapter);
        //头部View
        mHeadView = LayoutInflater.from(mContext).inflate(R.layout.hk_daxin_detail_title_content, mRecyclerView, false);

    }

    @Override
    protected void initData() {
        super.initData();
        mData.clear();
        mData.add(mHeadView);
        //一种数据样式
        List<ItemOneBean> renGouTimeList = HkStockUtil.getInstance().getRenGouTimeList();
        mData.addAll(renGouTimeList);

mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("","");
    }
}
