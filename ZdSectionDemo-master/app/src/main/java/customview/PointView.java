package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import utils.UIUtils;
import view.niudong.com.demo.R;


public class PointView extends View {

	/**
	 * 画布工具
	 */
	private Paint pointPaint;
	private int mCenterTextColor = 20;
	/**
	 * 控件的宽度和高度
	 */
	private int viewHeight;
	private int viewWidth;

	public PointView(Context context) {
		super(context);
		init(context);
	}

	public PointView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PointView);
		mCenterTextColor = ta.getColor(R.styleable.PointView_PointViewColor, 20);
		ta.recycle();
	}

	private void init(Context context) {
		viewWidth = UIUtils.dip2px(context, 7);
		viewHeight = UIUtils.dip2px(context, 7);
		pointPaint = new Paint();
		pointPaint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(viewWidth, viewHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawCircle(canvas);
	}

	public void setParame(int color) {
		this.mCenterTextColor = color;
		invalidate();
	}

	/**
	 * 是否填充
	 * @param color
	 * @param isFill
     */
	public void setParame(int color,boolean isFill) {
		this.mCenterTextColor = color;
		pointPaint.setStyle(isFill? Paint.Style.FILL:Paint.Style.STROKE);
		invalidate();
	}

	private void drawCircle(Canvas canvas) {
		pointPaint.setColor(mCenterTextColor);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, pointPaint);
	}

}
