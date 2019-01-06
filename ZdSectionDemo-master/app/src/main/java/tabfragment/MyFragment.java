package tabfragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import base.TestBaseFragment;
import utils.ToastUtils;
import view.niudong.com.demo.FragmentStackActivity;
import view.niudong.com.demo.MainActivity;
import view.niudong.com.demo.R;

/**
 * 名称：我的页面
 * Created by niudong on 2018/5/29.
 * Tel：18811793194
 */
public class MyFragment extends TestBaseFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected String getFragmentTitle() {
        return "遇见更好的自己";
    }
    @Override
    protected void initData() {
        super.initData();
        ToastUtils.showToast(mContext,"MyFragment");


    }

    @Override
    protected void initView(View view) {
        super.initView(view);

    }

    @Override
    public void onResume() {
        super.onResume();
        ToastUtils.showToast(mContext,"onResume MyFragment");
    }
}
