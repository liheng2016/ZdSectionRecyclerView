package customview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 
 * 能购自定属性播放帧图片的ImageView
 * @author Administrator
 */
public class FrameImage extends ImageView {
	public FrameImage(Context context, AttributeSet attrs) {
		super(context, attrs);

		AnimationDrawable animationDrawable = (AnimationDrawable) this.getDrawable();  
        animationDrawable.start();  
	}
	
	
}
