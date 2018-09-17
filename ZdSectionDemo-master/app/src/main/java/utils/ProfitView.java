package utils;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by niudong on 2017/11/2.
 */
public class ProfitView extends View {
    private static final java.lang.String TAG = ProfitView.class.getSimpleName();
    /**
     * 圈的颜色
     */
    private int mDefaultColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 当前进度
     */
    private int mProgress = 0;
    /**
     * 圆心点的坐标
     */
    private float mProfitCircleCoordinateX;
    private float mProfitCircleCoordinateY;

    /**
     * 圆的半径
     */
    private float radius;
    /**
     * 圆中心文字
     */
    private int mCenterTextSize;
    private int mBottomTextSize;
    private int mNormalTextSize;

    /**
     * 绘制圆点距圆的距离
     */
    private float point1X;
    private float point2X;
    private float point3X;
    private float point4X;
    /**
     * 点半径
     */
    private float pointRadius;

    /**
     * 绘制虚线距离圆
     */
    private float lineMaringTop;
    /**
     * 线的绘制起始高度
     */
    private float lineInitialHeight;
    /**
     * 线的宽度
     */
    private float mLineWidth;

    /**
     * 总累计收益
     */
    private float mTotalIncomeTextStr;

    /**
     * 已清仓收益
     */
    private float mClearTextStr;
    /**
     * 其他收益
     */
    private float mOtherTextStr;
    /**
     * 累计收益
     */
    private float mTotalAddTextStr;

    /**
     * 持仓收益
     */
    private float mPositionTextStr;
    /**
     * 已实现收益
     */
    private float mRealizeTextStr;

    /**
     * 已清仓颜色
     */
    private int mClearColor;
    /**
     * 已实现收益颜色
     */
    private int mTotalAddColor;
    /**
     * 其它收益颜色
     */
    private int mOtherColor;
    private float mEffectiveIncomeRate;
    /**
     * 画笔
     */
    private Paint mPaint;//
    private Paint mPointPaint;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Paint mBottomPaint;
    private Paint mNormalTextPaint;
    private Paint mProfitPaint;
    private FontMetrics fontMetrics;
    /**
     * 矩形
     */
    private Rect rect;
    /**
     * 矩形
     */
    private RectF oval = new RectF();
    /**
     * 最大高度
     */
    private int rectMaxHeight;
    /**
     * 最小高度
     */
    private int rectMinHeight;
    /**
     * 柱子宽度
     */
    private int rectWidth;

    /**
     * 中心线
     */
    protected float xBaseLine;
    private int yTop;
    private int yDown;
    private boolean isFinishAnimation;
    /**
     * 边距
     */
    private int marginBottom;
    /**
     * 左边距
     */
    private int marginLeft;
    /**
     * 宽度为屏幕宽度
     */
    private int viewWidth;
    private int viewHeight;


    /**
     * 中心文字颜色
     */
    private int mCenterColor;
    /**
     * 底板文字颜色
     */

    private int mRedColor = Color.parseColor("#f1453b");
    private int mGreenColor = Color.parseColor("#38c4a9");
    private int mGrayColor = Color.parseColor("#bfbfbf");
    private int mTextColor = Color.parseColor("#666666");

    /**
     * 矩形的圆角半径
     */
    private float rectRadius;

    private Context mContext;
    String clearText = "实际累计收益";//已清仓收益
    //        String totalAddText = "累计持仓收益";
    String totalAddText = "买入汇率差金额";
    String otherText = "卖出汇率差金额";//其他收益

    public ProfitView(Context context) {

        super(context);
        init(context);
    }

    public ProfitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 作为view创建的时候用
     *
     * @param context
     * @param mPositionTextStr
     * @param mRealizeTextStr
     */
    public ProfitView(Context context, float mPositionTextStr, float mRealizeTextStr) {
        super(context);
        this.mPositionTextStr = mPositionTextStr;
        this.mRealizeTextStr = mRealizeTextStr;
        init(context);
    }

    /**
     * {0累计收益，1实际累计收益，买入汇率差金额，3卖出汇率差金额，4持仓收益，5已实现收益}
     * * 动态设置参数       播放动画
     *
     * @param mTotalIncomeTextStr 0累计收益
     * @param mEffectiveIncome    1实际累计收益
     * @param mBuyRateDiffAmout   买入汇率差金额
     * @param mSellRateDiffAmout  3卖出汇率差金额
     * @param mPositionTextStr
     * @param mRealizeTextStr
     */
    public void setParam(float mTotalIncomeTextStr,
                         float mEffectiveIncome,
                         float mBuyRateDiffAmout,
                         float mSellRateDiffAmout,
                         float mPositionTextStr,
                         float mRealizeTextStr) {

        this.mTotalIncomeTextStr = mTotalIncomeTextStr;
        this.mClearTextStr = mEffectiveIncome;
        this.mTotalAddTextStr = mBuyRateDiffAmout;
        this.mOtherTextStr = mSellRateDiffAmout;
        this.mPositionTextStr = mPositionTextStr;
        this.mRealizeTextStr = mRealizeTextStr;
        setColorAndBottomSize();
        //播放动画的条件
        mProgress = 0;
        xBaseLine = point1X;
        yTop = (int) (mProfitCircleCoordinateY - marginBottom);
        yDown = (int) (mProfitCircleCoordinateY + marginBottom);
        isFinishAnimation = false;
        dynamicDraw();
    }


    /**
     * {0累计收益，1实际累计收益，买入汇率差金额，3卖出汇率差金额，4持仓收益，5已实现收益}
     * * 动态设置参数       播放动画
     *
     * @param mTotalIncomeTextStr 0累计收益
     * @param mEffectiveIncome    1实际累计收益
     * @param mBuyRateDiffAmout   买入汇率差金额
     * @param mSellRateDiffAmout  3卖出汇率差金额
     */
    public void setHktParam(float mTotalIncomeTextStr,
                            float mEffectiveIncome,
                            float mEffectiveIncomeRate,
                            float mBuyRateDiffAmout,
                            float mSellRateDiffAmout) {
        this.mTotalIncomeTextStr = mTotalIncomeTextStr;
        this.mClearTextStr = mEffectiveIncome;
        this.mTotalAddTextStr = mBuyRateDiffAmout;
        this.mOtherTextStr = mSellRateDiffAmout;
        this.mEffectiveIncomeRate = mEffectiveIncomeRate;
        setColorAndBottomSize();
        //播放动画的条件
        mProgress = 0;
        xBaseLine = point1X;
        yTop = (int) (mProfitCircleCoordinateY - marginBottom);
        yDown = (int) (mProfitCircleCoordinateY + marginBottom);
        isFinishAnimation = false;
        dynamicDraw();
    }

    /**
     * 动态设置参数                不播放动画
     *
     * @param mPositionTextStr
     * @param mRealizeTextStr
     */

    public void setParamsNotAnimation(float mTotalIncomeTextStr,
                                      float mClearTextStr,
                                      float mTotalAddTextStr,
                                      float mOtherTextStr,
                                      float mPositionTextStr,
                                      float mRealizeTextStr) {
        this.mTotalIncomeTextStr = mTotalIncomeTextStr;
        this.mClearTextStr = mClearTextStr;
        this.mTotalAddTextStr = mTotalAddTextStr;
        this.mOtherTextStr = mOtherTextStr;
        this.mPositionTextStr = mPositionTextStr;
        this.mRealizeTextStr = mRealizeTextStr;
        setColorAndBottomSize();
        //不播放动画的条件
        mProgress = 360;
        xBaseLine = point3X;
        yTop = (int) (mProfitCircleCoordinateY - marginBottom - rectMaxHeight);
        yDown = (int) (mProfitCircleCoordinateY + marginBottom + rectMaxHeight);
        isFinishAnimation = true;
        invalidate();
    }

    /**
     * 根据值设置颜色
     */
    private void setColorAndBottomSize() {
        //需要输入mPositionTextStr和mRealizeTextSt和rmTotalAddTextStr
        //mTotalAddTextStr=mPositionTextStr+mRealizeTextStr;

        //设置收累计益颜色
        if (mTotalIncomeTextStr > 0) {
            mDefaultColor = Color.parseColor("#ff827b");
        } else if (mTotalIncomeTextStr < 0) {
            mDefaultColor = mGreenColor;
        } else {
            mDefaultColor = mGrayColor;
        }
        //设置清仓收益颜色
        if (mClearTextStr > 0) {
            mClearColor = mRedColor;
        } else if (mClearTextStr < 0) {
            mClearColor = mGreenColor;
        } else {
            mClearColor = mGrayColor;
        }
        //设置累计持仓收益
        if (mTotalAddTextStr > 0) {
            mTotalAddColor = mRedColor;
        } else if (mTotalAddTextStr < 0) {
            mTotalAddColor = mGreenColor;
        } else {
            mTotalAddColor = mGrayColor;
        }

        //设置其他收益
        if (mOtherTextStr > 0) {
            mOtherColor = mRedColor;
        } else if (mOtherTextStr < 0) {
            mOtherColor = mGreenColor;
        } else {
            mOtherColor = mGrayColor;
        }

        mBottomPaint = new Paint(); // 圆环内底部文字
        mBottomPaint.setAntiAlias(true);
        // mBottomPaint.setColor(mDefaultColor);//--------------------------
        if (mTotalAddTextStr > 0) {
            mBottomPaint.setColor(mRedColor);
        }
        //动态调整圈内下部字体大小
        rect = new Rect();
        radius = UIUtils.dip2px(mContext, 45);//圆环的直径
        mBottomPaint.setTextSize(mBottomTextSize);
        mBottomPaint.getTextBounds(mTotalIncomeTextStr + "", 0, (mTotalIncomeTextStr + "").length(), rect);
        fontMetrics = mBottomPaint.getFontMetrics();
        //动态调整textView size
        while (rect.width() >= radius * 2) {
            mBottomTextSize -= 1;
            mBottomPaint.setTextSize(mBottomTextSize);
            mBottomPaint.getTextBounds(mTotalIncomeTextStr + "", 0, (mTotalIncomeTextStr + "").length(), rect);
        }
    }

    private void init(Context context) {
        mContext = context;
        //假数据可删
        mPositionTextStr = 10000;
        mRealizeTextStr = -1000;
        mTotalAddTextStr = mPositionTextStr + mRealizeTextStr;
        mClearTextStr = 50000;
        mOtherTextStr = 2000;
        mTotalIncomeTextStr = mTotalAddTextStr + mClearTextStr + mOtherTextStr;

        setColorAndBottomSize();

        //设置普通字颜色
        mCenterColor = Color.parseColor("#666666");
        //设置字体大小
        mCenterTextSize = UIUtils.dip2px(mContext, 12);
        mBottomTextSize = UIUtils.dip2px(mContext, 10);
        mNormalTextSize = UIUtils.dip2px(mContext, 9);


        //设置视图宽高 固定值
        viewWidth = UIUtils.getScreenW(mContext);
        viewHeight = UIUtils.dip2px(mContext, 180);
        //圆环
        mCircleWidth = UIUtils.dip2px(mContext, 5);//圆环宽度
        //radius=UIUtils.dip2px(mContext, 50);//圆环的直径
        marginLeft = UIUtils.dip2px(mContext, 15);
        mProfitCircleCoordinateX = radius + mCircleWidth; //圆心坐标  -----------------------圆心坐标---------------------圆心坐标
        mProfitCircleCoordinateY = viewHeight / 2;
        mPaint = new Paint();
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        //mPaint.setColor(mDefaultColor); // 设置圆环的颜色
        //设置居中
        int margin1 = UIUtils.dip2px(mContext, 10);//小点定位距离，后期方便改
        int margin2 = UIUtils.dip2px(mContext, 45);
        int margin3 = UIUtils.dip2px(mContext, 65);
        int margin4 = UIUtils.dip2px(mContext, 65);
        rectWidth = UIUtils.dip2px(mContext, 26);//矩形的宽度
        int doubleMarginleft = (int) (viewWidth - radius * 2 - margin1 - margin2 - margin3 - margin4 - rectWidth);
        mProfitCircleCoordinateX += doubleMarginleft / 2;

        //画点  第一个小点
        point1X = mProfitCircleCoordinateX + radius + mCircleWidth / 2 + margin1;// 小点定位坐标-----------------------------小点定位坐标
        point2X = point1X + margin2;
        point3X = point2X + margin3;
        point4X = point3X + margin4;
        pointRadius = UIUtils.dip2px(mContext, 2);//小点半径
        mPointPaint = new Paint();
        mPaint.setAntiAlias(true);
        //画线
        mLinePaint = new Paint();
        mLinePaint.setColor(mGrayColor);
        //画矩形
        rectMaxHeight = UIUtils.dip2px(mContext, 70);//矩形的最大高度
        rectMinHeight = UIUtils.dip2px(mContext, 5);//矩形的最小高度

        mProfitPaint = new Paint();
        mProfitPaint.setAntiAlias(true);
        //画文字
        mTextPaint = new Paint(); // 圆环内上部文字
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mCenterColor);

//		 mBottomPaint=new Paint(); // 圆环内底部文字
//		 mBottomPaint.setAntiAlias(true);
//		// mBottomPaint.setColor(mDefaultColor);//--------------------------
//		 if(mTotalAddTextStr>0){
//			 mBottomPaint.setColor(mRedColor);
//		 }
//		//动态调整圈内下部字体大小
//		 rect=new Rect();
//		 mBottomPaint.setTextSize(mBottomTextSize);
//		 mBottomPaint.getTextBounds(mTotalAddTextStr+"", 0, (mTotalAddTextStr+"").length(), rect);
//			fontMetrics = mBottomPaint.getFontMetrics();
//			//动态调整textView size
//			while(rect.width()>=radius*2){
//				mBottomTextSize-=1;
//				mBottomPaint.setTextSize(mBottomTextSize);
//				mBottomPaint.getTextBounds(mTotalAddTextStr+"", 0, (mTotalAddTextStr+"").length(), rect);
//			}

        mNormalTextPaint = new Paint();//其他文字的大小
        mNormalTextPaint.setAntiAlias(true);
        mNormalTextPaint.setTextSize(mNormalTextSize);

        //设置的间隔8dp
        marginBottom = UIUtils.dip2px(mContext, 8);

        //播放动画的条件
        mProgress = 0;
        xBaseLine = point1X;
        yTop = (int) (mProfitCircleCoordinateY - marginBottom);
        yDown = (int) (mProfitCircleCoordinateY + marginBottom);
        isFinishAnimation = false;

        rectRadius = UIUtils.dip2px(mContext, 3);

        //不播放动画的条件 使用的时候调节
//		 mProgress=360;
//		 xBaseLine=point3X;
//		 yTop=(int)  (mProfitCircleCoordinateY - marginBottom-rectMaxHeight);
//		 yDown=(int) (mProfitCircleCoordinateY + marginBottom+rectMaxHeight);
//		 isFinishAnimation=true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.d(TAG, "onMeasure....执行了.....widthMeasureSpec== " + widthMeasureSpec + "...heightMeasureSpec==" + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //viewWidth=getMeasuredWidth();
        setMeasuredDimension(viewWidth, viewHeight);
        Logger.d(TAG, "onMeasure....执行了.....viewWidth== " + viewWidth + "...viewHeight==" + viewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.d(TAG, "onlayout....执行了");
        dynamicDraw();
    }

    private void dynamicDraw() {
        new Timer().scheduleAtFixedRate(new myTimeTask(), 0, 20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d(TAG, "onDraw....执行了");
        drawCircle(canvas);//画圆环
        drawPointAndLine(canvas);//画线、点
        drawRect(canvas);//画柱图
        if (isFinishAnimation) {//画文字
            drawCenterText(canvas);
            drawNormarlText(canvas);
            drawDownText(canvas);
        }
    }

    /**
     * 绘制底部文字
     *
     * @param canvas
     */
    private void drawDownText(Canvas canvas) {


    }

    /**
     * 绘制其他文字
     *
     * @param canvas
     */
    private void drawNormarlText(Canvas canvas) {
        //mPositionTextStr,mRealizeTextStr,mNormalTextPaint
        //mClearTextStr,mTotalAddTextStr，mOtherTextStr
        //2，3,4 个点的位置进行画文字
        drawtext(clearText, mClearTextStr, mClearColor, point2X, canvas, true);
        drawtext(totalAddText, mTotalAddTextStr, mTotalAddColor, point3X, canvas, false);

        //绘制其他收益
        drawtext(otherText, mOtherTextStr, mOtherColor, point4X, canvas, false);
    }

    /**
     * 绘制
     */
    private void drawtext(String text, float textStr, int selfColor, float pointX, Canvas canvas, boolean isSupportShowRate) {
        mTextPaint.setTextSize(mNormalTextSize);
        Rect norRec = new Rect();
        mNormalTextPaint.getTextBounds(MyUtil.convertF2UnitF1(textStr), 0, MyUtil.convertF2UnitF1(textStr).length(), norRec);
        //获取到值文字的宽和高
        int width1 = norRec.right - norRec.left;
        int height1 = norRec.bottom - norRec.top;

        mNormalTextPaint.getTextBounds(text, 0, text.length(), norRec);
        //获取到文字的宽
        int width2 = norRec.right - norRec.left;
        float resultValueX = pointX - width2 / 2;//点的中间画文字
        //间距处理
        int vauleX = UIUtils.dip2px(mContext, 12);
        int vaule2X = UIUtils.dip2px(mContext, 8);
        if (TextUtils.equals(text, clearText)) {
            resultValueX = resultValueX - vauleX;
            Logger.d(TAG, text + ".....valueX...." + resultValueX);
        } else if (TextUtils.equals(text, totalAddText)) {
            resultValueX = resultValueX - vaule2X;
            Logger.d(TAG, text + ".....valueX...." + resultValueX);
        } else if (TextUtils.equals(text, otherText)) {
            Logger.d(TAG, text + ".....valueX...." + resultValueX);
        }
        if (textStr < 0) {
            mNormalTextPaint.setColor(selfColor);
            //收益率
            if (isSupportShowRate) {
                //实际累计收益
                canvas.drawText(MyUtil.convertF2UnitF1(textStr), point1X, mProfitCircleCoordinateY - height1 - marginBottom, mNormalTextPaint);
                canvas.drawText(MyUtil.getValue(MyUtil.decimalbeforeAddTwoWord(mEffectiveIncomeRate), true, true), point2X, mProfitCircleCoordinateY - height1 - marginBottom, mNormalTextPaint);
            } else {
                canvas.drawText(MyUtil.convertF2UnitF1(textStr), pointX - width1 / 2, mProfitCircleCoordinateY - height1 - marginBottom, mNormalTextPaint);
            }
            canvas.drawText(text, resultValueX, mProfitCircleCoordinateY - 2 * height1 - 2 * marginBottom, mTextPaint);
            mNormalTextPaint.setColor(mTextColor);
        } else {
            mNormalTextPaint.setColor(selfColor);
            //收益率
            if (isSupportShowRate) {
                canvas.drawText(MyUtil.convertF2UnitF1(textStr), point1X, mProfitCircleCoordinateY + height1 + marginBottom, mNormalTextPaint);
                canvas.drawText(MyUtil.convertF2UnitF1(mEffectiveIncomeRate), point2X, mProfitCircleCoordinateY + height1 + marginBottom, mNormalTextPaint);
            } else {
                canvas.drawText(MyUtil.convertF2UnitF1(textStr), pointX - width1 / 2, mProfitCircleCoordinateY + height1 + marginBottom, mNormalTextPaint);
            }
            canvas.drawText(text, resultValueX, mProfitCircleCoordinateY + 2 * height1 + 2 * marginBottom, mTextPaint);
            mNormalTextPaint.setColor(mTextColor);
        }
    }

    /**
     * 绘制圆中心的字
     */
    private void drawCenterText(Canvas canvas) {
        mTextPaint.setTextSize(mCenterTextSize);
        //绘制累计收益
        String centerText = "累计收益";
        Rect rectCen = new Rect();
        mTextPaint.getTextBounds(centerText, 0, centerText.length(), rectCen);
        float x = mProfitCircleCoordinateX - (rectCen.right - rectCen.left) / 2;
        float y = mProfitCircleCoordinateY - (rectCen.bottom - rectCen.top) / 2;
        canvas.drawText(centerText, x, y, mTextPaint);
        //绘制累计收益的值
        Rect rectCenButtom = new Rect();
        mBottomPaint.getTextBounds(MyUtil.convertF2UnitF1(mTotalIncomeTextStr), 0, MyUtil.convertF2UnitF1(mTotalIncomeTextStr).length(), rectCenButtom);
        mBottomPaint.setColor(mDefaultColor);
        x = mProfitCircleCoordinateX - (rectCenButtom.right - rectCenButtom.left) / 2;
        //值距累计收益的距离
        y = mProfitCircleCoordinateY + (rectCenButtom.bottom - rectCenButtom.top);
        canvas.drawText(MyUtil.convertF2UnitF1(mTotalIncomeTextStr), x, y, mBottomPaint);

    }

    /**
     * 画矩形
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        float maxValue = calculateMaxValue();
        //实际累计收益
        if (mClearTextStr < 0) {
            drawNegtiveRect(canvas, maxValue, mClearTextStr, point2X);//画负柱子
        } else {
            drawPositiveRect(canvas, maxValue, mClearTextStr, point2X);//画正柱子
        }
        //马计入汇率差金额
        if (mTotalAddTextStr < 0) {
            drawNegtiveRect(canvas, maxValue, mTotalAddTextStr, point3X);//画负柱子
        } else {
            drawPositiveRect(canvas, maxValue, mTotalAddTextStr, point3X);//画正柱子
        }
        //卖出汇率差金额
        if (mOtherTextStr < 0) {
            drawNegtiveRect(canvas, maxValue, mOtherTextStr, point4X);//画负柱子
        } else {
            drawPositiveRect(canvas, maxValue, mOtherTextStr, point4X);//画正柱子
        }

    }

    /**
     * 绘制负柱子
     *
     * @param canvas
     * @param maxValue
     * @param value
     * @param x
     */
    private void drawNegtiveRect(Canvas canvas, float maxValue, float value, float x) {
        value = Math.abs(value);
        float bili = value / maxValue;
        float top;
        float bottom;
        if (bili < 0.05) {
            top = mProfitCircleCoordinateY + marginBottom;
            bottom = top + rectMinHeight;
        } else {
            top = mProfitCircleCoordinateY + marginBottom;
            bottom = top + bili * rectMaxHeight;
        }
        if (yDown <= bottom) {
            bottom = yDown;
        }

        RectF rect = new RectF(x - rectWidth / 2, top, x + rectWidth / 2, bottom);
        RectF rect2 = new RectF(x - rectWidth / 2, top, x + rectWidth / 2, top + rectRadius);
        mProfitPaint.setColor(mGreenColor);
        if (rect.bottom - rect.top <= rectRadius) {
            canvas.drawRect(rect, mProfitPaint);
        } else {
            canvas.drawRoundRect(rect, rectRadius, rectRadius, mProfitPaint);
            canvas.drawRect(rect2, mProfitPaint);
        }
    }

    /**
     * 绘制正柱子，为0 的操作放到正柱子了
     *
     * @param canvas
     * @param maxValue
     * @param value
     * @param x
     */
    private void drawPositiveRect(Canvas canvas, float maxValue, float value, float x) {
        float bili, bottom, top;
        if (maxValue == 0) { //都为0的情况
            bottom = mProfitCircleCoordinateY - marginBottom;
            top = bottom - rectMinHeight;
            mProfitPaint.setColor(mGrayColor);
        } else {
            bili = value / maxValue;
            if (value == 0) { // 一个为0 的情况
                bottom = mProfitCircleCoordinateY - marginBottom;
                top = bottom - rectMinHeight;
                mProfitPaint.setColor(mGrayColor);
            } else if (bili < 0.05) { // 比例过小的情况
                bottom = mProfitCircleCoordinateY - marginBottom;
                top = bottom - rectMinHeight;
                mProfitPaint.setColor(mRedColor);
            } else {    //一般情况
                bottom = mProfitCircleCoordinateY - marginBottom;
                top = bottom - bili * rectMaxHeight;
                mProfitPaint.setColor(mRedColor);
            }
        }
        if (yTop >= top) {
            top = yTop;
        }

        RectF rect = new RectF(x - rectWidth / 2, top, x + rectWidth / 2, bottom);
        RectF rect2 = new RectF(x - rectWidth / 2, bottom - rectRadius, x + rectWidth / 2, bottom);
        if (rect.bottom - rect.top <= rectRadius) {
            canvas.drawRect(rect, mProfitPaint);
        } else {
            canvas.drawRoundRect(rect, rectRadius, rectRadius, mProfitPaint);
            canvas.drawRect(rect2, mProfitPaint);
        }
    }

    /**
     * 画点
     *
     * @param canvas
     */
    private void drawPointAndLine(Canvas canvas) {
        //canvas.drawLine(point1X, mProfitCircleCoordinateY, point3X, mProfitCircleCoordinateY, mLinePaint);//画线
        if (xBaseLine < point3X) {//1,2,3
            //画点 X     Y轴坐标4个点事一致的
            canvas.drawLine(point1X, mProfitCircleCoordinateY, xBaseLine, mProfitCircleCoordinateY, mLinePaint);//画线
        } else {
            canvas.drawLine(point1X, mProfitCircleCoordinateY, point4X, mProfitCircleCoordinateY, mLinePaint);//画线
            mPointPaint.setColor(mGrayColor);
            canvas.drawCircle(point1X, mProfitCircleCoordinateY, pointRadius, mPointPaint); // 画圆点
            mPointPaint.setColor(mClearColor);
            canvas.drawCircle(point2X, mProfitCircleCoordinateY, pointRadius, mPointPaint); // 画圆点
            mPointPaint.setColor(mTotalAddColor);
            canvas.drawCircle(point3X, mProfitCircleCoordinateY, pointRadius, mPointPaint); // 画圆点
            mPointPaint.setColor(mOtherColor);
            canvas.drawCircle(point4X, mProfitCircleCoordinateY, pointRadius, mPointPaint); // 画圆点
        }

    }

    /**
     * 画圆环
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
//        canvas.drawCircle(mProfitCircleCoordinateX, mProfitCircleCoordinateY, radius, mPaint); // 画出圆环
        mPaint.setColor(mDefaultColor);// 设置圆环的颜色
        //动态描圆环   实际上是按照 矩形 来处理的 对角线
        oval.set(mProfitCircleCoordinateX - radius, mProfitCircleCoordinateY - radius, mProfitCircleCoordinateX + radius, mProfitCircleCoordinateY + radius);
        // 根据进度画圆弧
        //canvas.drawArc(oval, 90, mProgress, false, mPaint);
        canvas.drawArc(oval, 0, mProgress, false, mPaint);
    }


    private float calculateMaxValue() {

        return Math.abs(mTotalAddTextStr) > Math.abs(mClearTextStr) ? (Math.abs(mTotalAddTextStr) > Math.abs(mOtherTextStr) ?
                Math.abs(mTotalAddTextStr) : Math.abs(mOtherTextStr)) : (Math.abs(mClearTextStr) > Math.abs(mOtherTextStr) ? Math.abs(mClearTextStr) : Math.abs(mOtherTextStr));
    }

    class myTimeTask extends TimerTask {
        @Override
        public void run() {

            if (mProgress <= 360) {//先做圆圈转动
                mProgress += 10;
                postInvalidate();
            } else if (xBaseLine <= point3X) {//绘线
                xBaseLine += 20;
                postInvalidate();
            } else if (yTop >= (mProfitCircleCoordinateY - marginBottom - rectMaxHeight)
                    || yDown <= (mProfitCircleCoordinateY + marginBottom + rectMaxHeight)) {//绘柱子
                yDown += 10;
                yTop -= 10;
                //Log.e("tag",yTop+"Top");
                //Log.e("tag",yDown+"Down");
                postInvalidate();
            } else if (!isFinishAnimation) {//绘字
                isFinishAnimation = true;
                postInvalidate();
            } else {
                cancel();
            }
        }
    }

}
