package customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.logging.Logger;

import customview.FloatingStickClickDecoration.PostionMode;
import utils.MyUtil;

/**
 * 名称：
 * Created by niudong on 2018/6/15.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class RecyclerViewScroll extends RecyclerView {
    private OnTabClickListener onTabClickListener;
    private View mllTopView;

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    public RecyclerViewScroll(Context context) {
        this(context, null);
    }

    public RecyclerViewScroll(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RecyclerViewScroll(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * 互斥的
     */
    public void setTopView(View view) {
        this.mllTopView = view;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int dowY = (int) event.getY();
                float rawY = event.getRawY();
                Log.d("onTouchEvent","dowY"+dowY+"..........rawY.........."+rawY);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getY();
                int moveRY = (int) event.getRawY();

                Log.d("onTouchEvent","moveY"+moveY+".......moveRY......."+moveRY);
                if (moveY/2 >= 400) {
                    MyUtil.setVisibility(mllTopView, View.VISIBLE);
                } else {
                    MyUtil.setVisibility(mllTopView, View.INVISIBLE);
                }
                break;
            case MotionEvent.ACTION_UP:

                int upX = (int) event.getX();
                int upY = (int) event.getY();

                break;
        }
        return super.onTouchEvent(event);
    }

    public interface OnTabClickListener {
        void tabClick(int pos);
    }
}
