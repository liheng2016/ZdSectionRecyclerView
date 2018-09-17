package view.niudong.com.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import fragment.MimeFragment;
import fragment.NiuDongFragment;
import fragment.TradeFragmentFactory;
import fragment.ZhiDongFragment;
import recycleview.adapter.NavigationPagerAdapter;

public class NavigationMainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    private static final String TAG = NavigationMainActivity.class.getName();
    //
    private BottomNavigationBar mBottomNavigationBar;
    private ViewPager mViewPager;
    private List<Fragment> fragments;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_navigation_main);
//        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
    }

    @Override
    protected void initListener() {
        mBottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    protected void initData() {
        initBottomNavigation();
        //initViewPager();
        initFirstFragment();
    }

    private void initBottomNavigation() {

        mBottomNavigationBar.clearAll();
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        BottomNavigationItem conversationItem = new BottomNavigationItem(R.mipmap.ic_launcher, R.string.buy);
//        conversationItem.setActiveColor(getResources().getColor(R.color.btn_normal));//选中的颜色
//        conversationItem.setInActiveColor(getColoretResources().getColor(R.color.inActive));//没选中的颜色
        mBottomNavigationBar.addItem(conversationItem);

        BottomNavigationItem contactItem = new BottomNavigationItem(R.mipmap.ic_launcher, R.string.sell);
//        contactItem.setActiveColor(getResources().getColor(R.color.btn_normal));//选中的颜色
//        contactItem.setInActiveColor(getResources().getColor(R.color.inActive));//没选中的颜色
        mBottomNavigationBar.addItem(contactItem);

        BottomNavigationItem pluginItem = new BottomNavigationItem(R.mipmap.ic_launcher, R.string.revocation);
//        pluginItem.setActiveColor(getResources().getColor(R.color.btn_normal));//选中的颜色
//        pluginItem.setInActiveColor(getResources().getColor(R.color.inActive));//没选中的颜色
        mBottomNavigationBar.addItem(pluginItem);
        BottomNavigationItem quItem = new BottomNavigationItem(R.mipmap.ic_launcher, R.string.query);
//        pluginItem.setActiveColor(getResources().getColor(R.color.btn_normal));//选中的颜色
//        pluginItem.setInActiveColor(getResources().getColor(R.color.inActive));//没选中的颜色
        mBottomNavigationBar.addItem(quItem);

        mBottomNavigationBar.setActiveColor(R.color.btn_normal);
        mBottomNavigationBar.setInActiveColor(R.color.inActive);

        mBottomNavigationBar.initialise();
        mBottomNavigationBar.setTabSelectedListener(NavigationMainActivity.this);
    }

    /**
     * 左右滑动时使用
     */
    private void initViewPager() {

        fragments = new ArrayList<>();
        fragments.add(new fragment.BuyFragment());
        fragments.add(new NiuDongFragment());
        fragments.add(new ZhiDongFragment());
        fragments.add(new MimeFragment());

        mViewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager(), fragments));
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
    }
    //TODO BottomBar 选中监听
    @Override
    public void onTabSelected(int position) {
//        mViewPager.setCurrentItem(position, false);
//        Fragment
        Log.d(TAG, "onTabSelected......." + position);
        /**
         * 先判断当前Fragment是否被添加到了MainActivity中
         * 如果添加了则直接显示即可
         * 如果没有添加则添加，然后显示
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = TradeFragmentFactory.getInstance().createFragment(position);
        if (!fragment.isAdded()) {
            transaction.add(R.id.fl_content, fragment, "" + position);
        }
        transaction.show(fragment).commit();
//        tvTitle.setText(titleIds[position]);
    }


    @Override
    public void onTabUnselected(int positon) {
        getSupportFragmentManager().beginTransaction().hide(TradeFragmentFactory.getInstance().createFragment(positon))
                .commit();
        Log.d(TAG, "onTabUnselected......." + positon);
    }

    @Override
    public void onTabReselected(int position) {
        Log.d(TAG, "onTabReselected......." + position);
    }

    //TODO  使用ViewPager
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        mBottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //TODO  使用Fragment
    private void initFirstFragment() {
        /**
         * 如果这个Activity中已经有老（就是Activity保存的历史的状态，又恢复了）的Fragment，先全部移除
         */
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < 4; i++) {
            Fragment fragment = supportFragmentManager.findFragmentByTag(i + "");
            if (fragment != null) {
                fragmentTransaction.remove(fragment);
            }
        }
        fragmentTransaction.commit();

        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,
                TradeFragmentFactory.getInstance().createFragment(0), "0").commit();
        //tvTitle.setText(R.string.conversation);
    }
}
