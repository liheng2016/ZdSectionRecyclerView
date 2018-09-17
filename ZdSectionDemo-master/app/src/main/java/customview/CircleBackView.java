package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;

import utils.UIUtils;


public class CircleBackView extends View {
    /**
     * 周长比例
     */
    private float rate;
    /**
     * 半径
     */
    private int radius;
    /**
     * 画笔宽度
     */
    private int strokeWidth;

    /**
     * 中心点坐标
     */
    private float centerX, centerY;

    private Paint paint;

    private Canvas mCanvas;

    private int red, green, gray;

    /**
     * 选择的颜色：1表示红，2表示绿,0灰色
     */
    private int choseColor;

    public CircleBackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CircleBackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleBackView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokeWidth = UIUtils.dip2px(getContext(), 4);
        red = Color.parseColor("#ff827b");
        green = Color.parseColor("#01b59c");
        gray = Color.parseColor("#EAEAEA");
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int viewWidth = height > width ? width : height;
        radius = (viewWidth - strokeWidth * 2) / 2;
        centerX = radius + strokeWidth;
        centerY = radius + strokeWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        drawBackCircle();
        drawFrountArc();
    }

    private void drawFrountArc() {
        if (choseColor == 1) {//红色
            paint.setColor(red);
        } else if (choseColor == 2) {
            paint.setColor(green);
        } else {
            paint.setColor(gray);

        }
        RectF oval = new RectF(strokeWidth, strokeWidth, radius * 2 + strokeWidth, radius * 2 + strokeWidth);
        mCanvas.drawArc(oval, -90, 360 * rate, false, paint);
    }

    private void drawBackCircle() {
        paint.setColor(gray);
        mCanvas.drawCircle(centerX, centerY, radius, paint);
    }

    /**
     * 设置前面的圆弧的比例
     *
     * @param rate
     */
    public void setRate(float value, float rate) {
        if (value == 0) {
            choseColor = 0;
        } else {
            if (value > 0) {
                choseColor = 1;
            } else {
                choseColor = 2;
            }
        }
        if (rate == 0) {
            this.rate = 0;
        } else {
            if (rate > 1) {
                rate = 1;
            }
            this.rate = rate;
        }
        invalidate();
    }

}
