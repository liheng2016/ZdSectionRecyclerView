package recycleview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by niudong on 2017/10/12.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class NavigationPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    public NavigationPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> fragments) {
        super(supportFragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
