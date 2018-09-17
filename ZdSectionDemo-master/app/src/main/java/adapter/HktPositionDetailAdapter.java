package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


import java.util.ArrayList;
import java.util.List;

import entity.DetailModel;
import testdb.DbSqlFragment;


public class HktPositionDetailAdapter extends FragmentStatePagerAdapter {

    private List<DetailModel> stockList;

    private List<DbSqlFragment> views;
    private ViewPager pager;
    private int mCount = 0;

    /**
     * 详情页面
     */
    public HktPositionDetailAdapter(FragmentManager fm, List<DetailModel> stockList, int count,
                                    ViewPager pager) {
        super(fm);
        this.stockList = stockList;
        this.mCount = count;
        this.pager = pager;
        views = new ArrayList<>();
        for (int i = 0; i < stockList.size(); i++) {
            views.add(i, null);
        }
    }


    @Override
    public Fragment getItem(int position) {
        if (views.get(position) == null) {
            DbSqlFragment fragment = new DbSqlFragment();
            fragment.setModel(stockList.get(position));
            views.set(position, fragment);
        }
        setFragmentNull(pager.getCurrentItem());
        return views.get(position);
    }

    @Override
    public int getCount() {
        if (stockList != null && stockList.size() != 0) {
            return stockList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /*
     * 重新初始化
     */
    public void invalidate(DetailModel model, int position) {
        try {
            if (views.get(position) != null) {
                views.get(position).invalidate(model);
            }
        } catch (Exception e) {
        }
    }


    public List<DetailModel> getStockList() {
        return stockList;
    }

    public void setStockList(List<DetailModel> stockList) {
        this.stockList = stockList;
    }

    /**
     * 初始控件位置
     */
    public void setFragmentNull(int position) {
        for (int i = 0; i < mCount; i++) {
            if (i == position || i == position - 1 || i == position + 1) {
                continue;
            }
            views.set(i, null);
        }
    }

    /**
     * 初始控件位置
     */
    public void initFragmentNull() {
        for (int i = 0; i < mCount; i++) {
            views.add(i, null);
        }
    }

    public void setCount(int count) {
        if (count > 0 && count <= 1000) {
            mCount = count;
            notifyDataSetChanged();
        }
    }

}
