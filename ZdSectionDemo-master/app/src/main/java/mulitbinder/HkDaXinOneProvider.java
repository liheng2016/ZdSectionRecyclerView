package mulitbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import entity.ItemOneBean;
import me.drakeet.multitype.ItemViewBinder;
import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/11/14.
 * Tel：18811793194
 * VersionChange：金贝塔
 */
public class HkDaXinOneProvider extends ItemViewBinder<ItemOneBean, HkDaXinOneProvider.ViewHolder> {
    private Context mContext;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_hk_daxin_one, parent, false);
        if (null == mContext) {
            mContext = view.getContext();
        }
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ItemOneBean item) {
        holder.tvTagView.setText(item.mTitle);
        holder.tvTQuestionView.setText(item.mQuestion);
        holder.tvAnswerView.setText(item.mAnswer);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTagView;
        TextView tvTQuestionView;
        TextView tvAnswerView;
        LinearLayout ll_root_view;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTagView = (TextView) itemView.findViewById(R.id.tv_time);
            tvTQuestionView = (TextView) itemView.findViewById(R.id.tv_title);
            tvAnswerView = (TextView) itemView.findViewById(R.id.tv_content);
        }

    }
}