package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.UIUtils;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/4/7.
 */

public class ZdViewPagerIndicator extends LinearLayout {
    private onTabItemClickListener onTabItemClickListener;

    public void setOnTabItemClickListener(ZdViewPagerIndicator.onTabItemClickListener onTabItemClickListener) {
        this.onTabItemClickListener = onTabItemClickListener;
    }

    private static final int COLOR_TEXT_NORMAL = 0xFF000000;
    private static final int COLOR_INDICATOR_COLOR = Color.GREEN;

    private String[] mTitles;
    private int[] mTabIcons;
    private int mTabCount;
    private int mIndicatorColor = COLOR_INDICATOR_COLOR;
    private float mTranslationX;
    private Paint mPaint = new Paint();
    private int mTabWidth;

    public ZdViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ZdViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStrokeWidth(6.0F);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w / mTabCount;
    }

    public void setTitles(String[] titles, int[] mTabIcon) {
        mTitles = titles;
        mTabIcons = mTabIcon;
        mTabCount = titles.length;
        generateTitleView();

    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setColor(mIndicatorColor);
        canvas.save();
        canvas.translate(mTranslationX, getHeight());
        canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
        canvas.restore();
    }

    public void scroll(int position, float offset) {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */
        mTranslationX = getWidth() / mTabCount * (position + offset);
        invalidate();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 处理子View Tab
     */
    private void generateTitleView() {
        if (getChildCount() > 0)
            this.removeAllViews();
        int count = mTitles.length;
        setWeightSum(count);
        for (int i = 0; i < count; i++) {
            final int mPostion = i;
            //TODO  父View
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            linearLayout.setGravity(Gravity.CENTER);
            layoutParams.weight = 1;
            linearLayout.setLayoutParams(layoutParams);
            //TODO  ImageView
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams imLay = new LinearLayout.LayoutParams(UIUtils.dip2px(15), UIUtils.dip2px(15));
            imLay.gravity = Gravity.CENTER;
            //图片样式
            imageView.setImageResource(mTabIcons[i]);
            imageView.setLayoutParams(imLay);
            //TODO  TextView
            TextView mTvTitle = new TextView(getContext());
            LinearLayout.LayoutParams mTvTitleLay = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mTvTitleLay.gravity = Gravity.CENTER;
            mTvTitleLay.leftMargin = UIUtils.dip2px(5);
            //TODO  字体样式
            mTvTitle.setTextColor(getContext().getResources().getColor(R.color.xct_lthj_color_closeblack));
            mTvTitle.setText(mTitles[i]);
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            mTvTitle.setLayoutParams(mTvTitleLay);

            mTvTitle.setGravity(Gravity.CENTER);
            linearLayout.addView(imageView);
            linearLayout.addView(mTvTitle);
            //TODO 设置点击事件
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onTabItemClickListener) {
                        onTabItemClickListener.onItemClick(mPostion);
                    }
                }
            });
            //将View 添加到整体容器中
            addView(linearLayout);
        }
    }

    public interface onTabItemClickListener {
        void onItemClick(int mPostion);
    }

    /**
     * 高亮选中字体颜色
     *
     * @param ponstion
     */
    public void hightTextColor(int ponstion) {
        if (ponstion <= mTabCount && null != getChildAt(ponstion)) {
            resetTextColor();
            View childView = getChildAt(ponstion);
            if (childView instanceof LinearLayout) {
                ((ImageView) ((LinearLayout) childView).getChildAt(0)).setSelected(true);
                ((TextView) ((LinearLayout) childView).getChildAt(1)).setTextColor(getContext().getResources().getColor(R.color.common_red));

            }
        }
    }

    /**
     * 重置所有字体颜色
     */
    private void resetTextColor() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof LinearLayout) {
                ((ImageView) ((LinearLayout) childAt).getChildAt(0)).setSelected(false);
                ((TextView) ((LinearLayout) childAt).getChildAt(1)).setTextColor(getContext().getResources().getColor(R.color.xct_lthj_color_closeblack));
            }
        }
    }
}
