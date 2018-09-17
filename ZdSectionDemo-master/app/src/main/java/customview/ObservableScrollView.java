package customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 监听ScrollView的滑动数据
 * Create by: chenwei.li
 * Date: 2017/8/22
 * time: 11:36
 * Email: lichenwei.me@foxmail.com
 */
public class ObservableScrollView extends ScrollView{

    public ObservableScrollView(Context context) {
        this(context,null);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private OnObservableScrollViewScrollChanged mOnObservableScrollViewScrollChanged;

    public void setOnObservableScrollViewScrollChanged(OnObservableScrollViewScrollChanged mOnObservableScrollViewScrollChanged) {
        this.mOnObservableScrollViewScrollChanged = mOnObservableScrollViewScrollChanged;
    }


    public interface OnObservableScrollViewScrollChanged{
        void onObservableScrollViewScrollChanged(ScrollView scrollView,int l, int t, int oldl, int oldt);
    }

    /**
     * @param l Current horizontal scroll origin. 当前滑动的x轴距离
     * @param t Current vertical scroll origin. 当前滑动的y轴距离
     * @param oldl Previous horizontal scroll origin. 上一次滑动的x轴距离
     * @param oldt Previous vertical scroll origin. 上一次滑动的y轴距离
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnObservableScrollViewScrollChanged!=null){
            mOnObservableScrollViewScrollChanged.onObservableScrollViewScrollChanged(this,l,t,oldl,oldt);
        }

        //搜索框在布局最上面
//        line.bringToFront();
//        mScrollview.setScrollViewListener(new CustomView.ScrollViewListener() {
//            @Override
//            public void onScrollChanged(CustomView customView, int x, int y, int oldx, int oldy) {
//                if (y <= 0) {
//                    line.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
//                } else if (y > 0 && y <= imageHeight) {
//                    //获取ScrollView向下滑动图片消失的比例
//                    float scale = (float) y / imageHeight;
//                    //更加这个比例,让标题颜色由浅入深
//                    float alpha = (255 * scale);
//                    // 只是layout背景透明
//                    line.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//                }
//            }
//        });
    }
}
