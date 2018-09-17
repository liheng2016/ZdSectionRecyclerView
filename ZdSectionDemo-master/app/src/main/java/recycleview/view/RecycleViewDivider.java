package recycleview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by niudong on 2017/7/18.
 * Tel：18811793194
 * VersionChange：5.4
 * <p> 为RecyclerView添加分割线 传入颜色值和高度即可
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Drawable mDivider;
    /**
     * 自设定分割线高度，默认为1px
     */
    private int mDividerHeight = 2;
    /**
     * 分割线
     * LinearLayoutManager.VERTICAL   ---显示竖直的线
     * LinearLayoutManager.HORIZONTAL  ---显示水平的线
     */
    private int mOrientation;
    /**
     * true 不显示最后一个条目分割线
     * false 显示最后一个分割线
     */
    private boolean mHasFooter;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param orientation 列表方向
     */
    public RecycleViewDivider(Context context, int orientation, boolean hasFooter) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        this.mHasFooter = hasFooter;
        mOrientation = orientation;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param orientation VERTICAL 显示竖直的线  HORIZONTAL 显示水平的线
     * @param drawableId  分割线图片   使用 R.drawable.divier   在drawable ---custom_recycler_view_divier 设定
     * @param hasFooter   true 不显示最后一个条目分割线   false 显示最后一个分割线
     */
    public RecycleViewDivider(Context context, int orientation, int drawableId, boolean hasFooter) {
        this(context, orientation, hasFooter);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }


    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation, dividerHeight, dividerColor, true);
    }

    /**
     * 自定义分割线
     *
     * @param orientation   VERTICAL 显示竖直的线  HORIZONTAL 显示水平的线
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     * @param hideLast      true 不显示最后一个条目分割线   false 显示最后一个分割线
     */
    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor, boolean hideLast) {
        this(context, orientation, hideLast);
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    /**
     * 绘制横向(水平) item 分割线  宽高
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            // 有脚部时，最后一条不画
            if (mHasFooter &&
                    parent.getChildLayoutPosition(child) == parent.getLayoutManager().getItemCount() - 1) {
                continue;
            }

            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 绘制纵向 item 分割线
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            // 有脚部时，最后一条不画
            if (mHasFooter &&
                    parent.getChildLayoutPosition(child) == parent.getLayoutManager().getItemCount() - 1) {
                continue;
            }
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}