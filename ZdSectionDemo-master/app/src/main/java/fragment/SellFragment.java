package fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import base.BaseLazyFragment;
import customview.MyIndicator;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/10/12.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class SellFragment extends BaseLazyFragment implements ViewPager.OnPageChangeListener {
    private View view;
    private MyIndicator tabLayout;
    private ViewPager viewPager;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_sell, container, false);
        tabLayout = (MyIndicator) view.findViewById(R.id.my_tab);
        viewPager = (ViewPager) view.findViewById(R.id.tb_view_pager);
        return view;
    }

    @Override
    protected void initData() {
        viewPager.setOnPageChangeListener(this);
        //界面
        setupViewPager();
    }

    @Override
    protected void lazyData() {
        super.lazyData();
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabLayout.whichChecked(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        String[] titleName = {"one", "two"};

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        //这是为什么？？
        @Override
        public Fragment getItem(int position) {
            return SellChildFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleName[position];
        }

        @Override
        public int getCount() {
            return titleName.length;
        }
    }
}