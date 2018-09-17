package holder;

import android.view.View;
import android.widget.TextView;

import base.BaseViewHolder;
import entity.SearchBean;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2017/12/16.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class SearchViewHolder extends BaseViewHolder<SearchBean> {

    public SearchViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(SearchBean data) {
        TextView view = (TextView) getView(R.id.tv_rv_name);
        view.setText(data.message);
    }
}
