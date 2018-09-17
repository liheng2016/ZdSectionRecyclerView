package holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import base.BaseViewHolder;
import entity.ContentBean;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2017/12/16.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class ContentViewHolder extends BaseViewHolder<ContentBean> {
    public ContentViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(ContentBean data) {
        TextView button = (TextView) getView(R.id.hhe);
        button.setText(data.getContent());

    }
}
