package fragment;

import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import base.TestBaseFragment;
import entity.ItemOneBean;
import entity.ItemTwoBean;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.HkDaXinOneProvider;
import mulitbinder.HkDaXinTwoProvider;
import mulitbinder.ViewBinder;
import utils.HkStockUtil;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/3/31.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class RecyWebFragment extends TestBaseFragment implements View.OnClickListener {

    //VIew  列表
    private RecyclerView mRecyclerView;

    //其他情况
    private MultiTypeAdapter mAdapter;
    private Items mData = new Items();
    public String FEIDI_ZIXUN_URL = "https://www.21fid.com/external/dyqh/newsFid/news/financing";

//    private View webRootView;
//    private WebView webItemView;
    private LinearLayout mRootWebView;


    @Override
    protected void initView(View view) {
        //TODO  RecyclerView 初始
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_investor_main);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MultiTypeAdapter(mData);


        //TODO 绑定多个条目
        mAdapter.register(View.class, new ViewBinder());
        mRecyclerView.setAdapter(mAdapter);
        //TODO 我是头部  我可以加入任何地方
        mRootWebView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.webview, mRecyclerView, false);
        HkStockUtil.getInstance().loadWebViewData(mRootWebView,5);
    }


    @Override
    public void onClick(View v) {

    }


    /**
     * 设置显示数据
     */
    private void setShowData() {
        //刷新数据
        mData.clear();
        mData.add(mRootWebView);
        mAdapter.notifyDataSetChanged();
    }



    @Override
    protected void initData() {
        setShowData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_investor_bill_main;
    }

    @Override
    protected String getFragmentTitle() {
        return null;
    }



}
