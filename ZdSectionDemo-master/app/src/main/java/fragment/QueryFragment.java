package fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/10/12.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class QueryFragment extends Fragment {

    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_two);
        textView.setText("我是Fragment：  查询");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);

        return view;
    }
}
