package customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.List;

import customview.FloatingStickClickDecoration.PostionMode;

/**
 * 名称：
 * Created by niudong on 2018/6/15.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class RecyclerClickView extends RecyclerView {
    private List<PostionMode> modes;
    private boolean isClick;
    private OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    public RecyclerClickView(Context context) {
        this(context, null);
    }

    public RecyclerClickView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RecyclerClickView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * 互斥的
     */
    public void setList(List<PostionMode> data) {
        this.modes = data;
        Log.d("test", data.toString() + "位置");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                Log.d("test", isClick + "按下位置");
                break;
            case MotionEvent.ACTION_MOVE:
                isClick = false;
                break;
            case MotionEvent.ACTION_UP:

                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (isClick && null!=modes&&modes.size() > 0) {
                    for (int i = 0; i < modes.size(); i++) {
                        PostionMode mode = modes.get(i);
                        float right = mode.right;
                        float left = mode.left;
                        float bottom = mode.bottom;
                        float top = mode.top;
                        if (left <= upX && upX <= right && bottom >= upY && upY >= top) {
                            Log.d("test", isClick + "位置");
                            if (onTabClickListener != null) {
                                onTabClickListener.tabClick(mode.clickPostion);
                            }
                            break;
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public interface OnTabClickListener {
        void tabClick(int pos);
    }
}
