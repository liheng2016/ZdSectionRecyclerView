package fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

import entity.News;

import static fragment.BillDetailListFragment.newInstance;

/**
 * Created by niudong on 2017/7/18.
 * Tel：18811793194
 * VersionChange：5.4
 * <p>
 * 作用：投资者账单页面
 * pagerAdapter是默认预加载前后一张的
 */

public class BillInDetailAdapter extends FragmentStatePagerAdapter {
    List<News> listObj;
    int mPosition;

    public BillInDetailAdapter(FragmentManager fm, List<News> listObj, int position) {
        super(fm);
        this.listObj = listObj;
        this.mPosition = position;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listObj.size();//;NUM_ITEMS;
    }

    /**
     * getItem(int position)是获取item的位置     1
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {

        return newInstance(listObj.get(position).getTitle());
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return PagerAdapter.POSITION_NONE;
    }

/*    *//**
     * 初始化  执行次数
     *
     * @param container
     * @param position
     * @return
     *//*
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        BillDetailListFragment fragment = (BillDetailListFragment) super.instantiateItem(container, position);
        News news = listObj.get(position);
        fragment.updateArguments(news.getTitle(), news.getContent());
        return fragment;
    }*/
}
