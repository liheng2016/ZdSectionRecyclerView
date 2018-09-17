package pullrefresh;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 下拉刷新的布局  整合了头+内容区域
 * Created by 牛栋 on 2018/5/13.
 */
public class XctRefreshLayout extends PtrFrameLayout {
    private OnRefreshListener mListener;

    public XctRefreshLayout(Context context) {
        super(context);
        init();
    }

    public XctRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XctRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        XctRefreshHeader mXctHeader = new XctRefreshHeader(getContext()) {
            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
                super.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
                if (null != mListener) {
                    float factor = ptrIndicator.getCurrentPercent();
                    mListener.onPullDownPositionChange(factor);
                }
            }
        };
        setHeaderView(mXctHeader);
        addPtrUIHandler(mXctHeader);

    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
        setPtrHandler(listener);
    }

    public interface OnRefreshListener extends PtrHandler {
        void onPullDownPositionChange(float factor);
    }

    /**
     * 下拉刷新接口, 点击加载更多接口
     */
    public abstract static class DefaultRefreshListener extends PtrDefaultHandler
            implements OnRefreshListener {
        @Override
        public void onPullDownPositionChange(float factor) {
        }
    }
}
