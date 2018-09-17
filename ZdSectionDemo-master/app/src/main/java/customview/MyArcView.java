package customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import utils.Logger;
import utils.ToastUtils;
import utils.UIUtils;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/1/23.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 * 矩形 左上角的坐标，右下角的坐标
 * http://blog.csdn.net/hard_working1/article/details/50888353
 * https://www.jianshu.com/p/1728b725b4a6
 * setMeasuredDimension(viewWidth, viewHeight);//设置View 的实际宽高
 */
public class MyArcView extends View implements View.OnClickListener {

    private OnViewClichListener listener;
    private Paint rectTextPaint;
    private Paint rectPaint;
    private int screenW;
    private int screenH;
    private int yiBai;
    private int wuShi;
    private Context mContext;
    String drawContent = "NiuDong";
    String content = "让优秀成为一种习惯";
    String comeGo = "Dream,Go ........";
    String yujian = "2018！遇见更好的自己";
    /**
     * 周长比例
     */
    private float rate;

    /**
     * 选择的颜色：1表示红，2表示绿
     */
    private int choseColor;

    /**
     * 画笔宽度
     */
    private int strokeWidth;
    private Paint arcPaint;

    /**
     * 半径
     */
    private int radius;
    private int x;
    private int y;
    private int image;

    public MyArcView(Context context) {
        this(context, null);
    }

    public MyArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * 初始化View
     */
    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        //矩形画笔
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setColor(Color.RED);
        rectPaint.setTextSize(60);
        rectPaint.setFakeBoldText(true);
        rectPaint.setAlpha(200);
        //测量相关
        screenW = UIUtils.getScreenW(context);
        screenH = UIUtils.getScreenH(context);
        yiBai = UIUtils.dip2px(context, 100);
        wuShi = UIUtils.dip2px(context, 50);
        strokeWidth = UIUtils.dip2px(getContext(), 4);
        //文字画笔初始化
        rectTextPaint = new Paint();
        rectTextPaint.setAntiAlias(true);
        rectTextPaint.setColor(Color.WHITE);
        rectTextPaint.setTextSize(40);
        rectTextPaint.setFakeBoldText(true);
        rectTextPaint.setStyle(Paint.Style.STROKE);
        //Arc 圆弧
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(strokeWidth);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置点击事件
        setOnClickListener(this);
        image = R.drawable.ic_aq_audio;


    }

    public void setListener(OnViewClichListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int result = height > width ? width : height;
        radius = (result - strokeWidth * 2) / 2;//（周长-笔宽*2）/2=半径
        x = radius + strokeWidth;
        y = radius + strokeWidth;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(Logger.TAG, "down");
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                Logger.d(Logger.TAG, "move   x" + x + "...y..." + y);
                break;
            case MotionEvent.ACTION_UP:
                Logger.d(Logger.TAG, "up");
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackCircle(canvas);
        drawArc(canvas);
        drawDrable(canvas);
    }

    private void drawDrable(Canvas canvas) {
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), image);
        canvas.drawBitmap(bmp, radius, radius, arcPaint);
    }

    private void drawArc(Canvas canvas) {
        if (choseColor == 1) {//红色
            arcPaint.setColor(Color.RED);
        } else {
            arcPaint.setColor(Color.GREEN);
        }
        RectF rectF = new RectF(strokeWidth, strokeWidth, strokeWidth + radius * 2, strokeWidth + radius * 2);
        canvas.drawArc(rectF, -90, 360 * rate, false, arcPaint);
    }

    private void drawBackCircle(Canvas canvas) {
        arcPaint.setColor(Color.GRAY);
        canvas.drawCircle(x, y, radius, arcPaint);
    }

    @Override
    public void onClick(View v) {
        if (null != listener) {
            listener.clickView();
        }
    }


    public interface OnViewClichListener {
        void clickView();
    }


    /**
     * 获得一个比例的高度
     *
     * @return
     */
    public int getPerHeight() {
        Paint.FontMetrics fm = rectTextPaint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent) + 2;
    }


    /**
     * 设置前面的圆弧的比例
     *
     * @param rate
     */
    public void setRateAndImage(float rate, int icon) {
        this.image = icon;
        if (rate < 0) {
            this.rate = Math.abs(rate);
            choseColor = 2;

        } else {
            if (rate > 1) {
                rate = 1;
            }
            this.rate = rate;
            choseColor = 1;
        }
        invalidate();
    }

}
