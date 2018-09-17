package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import base.BaseViewHolder;
import contants.ItemTypeFactory;
import contants.TypeFactory;
import contants.Visitable;

/**
 * 名称：
 * Created by niudong on 2017/12/16.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class MultiRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    //数据源
    List<Visitable> mData;
    //多种条目类型
    TypeFactory typeFactory;

    public MultiRecyclerAdapter(List<Visitable> mData) {
        this.mData = mData;
        this.typeFactory = new ItemTypeFactory();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return typeFactory.createViewHolder(viewType, view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type(typeFactory);
    }

    @Override
    public int getItemCount() {
        return (mData != null ? mData.size() : 0);
    }
}
