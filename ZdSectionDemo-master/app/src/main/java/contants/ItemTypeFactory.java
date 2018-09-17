package contants;

import android.view.View;

import base.BaseViewHolder;
import entity.ContentBean;
import entity.DividerBean;
import entity.SearchBean;
import holder.ContentViewHolder;
import holder.DividerViewHolder;
import holder.SearchViewHolder;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2017/12/16.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class ItemTypeFactory implements TypeFactory {
    public static final int CONTENT_ITEM_LAYOUT = R.layout.item_hk_head_view;
    public static final int SEARCH_ITEM_LAYOUT = R.layout.item_list;
    public static final int DIVIDER_ITEM_LAYOUT = R.layout.investor_head_view;

    @Override
    public int type(ContentBean contentBean) {
        return CONTENT_ITEM_LAYOUT;
    }

    @Override
    public int type(SearchBean searchBean) {
        return SEARCH_ITEM_LAYOUT;
    }

    @Override
    public int type(DividerBean dividerBean) {
        return DIVIDER_ITEM_LAYOUT;
    }

    @Override
    public BaseViewHolder createViewHolder(int type, View itemView) {
        switch (type) {
            case SEARCH_ITEM_LAYOUT:
                return new SearchViewHolder(itemView);
            case CONTENT_ITEM_LAYOUT:
                return new ContentViewHolder(itemView);
            case DIVIDER_ITEM_LAYOUT:
                return new DividerViewHolder(itemView);
            default:
                return null;
        }
    }
}
