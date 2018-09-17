package mulitbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import entity.SixGroupItem;
import me.drakeet.multitype.ItemViewBinder;
import utils.GlideManagerUtil;
import view.niudong.com.demo.R;

import static view.niudong.com.demo.MyApplication.mContext;

/**
 * Created by niudong on 2017/10/13.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */

public class GroupItemViewBinder extends ItemViewBinder<SixGroupItem, GroupItemViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_group, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SixGroupItem oneItem) {
        holder.tv_title.setText(oneItem.title);
        GlideManagerUtil.glideLoader(mContext, oneItem.imageUrl, R.mipmap.ic_launcher, R.mipmap.ic_launcher, holder.iv_one);
        GlideManagerUtil.glideLoader(mContext, oneItem.imageUrl, R.mipmap.ic_launcher, R.mipmap.ic_launcher, holder.iv_two);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;
        public ImageView iv_one;
        public ImageView iv_two;

        ViewHolder(View itemView) {
            super(itemView);
            iv_one = (ImageView) itemView.findViewById(R.id.iv_one);
            iv_two = (ImageView) itemView.findViewById(R.id.iv_two);
            tv_title = (TextView) itemView.findViewById(R.id.tv_one);
        }

    }
}
