package customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;



import java.util.LinkedList;
import java.util.List;

import testinterface.Pullable;
import view.niudong.com.demo.R;

/***
 * 支持悬浮式状态栏 和下拉刷新 上拉加载的ScrollView
 */
public class StickScrollView extends ScrollView implements Pullable {

    private static final String STICKY = "sticky";
    private View mCurrentStickyView;
    private Drawable mShadowDrawable;
    private List<View> mStickyViews;
    private int mStickyViewTopOffset;
    private int defaultShadowHeight = 5;// 阴影高度
    private float density;
    private boolean redirectTouchToStickyView;

    private boolean pullDownEnable = true; //下拉刷新开关
    private boolean pullUpEnable = true; //上拉刷新开关

    private OnScrollDownListener onScrollListener;

    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;
    int a = 0;



    /**
     * 当点击Sticky的时候，实现某些背景的渐变
     */
    private Runnable mInvalidataRunnable = new Runnable() {

        @Override
        public void run() {
            if (mCurrentStickyView != null) {
                int left = mCurrentStickyView.getLeft();
                int top = mCurrentStickyView.getTop();
                int right = mCurrentStickyView.getRight();
                int bottom = getScrollY()
                        + (mCurrentStickyView.getHeight() + mStickyViewTopOffset);

                invalidate(left, top, right, bottom);
            }

            postDelayed(this, 16);

        }
    };

    public StickScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("InflateParams")
    public StickScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mShadowDrawable = context.getResources().getDrawable(
                R.drawable.sticky_shadow_default);
        mStickyViews = new LinkedList<View>();
        density = context.getResources().getDisplayMetrics().density;
    }

    /**
     * 找到设置tag的View
     *
     * @param viewGroup
     */
    private void findViewByStickyTag(ViewGroup viewGroup) {
        int childCount = ((ViewGroup) viewGroup).getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);

            if (getStringTagForView(child).contains(STICKY)) {
                mStickyViews.add(child);
                a = child.getTop();
                Log.i("查看高度",a+"");
            }

            if (child instanceof ViewGroup) {
                findViewByStickyTag((ViewGroup) child);
            }
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            findViewByStickyTag((ViewGroup) getChildAt(0));
        }
        showStickyView();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        showStickyView();
    }

    /**
     *
     */
    private void showStickyView() {
        View curStickyView = null;
        View nextStickyView = null;

        for (View v : mStickyViews) {
            int topOffset = v.getTop() - getScrollY(); //  你加入标签所在的高度 减去 你滑动的距离

            if (topOffset <= 0) {   //  当你加入标签的控件被你的手指画上去的时候
                if (curStickyView == null
                        || topOffset > curStickyView.getTop() - getScrollY()) {
                    curStickyView = v;
                }
            } else {
                if (nextStickyView == null
                        || topOffset < nextStickyView.getTop() - getScrollY()) {
                    nextStickyView = v;
                }
            }
        }

        if (curStickyView != null) {
            mStickyViewTopOffset = nextStickyView == null ? 0 : Math.min(
                    0,
                    nextStickyView.getTop() - getScrollY()
                            - curStickyView.getHeight());
            mCurrentStickyView = curStickyView;
            post(mInvalidataRunnable);
        } else {
            mCurrentStickyView = null;
            removeCallbacks(mInvalidataRunnable);

        }

    }

    private String getStringTagForView(View v) {
        Object tag = v.getTag();
        return String.valueOf(tag);
    }

    /**
     * 将sticky画出来
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mCurrentStickyView != null) {
            // 先保存起来
            canvas.save();
            // 将坐标原点移动到(0, getScrollY() + mStickyViewTopOffset)
            canvas.translate(0, getScrollY() + mStickyViewTopOffset);

            if (mShadowDrawable != null) {
                int left = 0;
                int top = mCurrentStickyView.getHeight() + mStickyViewTopOffset;
                int right = mCurrentStickyView.getWidth();
                int bottom = top + (int) (density * defaultShadowHeight + 0.5f);
                mShadowDrawable.setBounds(left, top, right, bottom-20);
                mShadowDrawable.draw(canvas);
            }

            canvas.clipRect(0, mStickyViewTopOffset,
                    mCurrentStickyView.getWidth(),
                    mCurrentStickyView.getHeight());

            mCurrentStickyView.draw(canvas);

            // 重置坐标原点参数
            canvas.restore();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            redirectTouchToStickyView = true;
        }

        if (redirectTouchToStickyView) {
            redirectTouchToStickyView = mCurrentStickyView != null;

            if (redirectTouchToStickyView) {
                redirectTouchToStickyView = ev.getY() <= (mCurrentStickyView
                        .getHeight() + mStickyViewTopOffset)
                        && ev.getX() >= mCurrentStickyView.getLeft()
                        && ev.getX() <= mCurrentStickyView.getRight();
            }
        }

        if (redirectTouchToStickyView) {
            ev.offsetLocation(
                    0,
                    -1
                            * ((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView
                            .getTop()));
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean hasNotDoneActionDown = true;

    @SuppressLint({"Recycle", "ClickableViewAccessibility"})
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (redirectTouchToStickyView) {
            ev.offsetLocation(0,
                    ((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView
                            .getTop()));
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            hasNotDoneActionDown = false;
        }

        if (hasNotDoneActionDown) {
            MotionEvent down = MotionEvent.obtain(ev);
            down.setAction(MotionEvent.ACTION_DOWN);
            super.onTouchEvent(down);
            hasNotDoneActionDown = false;
        }

        if (ev.getAction() == MotionEvent.ACTION_UP
                || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            hasNotDoneActionDown = true;
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View view = getChildAt(0);
            if (view.getMeasuredHeight() <= getScrollY() + getHeight()) {
                // 加载数据代码
                if (onScrollListener != null) {
                    onScrollListener.onLoadMore();

                }
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 滚动的回调接口
     */
    public interface OnScrollDownListener {
        public void onLoadMore();
    }

    public void setOnScrollListener(OnScrollDownListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public boolean canPullDown() {
        if (!pullDownEnable) {
            return false;
        }
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (!pullUpEnable) {
            return false;
        }
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
            return true;
        else
            return false;
    }

    public boolean isPullDownEnable() {
        return pullDownEnable;
    }

    public void setPullDownEnable(boolean pullDownEnable) {
        this.pullDownEnable = pullDownEnable;
    }

    public boolean isPullUpEnable() {
        return pullUpEnable;
    }

    public void setPullUpEnable(boolean pullUpEnable) {
        this.pullUpEnable = pullUpEnable;
    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev)
//                && mGestureDetector.onTouchEvent(ev);
//    }
//
//    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
//
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2,
//                                float distanceX, float distanceY) {
//            /**
//             * 如果我们滚动更接近水平方向,返回false,让子视图来处理它
//             */
//            return (Math.abs(distanceY) > Math.abs(distanceX));
//        }
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if(xDistance > yDistance){
                    return false;
                }
//                if(getScrollY() >a){
//                    return false;
//                }else {
////                    return true;
//                }
        }

        return super.onInterceptTouchEvent(ev);
    }

}
