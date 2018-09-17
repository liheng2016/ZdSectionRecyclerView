package mulitbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import entity.BaseItem;
import entity.FourItem;
import entity.OneItem;
import entity.ThreeItem;
import entity.TwoItem;
import me.drakeet.multitype.ItemViewBinder;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/10/13.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */

public class ShareItemViewBinder extends ItemViewBinder<BaseItem, ShareItemViewBinder.ViewHolder> {
    private List<BaseItem> itemList1;

    private Context mContext;

    public ShareItemViewBinder(List<BaseItem> mData) {
        this.itemList1 = mData;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_share_item, parent, false);
        if (mContext == null) {
            mContext = root.getContext();
        }
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BaseItem oneItem) {
        int viewType = oneItem.viewType;
//        OneItem mOneItem = (OneItem) oneItem;
//        holder.tv_title.setText(oneItem.title);

        if (viewType == 11) {
            OneItem mOneItem = (OneItem) oneItem;
            holder.tv_title.setText(mOneItem.title);
        } else if (viewType == 22) {
            TwoItem mOneItem = (TwoItem) oneItem;
            holder.tv_title.setText(mOneItem.title);
        } else if (viewType == 33) {
            ThreeItem mOneItem = (ThreeItem) oneItem;
            holder.tv_title.setText(mOneItem.title);
        } else if (viewType == 44) {
            FourItem mOneItem = (FourItem) oneItem;
            holder.tv_title.setText(mOneItem.title);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView recyclerView_one;
        public TextView tv_title;
        public BaseItem baseItem;

        ViewHolder(View itemView) {
            super(itemView);
            recyclerView_one = (RecyclerView) itemView.findViewById(R.id.recyclerView_one);
            tv_title = (TextView) itemView.findViewById(R.id.tv_one);
            recyclerView_one.setLayoutManager(
                    new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            ShareDataAdapter mAdapter = new ShareDataAdapter(itemList1);
            recyclerView_one.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

    }
}
