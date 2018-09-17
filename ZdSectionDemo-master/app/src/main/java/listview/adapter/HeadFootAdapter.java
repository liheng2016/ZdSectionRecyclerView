package listview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/6/16.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：下拉刷新和加载更多的ListView  Adapter
 */


public class HeadFootAdapter extends BaseAdapter {

    List<String> list;
    Context mContext;

    public HeadFootAdapter(List<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setData(List<String> list) {
        this.list = list;
//        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.item_list, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_rv_name);
            viewHolder.linear = (LinearLayout) view.findViewById(R.id.linear);
            view.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) view.getTag();

        viewHolder.textView.setText(list.get(i));
        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(i);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    static class ViewHolder {
        TextView textView;
        LinearLayout linear;
    }
}
