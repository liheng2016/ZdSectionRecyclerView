package view.niudong.com.demo;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import base.BaseActivity;
import customview.ScrollViewWithStickHeader;
import customview.ZdViewPagerIndicator;
import customview.StickyNavLayout;
import entity.ItemOneBean;
import entity.ItemTwoBean;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.HkDaXinOneProvider;
import mulitbinder.HkDaXinTwoProvider;
import mulitbinder.ViewBinder;
import utils.HkStockUtil;

/**
 * 自定义StickNestedScrollView
 */
public class SlideScrollActivity extends BaseActivity implements View.OnClickListener {


    //VIew  列表
    private RecyclerView mRecyclerView;
    Toolbar toolbar;
    //其他情况
    private MultiTypeAdapter mAdapter;
    private Items mData = new Items();
    public String FEIDI_ZIXUN_URL = "https://www.baidu.com/";
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

//    private View mHeadView;
//    private View webRootView;
    private TextView mHeadTvContent;
    private String[] mTitles = new String[] { "简介", "评价", "相关" };
    private ZdViewPagerIndicator mIndicator;
    private StickyNavLayout roottickyNavLayout;
    private LinearLayout mTabLayout;
    private ScrollViewWithStickHeader observableScrollView;
    private TextView mBottomLayout;
//    private WebView webItemView;
    private LinearLayout mRootWebView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_slide_scroll);
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

    protected void initContentView() {
        //TODO  顶部Tan 初始  其实自定义了会简洁 清爽
        mTabLayout = findViewById(R.id.ll_community_root);
        mBottomLayout = findViewById(R.id.tv_fenxiang);


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
        mRootWebView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.webview, mRecyclerView, false);

        HkStockUtil.getInstance().loadWebViewData(mRootWebView,4);
//        webRootView = LayoutInflater.from(this).inflate(R.layout.item_web_view, mRecyclerView, false);
//        webItemView = webRootView.findViewById(R.id.item_web_view);
//        WebSettings settings = webItemView.getSettings();
//        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        webItemView.setWebChromeClient(new WebChromeClient());
//        webItemView.setWebViewClient(new WebViewClient());
//        settings.setJavaScriptEnabled(true);


        //设置Tab 和底部View
//        observableScrollView.setContentView(mTabLayout);
//        observableScrollView.setSuspensionView(mBottomLayout);
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
                mData.add(mRootWebView);
                break;
            case ITEM_TWO:
                List<ItemTwoBean> itemTwoBeanList = HkStockUtil.getInstance().getRenGoTimeChildList();
                mData.addAll(itemTwoBeanList);
                mData.addAll(HkStockUtil.getInstance().getZhongQianOtherList());
                mData.addAll(HkStockUtil.getInstance().getRenGoTimeChildList());
                break;
            case ITEM_THREE:
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


}
