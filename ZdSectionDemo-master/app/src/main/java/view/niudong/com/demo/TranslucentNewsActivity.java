package view.niudong.com.demo;

import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import base.BaseActivity;
import customview.BannerView;
import customview.ZdViewPagerIndicator;
import entity.ItemOneBean;
import entity.ItemTwoBean;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.HkDaXinOneProvider;
import mulitbinder.HkDaXinTwoProvider;
import mulitbinder.ViewBinder;
import utils.ColorUtils;
import utils.HkStockUtil;
import utils.Logger;
import utils.ToastUtils;
import utils.UIUtils;

/**
 * 名称：TODO NestedScrollView 实现固定头部及切换Tab 展示 RecyclerView和WebVIew
 * 分析头部固定实现
 * Created by niudong on 2018/6/23.
 * Tel：18811793194
 * 减少层级嵌套   避免处理事件，这样性能更优！
 * <p>
 * TODO  防止列表顶起Tab  工作中经常会用到 android:descendantFocusability="blocksDescendants"
 */
public class TranslucentNewsActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, ZdViewPagerIndicator.onTabItemClickListener {
    private static final String TAG = TranslucentNewsActivity.class.getSimpleName();
    //列表
    private RecyclerView mRecyclerView;
    //多条目适配器，记得build.grade添加依赖
    private MultiTypeAdapter mAdapter;
    //列表数据
    private Items mData = new Items();
    //测量 tab 距屏幕顶部的距离
    private int mTabMarginTop;
    /**
     * 内容区域   0,WebView   1 RecyclerView   2 RecyclerView
     */
    public static final int ITEM_ONE = 0;
    public static final int ITEM_TWO = 1;
    public static final int ITEM_THREE = 2;

    private View mHeadView;
    private TextView mHeadTvContent;
    private NestedScrollView mNestedScrollView;
    private LinearLayout topLayout;
    private LinearLayout childLayout;
    private ZdViewPagerIndicator mIndicator;
    private LinearLayout mRootWebView;
    /**
     * 视差渐变相关初始View
     */
    private TextView centerText;
    private int anchorHeight;
    private float offset;
    private int headerHeight;
    private RelativeLayout layoutToolBarBackground;
    /**
     * 轮播图，你可以用合信加的轮播替换它
     */
    private BannerView mBannerView;
    /**
     * Tab选项卡的名称
     */
    private String[] mTitles = new String[]{"WebView", "TianJin", "BeiJing"};

    /**
     * Tab选项卡的图片
     */
    private int[] mTabIcons = {R.drawable.detail_icon_tab1_style, R.drawable.detail_icon_tab2_style,
            R.drawable.detail_icon_tab3_style};
    /**
     * Tab 是否在顶部
     */
    private boolean isTopHasTab;
    /**
     * 记录三个列表缓存位置
     */
    private HashMap<Integer, Integer> cacheListPostion;
    /**
     * 当前List
     */
    private int mCurrPostion = 0;
    private boolean isSetPostionFinish;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_project_detail_new);
        cacheListPostion = new HashMap<>();
        //TODO  初始RecyclerView相关  初始Tab View相关
        initContentView();
    }

    @Override
    protected void initListener() {
        int screenHeight = UIUtils.getScreenHeight(this);
        int screenH = UIUtils.getScreenH(this);
        Logger.d(TAG,"高度...."+screenH+"高度值..."+screenHeight);
    }

    @Override
    protected void initData() {
        //默认显示第一个
        setShowData(ITEM_ONE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //TODO 测量View 高度   - UIUtils.dip2px(44)  的原因 是 减去顶部 标题栏 高度
        mTabMarginTop = childLayout.getTop() - UIUtils.dip2px(44);
        anchorHeight = mBannerView.getHeight();
        headerHeight = layoutToolBarBackground.getHeight();

        //给缓存默认赋值
        for (int i = 0; i <= 2; i++) {
            cacheListPostion.put(i, mTabMarginTop);
        }

    }

    protected void initContentView() {
        mNestedScrollView = findViewById(R.id.myScrollView);
        //轮播图初始并赋值
        mBannerView = findViewById(R.id.mBannerView);
        mBannerView.setImageUrls(HkStockUtil.getInstance().getIvUrlData());
        //设置滚动监听
        mNestedScrollView.setOnScrollChangeListener(this);
        //外层父布局
        topLayout = findViewById(R.id.ll_fixedView);
        //普通情况父View
        childLayout = findViewById(R.id.ll_topView);
        //TODO  Tab 相关
        mIndicator = findViewById(R.id.ll_community_root);
        mIndicator.setIndicatorColor(ColorUtils.COLOR_RED);
        mIndicator.setTitles(mTitles, mTabIcons);
        //TODO  点击Tab 监听
        mIndicator.setOnTabItemClickListener(this);
        // 标题
        centerText = findViewById(R.id.text_toolbar_index);
        layoutToolBarBackground = findViewById(R.id.my_container);
        //默认设置透明
        layoutToolBarBackground.setBackgroundColor(Color.TRANSPARENT);
        //TODO  RecyclerView 初始
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_detail);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MultiTypeAdapter(mData);
        //TODO 核心代码  让 NestedScrollView帮助RecyclerView 滚动
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        //TODO 绑定多个条目   这是新写法，快速做出多种条目  有疑问 @我
        mAdapter.register(ItemOneBean.class, new HkDaXinOneProvider());
        mAdapter.register(ItemTwoBean.class, new HkDaXinTwoProvider());
        mAdapter.register(View.class, new ViewBinder());
        mRecyclerView.setAdapter(mAdapter);
        //TODO 我是头部  我可以加入任何地方
        mHeadView = LayoutInflater.from(this).inflate(R.layout.hk_daxin_detail_title_content, mRecyclerView, false);
        mHeadTvContent = (TextView) mHeadView.findViewById(R.id.tv_head_content);
        //WebView 的父布局
        mRootWebView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.webview, mRecyclerView, false);
        //TODO 做了WebView 预加载处理 （原理是 进入闪屏页开始加载WebView  到这个页面直接取缓存）
        HkStockUtil.getInstance().loadWebViewData(mRootWebView, 2);


    }


    /**
     * 设置显示数据
     */
    private void setShowData(int mPostion) {
        //高亮 选中tab 以及切换分割线位置
        mIndicator.hightTextColor(mPostion);
        mIndicator.scroll(mPostion, 0);
        //刷新数据
        mData.clear();
        switch (mPostion) {
            case ITEM_ONE:
                //常见问题
                mData.add(mRootWebView);
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
                //标题头  自己改名
                setTitle("大家好！我是C标题 券商将上个交易日用户准备的新股认购资金化划转给交易所完成认购，总资产这部分资金将减少。");
                mData.add(mHeadView);
                mData.addAll(HkStockUtil.getInstance().getZhongQianOtherList());
                List<ItemTwoBean> itemTwoBeanList = HkStockUtil.getInstance().getRenGoTimeChildList();
                mData.addAll(itemTwoBeanList);
                mData.addAll(HkStockUtil.getInstance().getZhongQianOtherList());
                mData.addAll(HkStockUtil.getInstance().getRenGoTimeChildList());
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
//        Logger.d(TAG, "onScrollChange......" + "scrollY......" + scrollY + "相差多少" + (scrollY - mTabMarginTop));
        //TODO 因为三个界面为同一个RecyclerView ，为了用户体验，有必要记录每个列表上次的滚动位置并且复原  Map 每次会覆盖上次的值
        ToastUtils.showToast(this,"当前界面"+mCurrPostion+"..高度.."+scrollY);
        if (isTopHasTab) {//防止 位置不精确
            cacheListPostion.put(mCurrPostion, scrollY);
        }
        //TODO 当前Tab 标签距离顶部的距离
        if (scrollY >= mTabMarginTop) {
            if (mIndicator.getParent() != topLayout) {
                childLayout.removeView(mIndicator);
                isTopHasTab = true;
                topLayout.addView(mIndicator);
            }
        } else {
            if (mIndicator.getParent() != childLayout) {
                topLayout.removeView(mIndicator);
                isTopHasTab = false;
                childLayout.addView(mIndicator);
            }
        }
        Logger.d(TAG, "onScrollChange.当前滚动...." + mCurrPostion + ".......scrollY......" + scrollY);
        //TODO  滚动监听  视差效果
        centerText.setVisibility(View.VISIBLE);
        //变化率
        float headerBarOffsetY = anchorHeight - headerHeight;//Toolbar与header高度的差值
        offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
        if (scrollY >= 0 && oldScrollY >= 0) {
            layoutToolBarBackground.setBackgroundColor(Color.argb((int) ((offset) * 255), 239, 65, 35));
            centerText.setTextColor(Color.argb((int) (offset * 255), 255, 255, 255));
        } else {
            layoutToolBarBackground.setBackgroundColor(Color.argb((int) (0), 239, 65, 35));
            centerText.setTextColor(Color.argb((int) 0, 255, 255, 255));
        }
    }

    @Override
    public void onItemClick(int mPostion) {
        this.mCurrPostion = mPostion;
        //显示列表内容
        //点击滚动到顶部   如何在顶部  那么不执行

        if (isTopHasTab) {
            //在顶部  处理位置   更严谨-- 小于Tab 索引      那么滑到上次的位置
            if (null != cacheListPostion && mCurrPostion <= 2 && cacheListPostion.size() > 0) {
                // 那么滑到第一个
                int result = cacheListPostion.get(mCurrPostion);
                mNestedScrollView.scrollTo(0, result);

                Logger.d(TAG, "onScrollChange....." + mCurrPostion + ".......scrollY......" + cacheListPostion.get(mCurrPostion));
            }
        } else {
            //不在顶部
            mNestedScrollView.scrollTo(0, mTabMarginTop);
        }
        setShowData(mPostion);
    }


}
