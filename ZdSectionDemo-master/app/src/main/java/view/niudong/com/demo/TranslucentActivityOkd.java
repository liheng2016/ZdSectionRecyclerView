package view.niudong.com.demo;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import base.BaseActivity;
import customview.BannerView;
import entity.ItemOneBean;
import entity.ItemTwoBean;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.HkDaXinOneProvider;
import mulitbinder.HkDaXinTwoProvider;
import mulitbinder.ViewBinder;
import utils.HkStockUtil;

/**
 * 减少层级嵌套   避免处理事件
 */
public class TranslucentActivityOkd extends BaseActivity implements View.OnClickListener,
        NestedScrollView.OnScrollChangeListener {
    //VIew  列表
    private RecyclerView mRecyclerView;
    Toolbar toolbar;
    //其他情况
    private MultiTypeAdapter mAdapter;
    private Items mData = new Items();
    public String FEIDI_ZIXUN_URL = "https://www.21fid.com/external/dyqh/newsFid/news/financing";
    CollapsingToolbarLayout collapsingToolbar;
    /**
     * 当前选中的Tabs 序号
     */
    private int mCurShowTabIndex = -1;
    private TextView[] mTxtTabs;
    private View[] mViewLines;

    private int top;
    private TextView tv_fenxiang;
    public static final int ITEM_ONE = 0;
    public static final int ITEM_TWO = 1;
    public static final int ITEM_THREE = 2;
    public static final int ITEM_FOUR = 3;

    private View mHeadView;
    private TextView mHeadTvContent;
    private NestedScrollView observableScrollView;
    private LinearLayout topLayout;
    private LinearLayout childLayout;
    private LinearLayout contentLayout;
    private LinearLayout mRootWebView;
    private TextView tvHeadHeight;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_my_stock);
        //TODO  初始RecyclerView相关  初始Tab View相关
        initContentView();

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        //默认显示第一个
        setSelectedTabLayout(mCurShowTabIndex, 0);
        setShowData(ITEM_ONE);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        top = childLayout.getTop();

    }

    protected void initContentView() {
        //TODO  顶部Tan 初始  其实自定义了会简洁 清爽
        observableScrollView = findViewById(R.id.mScrollView);
        BannerView mBannerView = findViewById(R.id.mBannerView);
        mBannerView.setImageUrls(HkStockUtil.getInstance().getIvUrlData());
        observableScrollView.setOnScrollChangeListener(this);
//        外层父布局
        topLayout = findViewById(R.id.top_content);
//        普通情况父View
        childLayout = findViewById(R.id.child_content);
        contentLayout = findViewById(R.id.ll_community_root);
        mTxtTabs = new TextView[4];
        mTxtTabs[0] = (TextView) findViewById(R.id.tv_tabs_text1);
        mTxtTabs[1] = (TextView) findViewById(R.id.tv_tabs_text2);
        mTxtTabs[2] = (TextView) findViewById(R.id.tv_tabs_text3);
        mTxtTabs[3] = (TextView) findViewById(R.id.tv_tabs_text4);
        findViewById(R.id.ll_tabs_tab1).setOnClickListener(this);
        findViewById(R.id.ll_tabs_tab2).setOnClickListener(this);
        findViewById(R.id.ll_tabs_tab3).setOnClickListener(this);
        findViewById(R.id.ll_tabs_tab4).setOnClickListener(this);
        //TODO 底部分割线处理
        mViewLines = new View[4];
        mViewLines[0] = findViewById(R.id.view_tabs_line1);
        mViewLines[1] = findViewById(R.id.view_tabs_line2);
        mViewLines[2] = findViewById(R.id.view_tabs_line3);
        mViewLines[3] = findViewById(R.id.view_tabs_line4);

        //TODO  RecyclerView 初始
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_detail);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MultiTypeAdapter(mData);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        //TODO 绑定多个条目
        mAdapter.register(ItemOneBean.class, new HkDaXinOneProvider());
        mAdapter.register(ItemTwoBean.class, new HkDaXinTwoProvider());
        mAdapter.register(View.class, new ViewBinder());
        mRecyclerView.setAdapter(mAdapter);
        //TODO 我是头部  我可以加入任何地方
        mHeadView = LayoutInflater.from(this).inflate(R.layout.hk_daxin_detail_title_content, mRecyclerView, false);
        mHeadTvContent = (TextView) mHeadView.findViewById(R.id.tv_head_content);
        mRootWebView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.webview, mRecyclerView, false);

        HkStockUtil.getInstance().loadWebViewData(mRootWebView,4);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tabs_tab1:
                setSelectedTabLayout(mCurShowTabIndex, 0);
                setShowData(ITEM_ONE);
                break;
            case R.id.ll_tabs_tab2:
                setSelectedTabLayout(mCurShowTabIndex, 1);
                setShowData(ITEM_TWO);
                break;
            case R.id.ll_tabs_tab3:
                setSelectedTabLayout(mCurShowTabIndex, 2);
                setShowData(ITEM_THREE);
                break;
            case R.id.ll_tabs_tab4:
                setSelectedTabLayout(mCurShowTabIndex, 3);
                setShowData(ITEM_FOUR);

                break;
            default:
        }
    }

    /**
     * 选中Tab
     *
     * @param oldSelected 上一个Tab
     * @param newSelected 新的Tab
     */

    private void setSelectedTabLayout(int oldSelected, int newSelected) {
        if (mCurShowTabIndex == newSelected) return;
        mCurShowTabIndex = newSelected;
        if (oldSelected != -1) {
            //恢复原来的
            mTxtTabs[oldSelected].setSelected(false);
            mViewLines[oldSelected].setVisibility(View.INVISIBLE);
        }

        if (newSelected != 0) {
            for (int i = 0; i < mTxtTabs.length; i++) {
                mTxtTabs[i].setSelected(false);
                mViewLines[i].setVisibility(View.INVISIBLE);
            }
        }
        //设置当前的
        mTxtTabs[newSelected].setSelected(true);
        mViewLines[newSelected].setVisibility(View.VISIBLE);
    }

    /**
     * 设置显示数据
     */
    private void setShowData(int mPostion) {
        //刷新数据
        mData.clear();
        switch (mPostion) {
            case ITEM_ONE:
                //标题头  自己改名
                setTitle("大家好！我是C标题 券商将上个交易日用户准备的新股认购资金化划转给交易所完成认购，总资产这部分资金将减少。");
                mData.add(mHeadView);
                mData.addAll(HkStockUtil.getInstance().getZhongQianOtherList());
                List<ItemTwoBean> itemTwoBeanList = HkStockUtil.getInstance().getRenGoTimeChildList();
                mData.addAll(itemTwoBeanList);
                mData.addAll(HkStockUtil.getInstance().getZhongQianOtherList());
                mData.addAll(HkStockUtil.getInstance().getRenGoTimeChildList());
                break;
            case ITEM_TWO:
                setTitle("大家好！我是RecyclerView列表");
                mData.add(mHeadView);
                List<ItemTwoBean> itemTwoBenList = HkStockUtil.getInstance().getRenGoTimeChildList();
                mData.addAll(itemTwoBenList);
                mData.addAll(HkStockUtil.getInstance().getZhongQianOtherList());
                mData.addAll(HkStockUtil.getInstance().getRenGoTimeChildList());
                break;
            case ITEM_THREE:
                setTitle("大家好！我是C标题 券商将上个交易日用户准备的新股认购资金化划转给交易所完成认购，总资产这部分资金将减少。");
                mData.add(mHeadView);
                mData.addAll(HkStockUtil.getInstance().getZhongQianOtherList());
                //常见问题
                break;
            case ITEM_FOUR:
                mData.add(mRootWebView);
                break;
            default:
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 标题
     */
    private void setTitle(String inputContent) {
        mHeadTvContent.setText(inputContent);
    }


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY >= top) {
            if (contentLayout.getParent() != topLayout) {
                childLayout.removeView(contentLayout);
                topLayout.addView(contentLayout);
            }
        } else {
            if (contentLayout.getParent() != childLayout) {
                topLayout.removeView(contentLayout);
                childLayout.addView(contentLayout);
            }
        }
    }
}
