package testdb;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import adapter.HktPositionDetailAdapter;
import entity.DetailModel;
import view.niudong.com.demo.R;

public class TestDBApiActivity extends AppCompatActivity {
    /**
     * 适配器
     */
    private HktPositionDetailAdapter adapter;

    /**
     * 用于填充fragment的最终的数据列表
     */
    private List<DetailModel> datas;
    public ViewPager vpContainer;
    /**
     * 当前显示页面的下标
     */
    private int currPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        vpContainer = (ViewPager) findViewById(R.id.vp_position_detail);
        initfragmentDatas();
        // 根据数据生成页面
        adapter = new HktPositionDetailAdapter(getSupportFragmentManager(), datas,
                datas.size(), vpContainer);
        vpContainer.setAdapter(adapter);
        vpContainer.setOnPageChangeListener(new MyOnPagerChangListener());
        vpContainer.setOffscreenPageLimit(1);
        // 默认选中页
        vpContainer.setCurrentItem(currPage);
//        refreshCurrPage();
    }

    /**
     * 根据传入的列表初始化需要传入fragment的数据
     */
    private void initfragmentDatas() {
        datas = new ArrayList<>();
        datas.add(new DetailModel("api db",0));
        datas.add(new DetailModel("sql db",1));
    }

    /**
     * 滑动监听
     */
    class MyOnPagerChangListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            currPage = position;
            // 选择页面,刷新数据
            selectPageSet();
            //刷新
            refreshCurrPage();
        }

    }

    private void selectPageSet() {
        // 获取当前model
        DetailModel model = datas.get(vpContainer.getCurrentItem());
        model.titleData = currPage == 0 ? "api db" : "sql db";
        model.currentType = currPage == 0 ? 0 :1;
    }

    /**
     * 刷新当前页面数据
     */
    private void refreshCurrPage() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currPage == vpContainer.getCurrentItem()) {
                    // 更新当前页面
                    ((HktPositionDetailAdapter) vpContainer.getAdapter())
                            .setStockList(datas);
                    ((HktPositionDetailAdapter) vpContainer.getAdapter())
                            .invalidate(datas.get(currPage), currPage);
                }
            }
        }, 200);
    }
}
