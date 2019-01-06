package customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import view.niudong.com.demo.R;


/**
 * RecyclerView 上拉加载更多
 */
public class LoadMoreWrapper extends Adapter<ViewHolder> {
    private static final String TAG = LoadMoreWrapper.class.getSimpleName();

    private static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private Adapter<ViewHolder> mInnerAdapter;
    private OnLoadMoreListener mOnLoadMoreListener;
    private LinearLayoutManager mLayoutManager;
    private Context mContext;
    /**
     * 正在加载更多中
     */
    private boolean mIsLoading;
    private View mFooterView;
    /**
     * 是否开启上拉加载更多
     */
    private boolean mIsEnable = false;
    private TextView mTxtLoadMore;
    private ProgressBar mPbLoadMore;
    private RecyclerView mRecyclerView;


    public LoadMoreWrapper(@NonNull Adapter<ViewHolder> adapter) {
        mInnerAdapter = adapter;
    }


    @Override
    public int getItemCount() {
        int itemCount = mInnerAdapter.getItemCount();
        if (0 != itemCount) {
            itemCount += (isEnableLoadMore() ? 1 : 0);
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMoreView(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            return new ViewHolder(getLoadMoreView(inflater, parent)) {
            };
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onBindViewHolder(holder, position, null);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (!isLoadMoreView(position)) {
            mInnerAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new EndlessScrollListener());
            mContext = recyclerView.getContext();

            mRecyclerView = recyclerView;
            mInnerAdapter.registerAdapterDataObserver(mObserver);
        } else {
            Log.i(TAG, "onScrolled: 现只支持线性布局");
        }
    }


    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
    }


    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    /**
     * 加载更多完成
     */
    public void loadMoreComplete() {
        loadMoreComplete(mIsEnable);
    }

    /**
     * 加载更多完成
     *
     * @param hasMore 是否还有更多
     */
    public void loadMoreComplete(boolean hasMore) {
        if (mIsLoading) {
            mIsLoading = false;
            onLoadMoreComplete(mFooterView);

            mRecyclerView.scrollBy(0, -mFooterView.getHeight());
        }
        setLoadMoreEnable(hasMore);
    }

    public void setLoadMoreEnable(boolean enable) {
        if (mIsEnable != enable) {
            mIsEnable = enable;

            if(mInnerAdapter.getItemCount() > 0) {
                if (enable) {
                    notifyItemInserted(mInnerAdapter.getItemCount());
                } else {
                    loadMoreComplete(false);
                    notifyItemRemoved(mInnerAdapter.getItemCount());
                }
            }
        }
    }

    private View getLoadMoreView(LayoutInflater inflater, ViewGroup parent) {
        if (null == mFooterView) {
            View view = inflater.inflate(R.layout.view_load_more, parent, false);

            mTxtLoadMore = (TextView) view.findViewById(R.id.tv_load_more_msg);
            mPbLoadMore = (ProgressBar) view.findViewById(R.id.pb_load_more_doing);
            mFooterView = view;
        }
        return mFooterView;
    }

    private void onLoadMoreStart(View footerView) {
        mTxtLoadMore.setVisibility(View.INVISIBLE);
        mPbLoadMore.setVisibility(View.VISIBLE);
    }

    private void onLoadMoreComplete(View footerView) {
        mTxtLoadMore.setText("上拉加载");
        mTxtLoadMore.setVisibility(View.VISIBLE);
        mPbLoadMore.setVisibility(View.INVISIBLE);
    }

    private boolean isEnableLoadMore() {
        return mIsEnable;
    }


    private boolean isLoadMoreView(int position) {
        return isEnableLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    public interface OnLoadMoreListener {
        /**
         * 加载更多
         */
        void onLoadMore();
    }

    /**
     * 滑动监听
     */
    private class EndlessScrollListener extends RecyclerView.OnScrollListener {
        /**
         * 状态的更改, 即使item的数量不够铺满列表, 该方法也会被调用, 但onScrolled不会执行.
         */
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
//            Logger.d(TAG, "onScrollStateChanged() newState = [" + newState + "]");

            if (RecyclerView.SCROLL_STATE_IDLE == newState) {

                int itemCount = mLayoutManager.getItemCount();
                if (itemCount > 1 && isEnableLoadMore() && !mIsLoading) { //列表为空,或者为1(错误提示)
                    int lastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                    if (lastPosition == itemCount - 1) { //加载更多
                        mIsLoading = true;
                        //动画
                        onLoadMoreStart(mFooterView);

                        //回调
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            Logger.d(TAG, "onScrolled() dx = [" + dx + "], dy = [" + dy + "]");
        }
    }

    /**
     * 内部adapter更改数据时
     */
    private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition, toPosition);
        }
    };
}