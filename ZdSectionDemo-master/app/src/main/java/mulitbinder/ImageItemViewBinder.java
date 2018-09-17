package mulitbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import entity.ImageItem;
import me.drakeet.multitype.ItemViewBinder;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/7/18.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：  bean---view
 */


public class ImageItemViewBinder extends ItemViewBinder<ImageItem, ImageItemViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ImageItemViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ImageItem item) {
        holder.item=item;
        holder.imageView.setImageResource(item.getImage());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        public final ImageView imageView;
        public  ImageItem item;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.iv);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(itemView.getContext(),"我是"+getAdapterPosition());
                }
            });
        }
    }
}
