package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import utils.ColorUtils;
import utils.ToastUtils;
import utils.UIUtils;
import view.niudong.com.demo.MyApplication;

/**
 * 名称：
 * Created by niudong on 2018/6/13.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 * <p>
 * <p>
 * <p>
 * TODO  如果在View 中使用wrap_content（AT_MOST），那么 需要自己实现测量方法，如下
 * https://www.jianshu.com/p/c84693096e41
 * https://blog.csdn.net/baidu_34928905/article/details/79017089  viewgroup
 */
public class TestCustomView extends View implements View.OnClickListener{
    public TestCustomView(Context context) {
        this(context, null);
    }

    public TestCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(ColorUtils.COLOR_RED);

        setOnClickListener(this);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //得到宽值和 测量模式
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//
        //得到高的
        int height = MeasureSpec.getSize(heightMeasureSpec);//得到宽
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//测量模式
        //设置最终的结果值
        setMeasuredDimension(getWidthResult(width, widthMode), getHeightResult(height, heightMode));
    }

    /**
     * 得到测量的宽
     */
    private int getWidthResult(int width, int widthMode) {
        int resultWidth = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            resultWidth = width;//采用用户自己设定的
        } else {
            //设定默认值
            resultWidth = UIUtils.dip2px(MyApplication.mContext, 200);//给个默认高度
            if (widthMode == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(width, resultWidth);
            }
        }
        return resultWidth;
    }

    /**
     * 得到测量的高
     */
    private int getHeightResult(int height, int heightMode) {
        int resultHeight = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
            resultHeight = height;//采用用户自己设定的
        } else {
            //设定默认值
            resultHeight = UIUtils.dip2px(MyApplication.mContext, 200);//给个默认高度
            if (heightMode == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(height, resultHeight);
            }
        }
        return resultHeight;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onClick(View view) {
        ToastUtils.showToast(MyApplication.mContext,"click 矩形");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}
