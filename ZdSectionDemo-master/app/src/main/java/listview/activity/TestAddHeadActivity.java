package listview.activity;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import entity.HkHistoryRenGouData;
import entity.InvestorBillDetailGroupBean;
import entity.InvestorBillDetailGroupBean.ChidBean;
import entity.ListGrup;
import listview.adapter.MyHeadAdapter;
import utils.MyUtil;
import view.niudong.com.demo.R;


/**
 * http://blog.csdn.net/chenliguan/article/details/48183917
 * <p>
 * 2）布局
 * Android：divider 边框线条 = “#FFFFFF”/ “@drawable/list”
 * android：dividerHeight 边框线条
 * <p>
 * 3）去除分割线
 * ①设置android:divider=”@null”
 * ②android:divider=”#00000000” #00000000后面两个零表示透明
 * ③.setDividerHeight(0) 高度设为0
 * <p>
 * 4）去除滚动棒
 * android:scrollbars=”none”
 * <p>
 * <p>
 * 折叠的ListView
 */
public class TestAddHeadActivity extends BaseActivity {

    private ExpandableListView listView;
    private ViewPager vp;
    private int[] images = {R.mipmap.icon, R.mipmap.ic_launcher, R.mipmap.icon};

    String[] armTypes = new String[]{"神族", "虫族", "人族"};
    String[][] arms = new String[][]{
            {"狂战士", "龙骑士", "黑暗圣堂"},
            {"小狗", "飞龙", "自爆妃子"},
            {"步兵", "伞兵", "护士mm"}
    };
    private MyHeadAdapter myHeadAdapter;
    private View mHeaderView;
    private InvestorBillDetailGroupBean group;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_test_add_head);
        listView = (ExpandableListView) findViewById(R.id.list_view);
        // 初始化头布局View
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.head_image, listView, false);
        listView.addHeaderView(mHeaderView);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        addData();
        bindAdapter();
    }

    private void addData() {

        group = new InvestorBillDetailGroupBean();
        for (int i = 0; i < 2; i++) {
            List<ChidBean> childList = new ArrayList<>();
            ChidBean chid = new ChidBean(i == 0 ? "朝阳区" + i : "太原市" + i, i == 0 ? "北京市" : "山西省");
            childList.add(chid);
            group.list_child.addAll(childList);
        }
    }


    /**
     * 得到显示的数据  构建两个新数据  一次取两数据     head he zu  duiying
     *
     * @param hkHistoryDataList 来源于前置
     */
    private List<ListGrup> getGroupChildList(List<HkHistoryRenGouData> hkHistoryDataList) {
        List<ListGrup> groupList=new ArrayList<>();
        List<HkHistoryRenGouData> mChildList = new ArrayList<>();
        ListGrup  listGrup=new ListGrup();
        if (null != hkHistoryDataList && hkHistoryDataList.size() > 0) {

            HkHistoryRenGouData data = hkHistoryDataList.get(hkHistoryDataList.size() - 1);
            String lastDate = MyUtil.substring(data.mCreateDate, 0, 6);
            for (int i = 0; i < hkHistoryDataList.size() - 1; i++) {
                HkHistoryRenGouData hkHistoryRenGouOne = hkHistoryDataList.get(i);
                HkHistoryRenGouData hkHistoryRenGouTwo = hkHistoryDataList.get(i + 1);
                String mCreateDateOne = hkHistoryRenGouOne.mCreateDate;
                String mCreateDateTwo = hkHistoryRenGouTwo.mCreateDate;

                String one = MyUtil.substring(mCreateDateOne, 0, 6);//20180305
                String two = MyUtil.substring(mCreateDateTwo, 0, 6);//20180302

                if (TextUtils.equals(one, two)) {
                    mChildList.add(hkHistoryRenGouOne);
//                    TODO
                    listGrup.titleData=one;
                    listGrup.setmChildList(mChildList);
                } else {
                    //不同
                    mChildList.add(hkHistoryRenGouOne);
                    listGrup.titleData=one;
                    listGrup.setmChildList(mChildList);
                    groupList.add(listGrup);
                    mChildList = new ArrayList<>();
                    listGrup=new ListGrup();
                }
            }
            mChildList.add(data);
            listGrup.titleData=lastDate;
            listGrup.setmChildList(mChildList);
            groupList.add(listGrup);
        }
        return groupList;
    }
    private void bindAdapter() {

        //MyHeadAdapter myHeadAdapter = new MyHeadAdapter(armTypes, arms, getApplicationContext());
        myHeadAdapter = new MyHeadAdapter(getApplicationContext(), getGroupChildList(hkHistoryDataList()));
        listView.setAdapter(myHeadAdapter);
        //遍历所有group,将所有项设置成默认展开
//        int groupCount = listView.getCount();
//        for (int i = 0; i < groupCount; i++) {
//            listView.expandGroup(i);
//        }

        myHeadAdapter.notifyDataSetChanged();
        //设置Fragment 适配器
        // vp.setAdapter();

    }

    /**
     * 历史申购
     */
    public List<HkHistoryRenGouData> hkHistoryDataList() {
        List<HkHistoryRenGouData> hkHistoryDataList = new ArrayList<>();
        HkHistoryRenGouData data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180721";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180710";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180701";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180511";
        data.mlotWinQty = "222";
        data.mStatus = "2";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180116";
        data.mlotWinQty = "222";
        data.mStatus = "3";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180111";
        data.mlotWinQty = "222";
        data.mStatus = "3";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20171211";
        data.mlotWinQty = "222";
        data.mStatus = "5";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20170511";
        data.mlotWinQty = "222";
        data.mStatus = "4";
        hkHistoryDataList.add(data);

        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20170510";
        data.mlotWinQty = "222";
        data.mStatus = "4";
        hkHistoryDataList.add(data);

        return hkHistoryDataList;
    }
}
