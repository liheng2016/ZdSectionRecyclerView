package fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import base.BaseLazyFragment;

/**
 * 名称：
 * Created by niudong on 2017/11/14.
 * Tel：18811793194
 * VersionChange：金贝塔
 */
public class HkTestTwoFragment extends BaseLazyFragment {
    @Override
    protected void initData() {
        Log.d("initData2", "initData2");
    }

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        TextView textView = new TextView(getContext());
        textView.setText("Test2");
        Log.d("setView2", "setView2");
        return textView;
    }


    @Override
    protected void lazyData() {
        super.lazyData();
        Log.d("lazyData2", "lazyData2");
    }
}
