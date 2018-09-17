package customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 能设置是否可以划动的 viewpager 
 * @author Administrator
 *
 */
public class CanScrollViewPager extends ViewPager{

	public CanScrollViewPager(Context context) {
		super(context);
		this.setFadingEdgeLength(0);
		
	}
	/**
	 * 默认不能滑动。
	 */
	private boolean enabled;
	 

    public CanScrollViewPager(Context context, AttributeSet attrs) {
         super(context, attrs);
         this.enabled = false;
         this.setFadingEdgeLength(0);
     }
 
//触摸没有反应就可以了
     @Override
     public boolean onTouchEvent(MotionEvent event) {
         if (this.enabled) {
             return super.onTouchEvent(event);
         }
   
         return false;
     }
 

    @Override
     public boolean onInterceptTouchEvent(MotionEvent event) {
         if (this.enabled) {
             return super.onInterceptTouchEvent(event);
         }
  
         return false;
     }
  
     public void setPagingEnabled(boolean enabled) {
         this.enabled = enabled;
     }
 
}
