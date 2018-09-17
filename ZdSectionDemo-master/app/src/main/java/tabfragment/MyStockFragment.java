package tabfragment;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;

import adapter.InforMationPagerAdapter;
import base.TestBaseFragment;
import customview.ViewPagerScroll;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/5/29.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class MyStockFragment extends TestBaseFragment{

    private String mTab[];
    private TabLayout infoTab;
    private ViewPager mViewPager;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    TabLayout tabLayout;
    AppBarLayout appbar;
    ViewPager viewpager;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_stock;
    }

    @Override
    protected String getFragmentTitle() {
        return "自选";
    }
    @Override
    protected void initData() {
        super.initData();
        ToastUtils.showToast(mContext,"MyStockFragment");
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

    }

    private void setupViewPager() {
        int length = mTab.length;
        if (null == mTab || length == 0) return;
        InforMationPagerAdapter adapter = new InforMationPagerAdapter(getChildFragmentManager(), mTab);
        mViewPager.setAdapter(adapter);
        infoTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        infoTab.setupWithViewPager(mViewPager);
        //选中那个页面
        //在taLayout之后设置
    }


}
