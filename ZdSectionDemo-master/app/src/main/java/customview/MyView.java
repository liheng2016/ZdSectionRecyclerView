package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import utils.Logger;
import utils.ToastUtils;
import utils.UIUtils;
import view.niudong.com.demo.MyApplication;

/**
 * 名称：
 * Created by niudong on 2018/1/23.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 * 矩形 左上角的坐标，右下角的坐标
 * http://blog.csdn.net/hard_working1/article/details/50888353
 * https://www.jianshu.com/p/1728b725b4a6
 */
public class MyView extends View implements View.OnClickListener {

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
     * 画笔宽度
     */
    private int strokeWidth;
    private Paint arcPaint;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
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
        rectPaint.setStyle(Paint.Style.STROKE);
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
        arcPaint.setColor(Color.GREEN);
        //设置点击事件
        setOnClickListener(this);


    }

    public void setListener(OnViewClichListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        processValue(wSpecMode, wSpecSize, hSpecSize);
    }

    /**
     * 测量
     */
    private void processValue(int wSpecMode, int wSpecSize, int hSpecSize) {
        if (wSpecMode == MeasureSpec.AT_MOST) {
            Logger.d(Logger.TAG, "Mode" + "......AT_MOST");//wrap_content
            setMeasuredDimension(720, 1280);
        } else if (wSpecMode == MeasureSpec.EXACTLY) {//weight  特定的宽高   match_parent
            Logger.d(Logger.TAG, "Mode" + "......EXACTLY");
            Logger.d(Logger.TAG, "Value" + "...wSpecSize.." + wSpecSize + "...hSpecSize.." + hSpecSize);
        } else if (wSpecMode == MeasureSpec.UNSPECIFIED) {//match_parent
            Logger.d(Logger.TAG, "Mode" + "......UNSPECIFIED");
            Logger.d(Logger.TAG, "Value" + "...wSpecSize.." + wSpecSize + "...hSpecSize.." + hSpecSize);
        }
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
        Rect rectCen = new Rect();
        rectTextPaint.getTextBounds(drawContent, 0, drawContent.length(), rectCen);

        Paint.FontMetrics fontMetrics = rectTextPaint.getFontMetrics();
        Rect rectContent = new Rect(20, 20, screenW / 2 - 60, 400);
        canvas.drawRect(rectContent, rectPaint);
        canvas.drawText(yujian, (screenW / 2 - 40 - rectTextPaint.measureText(yujian)) / 2, 220, rectTextPaint);
        canvas.drawCircle(screenW / 2, screenH / 2 - wuShi, wuShi, rectPaint);
        canvas.drawText(drawContent, screenW / 2 - (rectCen.right + rectCen.left) / 2, screenH / 2 - wuShi, rectTextPaint);
        ToastUtils.showToast(MyApplication.mContext, "..left.." + rectCen.left + "..right.." + rectCen.right +
                "..top.." + rectCen.top + "..bottom.." + rectCen.bottom);

        int X = (int) ((getWidth() - rectTextPaint.measureText(comeGo)) / 2);
        int Y = (int) (getHeight() / 2 + yiBai);
        canvas.drawText(comeGo, X, Y, rectPaint);
        //绘制椭圆
        RectF rectO = new RectF(screenW / 2 + 60, 20, screenW - 20, 400);
        canvas.drawOval(rectO, rectPaint);
        //椭圆左边的坐标点+（屏幕宽-右边距-椭圆左边的坐标点= 椭圆的长度 -文字的宽度）/2
        int oX = (int) (screenW / 2 + 60 + (screenW - 20 - (screenW / 2 + 60) - rectTextPaint.measureText(content)) / 2);
        canvas.drawText(content, oX, 220, rectTextPaint);
        //画圆
//        canvas.drawArc(p);
    }

    @Override
    public void onClick(View v) {
        if (null != listener) {
            listener.clickView();
        }
    }

//    TODO match_parent
//01-23 14:27:46.061 7417-7417/view.niudong.com.demo D/LTHJ: Value...wSpecSize..1080...hSpecSize..3
//01-23 14:27:46.061 7417-7417/view.niudong.com.demo D/LTHJ: Value...wSpecSize..1080...hSpecSize..1716
//01-23 14:27:46.131 7417-7417/view.niudong.com.demo D/LTHJ: Value...wSpecSize..1080...hSpecSize..3
//01-23 14:27:46.131 7417-7417/view.niudong.com.demo D/LTHJ: Value...wSpecSize..1080...hSpecSize..1716

//    01-23 14:30:54.764 15549-15549/view.niudong.com.demo D/LTHJ: AT_MOST
//01-23 14:30:54.764 15549-15549/view.niudong.com.demo D/LTHJ: Value...wSpecSize..1080...hSpecSize..1716
//            01-23 14:30:54.834 15549-15549/view.niudong.com.demo D/LTHJ: AT_MOST
//01-23 14:30:54.834 15549-15549/view.niudong.com.demo D/LTHJ: Value...wSpecSize..1080...hSpecSize..1716

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
}
