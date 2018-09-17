package view.niudong.com.demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;

import adapter.InforMationPagerAdapter;

/**
 * Created by niudong on 2017/4/7.
 * 资源包的Ac
 */
public class FragmentVPActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private XTabLayout infoTab;
    private ViewPager mViewPager;
    private String mTab[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initView();
        mTab = getResources().getStringArray(R.array.tab_names);
        setupViewPager();
    }

    protected void initView() {
        infoTab = (XTabLayout) findViewById(R.id.info_tab);
        mViewPager = (ViewPager) findViewById(R.id.information_pager);
        mViewPager.setOnPageChangeListener(this);
    }

    private void setupViewPager() {
        int length = mTab.length;
        if (null == mTab || length == 0) return;
        InforMationPagerAdapter adapter = new InforMationPagerAdapter(getSupportFragmentManager(), mTab);
        mViewPager.setAdapter(adapter);
        infoTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        infoTab.setxTabDisplayNum(3);
        infoTab.setupWithViewPager(mViewPager);
        //选中那个页面
        //在taLayout之后设置
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        ToastUtils.showToast(this, position + "");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
