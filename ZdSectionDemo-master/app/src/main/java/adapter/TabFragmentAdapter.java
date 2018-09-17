package adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.webkit.WebViewFragment;

import fragment.InfoFragmentFactory;


/**

 */
public class TabFragmentAdapter extends FragmentPagerAdapter {

    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return InfoFragmentFactory.createFragment(position);

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "WEBVIEW";
        }
        if (position == 1) {
            return "WEBVIEW";
        }
        if (position == 2) {
            return "RECYCLERVIEW";
        }
        return "tab" + position;
    }
}
