package adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 当前类注释:Fragment，Viewpager的自定义适配器
 * QQ： 1664131613
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    String[] titleName = {"one", "two"};
    private List<Fragment> fragmentList;

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> fragmentList) {
        super(manager);
        this.fragmentList = fragmentList;
    }

    //这是为什么？？
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
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

