package fragment;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import base.TestBaseFragment;
import customview.BannerView;
import customview.TestCustomView;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/6/13.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class BannerviewFragment extends TestBaseFragment implements BannerView.onItemClickListener {

    private BannerView mBannerView;
    private TestCustomView mTestCustomView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_bannerview_fragment;
    }

    @Override
    protected String getFragmentTitle() {
        return "自定义控件测试";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mTestCustomView = view.findViewById(R.id.mTestCustomView);
    }

    @Override
    public void onClick(View view, int position) {

    }


}
