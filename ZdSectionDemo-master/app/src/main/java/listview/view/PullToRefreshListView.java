package listview.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import view.niudong.com.demo.R;

public class PullToRefreshListView extends ListView {

	private View headerView;
	private float downY;
	private int headerViewHeight;
	/** 状态：下拉刷新 */
	private static final int STATE_PULL_TO_REFRESH = 0;
	/** 状态：松开刷新 */
	private static final int STATE_RELEASE_REFRESH = 1;
	/** 状态：正在刷新 */
	private static final int STATE_REFRESHING = 2;
	/** 当前状态 */
	private int currentState = STATE_PULL_TO_REFRESH;	// 默认是下拉刷新状态
	private ImageView iv_arrow;
	private ProgressBar progress_bar;
	private TextView tv_state;
	private RotateAnimation upAnim;
	private RotateAnimation downAnim;
	private OnRefreshingListener mOnRefreshingListener;
	private View footerView;
	private int footerViewHeight;
	/** 正在加载更多 */
	private boolean loadingMore;

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}
	
	private void initHeaderView() {
		headerView = View.inflate(getContext(), R.layout.header_view, null);
		iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
		progress_bar = (ProgressBar) headerView.findViewById(R.id.progress_bar);
		showRefreshingProgressBar(false);
		tv_state = (TextView) headerView.findViewById(R.id.tv_state);
		headerView.measure(0, 0);	// 主动触发测量，mesure内部会调用onMeasure
		headerViewHeight = headerView.getMeasuredHeight();
		hideHeaderView();
		super.addHeaderView(headerView);
		upAnim = createRotateAnim(0f, -180f);
		downAnim = createRotateAnim(-180f, -360f);
	}
	
	private void initFooterView() {
		footerView = View.inflate(getContext(), R.layout.footer_view, null);
		footerView.measure(0, 0);// 主动触发测量，mesure内部会调用onMeasure
		footerViewHeight = footerView.getMeasuredHeight();
		hideFooterView();
		super.addFooterView(footerView);
		
		super.setOnScrollListener(new OnScrollListener() {
			
			// 当ListView滚动的状态发生改变的时候会调用这个方法
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE	// ListView处于空闲状态
						&& getLastVisiblePosition() == getCount() - 1	// 界面上可见的最后一条item是ListView中最后的一条item
						&& loadingMore == false							// 如果当前没有去做正在加载更多的事情
						) {
					loadingMore = true;
					showFooterView();
					setSelection(getCount() - 1);
					
					if (mOnRefreshingListener != null) {
						mOnRefreshingListener.onLoadMore();
					}
				}
			}
			
			// 当ListView滚动的时候会调用这个方法
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				
			}
		});
	}

	private void hideFooterView() {
		int paddingTop = -footerViewHeight;
		setFooterViewPaddingTop(paddingTop);
	}
	
	private void showFooterView() {
		int paddingTop = 0;
		setFooterViewPaddingTop(paddingTop);
	}

	private void setFooterViewPaddingTop(int paddingTop) {
		footerView.setPadding(0, paddingTop, 0, 0);
	}

	/**
	 * 设置显示进度的圈圈
	 * @param showProgressBar 如果是true，则显示ProgressBar，否则的话显示箭头
	 */
	private void showRefreshingProgressBar(boolean showProgressBar) {
		progress_bar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
		iv_arrow.setVisibility(!showProgressBar ? View.VISIBLE : View.GONE);
		
		if (showProgressBar) {
			iv_arrow.clearAnimation();	// 有动画的View要清除动画才能真正的隐藏
		}
	}

	/**
	 * 创建旋转动画
	 * @param fromDegrees 从哪个角度开始转
	 * @param toDegrees 转到哪个角度
	 * @return 
	 */
	private RotateAnimation createRotateAnim(float fromDegrees, float toDegrees) {
		int pivotXType = RotateAnimation.RELATIVE_TO_SELF;		// 旋转点的参照物
		int pivotYType = RotateAnimation.RELATIVE_TO_SELF;		// 旋转点的参照物
		float pivotXValue = 0.5f;	// 旋转点x方向的位置
		float pivotYValue = 0.5f;	// 旋转点y方向的位置
		RotateAnimation ra = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
		ra.setDuration(300);
		ra.setFillAfter(true);	// 让动画停留在结束位置
		return ra;
	}

	/** 隐藏HeaderView */
	private void hideHeaderView() {
		int paddingTop = -headerViewHeight;
		setHeaderViewPaddingTop(paddingTop);
	}
	
	/** 显示HeaderView */
	private void showHeaderView() {
		int paddingTop = 0;
		setHeaderViewPaddingTop(paddingTop);
	}

	/**
	 * 设置HeaderView的paddingTop
	 * @param paddingTop
	 */
	private void setHeaderViewPaddingTop(int paddingTop) {
		headerView.setPadding(0, paddingTop, 0, 0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (currentState == STATE_REFRESHING) {
				// 如果当前已经是“正在刷新“的状态了，则不用去处理下拉刷新了
				return super.onTouchEvent(ev);
			}
			
			int fingerMoveDistanceY = (int) (ev.getY() - downY);		// 手指移动的距离
			// 如果是向下滑动，并且界面上可见的第一条item是ListView的索引为0的item时我们才处理下拉刷新的操作
			if (fingerMoveDistanceY > 0 && getFirstVisiblePosition() == 0) {
				int paddingTop = -headerViewHeight + fingerMoveDistanceY;
				setHeaderViewPaddingTop(paddingTop);
				
				if (paddingTop < 0 && currentState != STATE_PULL_TO_REFRESH) {
					// 如果paddingTop小于0，说明HeaderView没有完全显示出来，则进入下拉刷新的状态
					currentState = STATE_PULL_TO_REFRESH;
					tv_state.setText("下拉刷新");
					iv_arrow.startAnimation(downAnim);
					showRefreshingProgressBar(false);
					// 让箭头转一下
				} else if (paddingTop >= 0 && currentState != STATE_RELEASE_REFRESH) {
					// 如果paddingTop>=0，说明HeaderView已经完全显示出来，则进入松开刷新的状态
					currentState = STATE_RELEASE_REFRESH;
					tv_state.setText("松开刷新");
					iv_arrow.startAnimation(upAnim);
					showRefreshingProgressBar(false);
					
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (currentState == STATE_RELEASE_REFRESH) {
				// 如果当前状态是松开刷新，并且抬起了手，则进入正在刷新状态
				currentState = STATE_REFRESHING;
				tv_state.setText("正在刷新");
				showRefreshingProgressBar(true);
				showHeaderView();
				
				if (mOnRefreshingListener != null) {
					mOnRefreshingListener.onRefreshing();
				}
			} else if (currentState == STATE_PULL_TO_REFRESH) {
				// 如果抬起手时是下拉刷新状态，则把HeaderView完成隐藏
				hideHeaderView();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void setOnRefreshingListener(OnRefreshingListener mOnRefreshingListener) {
		this.mOnRefreshingListener = mOnRefreshingListener;
	}
	
	/** ListView刷新的监听器 */
	public interface OnRefreshingListener {
		/** 当ListView可以刷新数据的时候会调用这个方法 */
		void onRefreshing();
		/** 当ListView可以加载更多 的时候会调用这个方法 */
		void onLoadMore();
	}

	/** 联网刷新数据的操作已经完成了 */
	public void onRefreshComplete() {
		hideHeaderView();
		currentState = STATE_PULL_TO_REFRESH;
		showRefreshingProgressBar(false);
	}

	/** 加载更多新数据的操作已经完成了 */
	public void onLoadmoreComplete() {
		hideFooterView();
		loadingMore = false;
	}
}












