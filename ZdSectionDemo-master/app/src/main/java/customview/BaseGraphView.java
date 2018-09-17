package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import utils.UIUtils;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.R;


/**
 * @Author niudong
 * @Date 2018/3/1  11:27
 * @Version v1.0.0
 * @Annotation 创建一个图表抽象基类  共性父类处理，特性交给子类
 * <p>
 * 注意事项：
 * 1，图表控件宽高的确定
 * 可以通过获取屏幕的宽高，再减去边距
 * <p>
 * 2，坐标轴上文字的标记
 * 3，标题的位置
 * 由屏幕宽度的一半再减去标题字符串宽度的一半，就得到标题显示在屏幕正中间
 */
public abstract class BaseGraphView extends View {

    //坐标轴画笔
    public Paint mPaint;

    //柱形图的名字
    private String mGrapthTitle;
    //X轴的名字
    private String mXAxisName;
    //Y轴的名字
    private String mYAxisName;
    private int mAxisTextColor;
    private float mAxisTextSize;
    //X坐标轴最大值
    public float maxAxisValueX = 900;
    //X坐标轴刻度线数量
    public int axisDivideSizeX = 9;
    //Y坐标轴最大值
    public float maxAxisValueY = 700;
    //Y坐标轴刻度线数量
    public int axisDivideSizeY = 7;
    //视图宽度
    public int width;
    //视图高度
    public int height;
    //坐标原点位置
    public final int originX = 90;
    //安卓坐标在左上角
    public  int originY = 900;
    //柱状图数据
    public int columnInfo[][];


    /**
     * 虚线的方向
     */
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    /**
     * 默认为水平方向
     */
    public static final int DEFAULT_DASH_ORIENTATION = ORIENTATION_HORIZONTAL;
    /**
     * 间距宽度
     */
    public float dashWidth = 4;
    /**
     * 线段高度
     */
    public float lineHeight = 1;
    /**
     * 线段宽度
     */
    public float lineWidth = 1;


    public BaseGraphView(Context context) {
        this(context, null);
    }

    public BaseGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SunnyGraphStyle);
        mGrapthTitle = typedArray.getString(R.styleable.SunnyGraphStyle_graphTitle);
        mXAxisName = typedArray.getString(R.styleable.SunnyGraphStyle_X_AxisName);
        mYAxisName = typedArray.getString(R.styleable.SunnyGraphStyle_Y_AxisName);
        mAxisTextColor = typedArray.getColor(R.styleable.SunnyGraphStyle_axisTextColor, context.getResources().getColor(android.R.color.black));
        mAxisTextSize = typedArray.getDimension(R.styleable.SunnyGraphStyle_axisTextSize, 14);

        if (typedArray != null) {
            typedArray.recycle();
        }
        initPaint(context);
    }

    private void initPaint(Context context) {
        if (mPaint == null) {
            mPaint = new Paint();
            //去锯齿和防止抖动设置
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
        }
    }

    public void setGrapthTitle(String grapthTitle) {
        mGrapthTitle = grapthTitle;
    }

    public void setXAxisName(String XAxisName) {
        mXAxisName = XAxisName;
    }

    public void setYAxisName(String YAxisName) {
        mYAxisName = YAxisName;
    }

    public void setAxisTextColor(int axisTextColor) {
        mAxisTextColor = axisTextColor;
    }

    public void setAxisTextSize(float axisTextSize) {
        mAxisTextSize = axisTextSize;
    }

    /**
     * 手动设置X轴最大值及等份数
     *
     * @param maxAxisValueX
     * @param dividedSize
     */
    public void setXAxisValue(float maxAxisValueX, int dividedSize) {
        this.maxAxisValueX = maxAxisValueX;
        this.axisDivideSizeX = dividedSize;
    }


    /**
     * 手动设置等份数
     *
     * @param dividedSize
     */
    public void setXAxisValue(int dividedSize) {
        this.axisDivideSizeX = dividedSize;
    }

    /**
     * 手动设置Y轴最大值及等份数
     *
     * @param maxAxisValueY
     * @param dividedSize
     */
    public void setYAxisValue(float maxAxisValueY, int dividedSize) {
        this.maxAxisValueY = maxAxisValueY;
        this.axisDivideSizeY = dividedSize;
    }

    /**
     * 手动设置Y轴最大值及等份数
     */
    public void setYAxisValue(int dividedSize) {
        this.axisDivideSizeY = dividedSize;
    }

    /**
     * 传入柱状图数据
     *
     * @param columnInfo
     */
    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    /**
     * 根据需求进行测试视图宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        width = MeasureSpec.getSize(widthMeasureSpec)  ;
//        height = MeasureSpec.getSize(heightMeasureSpec);

        Log.e(",,,,,,,,,", "width:" + width + ",height:" + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        originY=getHeight()-UIUtils.dip2px(MyApplication.mContext, 100);
        width = getWidth() - originX - UIUtils.dip2px(MyApplication.mContext, 80);//getWidth 是屏幕宽高
        height = getHeight() - UIUtils.dip2px(MyApplication.mContext, 200);// 高度-400
        //画X轴
        drawAxisX(canvas, mPaint);
        drawAxisY(canvas, mPaint);
        //绘制坐标值
        drawAxisScaleMarkX(canvas, mPaint);
        drawAxisScaleMarkY(canvas, mPaint);
        //绘制箭头
        drawAxisArrowsX(canvas, mPaint);
        drawAxisArrowsY(canvas, mPaint);
        //坐标值数据
        drawAxisScaleMarkValueX(canvas, mPaint);
        drawAxisScaleMarkValueY(canvas, mPaint);
        //柱形图
        drawColumn(canvas, mPaint);
        //文字标题
        drawTitle(canvas, mPaint);
        drawLeftToRectLine(canvas, mPaint);
        drawTopTitle(canvas, mPaint);
        drawDetailDailog(canvas, mPaint);

    }

    protected  void drawDetailDailog(Canvas canvas, Paint mPaint){};

    protected void drawTopTitle(Canvas canvas, Paint mPaint) {
    }



    /**
     * 绘制右上角 文字说明
     */
    protected void drawLeftToRectLine(Canvas canvas, Paint mPaint) {

    }

    protected abstract void drawAxisScaleMarkValueY(Canvas canvas, Paint paint);

    protected abstract void drawAxisScaleMarkValueX(Canvas canvas, Paint paint);

    //画底部标题
    private void drawTitle(Canvas canvas, Paint paint) {
        //画标题
        if (mGrapthTitle != null) {
            //设置画笔绘制文字的属性
            mPaint.setColor(mAxisTextColor);
            mPaint.setTextSize(mAxisTextSize);
            mPaint.setFakeBoldText(true);
            //X轴 居中技巧---视图宽度/2-字体/2   Y轴---要在X轴下方，所以偏移100
            canvas.drawText(mGrapthTitle, (getWidth() / 2) - (paint.measureText(mGrapthTitle) / 2), originY + 100, paint);
        }
    }

    //开始 画中间的矩形
    protected abstract void drawColumn(Canvas canvas, Paint paint);

    /**
     * 绘制Y轴的箭头
     */
    private void drawAxisArrowsY(Canvas canvas, Paint paint) {
        //画三角形（Y轴箭头）
        Path mPathX = new Path();
        mPathX.moveTo(originX, originY - height - 30);//起始点坐标
        mPathX.lineTo(originX - 10, originY - height);//下一点 左边
        mPathX.lineTo(originX + 10, originY - height);//下一点 右边
        mPathX.close();  //封闭的
        canvas.drawPath(mPathX, paint);
        canvas.drawText(mYAxisName, originX - 50, originY - height - 50, paint);
    }

    /**
     * X轴上的箭头
     */
    private void drawAxisArrowsX(Canvas canvas, Paint paint) {
        //画三角形（X轴箭头）
        Path mPathX = new Path();
        mPathX.moveTo(originX + width + 30, originY);//起始点坐标
        mPathX.lineTo(originX + width, originY - 10);//下一点 上
        mPathX.lineTo(originX + width, originY + 10);//下一点 下
        mPathX.close();
        canvas.drawPath(mPathX, paint);
        canvas.drawText(mXAxisName, originX + width, originY + 40, paint);
    }

    /**
     * Y轴上的标记
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawAxisScaleMarkY(Canvas canvas, Paint paint);

    /**
     * X轴上的标记
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawAxisScaleMarkX(Canvas canvas, Paint paint);

    protected abstract void drawAxisY(Canvas canvas, Paint paint);

    protected abstract void drawAxisX(Canvas canvas, Paint paint);
}
