package fragment;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.MultiRecyclerAdapter;
import base.BaseLazyFragment;
import contants.Visitable;
import entity.ContentBean;
import entity.DividerBean;
import entity.SearchBean;
import utils.Logger;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/9/2.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 * <p>
 * 便于维护。增删Item的类型不需要修改Adapter的代码；
 * 条例清晰。不同类型的Item对应不同的Bean类，对应不用的ViewHolder,对应不同的layout
 * item type <—-> layout <—> ViewHolder <—-> java bean
 * 工厂接口。抽象出TypeFactory接口，针对同一个Bean,可以使用不同的layout和不同的ViewHolder。
 */


public class ZhiDongFragment extends BaseLazyFragment {
    private static final java.lang.String TAG = ZhiDongFragment.class.getSimpleName();
    private View view;
    private RecyclerView mRecyclerView;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        WebView   webView = new WebView(getActivity());
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        settings.setJavaScriptEnabled(true);

webView.loadUrl("http://ads.wutongtec.com:8080/homepage/main.html");

        return webView;
    }

    @Override
    protected void initData() {


    }


    @Override
    protected void lazyData() {
        super.lazyData();


}}
