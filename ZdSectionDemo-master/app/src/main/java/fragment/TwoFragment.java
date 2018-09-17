package fragment;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.view.Gravity;
import android.widget.TextView;

import base.BaseFragment;
import utils.IconFontTextview;
import utils.Logger;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/8/27.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class TwoFragment extends BaseFragment {

    private static final java.lang.String TAG = TwoFragment.class.getName();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        Logger.d(TAG, "我是Fragment：  牛栋");
        ToastUtils.showToast(getActivity(), "牛栋");
    }

 /*   @Override
    protected void lazyData() {
        super.lazyData();
    }*/


}
