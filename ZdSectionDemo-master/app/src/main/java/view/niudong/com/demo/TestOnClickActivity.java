package view.niudong.com.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import fragment.HkTestOneFragment;
import fragment.HkTestTwoFragment;

public class TestOnClickActivity extends AppCompatActivity implements
        View.OnClickListener, ViewPager.OnPageChangeListener {

    /**
     * 当前选中的Tabs 序号
     */
    private int mCurShowTabIndex = -1;


    private TextView[] mTxtTabs;
    private View[] mViewLines;
    private LinearLayout one;
    private LinearLayout two;
    private ViewPager mViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_on_click);
        mTxtTabs = new TextView[2];
        mTxtTabs[0] = (TextView) findViewById(R.id.tv_tabs_text1);
        one = (LinearLayout) findViewById(R.id.ll_tabs_tab1);
        mTxtTabs[1] = (TextView) findViewById(R.id.tv_tabs_text2);
        two = (LinearLayout) findViewById(R.id.ll_tabs_tab2);
        mViewPager = (ViewPager) findViewById(R.id.test_viewPager);

        mViewLines = new View[2];
        mViewLines[0] = findViewById(R.id.view_tabs_line1);
        mViewLines[1] = findViewById(R.id.view_tabs_line2);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(this);
        HkTestOneFragment mOne = new HkTestOneFragment();
        fragmentList.add(mOne);
        HkTestTwoFragment mTwo = new HkTestTwoFragment();
        fragmentList.add(mTwo);
        setupViewPager();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tabs_tab1:
                showTab(0);
                break;
            case R.id.ll_tabs_tab2:
                showTab(1);
                break;
            default:
                break;

        }
    }


    /**
     * 切换tab被选中, 以及加载相应的页面
     */
    private void showTab(int index) {
        if (mCurShowTabIndex == index) {
            return;
        }
        setSelectedTabLayout(mCurShowTabIndex, index);
        mViewPager.setCurrentItem(index, false);
        //        switch (index) {
//            case 0:
//                //最新
//
//                break;
//            case 1:
//                setSelectedTabLayout(mCurShowTabIndex, 1);
//                mViewPager.setCurrentItem(1, false);
//                break;
//            default:
//                break;
//        }



    }

    /**
     * 选中Tab
     *
     * @param oldSelected 上一个Tab
     * @param newSelected 新的Tab
     */

    private void setSelectedTabLayout(int oldSelected, int newSelected) {
        mCurShowTabIndex = newSelected;
        if (oldSelected != -1) {
            //恢复原来的
            mTxtTabs[oldSelected].setSelected(false);
            mViewLines[oldSelected].setVisibility(View.INVISIBLE);
        }

        if (newSelected != 0) {
            mTxtTabs[0].setSelected(false);
            mViewLines[0].setVisibility(View.INVISIBLE);
        }
        //设置当前的
        mTxtTabs[newSelected].setSelected(true);
        mViewLines[newSelected].setVisibility(View.VISIBLE);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        showTab(position);
//        boolean isSelectTab = position == 0;
//        mTxtTabs[0].setSelected(isSelectTab);
//        mViewLines[0].setVisibility(isSelectTab ? View.VISIBLE : View.INVISIBLE);
//        mTxtTabs[1].setSelected(!isSelectTab);
//        mViewLines[1].setVisibility(!isSelectTab ? View.VISIBLE : View.INVISIBLE);
//        mViewPager.setCurrentItem(position, false);
//        mViewPager.setOffscreenPageLimit(2);
//        mCurShowTabIndex=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
    }
}
