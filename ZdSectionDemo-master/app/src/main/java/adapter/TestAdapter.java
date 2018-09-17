package adapter;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import base.BaseViewHolder;
import base.BaseZdAdapter;
import entity.DetailModel;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2017/12/18.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class TestAdapter extends BaseZdAdapter {

    public TestAdapter(List mDataList) {
        super(mDataList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_list;
    }

    @Override
    protected BaseViewHolder addChildViewHolder(View view) {
        return new TestHolder(view);
    }

    static class TestHolder extends BaseViewHolder<DetailModel> {
        public TestHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(DetailModel data) {
            TextView view = (TextView) getView(R.id.tv_rv_name);
            view.setText(data.titleData);
        }
    }
}
