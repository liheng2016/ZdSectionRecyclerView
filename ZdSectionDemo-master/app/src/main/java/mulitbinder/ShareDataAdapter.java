package mulitbinder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import entity.BaseItem;
import entity.OneItem;
import utils.GlideManagerUtil;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/10/13.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */
public class ShareDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseItem> baseItem;
    private Context mContext;
    private static final int TYPE_HEAD = 1;
    private static final int TYPE_ITEM = 2;
    private View view;
    private boolean mViewType;
    //    private final int mType;

    public ShareDataAdapter(List<BaseItem> data) {
        this.baseItem = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int mPonstion = baseItem.get(viewType).viewType;
        if (mPonstion == 1) {
            setView(parent, R.layout.rv_item_one);
        } else if (mPonstion == 2) {
            setView(parent, R.layout.rv_item_two);
        } else if (mPonstion == 3) {
            setView(parent, R.layout.rv_item_three);
        } else if (mPonstion == 4) {
            setView(parent, R.layout.rv_item_four);

        }
        if (mContext == null) {
            mContext = view.getContext();
        }
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;


    }

    private void setView(ViewGroup parent, int id) {
        view = LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.iv_imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(view.getContext(), "点击了..." + getAdapterPosition());
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 不等于头       position = position - 1;

        if (holder instanceof ItemViewHolder) {
            OneItem oneItem = (OneItem) baseItem.get(position);
            String imageUrl = oneItem.imageUrl;
            GlideManagerUtil.glideLoader(mContext, imageUrl, R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher, ((ItemViewHolder) holder).mImageView, 1);
        } /*else if (baseItem.get(position).viewType == 2) {
                TwoItem twoItem = (TwoItem) baseItem.get(position);
                String imageUrl = twoItem.imageUrl;
                GlideManagerUtil.glideLoader(mContext, imageUrl, R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher, ((ItemViewHolder) holder).mImageView, 0);
            }*/
    }

    @Override
    public int getItemCount() {
        //  32----31    0--head    数据在1-30   foot--31
        return baseItem.size();
//return dataList == null ? 0 : dataList.size() + 2;//加了一个head
    }


}
