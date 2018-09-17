package base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 名称：
 * Created by niudong on 2017/12/16.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    SparseArray<View> mViews;
    View mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
        mViews = new SparseArray<>();
    }

    /**
     * 获取到View
     */
    public View getView(int resId) {
        View view = mViews.get(resId);
        if (null == view) {
            view = itemView.findViewById(resId);
            mViews.put(resId, view);
        }
        return view;
    }

    public abstract void bindData(T data);

}
