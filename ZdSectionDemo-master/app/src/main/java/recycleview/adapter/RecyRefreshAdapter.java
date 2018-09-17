package recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/3/5.
 */


public class RecyRefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOT = 2;
    private List<String> dataList;
    private View mHeadView;
    private View mFootView;
    private Context mContext;

    public RecyRefreshAdapter(List<String> data, Context mContext) {
        this.mContext = mContext;
        this.dataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeadView != null && viewType == TYPE_HEAD) {
            return new HeadViewHolder(getHeaderView());
        } else if (mFootView != null && viewType == TYPE_FOOT) {
            FootViewHolder footViewHolder = new FootViewHolder(getFooterView());
            return footViewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_one, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }

    }

    public void setData(List<String> datas) {
        this.dataList = datas;
        notifyDataSetChanged();
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeadView;
    }

    public void setHeaderView(View headerView) {
        mHeadView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFootView;
    }

    // 32-1=31
    public void setFooterView(View footerView) {
        mFootView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            //自动适配
            tvNum = (TextView) itemView.findViewById(R.id.tv_rv_name);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);
            //自动适配
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
            //自动适配
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 不等于头       position = position - 1;

        if (holder instanceof ItemViewHolder) {
            //TODO  这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
            ((ItemViewHolder) holder).tvNum.setText(dataList.get(position - 1));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();

                }
            });
        } else if (holder instanceof HeadViewHolder) {

        } else if (holder instanceof FootViewHolder) {

        }

    }

    @Override
    public int getItemViewType(int position) {
        //
        if (mHeadView == null && mFootView == null) {
            return TYPE_ITEM;
        }

        if (position == 0) {
            return TYPE_HEAD;
        }
        if (position == getItemCount() - 1) { //32-1=31
            return TYPE_FOOT;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        //  32----31    0--head    数据在1-30   foot--31

        if (mHeadView == null && mFootView == null) {
            return dataList == null ? 0 : dataList.size();
        } else if (mHeadView != null && mFootView == null) {
            return dataList == null ? 0 : dataList.size() + 1;
        } else if (mHeadView == null && mFootView != null) {
            return dataList == null ? 0 : dataList.size() + 1;
        } else {
            return dataList == null ? 0 : dataList.size() + 2;
        }
    }


}
