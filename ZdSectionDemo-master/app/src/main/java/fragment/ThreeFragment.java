package fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseFragment;
import utils.Logger;
import utils.ToastUtils;
import view.niudong.com.demo.R;
import view.niudong.com.demo.TestHtmlJsActivity;

/**
 * Created by niudong on 2017/8/27.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class ThreeFragment extends BaseFragment {

    private static final java.lang.String TAG = ThreeFragment.class.getName();
    private WebView webview;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView() {
        webview = (WebView) mContentView.findViewById(R.id.web_view);
        WebSettings webSettings = webview.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //点击超链接的时候重新在原来的进程上加载URL
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webview.loadUrl("file:///android_asset/err_html.html");
        //在js中调用本地java方法
        webview.addJavascriptInterface(new JsInterface(), "AndroidWebView");


    }

    @Override
    protected void initData() {
        Logger.d(TAG, "我是Fragment：  我的");
    }

    private class JsInterface {
        private Context mContext;

        public JsInterface() {
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs() {
            ToastUtils.showToast(getActivity(), Thread.currentThread().toString());
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    webview.loadUrl("https://www.baidu.com/");
                }
            }, 1000);

        }
    }

}
