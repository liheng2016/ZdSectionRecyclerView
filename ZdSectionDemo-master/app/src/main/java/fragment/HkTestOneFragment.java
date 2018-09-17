package fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import base.BaseLazyFragment;
import entity.UserInfo;
import utils.TestDataUtils;
import utils.ToastUtils;

/**
 * 名称：
 * Created by niudong on 2017/11/14.
 * Tel：18811793194
 * VersionChange：金贝塔
 */
public class HkTestOneFragment extends BaseLazyFragment implements TestDataUtils.OnUpdateListener {

    private List<UserInfo> mData;

    @Override
    protected void initData() {
        Log.d("initData1", "initData1");
    }

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        TextView textView = new TextView(getContext());
        textView.setText("Test1");
        Log.d("setView1", "setView1");
        return textView;
    }

    @Override
    protected void lazyData() {
        super.lazyData();
        if (null != mData) {
            mData.clear();
        }
        TestDataUtils.getsInstance().requestData(this);
        //注册接口

    }


    @Override
    public void onSuccess(List<UserInfo> stocks) {
        mData = stocks;
        ToastUtils.showToast(getActivity(), "我是回调回来的数据" + stocks.toString());
    }

    @Override
    public void onError(String error) {

    }

}
