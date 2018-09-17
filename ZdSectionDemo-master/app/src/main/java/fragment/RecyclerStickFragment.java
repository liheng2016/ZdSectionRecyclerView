package fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.TestBaseFragment;
import customview.FloatingStickClickDecoration;
import customview.RecyclerClickView;
import entity.ListGrup;
import utils.HkStockUtil;
import utils.MyUtil;
import utils.ToastUtils;
import view.niudong.com.demo.NewsContentActivity;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/6/7.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class RecyclerStickFragment extends TestBaseFragment implements RecyclerClickView.OnTabClickListener {
    private Map<Integer, String> keys = new HashMap<>();//存放所有key的位置和内容
    private MyAdapter adapter;
    private RecyclerClickView rv;
    private List<String> list = new ArrayList<>();//adapter数据源
    private LinearLayoutManager layoutManager;


    private static final int TYPE_HEAD = 0;  //顶部headView
    private static final int TYPE_ITEM = 2;  //普通Item View
    private boolean isWebView;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recycler_stick;
    }

    @Override
    protected String getFragmentTitle() {
        return "固定头部RecyclerView";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        rv = (RecyclerClickView) view.findViewById(R.id.recycler_view_detail);
        rv.setOnTabClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();

//TODO  模拟分组列表数据
        List<ListGrup> grupList = new ArrayList<>();
        ListGrup listGrup = new ListGrup();
        listGrup.head = 1;
        listGrup.setmChildList(HkStockUtil.getInstance().hkHistoryDataList());
        grupList.add(listGrup);
//  2
        keys.put(0, "");//为处理头部数据
        for (int i = 0; i < grupList.size(); i++) {
            keys.put(list.size() + 1, i == 0 ? "RecyclerView" : "港股");//组名  0,1
            for (int j = 0; j < grupList.get(i).mChildList.size(); j++) {//
                list.add(grupList.get(i).mChildList.get(j).mCreateDate);
            }
        }
        //设置adapter
        adapter = new MyAdapter(list, mContext);
        // 添加分割线
        final FloatingStickClickDecoration floatingItemDecoration = new FloatingStickClickDecoration(mContext);
        floatingItemDecoration.setKeys(keys);
        floatingItemDecoration.setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        rv.addItemDecoration(floatingItemDecoration);

        //设置布局管理器
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(1);
        rv.setLayoutManager(layoutManager);

        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> datas;
        private Context mContext;
        private boolean isWebView;

        public MyAdapter(List<String> datas, Context mContext) {
            this.datas = datas;
            this.mContext = mContext;
        }

        public void setTabcLick(boolean isWebView) {
            this.isWebView = isWebView;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_content, parent, false);
                ItemViewHolder itemViewHolder = new ItemViewHolder(view);
                return itemViewHolder;
            } else if (viewType == TYPE_HEAD) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investor_head_view, parent, false);
                //这边可以做一些属性设置，甚至事件监听绑定
                HeadViewHolder headViewHolder = new HeadViewHolder(view);
                return headViewHolder;
            }
            return null;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                if (isWebView) {
                    HkStockUtil.getInstance().loadWebViewData(((ItemViewHolder) holder).mRootLinearLayout, 3);
                    MyUtil.setVisibility(((ItemViewHolder) holder).mRelativeLayout, View.GONE);
                    MyUtil.setVisibility(((ItemViewHolder) holder).mRootLinearLayout, View.VISIBLE);
                } else {
                    MyUtil.setVisibility(((ItemViewHolder) holder).mRootLinearLayout, View.GONE);
                    MyUtil.setVisibility(((ItemViewHolder) holder).mRelativeLayout, View.VISIBLE);
                    ((ItemViewHolder) holder).tvNum.setText(datas.get(holder.getAdapterPosition() - 1));
                }
            }
        }


        @Override
        public int getItemCount() {
            return datas.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEAD;
            } else {
                return TYPE_ITEM;
            }
        }


        public class ItemViewHolder extends RecyclerView.ViewHolder {
            public TextView tvNum;
            private final RelativeLayout mRelativeLayout;
            private final LinearLayout mRootLinearLayout;

            public ItemViewHolder(View itemView) {
                super(itemView);
                //自动适配
                tvNum = (TextView) itemView.findViewById(R.id.tv);
                mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_translucent);
                mRootLinearLayout = (LinearLayout) itemView.findViewById(R.id.web_view_root_layout);
                itemView.setOnClickListener(null);

            }
        }

        /**
         * 底部FootView布局
         */
        public class HeadViewHolder extends RecyclerView.ViewHolder {

            public HeadViewHolder(View view) {
                super(view);
            }
        }

    }

    @Override
    public void tabClick(int pos) {
        ToastUtils.showToast(mContext, "点击了" + (pos == 0 ? "RecyclerView" : "WebView") + "即将改变数据...");
        adapter.setTabcLick(pos != 0);//触发修改数据

    }
}
