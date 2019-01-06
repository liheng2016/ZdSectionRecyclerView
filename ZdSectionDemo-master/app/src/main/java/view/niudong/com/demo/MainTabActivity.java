package view.niudong.com.demo;

import android.content.Intent;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import contants.Constant;
import fragment.LoadMoreRecyclerFragment;
import fragment.MulitTabFragment;
import recycleview.activity.ItemDecorationActivity;
import tabfragment.MyFragment;
import tabfragment.HomeFragment;
import tabfragment.IFramePage;
import tabfragment.FuturePlanFragment;
import utils.Bus;
import utils.StaticInnerSingleton;
import utils.ToastUtils;
import view.niudong.com.demo.receiver.MyReceiver;

/**
 * 名称：
 * Created by niudong on 2018/5/29.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 * <p>
 * TODO  4个子Fragment 每次切换onResume都会触发
 */

public class MainTabActivity extends BaseActivity implements View.OnClickListener, IFramePage {
    /**
     * 主页面 对应显示内容
     */
    public static final String RECEVICE_DATA = "notify";
    public static final String NOTIFY_CLICK = "NOTIFY_CLICK";
    private MyReceiver myReceiver;

    private List<TabInfo> fragments = new ArrayList<>();
    /**
     * 各Fragment对应的下方TextView
     */
    private TextView[] tabs = new TextView[4];
    /**
     * 当前选中的tab序号, 从0开始, -1表示未初始化
     */
    private int tabIndex = -1;
    /**
     * 当前选中的Fragment
     */
    private Fragment tabFragment;
    /**
     * 数据传回来
     */
    private Bus.ISubscriber mMsgSuccess = new Bus.ISubscriber() {
        @Override
        public void onSubscribe(Object obj) {
            if (null != obj && null != fragments && fragments.size() > 0 && StaticInnerSingleton.getInstance().getclickTab()) {
                onTabSelected(1);
                        LoadMoreRecyclerFragment fragment = (LoadMoreRecyclerFragment) fragments.get(1).fragment;
                        fragment.requestNotify();
            }else {
                onTabSelected(1);
            }
        }
    };


    @Override
    protected void initView() {
        setContentView(R.layout.activity_main_tab);
        //        主框架
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver, intentFilter);
        //初始化tab
        LinearLayout maintab = (LinearLayout) findViewById(R.id.xct_lthj_newquot_id_maintab);
        Intent intent = new Intent(Constant.NOTIFIY_LIST_ITEM);
        sendBroadcast(intent);

        fragments.add(new TabInfo("首页", HomeFragment.class));
        fragments.add(new TabInfo("趋势", LoadMoreRecyclerFragment.class));
        fragments.add(new TabInfo("我的", MulitTabFragment.class));

        tabs[0] = (TextView) findViewById(R.id.xct_lthj_frame_text_tab1);
        tabs[1] = (TextView) findViewById(R.id.xct_lthj_frame_text_tab2);
        tabs[2] = (TextView) findViewById(R.id.xct_lthj_frame_text_tab3);

        //初始化文字
        for (int i = 0; i < fragments.size(); i++) {
            TabInfo info = fragments.get(i);
            tabs[i].setText(info.title);
        }

        tabs[0].setOnClickListener(this);
        tabs[1].setOnClickListener(this);
        tabs[2].setOnClickListener(this);
        //默认选中
        onTabSelected(0);

    }

    @Override
    protected void initListener() {

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
    protected void initData() {


    }

    /**
     * 处理tab button 选中事件的背景，和字体颜色处理, 以及切换相关fragment
     */
    public void onTabSelected(int index) {
        onTabSelected(index, null);
    }

    /**
     * 处理tab button 选中事件的背景，和字体颜色处理, 以及切换相关fragment
     */
    private void onTabSelected(int index, String channel) {
        if (tabIndex == index) {
            return;
        }

        if (-1 != tabIndex) { //更新旧的
            tabs[tabIndex].setSelected(false);
        }

        tabs[index].setSelected(true);
        tabIndex = index;

        //设置新的
//        QuotGlobalInfo.FORWARD_MODEL.setCurrentTab(tabIndex);

        TabInfo info = fragments.get(index);
        if (null == info.fragment) {
            try {
                info.fragment = (Fragment) info.clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        switch (info.title) {
            case "趋势":
                break;
            case "首页":
                break;
            case "我的": //圈子

                break;
        }

        if (null != info.fragment) {
            String tag = info.title;
            FragmentManager manager = getSupportFragmentManager();
            Fragment f = manager.findFragmentByTag(tag);

            if (null != tabFragment && f == tabFragment) return; //如果一样, 则不用加载

            FragmentTransaction transaction = manager.beginTransaction();
            if (null != tabFragment) transaction.detach(tabFragment);

            if (null != f) {
                transaction.attach(f);
            } else {
                transaction.add(R.id.xct_lthj_fl, info.fragment, tag);
            }
            transaction.commit();

            tabFragment = info.fragment;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xct_lthj_frame_text_tab1: //首页
                onTabSelected(0);
                break;
            case R.id.xct_lthj_frame_text_tab2: //未来
                onTabSelected(1);
                StaticInnerSingleton.getInstance().clickTab(true);
                break;
            case R.id.xct_lthj_frame_text_tab3: //我的
                onTabSelected(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void navigate(int index) {
        onTabSelected(index);
    }

    @Override
    public void setMainTabVisible(boolean visible) {

    }

    /**
     * 各Tab相关信息
     */
    private static class TabInfo {
        private String title;
        private Fragment fragment;
        private Class clazz;

        private TabInfo(String title, Class clazz) {
            this.title = title;
            this.clazz = clazz;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Bus.getInstance().unregister(RECEVICE_DATA, mMsgSuccess);
        StaticInnerSingleton.getInstance().clickTab(false);
        unregisterReceiver(myReceiver);

    }
}
