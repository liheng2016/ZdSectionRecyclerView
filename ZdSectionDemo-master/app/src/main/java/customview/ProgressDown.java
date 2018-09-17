package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/4/21.
 */


public class ProgressDown extends LinearLayout {
    //是否允许进度条
    public boolean isProgressEnable = true;
    public long maxProgress = 100;
    public long currentProgress;
    private ImageView ivIcon;
    private Paint paint;

    public void setProgressEnable(boolean progressEnable) {
        isProgressEnable = progressEnable;
    }

    public void setMaxProgress(long maxProgress) {
        this.maxProgress = maxProgress;


    }

    public void setCurrentProgress(long currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }

    private TextView tvDownLoad;

    /**
     * 设置图片
     *
     * @param resId
     */
    public void setIvIcon(int resId) {
        ivIcon.setImageResource(resId);
    }

    /**
     * 设置文字
     *
     * @param content
     */
    public void setDLText(String content) {
        tvDownLoad.setText(content);
    }

    public ProgressDown(Context context) {
        this(context, null);
    }

    public ProgressDown(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressDown);

        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ProgressDown_image_color:

                    int color = typedArray.getColor(attr, 0x11111);
                    break;
                case R.styleable.ProgressDown_image_ground:
                    Drawable drawable = typedArray.getDrawable(attr);
                    break;
            }
        }
        typedArray.recycle();
//        typedArray.getDrawable(R.styleable.pb_color_background);
//        typedArray.getColor(R.styleable.pb_color_color, 0x11111);


        //把当前inflate对象挂在到当前控件上来
        View inflate = View.inflate(context, R.layout.inflate_progressview, this);

        ivIcon = (ImageView) inflate.findViewById(R.id.ivIcon);
        tvDownLoad = (TextView) inflate.findViewById(R.id.tvNote);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//绘制背景

//    noDraw(canvas);Test
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (isProgressEnable) {
            if (paint == null) {
                paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2);
                paint.setAntiAlias(true);//消除锯齿
            }
            //和矩形宽高一样
            int Left = ivIcon.getLeft();
            int Top = ivIcon.getTop();
            int Right = ivIcon.getRight();
            int Bottom = ivIcon.getBottom();
            RectF oval = new RectF(Left, Top, Right, Bottom);
            float startAngle = -90;
            //动态计算扫过的角度
            float sweepAnge = currentProgress * 1.0f / maxProgress * 360;
            canvas.drawArc(oval, startAngle, sweepAnge, false, paint);

        }
    }

    private void noDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);//消除锯齿

        canvas.drawText("nijdong", 20, 20, paint);
    }
}
