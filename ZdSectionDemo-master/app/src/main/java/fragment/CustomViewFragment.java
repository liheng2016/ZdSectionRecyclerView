package fragment;

import android.graphics.Color;
import android.view.View;

import base.TestBaseFragment;
import customview.ColumnView;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/6/15.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class CustomViewFragment extends TestBaseFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_custom;
    }

    @Override
    protected String getFragmentTitle() {
        return "自定义直方图";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        ColumnView mColumnView = (ColumnView) view.findViewById(R.id.columnView);
        mColumnView.setXAxisValue(7,7);//从第一个开始绘制哦
        mColumnView.setYAxisValue(6,6);

        int columnInfo[][] = new int[][]{
                {1, Color.YELLOW},
                {2, Color.GREEN},
                {3, Color.LTGRAY},
                {4, Color.BLUE},
                {5, Color.RED}};
        mColumnView.setColumnInfo(columnInfo);
    }
    }

