package recycleview.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;


import net.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import base.BaseFragmentActivity;
import contants.ConfigInfo;
import contants.Constant;
import contants.FragmentPage;
import customview.FloatingStickItemDecoration;
import entity.ListGrup;
import entity.UserInfo;
import in.srain.cube.views.ptr.PtrFrameLayout;
import mvp.view.ListActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pullrefresh.XctPtrLayout;
import pullrefresh.XctRefreshLayout;
import recycleview.adapter.MainPageItemAdapter;
import recycleview.view.InvestorBillMainActivity;
import testdb.TestDBApiActivity;
import utils.Bus;
import utils.ColorUtils;
import utils.HkStockUtil;
import utils.ToastUtils;
import view.niudong.com.demo.ClideAidlConnActivity;
import view.niudong.com.demo.CoolTranslucentActivity;
import view.niudong.com.demo.FragMentTabHostActivity;
import view.niudong.com.demo.FragmentStackActivity;
import view.niudong.com.demo.FragmentVPActivity;
import view.niudong.com.demo.Main2Activity;
import view.niudong.com.demo.MainTabActivity;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.NavigationMainActivity;
import view.niudong.com.demo.NewActivity;
import view.niudong.com.demo.R;
import view.niudong.com.demo.RxBusActivity;
import view.niudong.com.demo.SlideScrollActivity;
import view.niudong.com.demo.TestHtmlJsActivity;
import view.niudong.com.demo.TestLeakActivity;
import view.niudong.com.demo.TestOnClickActivity;
import view.niudong.com.demo.TestPayActivity;
import view.niudong.com.demo.TranslucentActivity;
import view.niudong.com.demo.receiver.MyReceiver;

public class ItemDecorationActivity extends BaseActivity implements MainPageItemAdapter.OnItemClickListener {
    private static final String TAG = "GenerateQrCodeActivity";
    private HashMap<Integer, List<String>> datas;//模拟服务器返回数据
    private List<String> mResultData = new ArrayList<>();//adapter数据源
    private Map<Integer, String> keys = new HashMap<>();//存放所有key的位置和内容
    private MainPageItemAdapter adapter;
    private RecyclerView rv;

    private LinearLayoutManager layoutManager;
    private XctPtrLayout mPullDownView;
    public static final String RECEVICE_DATA = "jnn";

    /**
     * 数据传回来
     */
    private Bus.ISubscriber mMsgSuccess = new Bus.ISubscriber() {
        @Override
        public void onSubscribe(Object obj) {
            String content = (String) obj;
            if (!TextUtils.isEmpty(content)) {
                ToastUtils.showToast(ItemDecorationActivity.this, content);
            }
        }
    };
    private MyReceiver myReceiver;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_item_d);
        rv = (RecyclerView) findViewById(R.id.rv);
        mPullDownView = (XctPtrLayout) findViewById(R.id.pull);
        //禁止下拉刷新头左右滑动
        mPullDownView.disableWhenHorizontalMove(true);
        MyApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(ItemDecorationActivity.this, "欢迎你，下拉试试！");
            }
        }, 600);
        OkHttpUtils.getInteance().getHttpUtils("https://www.androidos.net.cn/articles", new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(ItemDecorationActivity.this, e.toString() + "");
                    }
                }, 0);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(ItemDecorationActivity.this, response.toString() + "");
                    }
                }, 0);
            }
        });
//        主框架
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(Constant.NOTIFIY_LIST_ITEM);
        registerReceiver(myReceiver, intentFilter);
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
                return super.checkCanDoRefresh(frame, rv, header);
            }
        });
    }

    @Override
    protected void initData() {

        List<UserInfo> data = new ArrayList<>();
        List<UserInfo> result = new ArrayList<>();
        data.add(new UserInfo("ND", 25));
        data.add(new UserInfo("ND", 25));
        for (int i = 0; i < data.size(); i++) {
            UserInfo userInfo = data.get(i);
            processTestData(userInfo);
            result.add(userInfo);

        }

        //TODO  模拟分组列表数据
        List<ListGrup> grupList = HkStockUtil.getInstance().getGroupList();
        //第一个为头
        keys.put(0, "");
        for (int i = 0; i < grupList.size(); i++) {
            keys.put(mResultData.size() + 1, grupList.get(i).titleData);//组名
            for (int j = 0; j < grupList.get(i).mChildStr.size(); j++) {
                mResultData.add(grupList.get(i).mChildStr.get(j));
            }
        }


        //设置adapter
        adapter = new MainPageItemAdapter(mResultData, this);
        // 添加分割线
        final FloatingStickItemDecoration floatingItemDecoration = new FloatingStickItemDecoration(this, ColorUtils.COLOR_LINE, 1, 1);
        floatingItemDecoration.setKeys(keys);
        floatingItemDecoration.setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
        rv.addItemDecoration(floatingItemDecoration);

        //设置布局管理器
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(1);
        rv.setLayoutManager(layoutManager);
        adapter.setmItemClickListener(this);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void processTestData(UserInfo userInfo) {
        userInfo.age = 50;
        userInfo.username = "JN";
    }

    @Override
    public void onItemClick(int mPostion, String name) {
        String s = mResultData.get(mPostion);
        ToastUtils.showToast(this, "位置" + (mPostion) + "名字是" + name);
        selectPage(name);
    }

    private void selectPage(String name) {
        switch (name) {
            case "采用ItemDecoration悬停":
                BaseFragmentActivity.startFragment(this, FragmentPage.RECYCLER_STICK);

                break;
            case "采用自定义布局方式悬停":
                BaseFragmentActivity.startFragment(this, FragmentPage.RECYCLER_STICK_LAYOUT);
                break;
            case "采用multitype复杂列表":
                BaseFragmentActivity.startFragment(this, FragmentPage.MULIT_TAB);
                break;
            case "采用自定义getItemViewType方式":
                enterActivity(InvestorBillMainActivity.class);
                break;
            case "RecyclerView嵌套水平Recycler无冲突":
                enterActivity(NavigationMainActivity.class);
                break;

            case "采用FragMentTabHost+子Fragment":
                enterActivity(FragMentTabHostActivity.class);
                break;
            case "采用NavigationBar+子Fragment":
                enterActivity(NavigationMainActivity.class);
                break;


            case "封装List列表管理子Fragment":
                enterActivity(MainTabActivity.class);
                break;

            case "折叠一：CoordinatorLayout+RecyclerView":
                enterActivity(CoolTranslucentActivity.class);
                break;
            case "折叠二：NestedScrollView+RecyclerView":
                enterActivity(TranslucentActivity.class);
                break;
            case "折叠三：StickyNavLayout+RecyclerView":
                enterActivity(NewActivity.class);
                break;
            case "折叠四：替换索星ScrollView+Recycler":
                enterActivity(SlideScrollActivity.class);
                break;
            case "折叠五：一个RecyclerView切多Tab（悬停）":
                BaseFragmentActivity.startFragment(this, FragmentPage.MULIT_TAB);
                break;

            case "封装接口回调方便传消息":
                enterActivity(RxBusActivity.class);
                break;
            case "列表点击跳详情多页面--左右滑动":
                enterActivity(InvestorBillMainActivity.class);
                break;

            case "数据库测试--Api 和Sql":
                enterActivity(TestDBApiActivity.class);
                break;

            case "Fragment任务栈处理":
                enterActivity(FragmentStackActivity.class);
                break;
            case "LazyFragment懒加载+ViewPager":
                enterActivity(FragmentVPActivity.class);
                break;

            case "App内存泄漏那些事":
                enterActivity(TestLeakActivity.class);
                break;

            case "Okhttp-Ping++支付":
                enterActivity(TestPayActivity.class);
                break;

            case "Mvp+Retrofit+Okhttp列表(接口不通有代码)":
                enterActivity(ListActivity.class);
                break;

            case "H5和Js互调":
                enterActivity(TestHtmlJsActivity.class);
                break;

            case "包名得到签名":
                BaseFragmentActivity.startFragment(this, FragmentPage.PACKAGE_TEST);
                break;

            case "线性布局写Tab切换":
                enterActivity(TestOnClickActivity.class);
                break;

            case "WebView预加载":
                BaseFragmentActivity.startFragment(this, FragmentPage.MULIT_WEBVIEW);
                break;

            case "Aidl跨进程通讯":
                enterActivity(ClideAidlConnActivity.class);
                break;

            case "自定义图表相关":
                BaseFragmentActivity.startFragment(this, FragmentPage.CUSTOMVIEW_FRAGMENT);
                break;

            case "BitMap合成图片":
                enterActivity(Main2Activity.class);
                break;

            case "自定义刻度尺":
                BaseFragmentActivity.startFragment(this, FragmentPage.CUSTOMVIEWCHI_FRAGMENT);
                break;
            case "自定义钟表":
                BaseFragmentActivity.startFragment(this, FragmentPage.CUSTOM_CLOCK_FRAGMENT);
                break;
            case "自定义股票K线":
                BaseFragmentActivity.startFragment(this, FragmentPage.STOCK_KLINE_FRAGMENT);
                break;

            case "Pop中的RecyclerView":
                BaseFragmentActivity.startFragment(this, FragmentPage.POP_RECYCLER_FRAGMENT);
                break;
            case "手动解析Json":
                BaseFragmentActivity.startFragment(this, FragmentPage.DATA_JSON_FRAGMENT);
                break;
            case "加载更多的RecyclerView":
                BaseFragmentActivity.startFragment(this, FragmentPage.LOADMOR_RECYLER_FRAGMENT);
                break;
            case "ReactNative列表展示":
                Intent intent = new Intent(Constant.NOTIFIY_LIST_ITEM);
                this.sendBroadcast(intent);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (null != mMsgSuccess) {
            Bus.getInstance().unregister(RECEVICE_DATA, mMsgSuccess);
        }
        Bus.getInstance().register(RECEVICE_DATA, mMsgSuccess);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.getInstance().unregister(RECEVICE_DATA, mMsgSuccess);
        unregisterReceiver(myReceiver);
        //清空所有WebView的缓存
        //   Map<Integer, WebView> integerWebViewMap = HkStockUtil.getInstance().getmCacheData();
        // if (integerWebViewMap != null && integerWebViewMap.size() > 0) {
        //     integerWebViewMap.clear();
        // }
    }
}
