package recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/3/5.
 */


public class RecyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<String> dataList;

    private Context mContext;

    public RecyItemAdapter(List<String> data, Context mContext) {
        this.mContext = mContext;
        this.dataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_investor, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;

    }

    public void setData(List<String> datas) {
        this.dataList = datas;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            //自动适配
            tvNum = (TextView) itemView.findViewById(R.id.tv_rv_name);
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 不等于头       position = position - 1;

            ((ItemViewHolder) holder).tvNum.setText(dataList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (null != onItemClickListener) {
                        onItemClickListener.onClick(dataList.get(position));
                    }

                }
            });

    }


    @Override
    public int getItemCount() {
        //  32----31    0--head    数据在1-30   foot--31
        return dataList == null ? 0 : dataList.size();
    }

  public   interface OnItemClickListener {
        void onClick(String data);
    }
}
