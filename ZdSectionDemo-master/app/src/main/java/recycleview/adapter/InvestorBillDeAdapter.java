package recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import entity.HkHistoryRenGouData;
import view.niudong.com.demo.NewsContentActivity;
import view.niudong.com.demo.R;
import entity.News;

/**
 * 投资者账单主页
 */

public class InvestorBillDeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEAD = 0;  //顶部headView
    private static final int TYPE_HEAD_TITLE = 1;  //中间标题headView
    private static final int TYPE_ITEM = 2;  //普通Item View

    private String newMouth = "12";
    private List<News> dataList;

    private Context mContext;

    public InvestorBillDeAdapter(List<News> data, Context mContext) {
        this.mContext = mContext;
        this.dataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_investor, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        } else if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investor_head_view, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            HeadViewHolder headViewHolder = new HeadViewHolder(view);
            return headViewHolder;
        } else if (viewType == TYPE_HEAD_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investor_title_view, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            HeadViewHolder headViewHolder = new HeadViewHolder(view);

            return headViewHolder;
        }

        return null;

    }

    public void updateData(List<News> datas) {
        this.dataList = datas;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNum;
        public List<News> mDataList;

        public ItemViewHolder(View itemView) {
            super(itemView);
            //自动适配
            tvNum = (TextView) itemView.findViewById(R.id.tv_rv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 走这里
                    NewsContentActivity.actionStart(mContext, getAdapterPosition()-2, mDataList);

                }
            });

        }
    }

    /**
     * 底部FootView布局
     */
    public static class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 不等于头       position = position - 1;
        //TODO  这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mDataList = dataList;

            ((ItemViewHolder) holder).tvNum.setText(dataList.get(position - 2).getTitle());
          /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ToastUtils.showToast(mContext, "" + position);

                }
            });*/


        }
    }


    @Override
    public int getItemCount() {
        //  32----31    0--head    数据在1-30   foot--31
        return dataList == null ? 0 : dataList.size() + 2;//加了一个head
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == 1) {
            return TYPE_HEAD_TITLE;
        } else {
            return TYPE_ITEM;
        }
    }

}
