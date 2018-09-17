package fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import base.BaseLazyFragment;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2017/12/29.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class LayoutFragment extends BaseLazyFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {

    View  view= inflater.inflate(R.layout.fragment_layout_new,null);
        return view;
    }
}
