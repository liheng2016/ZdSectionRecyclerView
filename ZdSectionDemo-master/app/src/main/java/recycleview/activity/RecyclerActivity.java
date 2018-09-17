package recycleview.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import base.BaseActivity;
import entity.HotelEntity;
import recycleview.adapter.HeaderHolder;
import recycleview.adapter.HotelEntityAdapter;
import recycleview.view.RecycleViewDivider;
import utils.JsonUtils;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/6/15.
 * Tel：18811793194
 * VersionChange：
 * <p>
 */
public class RecyclerActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private HotelEntityAdapter mAdapter;
    private int height;
    private int currentPosition = 0;
    private LinearLayoutManager linearLayoutManager;
    private HeaderHolder headerHolder;
    private TextView tvStickyHeaderView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_recy);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);
        mAdapter = new HotelEntityAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this);
        //GridLayoutManager manager = new GridLayoutManager(this,4);
        //设置header
        //manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,manager));
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.VERTICAL, R.drawable.custom_recycler_view_divier, false));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //模拟假数据
        HotelEntity entity = JsonUtils.analysisJsonFile(this, "json");
        mAdapter.setData(entity.allTagsList);

    }
}
