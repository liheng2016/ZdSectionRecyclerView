package tabfragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import base.TestBaseFragment;
import in.srain.cube.views.ptr.PtrFrameLayout;
import pullrefresh.XctPtrLayout;
import pullrefresh.XctRefreshLayout;
import utils.ThreadFactory;
import utils.ToastUtils;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.R;

/**
 *mFramePageListener.navigate(2);
 * 名称：
 * Created by niudong on 2018/5/29.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
    mFramePageListener = (IFramePage) activity;
 */
public class HomeFragment extends TestBaseFragment {

    private XctPtrLayout mPullDownView;
    private TextView mTvContent;
    private boolean isErroData;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected String getFragmentTitle() {
        return "首页";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mPullDownView = (XctPtrLayout) view.findViewById(R.id.pull);
        mTvContent = (TextView) view.findViewById(R.id.tv_hhe);
        //正在为您准备数据.....
        mPullDownView.emptyViewLoading(null);
        //加载失败支持点击刷新
        mPullDownView.setEmptyClickRefresh(true);

    }

    @Override
    protected void initListener() {
        super.initListener();
        //下拉刷新
        mPullDownView.setPtrHandler(new XctRefreshLayout.DefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                if (isErroData){
                    mPullDownView.emptyViewLoading(null);
                }else {
                    mPullDownView.refreshComplete();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mTvContent, header);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               mPullDownView.showContentView(true);
            }
        },1500);


        ToastUtils.showToast(mContext, "首页HomeFragment");

    }
}
