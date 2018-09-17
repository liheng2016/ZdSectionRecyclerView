package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import utils.DensityUtils;


/**
 * Created by niudong on 2017/6/19.
 * Tel：18811793194
 * 作用：行情首页弹框---雷达五边形图
 */
public class RiskRankView extends View {
    //数据个数
    private int dataCount = 5;
    //每个角的弧度
    private float radian = (float) (Math.PI * 2 / dataCount);
    //雷达图半径   //网格最大半径
    private float radius;
    //中心X坐标
    private int centerX;
    //中心Y坐标
    private int centerY;
    //各维度标题
    public String[] titles;

    //各维度分值
    public float[] data;
    //数据最大值
    private float maxValue;
    //雷达图与标题的间距
    private int radarMargin = DensityUtils.dp2px(getContext(), 18);
    //雷达区画笔
    private Paint mainPaint;
    //数据区画笔
    private Paint valuePaint;

    public void setValuePaint(Paint valuePaint) {
        this.valuePaint = valuePaint;
    }

    //分数画笔
    private Paint scorePaint;
    //标题画笔
    private Paint titlePaint;
    //图标画笔
    private Paint iconPaint;

    private int scoreSize = DensityUtils.dp2px(getContext(), 28);
    //标题文字大小
    private int titleSize = DensityUtils.dp2px(getContext(), 9);

    public RiskRankView(Context context) {
        this(context, null);
    }

    public RiskRankView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RiskRankView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //TODO 五边形边框 内容区域
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(0.8f);
        mainPaint.setColor(Color.BLACK);
        mainPaint.setStyle(Paint.Style.STROKE);
        //TODO 内容区域
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.BLUE);

        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //分数画笔
        scorePaint = new Paint();
        scorePaint.setAntiAlias(true);
        scorePaint.setTextSize(scoreSize);
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextAlign(Paint.Align.CENTER);
        scorePaint.setStyle(Paint.Style.FILL);
        //文字
        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(titleSize);
        titlePaint.setColor(Color.BLACK);
        titlePaint.setStyle(Paint.Style.FILL);

        iconPaint = new Paint();
        iconPaint.setAntiAlias(true);
    }

    /**
     * @param w    屏幕的宽
     * @param h    高
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("tag", "onSizeChanged:w " + w + "height" + h);

        //雷达图半径       控制图形大小    内层图 也是由他决定的
        radius = Math.min(h, w) / 2 * 0.5f;
        //中心坐标
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    //
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawPolygonLine(canvas);
        //  内部单个框
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
        drawTitle(canvas);
    }

    /**
     * 绘制多边形<只有一个边框>
     *
     * @param canvas 画布
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            if (i == 0) {
                path.moveTo(getPoint(i).x, getPoint(i).y);
            } else {
                path.lineTo(getPoint(i).x, getPoint(i).y);
            }
        }

        //闭合路径
        path.close();
        canvas.drawPath(path, mainPaint);
    }


    private void drawPolygonLine(Canvas canvas) {
        Path path = new Path();

        float r = radius / (dataCount);//r是蜘蛛丝之间的间距  分成几份
        for (int i = 1; i <= dataCount; i++) {//中心点不用绘制    从1开始
            float curR = r * i;//当前半径
            path.reset();   //调用绘制
            for (int j = 0; j < dataCount; j++) {
                if (j == 0) {
                    float x = (float) (centerX + curR * Math.sin(radian));
                    float y = (float) (centerY - curR * Math.cos(radian));
                    path.moveTo(x, y);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x1 = (float) (centerX + curR * Math.sin(radian / 2));
                    float y1 = (float) (centerY + curR * Math.cos(radian / 2));
                    path.lineTo(x1, y1);
                    float x2 = (float) (centerX - curR * Math.sin(radian / 2));
                    float y2 = (float) (centerY + curR * Math.cos(radian / 2));
                    path.lineTo(x2, y2);
                    float x3 = (float) (centerX - curR * Math.sin(radian));
                    float y3 = (float) (centerY - curR * Math.cos(radian));
                    path.lineTo(x3, y3);
                    float x4 = (float) (centerX);
                    float y4 = (float) (centerY - curR);
                    path.lineTo(x4, y4);
                    float x = (float) (centerX + curR * Math.sin(radian));
                    float y = (float) (centerY - curR * Math.cos(radian));
                    path.lineTo(x, y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < 5; i++) {
            path.reset();
//            360    576
            path.moveTo(centerX, centerY);
            path.lineTo(getPoint(i).x, getPoint(i).y);
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制覆盖区域
     *
     * @param canvas 画布   根据传入的份数进行绘制覆盖范围
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            //计算百分比
            float percent = data[i] / maxValue;
            int x = getPoint(i, 0, percent).x;
            int y = getPoint(i, 0, percent).y;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        //绘制填充区域的边界
        path.close();
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);

        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        valuePaint.setAlpha(120);
        canvas.drawPath(path, valuePaint);
    }


    /**
     * 绘制分数
     *
     * @param canvas 画布
     */
    private void drawScore(Canvas canvas) {
        int score = 0;
        //计算总分
        for (int i = 0; i < dataCount; i++) {
            score += data[i];
        }
        canvas.drawText(score + "", centerX, centerY + scoreSize / 2, scorePaint);
    }

    /**
     * 绘制标题
     *
     * @param canvas 画布
     */
    private void drawTitle(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = getPoint(i, radarMargin, 1).x;
            int y = getPoint(i, radarMargin, 1).y;


            float titleWidth = titlePaint.measureText(titles[i]);
            if (i == 1) {
            } else if (i == 2) {
                x -= titleWidth;
            } else if (i == 3) {
                x -= titleWidth;
            } else if (i == 4) {
                x -= titleWidth / 2;
            }
            canvas.drawText(titles[i], x, y, titlePaint);
        }
    }

  /*  *//**
     * 绘制图标   万一加图标可以使用这里
     *
     * @param canvas 画布
     *//*
    private void drawIcon(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = getPoint(i, radarMargin, 1).x;
            int y = getPoint(i, radarMargin, 1).y;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
            int iconWidth = bitmap.getWidth();
            int iconHeight = bitmap.getHeight();
            float titleWidth = titlePaint.measureText(titles[i]);

            //上面获取到的x、y坐标是标题左下角的坐标
            //需要将图标移动到标题上方居中位置
            if (i == 0) {
                x += (titleWidth - iconWidth) / 2;
                y -= (iconHeight + getTextHeight(titlePaint));
            } else if (i == 1) {
                x += (titleWidth - iconWidth) / 2;
                y -= (iconHeight / 2 + getTextHeight(titlePaint));
            } else if (i == 2) {
                x -= (iconWidth + (titleWidth - iconWidth) / 2);
                y -= (iconHeight / 2 + getTextHeight(titlePaint));
            } else if (i == 3) {
                x -= (iconWidth + (titleWidth - iconWidth) / 2);
                y -= (iconHeight + getTextHeight(titlePaint));
            } else if (i == 4) {
                x -= iconWidth / 2;
                y -= (iconHeight + getTextHeight(titlePaint));
            }

            canvas.drawBitmap(bitmap, x, y, titlePaint);
        }
    }*/

    /**
     * 获取雷达图上各个点的坐标
     *
     * @param position 坐标位置（右上角为0，顺时针递增）
     * @return 坐标
     */
    private Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }

    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     *
     * @param position    坐标位置
     * @param radarMargin 雷达图与维度标题的间距
     * @param percent     覆盖区的的百分比
     * @return 坐标
     */
    private Point getPoint(int position, int radarMargin, float percent) {
        int x = 0;
        int y = 0;

        if (position == 0) {//  531   520
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 1) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 2) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 3) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 4) {
            x = centerX;
            y = (int) (centerY - (radius + radarMargin) * percent);
        }

        return new Point(x, y);
    }

    /**
     * 获取文本的高度
     *
     * @param paint 文本绘制的画笔
     * @return 文本高度
     */
    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    //设置标题
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    //设置数值
    public void setData(float[] data, String tvName[]) {
        this.data = data;

        this.titles = tvName;


    }

    //设置最大数值
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    //设置蜘蛛网颜色
    public void setMainPaintColor(int color) {
        mainPaint.setColor(color);
    }

    //设置标题颜色
    public void setTextPaintColor(int color) {
        titlePaint.setColor(color);
    }  //设置标题颜色

    //设置覆盖局域颜色

    public void setValuePaintColor(int color) {
        valuePaint.setColor(color);
    }

}
