package recycleview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import view.niudong.com.demo.R;


/**
 /**
 * Created by niudong on 2017/6/15.
 * Tel：18811793194
 * VersionChange：
 * <p>
 */

public class HeaderHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public TextView openView;

    public View view;
    public RelativeLayout rel_open;

    public static HeaderHolder instance;

/*
    public static HeaderHolder getInstance() {

        if (instance != null) {

            synchronized (HeaderHolder.class) {
                if (instance == null) {

                    instance = new HeaderHolder();
                }
            }

        }
        return instance;
    }
*/


    public HeaderHolder(View itemView) {
        super(itemView);
        initView();
    }


    private void initView() {
        titleView = (TextView) itemView.findViewById(R.id.tv_title);
        openView = (TextView) itemView.findViewById(R.id.tv_open);
        rel_open = (RelativeLayout) itemView.findViewById(R.id.rel_open);
    }
}
