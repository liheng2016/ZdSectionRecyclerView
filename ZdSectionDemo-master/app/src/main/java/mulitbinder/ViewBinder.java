package mulitbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by niudong on 2017/7/18.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */
public class ViewBinder extends ItemViewBinder<View, ViewBinder.ViewHolder> {
    private Context mContext;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup viewGroup) {
        if (null == mContext) {
            mContext = inflater.getContext();
        }
        FrameLayout view = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull View view) {
        if (-1 == viewHolder.container.indexOfChild(view)) {

            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeAllViews();
            }

            viewHolder.container.removeAllViews();
            viewHolder.container.addView(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final FrameLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = (FrameLayout) itemView;
        }
    }
}
