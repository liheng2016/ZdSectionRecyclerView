package recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import customview.BannerView;
import customview.DrawableTextView;

import utils.HkStockUtil;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/6/9.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class MainPageItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected OnItemClickListener mItemClickListener;
    private List<String> datas;

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private Context mContext;

    private static final int TYPE_HEAD = 0;  //顶部headView
    private static final int TYPE_ITEM = 2;  //普通Item View

    public MainPageItemAdapter(List<String> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            MainPageItemAdapter.ItemViewHolder itemViewHolder = new MainPageItemAdapter.ItemViewHolder(view);
            return itemViewHolder;
        } else if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_head_view, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            MainPageItemAdapter.HeadViewHolder headViewHolder = new MainPageItemAdapter.HeadViewHolder(view);
            return headViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainPageItemAdapter.ItemViewHolder) {
            ((ItemViewHolder) holder).data = datas.get(holder.getAdapterPosition() - 1);
            ((MainPageItemAdapter.ItemViewHolder) holder).tvNum.setText(datas.get(holder.getAdapterPosition() - 1));
        } else if (holder instanceof MainPageItemAdapter.HeadViewHolder) {
            ((HeadViewHolder) holder).mBannerView.setImageUrls(HkStockUtil.getInstance().getIvUrlData());
            String txt = "　　牛栋自定义View--and--事件处理";
            setLimitRedTextTv(((HeadViewHolder) holder).tv_text, utils.DateUtils.dateToString() + txt);

        }
    }


    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public DrawableTextView tvNum;
        public String data;

        public ItemViewHolder(View itemView) {
            super(itemView);
            //自动适配
            tvNum = (DrawableTextView) itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 走这里
                    if (mItemClickListener == null) return;
                    mItemClickListener.onItemClick((getAdapterPosition() - 1), data);
                }
            });

        }
    }

    /**
     * 底部FootView布局
     */
    public class HeadViewHolder extends RecyclerView.ViewHolder implements BannerView.onItemClickListener {
        public TextView tv_text;
        public BannerView mBannerView;

        public HeadViewHolder(View view) {
            super(view);
            tv_text = (TextView) view.findViewById(R.id.tv_text);
            mBannerView = view.findViewById(R.id.mBannerView);
            mBannerView.setOnItemClickListener(this);

        }

        @Override
        public void onClick(View view, int position) {
            ToastUtils.showToast(mContext, "你点击了" + position);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int mPostion, String data);
    }

    /**
     * TextView ----颜色、字体大小处理
     *
     * @param textView textView
     * @param textName 输入要处理的oo字符串
     */
    private void setLimitRedTextTv(TextView textView, String textName) {

        SpannableString builder = new SpannableString(textName);
        int color = mContext.getResources().getColor(R.color.colorPrimary);
        //设置字体颜色
        builder.setSpan(new ForegroundColorSpan(color), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体大小
        builder.setSpan(new AbsoluteSizeSpan(18, true), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(14, true), 2, textName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }
}

