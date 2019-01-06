package fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import base.TestBaseFragment;
import entity.ItemOneBean;
import entity.ItemTwoBean;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.HkDaXinOneProvider;
import mulitbinder.HkDaXinTwoProvider;
import mulitbinder.ViewBinder;
import utils.HkStockUtil;
import utils.Logger;
import utils.MyUtil;
import utils.ToastUtils;
import utils.UIUtils;
import view.niudong.com.demo.R;

/**
 * 名称：TODO 一个RecyclerView 实现固定头部及子View 切换Tab
 * 分析头部固定实现
 * Created by niudong on 2018/3/31.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 * <p>
 * onScrollChange   23上使用
 * 渐变参考：
 * https://blog.csdn.net/jdsjlzx/article/details/49966101
 */
public class MulitTabFragment extends TestBaseFragment implements View.OnClickListener {
    public static final int ITEM_ONE = 0;
    public static final int ITEM_TWO = 1;
    public static final int ITEM_THREE = 2;
    public static final int ITEM_FOUR = 3;
    //VIew  列表
    private RecyclerView mRecyclerView;
    //头部文字View
    private View mChildContentTitlte;
    //头部文字
    private TextView mHeadTvContent;
    //其他情况
    private MultiTypeAdapter mAdapter;
    private Items mData = new Items();
    /**
     * 当前选中的Tabs 序号
     */
    private int mCurShowTabIndex = -1;
    /**
     * Tab 相关
     */
    private TextView[] mTxtTabs;
    private View[] mViewLines;
    private View mOtherView;
    private View mHeadView;
    private View mTabTitleView;
    private LinearLayout mllTopView;
    private LinearLayout mRootWebView;
    private View[] mViewTopLines;
    private TextView[] mTxtTopTabs;
    private int viewHeight;
    /**
     * 记录列表的滚动位置
     */
    /**
     * 记录三个列表缓存位置
     */
    private HashMap<Integer, Integer[]> cacheListPostion;
    /**
     * 当前的页面
     */
    private int mClickPage;
    private boolean isTabTop;
    private Integer[] topPostion;
    private int currPage = -1;
    private boolean isFirst = true;
    private TextView tVbgHead;
    private LinearLayoutManager mLayoutManager;
    private int dyPos;
    private int jugedyPos;
    private View mMoreTitlte;
    private boolean isShowMore;


    @Override
    protected void initView(final View view) {
        //TODO  RecyclerView 初始
        mRecyclerView = (RecyclerView) view.findViewById(R.id.hk_daxin_recycler_view);
        //TODO 他可以实现固定头部
        cacheListPostion = new HashMap<>();
//        tVbgHead = (TextView) view.findViewById(R.id.tv_bg_head);
//        tVbgHead.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
//        tVbgHead.setTextColor(Color.WHITE);
//        topPostion = new Integer[]{2, UIUtils.dip2px(160)};
        mllTopView = (LinearLayout) view.findViewById(R.id.ll_topView);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Top 点击事件
        initTopView(view);
        mAdapter = new MultiTypeAdapter(mData);
        //TODO  预加载 WebView 所以写了两个
        //TODO 绑定多个条目
        mAdapter.register(ItemOneBean.class, new HkDaXinOneProvider());
        mAdapter.register(ItemTwoBean.class, new HkDaXinTwoProvider());
        mAdapter.register(View.class, new ViewBinder());
        mRecyclerView.setAdapter(mAdapter);

        //TODO 我是头部  我可以加入任何地方
        mHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.investor_head_view, mRecyclerView, false);
        //TODO  我是Tab 标题
        mTabTitleView = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_three_click, mRecyclerView, false);
        //TODO  顶部Tab初始  其实自定义了会简洁 清爽
        initTabView(mTabTitleView);
        // 获得ViewTreeObserver
        //注册观察者，监听变化
        //        TODO 测量方法
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mHeadView.measure(width, height);
        viewHeight = mHeadView.getMeasuredHeight() - UIUtils.dip2px(40);
        Logger.d("mHeadViewMeasuredHeightht", "mHeadViewMeasuredHeightht" + viewHeight);

        //TODO  子View内容标题
        mChildContentTitlte = LayoutInflater.from(getActivity()).inflate(R.layout.hk_daxin_detail_title_content, mRecyclerView, false);
        mHeadTvContent = (TextView) mChildContentTitlte.findViewById(R.id.tv_head_content);
        mMoreTitlte = LayoutInflater.from(getActivity()).inflate(R.layout.hk_daxin_click_more, mRecyclerView, false);
        RelativeLayout mClickMore = mMoreTitlte.findViewById(R.id.click_more);
        mClickMore.setOnClickListener(this);
        //一个View
        mOtherView = LayoutInflater.from(getActivity()).inflate(R.layout.hk_daxin_detail_title_other, mRecyclerView, false);
        mRootWebView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.webview, mRecyclerView, false);
        HkStockUtil.getInstance().loadWebViewData(mRootWebView, 1);
        //TODO 监听 滑动
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }

            /**
             * TODO
             * onScrolled提供了一个dx，dy的方法。
             * 但是该方法只能判定一次性滑动的距离，dx是横向，dy是纵向。
             * 而且默认停滞状态距离是0，如果要向上返回滑动，那么就会变成负数。也就是每一次滑动，只要停滞，距离都会重新计算。
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                jugedyPos += dy;//偏移量
                int firstVisiblePos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.d("Tab", "当前dy " + dy + "............当前可见的postion" + jugedyPos + "viewheight" + viewHeight);

                if (firstVisiblePos >= 1) {
                    isTabTop = true;
                    dyPos = viewHeight;
                    MyUtil.setVisibility(mllTopView, View.VISIBLE);
                } else {
                    isTabTop = false;
                    MyUtil.setVisibility(mllTopView, View.GONE);
                }
//                TODO  标题栏渐变效果
//                dyPos += dy;//偏移量
//                int dyPosResult = dyPos;
//                Log.d("Tab", "............viewHeight" + viewHeight + ".........dyPos..." + dyPosResult + ".....DY...." + dy);
//                if (dyPosResult <= 0) {
//                    //静止并处于最顶端状态
//                    tVbgHead.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
//                    tVbgHead.setTextColor(Color.BLACK);
//                } else if (dyPosResult > 0 && dyPosResult <= viewHeight) {//滑动在0-400距离的时候
//                    tVbgHead.setTextColor(Color.WHITE);
//                    float scale = (float) dyPosResult / (viewHeight);
//                    float alpha = (255 * scale);
//                    tVbgHead.setBackgroundColor(Color.argb((int) alpha, 254, 0, 0));
//                }
            }
        });


    }

    private void initTabView(View view) {
        mTxtTabs = new TextView[3];
        mTxtTabs[0] = (TextView) view.findViewById(R.id.tv_tabs_text1);
        view.findViewById(R.id.ll_tabs_tab1).setOnClickListener(this);
        mTxtTabs[1] = (TextView) view.findViewById(R.id.tv_tabs_text2);
        view.findViewById(R.id.ll_tabs_tab2).setOnClickListener(this);
        mTxtTabs[2] = (TextView) view.findViewById(R.id.tv_tabs_text3);
        view.findViewById(R.id.ll_tabs_tab3).setOnClickListener(this);
        //TODO 底部分割线处理
        mViewLines = new View[3];
        mViewLines[0] = view.findViewById(R.id.view_tabs_line1);
        mViewLines[1] = view.findViewById(R.id.view_tabs_line2);
        mViewLines[2] = view.findViewById(R.id.view_tabs_line3);
    }

    private void initTopView(View view) {
        mTxtTopTabs = new TextView[3];
        mTxtTopTabs[0] = (TextView) view.findViewById(R.id.tv_tabs_text1);
        view.findViewById(R.id.ll_tabs_tab1).setOnClickListener(this);
        mTxtTopTabs[1] = (TextView) view.findViewById(R.id.tv_tabs_text2);
        view.findViewById(R.id.ll_tabs_tab2).setOnClickListener(this);
        mTxtTopTabs[2] = (TextView) view.findViewById(R.id.tv_tabs_text3);
        view.findViewById(R.id.ll_tabs_tab3).setOnClickListener(this);
        //TODO 底部分割线处理
        mViewTopLines = new View[3];
        mViewTopLines[0] = view.findViewById(R.id.view_tabs_line1);
        mViewTopLines[1] = view.findViewById(R.id.view_tabs_line2);
        mViewTopLines[2] = view.findViewById(R.id.view_tabs_line3);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tabs_tab1:
                mClickPage = 0;

                setSelectedTabLayout(mCurShowTabIndex, 0);
                setShowData(ITEM_ONE);
                break;
            case R.id.ll_tabs_tab2:
                mClickPage = 1;
                setSelectedTabLayout(mCurShowTabIndex, 1);
                setShowData(ITEM_TWO);

                break;
            case R.id.ll_tabs_tab3:
                mClickPage = 2;
                setSelectedTabLayout(mCurShowTabIndex, 2);
                setShowData(ITEM_THREE);
                break;
            case R.id.click_more:
                isShowMore = !isShowMore;
                setShowData(ITEM_THREE);
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
//            TOP
            mTxtTopTabs[oldSelected].setSelected(false);
            mViewTopLines[oldSelected].setVisibility(View.INVISIBLE);
        }

        if (newSelected != 0) {
            for (int i = 0; i < mTxtTabs.length; i++) {
                mTxtTabs[i].setSelected(false);
                mViewLines[i].setVisibility(View.INVISIBLE);
//                Top
                mTxtTopTabs[i].setSelected(false);
                mViewTopLines[i].setVisibility(View.INVISIBLE);
            }
        }
        //设置当前的
        mTxtTabs[newSelected].setSelected(true);
        mViewLines[newSelected].setVisibility(View.VISIBLE);

        mTxtTopTabs[newSelected].setSelected(true);
        mViewTopLines[newSelected].setVisibility(View.VISIBLE);
    }

    /**
     * 设置显示数据
     */
    private void setShowData(int mPostion) {
//        if (mClickPage == currPage) return;   优化处理
        //刷新数据
        mData.clear();
        //添加头部和Tab
        mData.add(mHeadView);
        mData.add(mTabTitleView);
        switch (mPostion) {
            case ITEM_ONE:
                setTitle("大家好！我是A标题，金贝塔的认购截止日期会早于交易所公布的截止日期。");
                //标题头  自己改名
                mData.add(mChildContentTitlte);
                //一种数据样式
                mData.addAll(HkStockUtil.getInstance().getRenGouTimeList());
                //另一种数据
                mData.addAll(HkStockUtil.getInstance().getRenGoTimeChildList());
                mData.addAll(HkStockUtil.getInstance().getRenGouTimeList());
                currPage = 0;
                break;
            case ITEM_TWO:
                mData.add(mRootWebView);
                currPage = 1;
                break;
            case ITEM_THREE:
                currPage = 2;
                mData.add(mMoreTitlte);
                setTitle("大家好！我是C标题 券商将上个交易日用户准备的新股认购资金化划转给交易所完成认购，总资产这部分资金将减少。");
                mData.add(mChildContentTitlte);
                mData.addAll(HkStockUtil.getInstance().getZhongQianOtherList());
                if (isShowMore) {
                    //标题头  自己改名
                    mData.add(mOtherView);
                    mData.addAll(HkStockUtil.getInstance().getRenGoTimeChildList());
                }

                //常见问题
                break;
            default:
                break;
        }
        mAdapter.notifyDataSetChanged();
//        if (!isTabTop && !isFirst) {
//            for (int i = 0; i <= 2; i++) {
//                cacheListPostion.put(i, topPostion);
//            }
//        }

        isFirst = false;
//        scrollToPosition();
    }


    @Override
    protected void initData() {
        //默认显示第一个
        LinearLayout linearLayout = (LinearLayout) mTxtTopTabs[0].getParent();
        linearLayout.performClick();

        setSelectedTabLayout(mCurShowTabIndex, 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_hk_daxin;
    }

    @Override
    protected String getFragmentTitle() {
        return "明天，遇见最好的自己";
    }


    /**
     * 标题
     */
    private void setTitle(String inputContent) {
        mHeadTvContent.setText(inputContent);
    }


    /**
     * 记录RecyclerView当前位置
     */


    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            int lastOffset = topView.getTop();

            //得到该View的数组位置
            int lastPosition = layoutManager.getPosition(topView);
            //存储缓存
            if (isTabTop) {
                boolean isNull = cacheListPostion.size() == 0 ||
                        null == cacheListPostion.get(mClickPage) || cacheListPostion.get(mClickPage).length == 0;
                cacheListPostion.put(mClickPage, new Integer[]{isNull ? 2 : lastPosition, isNull ? UIUtils.dip2px(40) : lastOffset});
            }
            Logger.d(TAG, "getPositionAndOffset......" + "lastPosition......" + lastPosition + "lastOffset......." + lastOffset);

        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (null != cacheListPostion && mClickPage <= 2 && cacheListPostion.size() > 0) {
            Integer[] integers = cacheListPostion.get(mClickPage);
            if (mRecyclerView.getLayoutManager() != null && null != integers && integers[0] >= 0) {
                Logger.d(TAG, "scrollToPosition......" + "lastPosition......" + integers[0] + "lastOffset......." + integers[1]);
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(integers[0], integers[1]);
            }
        }
    }
}
