package view.niudong.com.demo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import adapter.InforMationPagerAdapter;
import customview.ZdViewPagerIndicator;
import customview.StickyNavLayout;
import utils.ColorUtils;

/**
 * 该类主要是RecyclerView嵌套
 */
public class NewActivity extends AppCompatActivity implements ZdViewPagerIndicator.onTabItemClickListener {
    private String[] mTitles = new String[]{"简介", "评价", "相关"};
    private ZdViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    //        private TabFragment[] mFragments = new TabFragment[mTitles.length];
    private StickyNavLayout roottickyNavLayout;

    /**
     * Tab选项卡的图片
     */
    private int[] mTabIcons = {R.drawable.detail_icon_tab1_style, R.drawable.detail_icon_tab2_style,
      R.drawable.detail_icon_tab3_style};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cool_detail_tab_main);
        initViews();
        initDatas();
        initEvents();
    }

    private void initEvents() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
                mIndicator.hightTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDatas() {
        mIndicator.setIndicatorColor(ColorUtils.COLOR_RED);
        mIndicator.setTitles(mTitles,mTabIcons);
        InforMationPagerAdapter mAdapter = new InforMationPagerAdapter(getSupportFragmentManager(), mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);


    }

    private void initViews() {
        roottickyNavLayout = (StickyNavLayout) findViewById(R.id.mStickyNavLayout);
        mIndicator = (ZdViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        mIndicator.setOnTabItemClickListener(this);
    }

    @Override
    public void onItemClick(int mPostion) {
        mViewPager.setCurrentItem(mPostion);
        mIndicator.hightTextColor(mPostion);
    }
}
