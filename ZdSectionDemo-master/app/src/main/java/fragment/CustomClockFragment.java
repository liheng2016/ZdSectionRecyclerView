package fragment;

import base.TestBaseFragment;
import view.niudong.com.demo.R;


/**
 * 名称：
 * Created by niudong on 2018/9/20
 * Tel：18811793194
 * VersionChange：mthq1.0
 */
public class CustomClockFragment extends TestBaseFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_clock;
    }

    @Override
    protected String getFragmentTitle() {
        return "自定义钟表";
    }
}
