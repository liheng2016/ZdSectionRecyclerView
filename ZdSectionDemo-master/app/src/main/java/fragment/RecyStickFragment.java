package fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import base.TestBaseFragment;
import callback.OnItemClickListener;
import customview.StickyHeadContainer;
import customview.StickyItemLayoutDecoration;
import entity.StickyHeadEntity;
import entity.StockEntity;
import entity.StockEntity.StockInfo;
import recycleview.adapter.StockAdapter;
import recycleview.adapter.StockStickAdapter;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/6/9.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 * 每一个分组头计算为一个item 暂距一个postion位置
 */
public class RecyStickFragment extends TestBaseFragment implements StickyHeadContainer.DataCallback {

    private TextView tvStockName;
    private RecyclerView mRecyclerView;
    private StockStickAdapter mAdapter;
    private StockEntity stockEntity;
    private boolean isWebView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recycler_layout;
    }

    @Override
    protected String getFragmentTitle() {
        return "采用自定义布局方式悬停";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        final StickyHeadContainer container = (StickyHeadContainer) view.findViewById(R.id.shc);
        tvStockName = (TextView) container.findViewById(R.id.tv_stock_name);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        container.setDataCallback(this);
        mAdapter = new StockStickAdapter(null, mContext);
        mRecyclerView.addItemDecoration(new StickyItemLayoutDecoration(container, mAdapter.TYPE_STICKY_HEAD));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new OnItemClickListener<StickyHeadEntity<StockInfo>>() {
            @Override
            public void onItemClick(View view, StickyHeadEntity<StockInfo> data, int position) {
                ToastUtils.showToast(mContext, "我的名字是" + data.getData().stock_name + "你点击了" + (position - 2));
            }
        });

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isWebView = !isWebView;
                ToastUtils.showToast(mContext, "你点击了" + tvStockName.getText().toString() + "分组");
                mAdapter.setData(parseAndSetData((int) (Math.random() * 3)),isWebView);
            }
        });
        Gson gson = new Gson();
        //TODO 数据源        
        stockEntity = gson.fromJson(getStrFromAssets(mContext, "rasking.json"), StockEntity.class);

        mAdapter.setData(parseAndSetData(0),false);

    }


    @Override
    public void onDataChange(int pos) {
        StockInfo item = mAdapter.getmData().get(pos).getData();
        tvStockName.setText(item.stickyHeadName);
    }

    private List<StickyHeadEntity<StockInfo>> parseAndSetData(int pagePostion) {
        List<StockInfo> data = new ArrayList<>();
        if (null == stockEntity) return null;
        data.clear();
        if (0 == pagePostion) {
            data.add(new StockInfo(mAdapter.TYPE_STICKY_HEAD, "涨幅榜"));
            for (int i = 0; i < stockEntity.increase_list.size(); i++) {
                StockInfo stockInfo = stockEntity.increase_list.get(i);
                stockInfo.setItemType(mAdapter.TYPE_DATA);
                data.add(stockInfo);
            }
        } else if (pagePostion == 1) {
            data.add(new StockInfo(mAdapter.TYPE_STICKY_HEAD, "换手率"));
            for (StockInfo info : stockEntity.change_list) {
                info.setItemType(mAdapter.TYPE_DATA);
                data.add(info);
            }
        } else if (pagePostion == 2) {
            data.add(new StockInfo(mAdapter.TYPE_STICKY_HEAD, "振幅榜"));
            for (StockInfo info : stockEntity.amplitude_list) {
                info.setItemType(mAdapter.TYPE_DATA);
                data.add(info);
            }
        }
        List<StickyHeadEntity<StockInfo>> list = new ArrayList<>(data.size());
        list.add(new StickyHeadEntity<StockInfo>(null, StockAdapter.TYPE_HEAD, null));
        for (StockInfo info : data) {
            list.add(new StickyHeadEntity<>(info, info.getItemType(), info.stickyHeadName));
        }
        return list;
    }

    /**
     * @return Json数据（String）
     * @description 通过assets文件获取json数据，这里写的十分简单，没做循环判断。
     */
    public static String getStrFromAssets(Context context, String name) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
