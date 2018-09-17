package view.niudong.com.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import entity.News;
import fragment.adapter.BillInDetailAdapter;

/**
 * 投资者详情页Activity---启动Fragment
 */

public class NewsContentActivity extends AppCompatActivity {
    ViewPager mPager;
    private BillInDetailAdapter mAdapter;

    public static void actionStart(Context context, int curruentNum, List<News> mNewsListint) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("curruentNum", curruentNum);
        intent.putExtra("listobj", (Serializable) mNewsListint);
        context.startActivity(intent);
    }
//    TODO 走完之后 先去创建Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        //TODO 获取点击的列表item
        int curruentNum = getIntent().getIntExtra("curruentNum", 1); //
        //获得传过来的List 数量

        List<News> mNewsListint = (List<News>) getIntent().getSerializableExtra("listobj");

        mPager = (ViewPager) findViewById(R.id.view_pager);

        //初始化Fragment适配器
        mAdapter = new BillInDetailAdapter(getSupportFragmentManager(), mNewsListint, curruentNum);
//        绑定数据
        mPager.setAdapter(mAdapter);
//        设置选中当前的点击的item
        mPager.setCurrentItem(curruentNum, false);
//        mAdapter.notifyDataSetChanged();
    }

}
