package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragment.InfoFragmentFactory;

/**
 * Created by niudong on 2017/4/7.
 */


public class InforMationPagerAdapter extends FragmentStatePagerAdapter {

    private String mTabs[];

    public InforMationPagerAdapter(FragmentManager fm, String tabs[]) {
        super(fm);
        this.mTabs = tabs;
    }

    @Override
    public int getCount() {
        return (null != mTabs && mTabs.length > 0) ? mTabs.length : 0;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        return InfoFragmentFactory.createFragment(position);
    }
}

