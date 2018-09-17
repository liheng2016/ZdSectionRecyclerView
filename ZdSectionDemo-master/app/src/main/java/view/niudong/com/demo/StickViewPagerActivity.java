package view.niudong.com.demo;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import adapter.InforMationPagerAdapter;
import customview.ObservableScrollView;

public class StickViewPagerActivity extends AppCompatActivity implements View.OnClickListener, ObservableScrollView.OnObservableScrollViewScrollChanged{
    /**
     * 当前选中的Tabs 序号
     */
    private String[] mTitles = new String[]{"简介", "评价", "相关"};
    private int mCurShowTabIndex = -1;
    private TextView[] mTxtTabs;
    private View[] mViewLines;
    private LinearLayout topLayout;
    private LinearLayout childLayout;
    private LinearLayout contentLayout;
    private int top;
    private ObservableScrollView observableScrollView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_view_pager);
        observableScrollView = findViewById(R.id.mScrollView);
        observableScrollView.setOnObservableScrollViewScrollChanged(this);
//        外层父布局
        topLayout = findViewById(R.id.top_content);
//        普通情况父View
        childLayout = findViewById(R.id.child_content);
        contentLayout = findViewById(R.id.ll_community_root);
        mTxtTabs = new TextView[3];
        mTxtTabs[0] = (TextView) findViewById(R.id.tv_tabs_text1);
        mTxtTabs[1] = (TextView) findViewById(R.id.tv_tabs_text2);
        mTxtTabs[2] = (TextView) findViewById(R.id.tv_tabs_text3);
        findViewById(R.id.ll_tabs_tab1).setOnClickListener(this);
        findViewById(R.id.ll_tabs_tab2).setOnClickListener(this);
        findViewById(R.id.ll_tabs_tab3).setOnClickListener(this);
        //TODO 底部分割线处理
        mViewLines = new View[3];
        mViewLines[0] = findViewById(R.id.view_tabs_line1);
        mViewLines[1] = findViewById(R.id.view_tabs_line2);
        mViewLines[2] = findViewById(R.id.view_tabs_line3);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        InforMationPagerAdapter mAdapter = new InforMationPagerAdapter(getSupportFragmentManager(), mTitles);
        mViewPager.setAdapter(mAdapter);

        setSelectedTabLayout(mCurShowTabIndex,0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                setSelectedTabLayout(mCurShowTabIndex, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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
        mViewPager.setCurrentItem(newSelected);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        top = childLayout.getTop();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tabs_tab1:
                setSelectedTabLayout(mCurShowTabIndex, 0);
                break;
            case R.id.ll_tabs_tab2:
                setSelectedTabLayout(mCurShowTabIndex, 1);
                break;
            case R.id.ll_tabs_tab3:
                setSelectedTabLayout(mCurShowTabIndex, 2);
                break;
            default:
        }
    }

    @Override
    public void onObservableScrollViewScrollChanged(ScrollView scrollView, int l, int scrollY, int oldl, int oldt) {
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
