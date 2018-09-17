package contants;

import android.view.View;

import base.BaseViewHolder;
import entity.ContentBean;
import entity.DividerBean;
import entity.SearchBean;

/**
 * 名称：
 * Created by niudong on 2017/12/16.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public interface TypeFactory {
    int type(ContentBean contentBean);
    int type(SearchBean searchBean);
    int type(DividerBean dividerBean);
    BaseViewHolder createViewHolder(int type, View itemView);
}
