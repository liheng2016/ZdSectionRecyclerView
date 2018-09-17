package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 在做之前思考，分析并罗列计算规则。
 *
 * @Author niudong
 * @Date 2018/3/1  11:25
 * @Version v1.0.0
 * @Annotation
 */
public class ColumnView extends BaseGraphView {

    private boolean isClick;
    private List<PostionMode> modes;
    private int clickPostion;


    public ColumnView(Context context) {
        super(context);
    }

    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 绘制Y轴上的刻度值
     */
    @Override
    protected void drawAxisScaleMarkValueY(Canvas canvas, Paint paint) {
        //高度/份数 得到比例哦
        paint.setTextSize(18);
        float cellHeight = height / axisDivideSizeY;
        float cellValue = maxAxisValueY / axisDivideSizeY;
        //从第一个绘制 数据
        for (int i = 1; i < axisDivideSizeY; i++) {
//            canvas.drawText(String.valueOf(cellValue * i), originX - 80, originY - cellHeight * i + 10, paint);
            //TODO  原理  Y轴 坐标= Y轴原点-比例*当前数量
            canvas.drawText(String.valueOf(i), originX - 30, originY - cellHeight * i, paint);
        }
    }

    /**
     * 绘制X轴上的刻度值
     */
    @Override
    protected void drawAxisScaleMarkValueX(Canvas canvas, Paint paint) {
        //设置画笔绘制文字的属性
        paint.setColor(Color.GRAY);
        paint.setTextSize(18);
        paint.setFakeBoldText(true);
        float cellWidth = width / axisDivideSizeX;
        float cellValue = maxAxisValueX / axisDivideSizeX;
        int postion = 0;
        for (int i = 1; i < axisDivideSizeX; i++) {
            if (0 == i % 2) continue;
            postion++;
            String data = "第" + String.valueOf(postion) + "季度";
//            canvas.drawText(String.valueOf(cellValue * i), cellWidth * i + originX - 35, originY + 30, paint);
            canvas.drawText(data, cellWidth * i + originX + cellWidth / 2 - paint.measureText(data) / 2, originY + 30, paint);
        }
    }

    /**
     *
     */
    @Override
    protected void drawColumn(Canvas canvas, Paint paint) {
        if (columnInfo == null)
            return;
        modes = new ArrayList<>();
        float cellWidth = width / axisDivideSizeX;
        int postion = 0;
        for (int i = 0; i < columnInfo.length; i++) {
            paint.setColor(columnInfo[i][1]);
            if (0 != i % 2) continue;
            postion++;
            float leftTopY = originY - height * columnInfo[i][0] / maxAxisValueY;
            float left = originX + cellWidth * (i + 1);
            float right = originX + cellWidth * (i + 2);
            float bottom = originY - 2;
            //左上角x,y右下角x,y，画笔
            canvas.drawRect(left, leftTopY, right, bottom, mPaint);
            PostionMode mode = new PostionMode();
            mode.left = left;
            mode.top = leftTopY;
            mode.right = right;
            mode.bottom = bottom;
            mode.clickPostion = postion - 1;
            mode.value = columnInfo[i][0];
            modes.add(mode);
        }
    }

    /**
     * 刻度
     */
    @Override
    protected void drawAxisScaleMarkY(Canvas canvas, Paint paint) {
        float cellHeight = height / axisDivideSizeY;
        for (int i = 0; i < axisDivideSizeY - 1; i++) {
            canvas.drawLine(originX, (originY - cellHeight * (i + 1)), originX + 10, (originY - cellHeight * (i + 1)), paint);
        }
    }

    /**
     * 刻度
     */
    @Override
    protected void drawAxisScaleMarkX(Canvas canvas, Paint paint) {
        float cellWidth = width / axisDivideSizeX;
        for (int i = 0; i < axisDivideSizeX - 1; i++) {
            canvas.drawLine(cellWidth * (i + 1) + originX, originY,
                    cellWidth * (i + 1) + originX, originY - 10, paint);
        }
    }

    @Override
    protected void drawAxisY(Canvas canvas, Paint paint) {
        //画竖轴(Y)
        canvas.drawLine(originX, originY+2, originX, originY - height, paint);//参数说明：起始点左边x,y，终点坐标x,y，画笔

    }

    @Override
    protected void drawAxisX(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        //设置画笔宽度
        paint.setStrokeWidth(5);
        //设置画笔抗锯齿
        paint.setAntiAlias(true);
        //画横轴(X)
        canvas.drawLine(originX, originY, originX + width, originY, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int startX = 0;
        int lastX = 0;
        int diffX = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                lastX = (int) event.getX();
                diffX = lastX - startX;
                isClick = false;
                startX = lastX;
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (isClick) {
                    for (int i = 0; i < modes.size(); i++) {
                        PostionMode mode = modes.get(i);
                        float right = mode.right;
                        clickPostion = mode.clickPostion;
                        float left = mode.left;
                        float bottom = mode.bottom;
                        float top = mode.top;
                        if (left <= upX && upX <= right && bottom >= upY && upY >= top) {
                            Log.d("test", isClick + "位置" + clickPostion);
                            invalidate();
                            break;
                        }

                    }
                }
                break;
        }
        return true;
    }


    static class PostionMode {
        public float left;
        public float right;
        public float top;
        public float bottom;
        public int clickPostion;
        public int value;
    }

    /**
     * 绘制头部提示语
     */
    @Override
    protected void drawTopTitle(Canvas canvas, Paint mPaint) {
        if (null != modes && modes.size() > 0 && null != modes.get(clickPostion)) {
            PostionMode mode = modes.get(clickPostion);
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(14);
            float right = mode.right;
            float left = mode.left;
            float bottom = mode.bottom;
            float top = mode.top;
            String topValue = mode.value + "万元";
            float measureText = mPaint.measureText(topValue);
            float xResult = (left + (right - left) / 2) - measureText / 2;
            canvas.drawText(topValue, xResult, top - 30, mPaint);
        }
    }

    @Override
    protected void drawLeftToRectLine(Canvas canvas, Paint mPaint) {
//        mPaint.setColor(Color.BLUE);
//        mPaint.setStrokeWidth(1f);
//        //设置虚线...
//        mPaint.setPathEffect(new DashPathEffect(new float[]{3, 2}, 0));
//        if (null != modes && modes.size() > 0) {
//            for (int i = 0; i < modes.size(); i++) {
//                PostionMode mode = modes.get(i);
//                float right = mode.right;
//                float left = mode.left;
//                float bottom = mode.bottom;
//                float top = mode.top;
//                //绘制一条直线
//                canvas.drawLine(originX, top + 1, left, top, mPaint);
//            }
//        }
    }


    /**
     * 绘制详细信息
     */
    @Override
    protected void drawDetailDailog(Canvas mCanvas, Paint mPaint) {
//        Paint  textPaint;
//        //获得流入与流出
//        String inDishStr ="99";
//        String outDishStr = "88";
//        textPaint= mPaint;
//        //外盘
//        String key1 = "外盘(流入): ";
//        String val1 = outDishStr;
//        //间隔
//        String space="  ";
//        //内盘
//        String key2 = "内盘(流出): ";
//        String val2 = inDishStr;
//
//        //计算显示文字宽度、高度
//        String content = space+key1+val1+space+key2+val2+space;
//        float textWidth = textPaint.measureText(content);
//        float textHeight =  textPaint.descent() -  textPaint.ascent();
//        //获得选中的矩形区域,中点X
//        Rect selectRect = rects.get(position).rect;
//        int width = selectRect.right-selectRect.left;
//        int midX = selectRect.left+(width/2);
//
//        RectF rf = new RectF();
//        rf.left= midX -textWidth/2;
//        if(rf.left < viewPadding){
//            rf.left = viewPadding;
//        }
//        rf.right = rf.left+textWidth;
//        if(rf.right > mViewWidth){
//            rf.right = mViewWidth -viewPadding ;
//            rf.left =  rf.right -textWidth -viewPadding;
//        }
//
//        rf.top = startY-UIUtils.dip2px(context, 8);
//        rf.bottom = rf.top+2*textHeight;
//
//        //绘制圆半径
//        float radios = rf.bottom-rf.top;
//        //矩形圆
//        RectF drawRF = new RectF();
//        drawRF.left = rf.left + radios/2;
//        drawRF.right = rf.right -radios/2;
//        drawRF.top = rf.top;
//        drawRF.bottom = rf.bottom;
//
//        //添加阴影
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
//        textPaint.setShadowLayer(5, 0, 0, Color.parseColor("#666666"));
//        textPaint.setStyle(Paint.Style.FILL);
//        textPaint.setColor(Color.WHITE);
//        mCanvas.drawRect(drawRF, textPaint);
//
//        //左边圆
//        mCanvas.drawCircle(rf.left+radios/2, rf.top+radios/2, radios/2, textPaint);
//        //右边圆
//        mCanvas.drawCircle(rf.right-radios/2, rf.top+radios/2, radios/2, textPaint);
//
//        textPaint.setShadowLayer(0, 0, 0, 0);
//        //需要矩形填掉右半边圆阴影
//        RectF fillRightR = new RectF();
//        fillRightR.left = drawRF.right-radios/2-UIUtils.dip2px(context, 3);
//        fillRightR.top  = rf.top;
//        fillRightR.right = drawRF.right;
//        fillRightR.bottom = rf.bottom;
//        mCanvas.drawRect(fillRightR, textPaint);
//
//        //需要矩形填掉左半边圆阴影
//        RectF fillLeftR = new RectF();
//        fillLeftR.left = drawRF.left;
//        fillLeftR.top  = rf.top;
//        fillLeftR.right = drawRF.left+radios/2+UIUtils.dip2px(context, 3);
//        fillLeftR.bottom = rf.bottom;
//        mCanvas.drawRect(fillLeftR, textPaint);
//
//        textPaint.setShadowLayer(5, 0, 0, Color.parseColor("#666666"));
//        //绘制三角形
//        Rect r = rects.get(position).rect;
//        float startX = (r.left+r.right)/2;
//        float startY = rf.bottom+textHeight/2;
//        float centerX = startX-textHeight/3;
//        float centerY = rf.bottom;
//        float endX = startX+textHeight/3;
//        float endY = rf.bottom;
//        Path path = new Path();
//        path.moveTo(startX, startY);
//        path.lineTo(centerX, centerY-2);
//        path.lineTo(endX, endY-2);
//        path.close();
//        textPaint.setColor(Color.WHITE);
//        textPaint.setStyle(Paint.Style.FILL);
//        mCanvas.drawPath(path, textPaint);
//        textPaint.setShadowLayer(0, 0, 0, 0);
//
//        //绘制三角形后,有需要填充掉突出部分
//        Rect  fillR = new Rect();
//        fillR.bottom = (int) rf.bottom;
//        fillR.left = (int) (startX-textHeight/2);
//        fillR.right = (int) (startX+textHeight/2);
//        fillR.top = fillR.bottom - UIUtils.dip2px(context, 3);
//        textPaint.setColor(Color.WHITE);
//        mCanvas.drawRect(fillR, textPaint);
//
//        //绘制文字
//        float spaceWidth = textPaint.measureText(space);
//        float startTxtX = rf.left+spaceWidth;
//        float startTxtY = rf.bottom-textHeight+UIUtils.dip2px(context, 5);
//        //外盘
//        textPaint.setColor(Color.parseColor("#666666"));
//        mCanvas.drawText(key1, startTxtX,startTxtY , textPaint);
//        startTxtX+= textPaint.measureText(key1);
//        //外盘值
//        textPaint.setColor(Color.parseColor(ColorUtils.RED));
//        mCanvas.drawText(val1, startTxtX,startTxtY, textPaint);
//        startTxtX+= textPaint.measureText(val1)+spaceWidth;
//        //内盘
//        textPaint.setColor(Color.parseColor("#666666"));
//        mCanvas.drawText(key2, startTxtX,startTxtY, textPaint);
//        startTxtX+= textPaint.measureText(key2);
//        //内盘值
//        textPaint.setColor(Color.parseColor(ColorUtils.GREEN));
//        mCanvas.drawText(val2, startTxtX,startTxtY, textPaint);
//
//        textPaint.setColor(Color.parseColor("#666666"));


}    }

