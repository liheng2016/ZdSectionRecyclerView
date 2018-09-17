package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 实践加上总结思考
 * Created by niudong on 2017/4/20.
 * 自定义View
 * 1、继承已有控件  增强
 * 2、组合已有控件
 * 3、继承View、ViewGroup
 * <p>
 * invalidate 重新绘制  会调用onDraw（）；
 * <p>
 * <p>
 * 4、self
 * onMesure测量
 * onLayout（） 布局    VieweGroup
 * onDraw 绘制
 */


public class ProgressBut extends Button {
    //是否允许进度条
    public boolean isProgressEnable = true;
    public long maxProgress = 100;
    public long currentProgress;
    private Drawable colorDrawable;


    public void setProgressEnable(boolean progressEnable) {
        isProgressEnable = progressEnable;
    }


    public void setMaxProgress(long maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setCurrentProgress(long currentProgress) {
        this.currentProgress = currentProgress;
        //触发onDraw
        invalidate();
    }

    public ProgressBut(Context context) {
        super(context);
    }

    public ProgressBut(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 展示进度条效果
     * 把drawerable.draw 画到 canvas上
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawText("niudong", 20, 20, getPaint());
        //红色

        if (isProgressEnable) {
            if (colorDrawable == null) {
                colorDrawable = new ColorDrawable(Color.RED);
            }
            int Left = 0;
            int Top = 0;

            //TODO  当前值*1.0f /Max= % * getMeasuredWidth()+ .5f
            int Right = (int) (currentProgress * 1.0f / maxProgress * getMeasuredWidth() + .5f);   //画了一半
            int Bottom = getBottom();
            colorDrawable.setBounds(Left, Top, Right, Bottom);
            colorDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }
}
