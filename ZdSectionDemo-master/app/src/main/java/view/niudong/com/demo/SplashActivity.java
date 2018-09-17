package view.niudong.com.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

import base.BaseActivity;
import recycleview.activity.ItemDecorationActivity;
import utils.HkStockUtil;

public class SplashActivity extends BaseActivity {
    private static final long DURATION = 1200;
    private ImageView splashView;
    public String FEIDI_ZIXUN_SIDI_URL = "https://www.21fid.com/external/dyqh/newsFid/news/financing";
    private static final String URL_BASE = "https://github.com/NIUDONG2015";
    public String FEIDI_ZIXUN_URL = "https://github.com/NIUDONG2015";
    public String FEIDI_BAIDU_URL = "https://www.baidu.com/";
    public String FEIDI_MAYUN_URL = "https://gitee.com/NiuDong/";

    @Override
    protected void initView() {
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        splashView = findViewById(R.id.iv);
//        TODO WebView 预加载处理
        HkStockUtil.getInstance().initRegisterWebView(new WebView(this), URL_BASE, 0);
        //动态创建一个WebView
        HkStockUtil.getInstance().initRegisterWebView(new WebView(this), FEIDI_ZIXUN_URL, 1);
        //动态创建第个WebView
        HkStockUtil.getInstance().initRegisterWebView(new WebView(this), FEIDI_ZIXUN_URL, 2);

        HkStockUtil.getInstance().initRegisterWebView(new WebView(this), FEIDI_MAYUN_URL, 3);
        HkStockUtil.getInstance().initRegisterWebView(new WebView(this), FEIDI_MAYUN_URL, 4);
//   HkStockUtil.getInstance().loadWebView(URL_BASE));
        //            否则闪屏2秒后（渐变动画），进入LoginActivity
        ObjectAnimator alpha = ObjectAnimator.ofFloat(splashView, "alpha", 0, 1).setDuration(DURATION);
        alpha.start();
        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(ItemDecorationActivity.class, true, null);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
