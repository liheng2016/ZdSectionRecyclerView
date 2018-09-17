package listview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import entity.InvestorBillDetailGroupBean.ChidBean;
import entity.ListGrup;
import listview.view.PinnedHeaderExpandableListView;
import view.niudong.com.demo.R;

import static view.niudong.com.demo.R.layout.child;

/**
 * Created by niudong on 2017/6/15.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * ExpandableListAdapter的抽象基类
 * 作用：从一些数据中提供数据和视图给可折叠列表视图
 * HeaderAdapter     折叠的ListView
 */


public class MyHeadAdapter extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {

    //  TODO 组元素表示可折叠的列表项，子元素表示列表项展开后看到的多个子元素项
    public String[] armTypes;
    public String[][] arms;
    public Context mContext;

    public LayoutInflater inflater;
    //    集合方式实现
    private List<ListGrup> list_data;

    public MyHeadAdapter(Context context, List<ListGrup> list_data) {
        this.mContext = context;
        this.list_data = list_data;

        inflater = LayoutInflater.from(mContext);
    }

    public MyHeadAdapter(String[] armTypes, String[][] arms, Context mContext) {
        this.armTypes = armTypes;
        this.arms = arms;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    //获取的群体数量，得到armTypes里元素的个数  组的数量
    @Override
    public int getGroupCount() {
        //return armTypes.length;
        return list_data.size();
    }

    //取得指定组中的儿童人数，就是armTypes中每一个种族它军种的个数
    @Override
    public int getChildrenCount(int i) {
//        TODO  孩子的数量
//        return arms[i].length;
        return list_data.get(i).mChildList.size();
    }

    @Override
    public Object getGroup(int i) {
        //TODO  每一个组里的数据
        //        return armTypes[i];
        return list_data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
//
//        return arms[i][i1];
//TODO  孩子数据

        return list_data.get(i).mChildList.get(i1);
    }

    //获取组在给定的位置编号，即armTypes中元素的ID
    @Override
    public long getGroupId(int i) {
        return i;

//        TODO

    }

    //获取在给定的组的儿童的ID，就是arms中元素的ID
    @Override
    public long getChildId(int i, int i1) {
//        return i1;
        return 0;
    }

    //表示孩子是否和组ID是跨基础数据的更改稳定
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        View viewgroup = null;

        if (viewgroup != null) {
            viewgroup = view;
        } else {
            viewgroup = createGroupView();
        }
        TextView text = (TextView) viewgroup.findViewById(R.id.groupto_head);
//        text.setText(armTypes[i]);
        text.setText(list_data.get(i).titleData);
        return viewgroup;
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.group_head, null);
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View viewchild = null;

        if (viewchild != null) {
            viewchild = view;
        } else {
            viewchild = createChildView();
        }
        TextView text = (TextView) viewchild.findViewById(R.id.childto);
//        text.setText(arms[i][i1]);
        text.setText(list_data.get(i).mChildList.get(i1).mCreateDate);
        return viewchild;
    }

    private View createChildView() {
        return inflater.inflate(child, null);
    }

    //孩子在指定的位置是可选的，即：arms中的元素是可点击的
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {

    }

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {

    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        return 0;
    }

    class HolderGroup {
        ImageView img;
        TextView tv;

        public HolderGroup(View convertView) {
//            img = (ImageView) convertView.findViewById(R.id.img_second_group_item);
//            tv = (TextView) convertView.findViewById(R.id.tv_second_group_item);
        }
    }

    class HolderChild {
        ImageView img;
        TextView tv;

        public HolderChild(View convertView) {
//            img = (ImageView) convertView.findViewById(R.id.img_second_child_item);
//            tv = (TextView) convertView.findViewById(R.id.tv_second_child_item);
        }
    }
}
