package fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import base.TestBaseFragment;
import entity.Category;
import entity.ItemOneBean;
import entity.ItemTwoBean;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.CategoryViewBinder;
import mulitbinder.HkDaXinOneProvider;
import mulitbinder.HkDaXinTwoProvider;
import mulitbinder.ViewBinder;
import utils.ColorUtils;
import utils.HkStockUtil;
import utils.UIUtils;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/9/27
 * Tel：18811793194
 * VersionChange：mthq1.0
 */
public class PopRecyclerFragment extends TestBaseFragment {

    private MultiTypeAdapter mAdapter;
    private Items items = new Items();
    private RecyclerView mRecyclerView;
    private View mHeadPayView;
    private TextView mHeadTvContent;
    private View mChildContentTitlte;

    @Override
    protected int getLayoutResId() {
        return R.layout.pop_recycler_view;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRecyclerView = view.findViewById(R.id.pop_recyclerview);
        mAdapter = new MultiTypeAdapter(items);
        //TODO 绑定多个条目
        mAdapter.register(View.class, new ViewBinder());
        mAdapter.register(Category.class, new CategoryViewBinder());
        //TODO 头部View
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mHeadPayView = LayoutInflater.from(getContext()).inflate(R.layout.pay_head_layout, null);
        //TODO  子View内容标题
        mChildContentTitlte = LayoutInflater.from(getContext()).inflate(R.layout.hk_daxin_detail_title_content, mRecyclerView, false);
        mHeadTvContent = (TextView) mChildContentTitlte.findViewById(R.id.tv_head_content);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected String getFragmentTitle() {
        return "Pop中的RecyclerView";
    }

    @Override
    protected void initData() {
        super.initData();
        List<Category> firstList = HkStockUtil.getInstance().getPopListData();
        List<Category> SecondList = HkStockUtil.getInstance().getPopList2Data();
        items.clear();
        items.add(mHeadPayView);
        items.add(addTitlte("我是标题一"));
        //List
        items.addAll(firstList);
        items.add(addTitlte("我是标题二"));
        //List
        items.addAll(SecondList);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 添加孩子标题
     */
    private TextView addTitlte(String value) {
        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(40)));
        textView.setTextColor(ColorUtils.COLOR_WHITE);
        textView.setBackgroundColor(ColorUtils.COLOR_RED);
        textView.setGravity(Gravity.CENTER | Gravity.LEFT);
        textView.setText(value);
        return textView;
    }


}
