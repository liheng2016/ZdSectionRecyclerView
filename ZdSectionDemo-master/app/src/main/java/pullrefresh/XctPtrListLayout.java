package pullrefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import view.niudong.com.demo.R;

/**
 * 使用时直接调用这里
 * 下拉刷新 RecyclerView - 具有默认的错误提示布局
 * <p>
 * Created by 牛栋 on 2018/2/24.
 */
public class XctPtrListLayout extends XctPtrLayout {
	private RecyclerView mRecyclerView;

	public XctPtrListLayout(Context context) {
		super(context);
		init();
	}

	public XctPtrListLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public XctPtrListLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Override
	protected void onFinishInflate() {
		int childCount = getChildCount();
		if (1 == childCount) { //只有下拉加载的头
			//添加RecyclerView
			mRecyclerView = new RecyclerView(getContext());
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			mRecyclerView.setLayoutParams(params);
			mRecyclerView.setId(R.id.rv_ptr_list);

			mRecyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
			addView(mRecyclerView);
		} else if (2 == childCount) { //加载头与实际内容
			//
			mRecyclerView = (RecyclerView) findViewById(R.id.rv_ptr_list);
			if (null == mRecyclerView) {
				throw new RuntimeException("必须指定RecyclerView的id为R.id.rv_ptr_list");
			}
		}
		super.onFinishInflate();
	}

	private void init() {

	}

	public RecyclerView getRecyclerView() {
		return mRecyclerView;
	}


	public abstract static class SimpleRefreshListener extends PtrDefaultHandler
			implements OnRefreshListener {

		@Override
		public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
			if (frame instanceof XctPtrListLayout) {
				RecyclerView view = ((XctPtrListLayout) frame).getRecyclerView();
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, view, header);
			}
			return super.checkCanDoRefresh(frame, content, header);
		}

		@Override
		public void onPullDownPositionChange(float factor) {
		}
	}
}
