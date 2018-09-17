package mulitbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import entity.Category;
import me.drakeet.multitype.ItemViewBinder;
import utils.ToastUtils;
import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/7/18.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */


public class CategoryViewBinder extends ItemViewBinder<Category, CategoryViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        final View root = inflater.inflate(R.layout.item_category, parent, false);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(root.getContext(),"我被点击了");
            }
        });
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Category item) {

        holder.category.setText(item.text);
        Log.d("demo", "position: " + getPosition(holder));
        Log.d("demo", "adapter: " + getAdapter());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final TextView category;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.category = (TextView) itemView.findViewById(R.id.category);
  /*          itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(itemView.getContext(),"我被点击了");
                }
            });
*/
        }


    }
}
