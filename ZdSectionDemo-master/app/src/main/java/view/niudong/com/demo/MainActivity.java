package view.niudong.com.demo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import base.BaseActivity;
import base.BaseFragmentActivity;
import contants.FragmentPage;
import fragment.BuyFragment;
import in.srain.cube.views.ptr.PtrFrameLayout;
import listview.activity.ListViewActivity;
import listview.activity.ListViewAddHeadFootActivity;
import listview.activity.TestAddHeadActivity;
import mvp.view.ListActivity;
import pullrefresh.XctPtrLayout;
import pullrefresh.XctRefreshLayout;
import recycleview.activity.ItemDecorationActivity;
import recycleview.activity.RefreshRecyActivity;
import recycleview.view.InvestorBillMainActivity;
import testdb.TestDBApiActivity;
import utils.Bus;
import utils.GlideUtils;
import utils.HkStockUtil;
import utils.MyUtil;
import utils.ToastUtils;

/**
 * Created by niudong on 2017/6/15.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * scrollView.fullScroll(ScrollView.FOCUS_DOWN);滚动到底部
 * scrollView.fullScroll(ScrollView.FOCUS_UP);滚动到顶部
 * 百度云：链接：https://pan.baidu.com/s/1dAEQQy 密码：1v1z  json jar
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private XctPtrLayout mPullDownView;
    private static final String URL_BASE = "https://github.com/NIUDONG2015";
    public String FEIDI_ZIXUN_URL = "https://www.21fid.com/external/dyqh/newsFid/news/financing";
    private Button button1, handler;
    private Button button2;
    public static final String RECEVICE_DATA = "jnn";
    private int count = 0;


    /**
     * AliPay 支付
     */
    private LinearLayout rlAlipay;
    /**
     * 账户余额支付
     */
    private LinearLayout rlBalance;


    private Button button3, generate_code, bt_input_money, multi_view,
            investor_bill_main, lv_test, lv_head_foot, re_view, radar_chart,
            db_test, fragment, fragment_vp, mLeak1, bt_crash, bt_pay, bt_mvp, bt_navigation,
            bt_fragment_tabhost, bt_search, bt_rxjava, bt_kotlin;

    private RelativeLayout bt_h5_js, bt_coollayout, bt_tab, mulit_tab, rl_vue_js, bt_package, drop_down_menu;

    private RadioButton ibBalance;
    private RadioButton ibAlipay;
    /**
     * 自动刷新行情 不带加载图标
     */
    public static final int MSG_AUTO_REFRESH = 100;
    /**
     * 账户余额支付
     */
    private static final int CHANNEL_BALANCE = 1;
    /**
     * 支付宝渠道
     */
    private static final int CHANNEL_ALIPAY = 2;
    private int choose = 2;
    private ScrollView mScrollView;

    /**
     * 数据传回来
     */
    private Bus.ISubscriber mMsgSuccess = new Bus.ISubscriber() {
        @Override
        public void onSubscribe(Object obj) {
            String content = (String) obj;
            if (!TextUtils.isEmpty(content)) {
                ToastUtils.showToast(MainActivity.this, content);
            }
        }
    };

    /**
     * 自动刷新定时器
     */
    private Handler mAutoRefreshHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUTO_REFRESH: //刷新行情
                    if (count > 5) return;
                    count++;
                    startStockAutoRefresh(3000);
                    break;
                default:
                    break;
            }
        }
    };


    //旋转动画
    private RotateAnimation mRotateOTo180Animation;
    private RotateAnimation mRotate180To0Animation;

    private ImageView mHeaderArrowView;
    private ImageView mIvClick;
    private String mSubcribePrice;
    private RelativeLayout rLtranslucent;
    private RelativeLayout rlStickya;
    private RelativeLayout rl_slide_scroll;
    private LinearLayout homeWebRootView;
    private int viewHeight;
    private TimePickerView pvTime;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mPullDownView = (XctPtrLayout) findViewById(R.id.pull);
        ibBalance = (RadioButton) findViewById(R.id.ib_balance);
        ibAlipay = (RadioButton) findViewById(R.id.ib_alipay);
        rlAlipay = (LinearLayout) findViewById(R.id.rl_alipay);
        rlBalance = (LinearLayout) findViewById(R.id.rl_balance);
        mScrollView = (ScrollView) findViewById(R.id.scrlView);
        //home界面
        homeWebRootView = (LinearLayout) findViewById(R.id.home_web_view);
        //动态创建一个WebView
        //TODO 顶部WebView
        //HkStockUtil.getInstance().loadWebViewData(homeWebRootView, 0);
        handler = (Button) findViewById(R.id.handler);
        button1 = (Button) findViewById(R.id.bt1);
        lv_test = (Button) findViewById(R.id.lv_test);
        re_view = (Button) findViewById(R.id.bt_re_view);

        button2 = (Button) findViewById(R.id.bt2);
        button3 = (Button) findViewById(R.id.bt3);
        generate_code = (Button) findViewById(R.id.generate_code);
        lv_head_foot = (Button) findViewById(R.id.lv_head_foot);
        radar_chart = (Button) findViewById(R.id.radar_chart);
        investor_bill_main = (Button) findViewById(R.id.investor_bill_main);
        db_test = (Button) findViewById(R.id.db_test);
        multi_view = (Button) findViewById(R.id.multi_view);
        bt_input_money = (Button) findViewById(R.id.bt_input_money);
        fragment = (Button) findViewById(R.id.fragment);
        fragment_vp = (Button) findViewById(R.id.fragment_vp);
        mLeak1 = (Button) findViewById(R.id.leak1);
        bt_crash = (Button) findViewById(R.id.bt_crash);
        bt_pay = (Button) findViewById(R.id.bt_pay);
        bt_mvp = (Button) findViewById(R.id.bt_mvp);
        bt_navigation = (Button) findViewById(R.id.bt_navigation);
        bt_fragment_tabhost = (Button) findViewById(R.id.bt_fragment_tabhost);
        bt_search = (Button) findViewById(R.id.bt_search);
        bt_h5_js = (RelativeLayout) findViewById(R.id.bt_h5_js);
        bt_tab = (RelativeLayout) findViewById(R.id.bt_tab);
        bt_rxjava = (Button) findViewById(R.id.bt_rxjava);
        bt_coollayout = (RelativeLayout) findViewById(R.id.bt_coollayout);
        bt_kotlin = (Button) findViewById(R.id.bt_kotlin);
        bt_package = (RelativeLayout) findViewById(R.id.bt_package);
        mHeaderArrowView = (ImageView) findViewById(R.id.pulldown_header_arrow);
        mIvClick = (ImageView) findViewById(R.id.iv_click);
        rl_vue_js = (RelativeLayout) findViewById(R.id.rl_vue_js);
        mulit_tab = (RelativeLayout) findViewById(R.id.mulit_tab);
        drop_down_menu = (RelativeLayout) findViewById(R.id.drop_down_menu);
        rLtranslucent = (RelativeLayout) findViewById(R.id.rl_translucent);
        rlStickya = (RelativeLayout) findViewById(R.id.rl_stickya);
        rl_slide_scroll = (RelativeLayout) findViewById(R.id.rl_slide_scroll);


//        TODO 测量方法
        int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        db_test.measure(width,height);
        final int heightDb=db_test.getMeasuredHeight();
        int widthDb=db_test.getMeasuredWidth();


        choosePayChannel(CHANNEL_ALIPAY);
        initChoose();

        float total_memory =
                Runtime.getRuntime().totalMemory() * 1.0f / 1024 / 1024;
        float free_memory =
                Runtime.getRuntime().freeMemory() * 1.0f / 1024 / 1024;
        float max_memory =
                Runtime.getRuntime().maxMemory() * 1.0f / 1024 / 1024;
        GlideUtils.getInstance().LoadContextRoundBitmap(this, BuyFragment.imagePath1, mIvClick, 30);

        List<UserInfo> data = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.age = 20;
        userInfo.username = "niudong";
        data.add(userInfo);


        Gson gson = new Gson();
        String json = gson.toJson(data);

        Log.d(TAG, "Gson..." + json);

        MyApplication.mContext.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(MainActivity.this, "下拉试试哦！"+heightDb );
            }
        }, 500);
    }

    public static JSONArray ProLogList2Json(List<UserInfo> list) {
        JSONArray json = new JSONArray();
        for (UserInfo pLog : list) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", pLog.age);
                jo.put("username", pLog.username);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            json.put(jo);
        }
        return json;
    }

    /**
     * 开启自选行情自动刷新
     */
    public void startStockAutoRefresh(long delay) {
//		Logger.i(TAG, "startStockAutoRefresh delay:" + delay);
        mAutoRefreshHandler.removeMessages(MSG_AUTO_REFRESH);
        Message msg = mAutoRefreshHandler.obtainMessage(MSG_AUTO_REFRESH,
                0, 0);
        mAutoRefreshHandler.sendMessageDelayed(msg, delay);

    }

    /**
     * 关闭自选行情自动刷新
     */
    public void stopStockAutoRefresh() {
//		Logger.i(TAG, "stopStockAutoRefresh: ");
        mAutoRefreshHandler.removeMessages(MSG_AUTO_REFRESH);
    }


    @Override
    protected void initListener() {
        //下拉刷新
        mPullDownView.setPtrHandler(new XctRefreshLayout.DefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {

                mPullDownView.refreshComplete();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mScrollView, header);
            }
        });
        rlAlipay.setOnClickListener(this);
        rlBalance.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        rlStickya.setOnClickListener(this);
        rl_slide_scroll.setOnClickListener(this);
        button3.setOnClickListener(this);
        generate_code.setOnClickListener(this);
        lv_test.setOnClickListener(this);
        lv_head_foot.setOnClickListener(this);
        re_view.setOnClickListener(this);
        radar_chart.setOnClickListener(this);
        investor_bill_main.setOnClickListener(this);
        db_test.setOnClickListener(this);
        multi_view.setOnClickListener(this);
        bt_input_money.setOnClickListener(this);
        handler.setOnClickListener(this);
        fragment.setOnClickListener(this);
        fragment_vp.setOnClickListener(this);
        mLeak1.setOnClickListener(this);
        bt_crash.setOnClickListener(this);
        bt_pay.setOnClickListener(this);
        bt_mvp.setOnClickListener(this);
        bt_navigation.setOnClickListener(this);
        bt_fragment_tabhost.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        bt_h5_js.setOnClickListener(this);
        bt_tab.setOnClickListener(this);
        bt_rxjava.setOnClickListener(this);
        bt_coollayout.setOnClickListener(this);
        bt_kotlin.setOnClickListener(this);
        bt_package.setOnClickListener(this);
        mIvClick.setOnClickListener(this);
        rl_vue_js.setOnClickListener(this);
        mulit_tab.setOnClickListener(this);
        drop_down_menu.setOnClickListener(this);
        rLtranslucent.setOnClickListener(this);
//        scrollView.fullScroll(ScrollView.FOCUS_DOWN);滚动到底部
//        scrollView.fullScroll(ScrollView.FOCUS_UP);滚动到顶部
        bt_fragment_tabhost.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                int top = bt_fragment_tabhost.getTop();
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
                bt_fragment_tabhost.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        String content = "03-29 15:00";
        String substring = content.substring(0, 5);


        mSubcribePrice = "44~55";

        String resultPrice = getResultPrice();
        Log.d(TAG, resultPrice);

//        Glide.with(this).load("").override()
    }


    /**
     * 得到价格
     */
    private String getResultPrice() {
        if (!TextUtils.isEmpty(mSubcribePrice) && mSubcribePrice.contains("~")) {
            int index = mSubcribePrice.indexOf("~");
            String value1 = MyUtil.substring(mSubcribePrice, 0, index);
            String value2 = MyUtil.substring(mSubcribePrice, index + 1, mSubcribePrice.length());
            return value1.compareTo(value2) > 0 ? value1 : value2;
        }
        return mSubcribePrice;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //初始旋转 动画
        initAnimation();

        if (null != mMsgSuccess) {
            Bus.getInstance().unregister(RECEVICE_DATA, mMsgSuccess);
        }
        Bus.getInstance().register(RECEVICE_DATA, mMsgSuccess);

        startStockAutoRefresh(0);

        initTimePicker();



    }




    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                Log.i("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .isDialog(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }


        pvTime.show();
    }
    private String getTime(Date date) {
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void initChoose() {
        Log.d("initChoose", "initChoose: " + choose);
    }


    /**
     * 将分转换为元 保留两位小数
     *
     * @param receive 接收服务器返回的String 字符串除以100
     * @param scale   保留的小数位数
     * @return
     */
    public static String str2doubleDiv(String receive, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(receive);
        BigDecimal b2 = new BigDecimal(100.00);
        Double aDouble = new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());

        return String.format("%.2f", aDouble);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_alipay:
                choosePayChannel(CHANNEL_ALIPAY);
                break;
            case R.id.rl_balance:
                choosePayChannel(CHANNEL_BALANCE);
                break;
            case R.id.generate_code:

                BaseFragmentActivity.startFragment(this, FragmentPage.RECYCLER_STICK_LAYOUT);
                break;
            case R.id.bt1:
//                intent = new Intent(MainActivity.this, RecyclerActivity.class);
//                startActivity(intent);

                BaseFragmentActivity.startFragment(this, FragmentPage.MULIT_TAB);
                break;
            case R.id.bt2:
                intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
                break;
            case R.id.bt3:
                intent = new Intent(MainActivity.this, ItemDecorationActivity.class);
                startActivity(intent);
                break;

            case R.id.lv_test:
                intent = new Intent(MainActivity.this, TestAddHeadActivity.class);
                startActivity(intent);
                break;
            case R.id.lv_head_foot:
                intent = new Intent(MainActivity.this, ListViewAddHeadFootActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_re_view:
                intent = new Intent(MainActivity.this, RefreshRecyActivity.class);
                startActivity(intent);
                break;

            case R.id.radar_chart:
                intent = new Intent(MainActivity.this, RiskLevelActivity.class);
                startActivity(intent);
                break;
            case R.id.investor_bill_main:
                intent = new Intent(MainActivity.this, InvestorBillMainActivity.class);
                startActivity(intent);
                break;
            case R.id.db_test:
                intent = new Intent(MainActivity.this, TestDBApiActivity.class);
                startActivity(intent);
                break;
            case R.id.multi_view:
                intent = new Intent(MainActivity.this, MultiActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_input_money:
                intent = new Intent(MainActivity.this, EditTextActivity.class);
                startActivity(intent);
                break;

            case R.id.handler:
                intent = new Intent(MainActivity.this, HandlerMsgActivity.class);
                startActivity(intent);
                break;

            case R.id.fragment:
                intent = new Intent(MainActivity.this, FragmentStackActivity.class);
                startActivity(intent);
                break;

            case R.id.fragment_vp:
                intent = new Intent(MainActivity.this, FragmentVPActivity.class);
                startActivity(intent);
                break;

            case R.id.leak1:
                intent = new Intent(MainActivity.this, TestLeakActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_crash:
                enterActivity(CrashTestActivity.class);
                break;

            case R.id.bt_pay:
                enterActivity(TestPayActivity.class);
                break;
            case R.id.bt_mvp:
                enterActivity(ListActivity.class);
                break;

            case R.id.bt_navigation:
                enterActivity(NavigationMainActivity.class);
                break;

            case R.id.bt_fragment_tabhost:
                enterActivity(FragMentTabHostActivity.class);
                break;

            case R.id.bt_search:
                enterActivity(SearchActivity.class);
                break;

            case R.id.bt_h5_js:
                enterActivity(TestHtmlJsActivity.class);
                break;

            case R.id.bt_tab:
                enterActivity(TestOnClickActivity.class);
                break;

            case R.id.bt_rxjava:
                enterActivity(RxBusActivity.class);
                break;
            case R.id.bt_coollayout:
                enterActivity(CoolLayoutActivity.class);
                break;
            case R.id.bt_kotlin:
                BaseFragmentActivity.startFragment(this, FragmentPage.PERSONAL_INFO_MANAGER);
                break;

            case R.id.bt_package:
                BaseFragmentActivity.startFragment(this, FragmentPage.PACKAGE_TEST);
                break;
            case R.id.mulit_tab:
                BaseFragmentActivity.startFragment(this, FragmentPage.RECYCLER_STICK);
                break;
            case R.id.drop_down_menu:
                BaseFragmentActivity.startFragment(this, FragmentPage.MULIT_WEBVIEW);
                break;
            case R.id.rl_vue_js:
                enterActivity(TranslucentActivity.class);
                break;
            case R.id.rl_translucent:
                enterActivity(CoolTranslucentActivity.class);
                break;
            case R.id.rl_stickya:
                enterActivity(NewActivity.class);
                break;

            case R.id.rl_slide_scroll:
                enterActivity(SlideScrollActivity.class);
                break;
            default:
                break;

        }
    }


    /**
     * 选取支付渠道
     * 1：余额支付    2：渠道支付
     */
    private void choosePayChannel(int payChannel) {
        choose = payChannel;
        boolean isSelectAliPay = payChannel == 2;
        ibAlipay.setChecked(isSelectAliPay);
        ibAlipay.setEnabled(isSelectAliPay);
        ibBalance.setChecked(!isSelectAliPay);
        ibBalance.setEnabled(!isSelectAliPay);
//        ToastUtils.showToast(MainActivity.this, isSelectAliPay ? "支付宝" : "余额");
    }



    @Override
    protected void initData() {

    }

    /**
     * 旋转动画
     */
    private void initAnimation() {
        // 图片旋转动画
        mRotateOTo180Animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateOTo180Animation.setDuration(1000);
        mRotateOTo180Animation.setFillAfter(true);
        mRotate180To0Animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotate180To0Animation.setDuration(1000);
        mRotate180To0Animation.setFillAfter(true);

        //初始化插值器
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.pulldown_icon);
        LinearInterpolator lin = new LinearInterpolator();
        mAnimation.setInterpolator(lin);


        //转起来啦
        mHeaderArrowView.clearAnimation();
        mHeaderArrowView.startAnimation(mAnimation);
        mHeaderArrowView.startAnimation(mRotateOTo180Animation);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mHeaderArrowView.startAnimation(mRotate180To0Animation);
            }
        }, 1000);

    }


    @Override
    protected void onPause() {
        super.onPause();

        mHeaderArrowView.clearAnimation();
        //关闭自动刷新
        stopStockAutoRefresh();
        List<UserInfo> mData = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.username = "nd";
        userInfo.age = 88;
        mData.add(userInfo);

//

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.getInstance().unregister(RECEVICE_DATA, mMsgSuccess);
    }

    /**
     * @param oldData      当前列表
     * @param clickPostion 修改条目postion
     * @param changeStr    你修改的内容
     */

    private void changeListData(List<UserInfo> oldData, int clickPostion, String changeStr) {
        if (null == oldData || oldData.size() == 0) return;
        for (int i = 0; i < oldData.size(); i++) {
            //点击一样
            if (i == clickPostion) {
                oldData.get(i).username = changeStr;
                //TODO  刷新局部数据
            }
        }
    }

    class UserInfo {

        public String username;
        public int age;

    }

}
