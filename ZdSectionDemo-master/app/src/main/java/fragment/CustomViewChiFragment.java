package fragment;

import android.view.View;
import android.widget.TextView;

import base.TestBaseFragment;
import customview.LoopScaleView;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/9/19
 * Tel：18811793194
 * VersionChange：mthq1.0
 */
public class CustomViewChiFragment extends TestBaseFragment {
    LoopScaleView mLsvScale;
    TextView mTvValue;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_widget;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLsvScale = view.findViewById(R.id.lsv_scale);
        mTvValue = view.findViewById(R.id.tv_value);
    }

    @Override
    protected String getFragmentTitle() {
        return "自定义刻度尺";
    }

    @Override
    protected void initData() {
        super.initData();
        mLsvScale.setOnValueChangeListener(new LoopScaleView.OnValueChangeListener() {
            @Override
            public void OnValueChange(int newValue) {
                mTvValue.setText(String.valueOf(newValue));
            }
        });

    }
}
