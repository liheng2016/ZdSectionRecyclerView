package fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

public class OneFragment extends BaseFragment {
    //飞笛资讯H5 页面
    public  String FEIDI_ZIXUN_URL = "https://www.21fid.com/external/dyqh/newsFid/news/financing";
    //飞笛财经日历Url
    public static String CAIJING_CALENDAR_URL = "https://www.21fid.com/external/dyqh/newsFid/financeCalendar/event";
    private static final java.lang.String TAG = OneFragment.class.getName();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initView() {
        WebView webView = (WebView) mContentView.findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(FEIDI_ZIXUN_URL);


    }

    @Override

    protected void initData() {
        Logger.d(TAG, "我是Fragment：  智栋");
    }

}
