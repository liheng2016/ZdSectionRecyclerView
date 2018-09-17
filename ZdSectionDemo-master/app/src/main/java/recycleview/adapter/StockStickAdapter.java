package recycleview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import callback.OnItemClickListener;
import entity.StickyHeadEntity;
import entity.StockEntity;
import fragment.RecyclerStickFragment;
import utils.HkStockUtil;
import utils.MyUtil;
import utils.ToastUtils;
import utils.UIUtils;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/6/9.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class StockStickAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected OnItemClickListener<StickyHeadEntity<StockEntity.StockInfo>> mItemClickListener;
    public final static int TYPE_HEAD = 0;
    public final static int TYPE_DATA = 2;
    public final static int TYPE_STICKY_HEAD = 1;
    boolean isWebView;
    protected List<StickyHeadEntity<StockEntity.StockInfo>> mData;

    private Context mContext;

    public List<StickyHeadEntity<StockEntity.StockInfo>> getmData() {
        return mData;
    }

    public StockStickAdapter(List<StickyHeadEntity<StockEntity.StockInfo>> datas, Context mContext) {
        this.mData = datas;
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(parent.getContext(),
                LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(viewType), parent, false));
        holder.setVisablePage(R.id.web_view_root_layout, R.id.data_layout);
        return holder;
    }



    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int type = holder.getItemViewType();
        StockEntity.StockInfo item = mData.get(position).getData();
        switch (type) {

            case TYPE_STICKY_HEAD:
                holder.setText(R.id.tv_stock_name, item.stickyHeadName);
                holder.setText(R.id.tv_more, position == 1 ? item.mSataus : "");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showToast(view.getContext(), "u89uu9");
                    }
                });
                break;
            case TYPE_DATA:
                setData(holder, item);
                break;
        }
    }

    private void setData(final RecyclerViewHolder holder, StockEntity.StockInfo item) {
        if (isWebView) {
            holder.setLayoutView(true);
           HkStockUtil.getInstance().loadWebViewData(holder.getLayoutView(R.id.web_view_root_layout),2);
        } else {
            holder.setLayoutView(false);
            final String stockNameAndCode = item.stock_name + "\n" + item.stock_code;
            SpannableStringBuilder ssb = new SpannableStringBuilder(stockNameAndCode);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#a4a4a7")), item.stock_name.length(), stockNameAndCode.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new AbsoluteSizeSpan(UIUtils.dip2px(holder.itemView.getContext(), 13)), item.stock_name.length(), stockNameAndCode.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.setText(R.id.tv_stock_name_code, ssb).setText(R.id.tv_current_price, item.current_price)
                    .setText(R.id.tv_rate, (item.rate < 0 ? String.format("%.2f", item.rate) : "+" + String.format("%.2f", item.rate)) + "%");
            if (mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int position = holder.getLayoutPosition();
                        mItemClickListener.onItemClick(view, mData.get(position), position);
                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }


    public int getItemLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                return R.layout.investor_head_view;
            case TYPE_STICKY_HEAD:
                return R.layout.item_stock_sticky_head;
            case TYPE_DATA:
                return R.layout.item_stock_data;

        }
        return 0;
    }

    public void setData(List<StickyHeadEntity<StockEntity.StockInfo>> list,boolean isWebView) {
        this.mData = list;
        this.isWebView = isWebView;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener<StickyHeadEntity<StockEntity.StockInfo>> itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}