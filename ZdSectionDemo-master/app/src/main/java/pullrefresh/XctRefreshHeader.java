package pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import utils.DateUtils;
import view.niudong.com.demo.R;


/**
 * Xct 下拉刷新头部
 * * Created by 牛栋 on 2018/5/13.
 */
public class XctRefreshHeader extends FrameLayout implements PtrUIHandler {
	private static final String TAG = XctRefreshHeader.class.getSimpleName();

	private ImageView mHeaderArrowView;
	private TextView mHeaderTextView;
	private TextView mHeaderViewDateView;
	private RotateAnimation mRotateOTo180Animation;
	private RotateAnimation mRotate180To0Animation;

	public XctRefreshHeader(Context context) {
		super(context);
		init(context);
	}


	public XctRefreshHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public XctRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		View view = View.inflate(context, R.layout.pulldown_header_new, null);
		//清除背景色
		view.setBackgroundResource(0);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(view, params);

		mHeaderArrowView = (ImageView) view.findViewById(R.id.pulldown_header_arrow);
		mHeaderTextView = (TextView) view.findViewById(R.id.pulldown_header_text);
		mHeaderViewDateView = (TextView) view.findViewById(R.id.pulldown_header_date);
		mHeaderViewDateView.setVisibility(VISIBLE);


		// 图片旋转动画
		mRotateOTo180Animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateOTo180Animation.setDuration(250);
		mRotateOTo180Animation.setFillAfter(true);
		mRotate180To0Animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotate180To0Animation.setDuration(250);
		mRotate180To0Animation.setFillAfter(true);
	}

	@Override
	public void onUIReset(PtrFrameLayout frame) {
//		Logger.i(TAG, "onUIReset: ");
	}

	/**
	 * 准备刷新，Header 将要出现时调用
	 */
	@Override
	public void onUIRefreshPrepare(PtrFrameLayout frame) {
//		Logger.i(TAG, "onUIRefreshPrepare: " + frame.isPullToRefresh());
		mHeaderViewDateView.setText("最后更新："+ DateUtils.getNowTime());
		mHeaderTextView.setText("下拉刷新");
	}

	@Override
	public void onUIRefreshBegin(PtrFrameLayout frame) {
//		Logger.i(TAG, "onUIRefreshBegin: ");
		Animation mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.pulldown_icon);
		LinearInterpolator lin = new LinearInterpolator();
		mAnimation.setInterpolator(lin);
		mHeaderArrowView.clearAnimation();
		mHeaderArrowView.startAnimation(mAnimation);
		mHeaderTextView.setText("正在刷新");
	}

	@Override
	public void onUIRefreshComplete(PtrFrameLayout frame) {
//		Logger.i(TAG, "onUIRefreshComplete: ");
		mHeaderArrowView.clearAnimation();
		mHeaderTextView.setText("刷新成功");
		mHeaderViewDateView.setVisibility(View.VISIBLE);
		mHeaderViewDateView.setText( "最后更新："+DateUtils.getNowTime());
	}

	@Override
	public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
		final int mOffsetToRefresh = frame.getOffsetToRefresh();
		final int currentPos = ptrIndicator.getCurrentPosY();
		final int lastPos = ptrIndicator.getLastPosY();

//		Logger.i(TAG, "onUIPositionChange offset:" + mOffsetToRefresh + " cur:" + currentPos + " last:" + lastPos);
		if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {//回退
			if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
				mHeaderTextView.setText("下拉刷新");
				mHeaderArrowView.clearAnimation();
				mHeaderArrowView.startAnimation(mRotateOTo180Animation);
			}
		} else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) { //下拉, 超过界限
			if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
				mHeaderTextView.setText("释放刷新" +
						"");
				mHeaderArrowView.clearAnimation();
				mHeaderArrowView.startAnimation(mRotate180To0Animation);
			}
		}
	}
}
