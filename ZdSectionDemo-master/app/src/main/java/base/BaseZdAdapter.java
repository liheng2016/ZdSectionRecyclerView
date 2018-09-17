package base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：
 * Created by niudong on 2017/12/18.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public abstract class BaseZdAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public List<T> mDataList;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return addChildViewHolder(view);
    }

    public BaseZdAdapter(List<T> mDataList) {
        this.mDataList = mDataList;
    }

    public void setData(List<T> data) {
        mDataList = null != data ? data : new ArrayList<T>();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (null == mDataList || mDataList.size() == 0) ? 0 : mDataList.size();
    }

    /**
     * 加载资源id
     */
    protected abstract int getLayoutId();

    //
    protected abstract BaseViewHolder addChildViewHolder(View view);
}
