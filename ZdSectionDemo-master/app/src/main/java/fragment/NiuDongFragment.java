package fragment;

import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import adapter.TestAdapter;
import base.BaseLazyFragment;
import customview.NestedScrollWebView;
import customview.RecyclerViewFitWebView;
import entity.DetailModel;
import test.NestedWebView;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/9/2.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class NiuDongFragment extends BaseLazyFragment {
    public  String FEIDI_ZIXUN_URL = "https://www.21fid.com/external/dyqh/newsFid/news/financing";

    private static final java.lang.String TAG = NiuDongFragment.class.getSimpleName();
    private View mContentView;
    private RecyclerView mRecyclerView;
    private WebView webView;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        mContentView = inflater.inflate(R.layout.fragment_zhidong_new, container, false);


        webView = (WebView) mContentView.findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        settings.setJavaScriptEnabled(true);


        return mContentView;
    }

    @Override
    protected void initData() {
        //Logger.d(TAG, "我是Fragment  initData：  牛栋");
        webView.loadUrl(FEIDI_ZIXUN_URL);
    }

    @Override
    protected void lazyData() {
        super.lazyData();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

