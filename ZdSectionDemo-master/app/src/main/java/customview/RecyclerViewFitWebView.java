package customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * 名称：
 * Created by niudong on 2018/5/30.
 * Tel：18811793194
 * VersionChange：5.7.0
 */
public class RecyclerViewFitWebView  extends WebView {
    public float oldY;
    public float oldX;
    public float newY;
    public float newX;

    public RecyclerViewFitWebView(Context context) {
        super(context);
    }

    public RecyclerViewFitWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                newX = ev.getX();
                newY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //手指滑动同时判断滑动方向，一旦滑动方向大于+-60便调用
                //getParent().getParent().requestDisallowInterceptTouchEvent(false);
                //将滑动事件交给RecyclerView来处理
                oldX = newX;
                oldY = newY;
                newX = ev.getX();
                newY = ev.getY();
                float moveX = Math.abs(oldX - newX);
                float moveY = Math.abs(oldY - newY);
                //moveX * 1.73 < moveY  ,判断左右滑动范围为+-60度
                if (moveX * 1.73 < moveY) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
