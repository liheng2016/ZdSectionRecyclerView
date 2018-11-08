package view.niudong.com.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Android中可以使用WebView加载网页，同时Android端的java代码可以与网页上的javascript代码之间相互调用。
 */
public class TestHtmlJsActivity extends AppCompatActivity {

    private WebView webView;
    private CoordinatorLayout mCoordinatorLayout;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_html_js);
        webView = (WebView) findViewById(R.id.webView);

        webView.setVerticalScrollbarOverlay(true);
        //设置WebView支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        String url = "http://192.168.1.27/js_17_android_webview.html";
        webView.loadUrl(url);
        webView.loadUrl("file:///android_asset/ndtest.html");

        //在js中调用本地java方法
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");

        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
    }

    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
    }

    //在java中调用js代码
    public void sendInfoToJs(View view) {
        String msg = ((EditText) findViewById(R.id.input_et)).getText().toString();
        //调用js中的函数：showInfoFromJava(msg)
//        webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
        //从 Android 传递参数到 H5 界面
        webView.loadUrl("javascript:getFromAndroidData(\"" + msg + "\")");
    }

}

