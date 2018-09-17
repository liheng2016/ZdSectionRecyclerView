package utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/1/3.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class CustomToolBar extends RelativeLayout {
    public CustomToolBar(Context context) {
        this(context, null);
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar);
        TextView text = (TextView) typedArray.getText(R.styleable.CustomToolBar_textSize);
        TextView textColor = (TextView) typedArray.getText(R.styleable.CustomToolBar_textColor);
    }
}
