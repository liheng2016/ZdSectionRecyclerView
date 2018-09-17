package view.niudong.com.demo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import base.BaseActivity;
import fragment.BuyFragment;
import fragment.CheFragment;
import fragment.QueryFragment;
import fragment.SellFragment;

public class FragMentTabHostActivity extends BaseActivity implements View.OnClickListener {
    private TextView count; //提醒的角标

    /**
     * Tab选项卡的文字
     */
    private int[] mTabTitles = {R.string.buy, R.string.sell, R.string.revocation,
            R.string.query};
    /**
     * Tab选项卡的图片
     */
    private int[] mTabIcons = {R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher};

    /**
     * Tab对应的内容Fragment
     */
    private Class[] mTabFragments = {SellFragment.class, BuyFragment.class,
            CheFragment.class, QueryFragment.class};
    private FragmentTabHost mFragmentTabHost;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_fragment_tabhost);
        mFragmentTabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        //设置内容区域
        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);
        int count = mTabFragments.length;
        for (int i = 0; i < count; i++) {
            // 给Tab设置标签和按钮的样式(图片和文字)
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(getResources().getString(mTabTitles[i])).setIndicator(getTabItemView(i));
            mFragmentTabHost.addTab(tabSpec, mTabFragments[i], null);
    }


        // 去掉分割线
        mFragmentTabHost.getTabWidget().setDividerDrawable(null);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        String tab = intent.getStringExtra("tab");
        if (TextUtils.isEmpty(tab)) return;
        setTab(Integer.parseInt(tab));
    }


    private View getTabItemView(int index) {
        View tabView = View.inflate(getApplicationContext(), R.layout.main_tab_view, null);
        ImageView imageView = (ImageView) tabView.findViewById(R.id.iv_icon);
        TextView textView = (TextView) tabView.findViewById(R.id.tv_title);
        imageView.setImageResource(mTabIcons[index]);
        count = (TextView) tabView.findViewById(R.id.tv_msg_count);
        textView.setText(mTabTitles[index]);
        if (index == 3) {
            count.setVisibility(View.VISIBLE);
        }
        return tabView;
    }

    public Fragment getFragment(String tabId) {
        return getSupportFragmentManager().findFragmentByTag(tabId);
    }

    public void setTab(int tab) {
        mFragmentTabHost.setCurrentTab(tab);
    }

    public int getCurrentTab() {
        return mFragmentTabHost.getCurrentTab();
    }


    public String getCurrentTabName() {
        return mFragmentTabHost.getCurrentTabTag();
    }


    @Override
    public void onClick(View v) {

    }
}