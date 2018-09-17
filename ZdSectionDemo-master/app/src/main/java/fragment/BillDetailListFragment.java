package fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import base.BaseLazyFragment;
import view.niudong.com.demo.R;
import utils.ToastUtils;

/**
 * TODO 注意：两个BaseFragment都是可以实现懒加载数据的
 */
public class BillDetailListFragment extends BaseLazyFragment {
    private static final String TAG = BillDetailListFragment.class.getName();
    public String mouth;
    //    private View view;
    private RecyclerView news_recycler;
    /***/
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mouth = getArguments().getString("mouth", "");
        }
        mouth = TextUtils.isEmpty(mouth) ? "" : mouth;
    }

    public static BillDetailListFragment newInstance(String mouth) {
        BillDetailListFragment f = new BillDetailListFragment();
        Bundle args = new Bundle();
        args.putString("mouth", mouth);
        f.setArguments(args);
        return f;
    }


    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.news_content_frag, container, false);
        news_recycler = (RecyclerView) view.findViewById(R.id.news_recycler);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        return view;
    }





    @Override
    protected void lazyData() {
        super.lazyData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        news_recycler.setLayoutManager(layoutManager);
        String[] strings = {mouth};
        NewsAdapter adapter = new NewsAdapter(strings);
        news_recycler.setAdapter(adapter);
        mProgressBar.setVisibility(View.GONE);
        Log.d(TAG, "lazyData: " + mouth);
        ToastUtils.showToast(getContext(), mouth);
    }

    @Override
    protected void initData() {

    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private String[] mContent;

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView newsTitleText;

            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_titleName);
            }
        }

        public NewsAdapter(String[] content) {
            mContent = content;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_detail, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.newsTitleText.setText(mContent[position] + "\n" +
                    "\n" +
                    "答：我们发月账单时会有通知推送给你，尽量提示你看到。如果收到账单后没有及时补缴，" +
                    "将暂停收取该理财师的新锦囊。等补缴后会即时恢复新锦囊的接受，同时也会收到最近7天的历史锦囊。\n" +
                    "\n" +
                    "如果一直都没有补缴，需补缴的欠款会挪到下一个月的账单。当然数据会重新计算，因为随着行情的变化，你的需" +
                    "补缴金额可能越来越多，也可能越来越少。" + "\n" + "\n" + "\n" + "TestFragment+ViewPager+LazyLoad");
        }

        @Override
        public int getItemCount() {
            return mContent.length;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
