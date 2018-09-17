package fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import base.BaseLazyFragment;
import entity.BaseItem;
import entity.FourItem;
import entity.MainPageEntity;
import entity.OneItem;
import entity.SixGroupItem;
import entity.ThreeItem;
import entity.TwoItem;
import entity.ViewPagerItem;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.GroupItemViewBinder;
import mulitbinder.ShareItemViewBinder;
import mulitbinder.ViewBinder;
import mulitbinder.ViewPagerBinder;
import okhttp3.Call;
import utils.GlideManagerUtil;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/10/12.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class BuyFragment extends BaseLazyFragment {
    private MultiTypeAdapter adapter;
    private Items items = new Items();
    private RecyclerView mRecyclerView;
    private View view;
    public static final String imagePath1 = "http://tu.yi23.net//collections/250-20171010_220609-1507644369263.jpg";
    private String imagePath2 = "http://tu.yi23.net//collections/250-20170905_194820-1504612100357.jpg";
    private String imagePath3 = "http://tu.yi23.net//collections/507-20171002_213127-1506951087933.jpg";
    private String vpOne = "http://tu.yi23.net/posting/507-20171012_204000-1507812000048.jpg";
    private String vpTwo = "http://tu.yi23.net/posting/225-20171009_152602-1507533962234.jpg";
    private String vpThree = "http://tu.yi23.net/posting/225-20171011_233721-1507736241636.jpg";


    private String imagePath5 = "http://tu.yi23.net/feedbackImgs/458665/15078592220690.jpg!840w";
    private String imagePath6 = "http://tu.yi23.net/feedbackImgs/458665/15078592236882.jpg!840w";
    private String imagePath7 = "http://tu.yi23.net//collections/225-20171013_121307-1507867987554.jpg";

    private String fangxin = "http://tu.yi23.net/TopicComponent/276-20171010_180541-1507629941639.png";
    private String redYi = "http://tu.yi23.net/posting/250-20171010_225353-1507647233430.jpg!750w";

    private String endImag = "http://tu.yi23.net/yi23img/postings/cover/48bee41612d4428289a48c7c1c31c808.jpg!750w";
    private List<BaseItem> itemList1 = new ArrayList<>();
    private List<BaseItem> itemList2 = new ArrayList<>();
    private List<BaseItem> itemList3 = new ArrayList<>();
    private List<BaseItem> itemList4 = new ArrayList<>();
    private ArrayList<View> itemList5 = new ArrayList<>();
    private ArrayList<BaseItem> itemList6 = new ArrayList<>();
    private View headView;
    private LinearLayout linearLayout1;
    private View endView;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_yi_er_three_recy, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.yi_er_three_recyclerView);

        adapter = new MultiTypeAdapter(items);
        return view;
    }

    @Override
    protected void initData() {
        //requestPage();
        //TODO  注册
        // 内层子recyclerView  one
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter.register(View.class, new ViewBinder());
        itemList1.clear();
        OneItem oneItem = new OneItem(imagePath1, null, 1);
        itemList1.add(oneItem);
        oneItem = new OneItem(imagePath2, null, 1);
        itemList1.add(oneItem);
        oneItem = new OneItem(imagePath1, null, 1);
        itemList1.add(oneItem);
        adapter.register(OneItem.class, new ShareItemViewBinder(itemList1));
        // 内层子recyclerView two
        itemList2.clear();
        OneItem twoItem = new OneItem(imagePath2, null, 2);
        itemList2.add(twoItem);
        twoItem = new OneItem(imagePath1, null, 2);
        itemList2.add(twoItem);
        twoItem = new OneItem(imagePath2, null, 2);
        itemList2.add(twoItem);
        adapter.register(TwoItem.class, new ShareItemViewBinder(itemList2));
        // 内层子recyclerView threeItem
        itemList3.clear();
        OneItem threeItem = new OneItem(imagePath3, null, 3);
        itemList3.add(threeItem);
        threeItem = new OneItem(imagePath7, null, 3);
        itemList3.add(threeItem);
        threeItem = new OneItem(fangxin, null, 3);
        itemList3.add(threeItem);
        adapter.register(ThreeItem.class, new ShareItemViewBinder(itemList3));

        // 内层子recyclerView threeItem
        itemList4.clear();
        OneItem fourItem = new OneItem(imagePath7, null, 4);
        itemList4.add(fourItem);
        fourItem = new OneItem(imagePath3, null, 4);
        itemList4.add(fourItem);
        fourItem = new OneItem(imagePath5, null, 4);
        itemList4.add(fourItem);
        adapter.register(FourItem.class, new ShareItemViewBinder(itemList4));


        // 内层子ViewPager threeItem
        itemList5.clear();
        View mViewPager = LayoutInflater.from(getContext()).inflate(R.layout.item_view_pager, null);
        ImageView ivOne = (ImageView) mViewPager.findViewById(R.id.iv_icon);
        GlideManagerUtil.glideLoader(getContext(), vpThree, R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivOne);
        itemList5.add(mViewPager);
        mViewPager = LayoutInflater.from(getContext()).inflate(R.layout.item_view_pager, null);

        ImageView ivTwo = (ImageView) mViewPager.findViewById(R.id.iv_icon);
        GlideManagerUtil.glideLoader(getContext(), vpOne, R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivTwo);
        itemList5.add(mViewPager);
        mViewPager = LayoutInflater.from(getContext()).inflate(R.layout.item_view_pager, null);
        ImageView ivFour = (ImageView) mViewPager.findViewById(R.id.iv_icon);
        GlideManagerUtil.glideLoader(getContext(), vpTwo, R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivFour);
        itemList5.add(mViewPager);
        adapter.register(ViewPagerItem.class, new ViewPagerBinder(itemList5));


        // 内层子recyclerView 分组
        adapter.register(SixGroupItem.class, new GroupItemViewBinder());
        //TODO  设置适配器
        headView = LayoutInflater.from(getContext()).inflate(R.layout.head_image, null);
        endView = LayoutInflater.from(getContext()).inflate(R.layout.item_end, null);

        ImageView ivRedYi = (ImageView) endView.findViewById(R.id.iv_red);
        ImageView ivEnd = (ImageView) endView.findViewById(R.id.iv_end);
        GlideManagerUtil.glideLoader(getContext(), redYi, R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivRedYi);
        GlideManagerUtil.glideLoader(getContext(), endImag, R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivEnd);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void lazyData() {
        super.lazyData();

        //TODO  数据
        items.clear();
        items.add(headView);
        OneItem oneItem = new OneItem(null, "衣二三玩法秒懂", 11);
        items.add(oneItem);
        TwoItem twoItem = new TwoItem(null, "衣二三有哪些品牌", 22);
        items.add(twoItem);
        ThreeItem threeItem = new ThreeItem(null, "明星和博主都在穿的衣二三", 33);
        items.add(threeItem);

        FourItem fourItem = new FourItem(null, "衣二三的新品", 44);
        items.add(fourItem);

        ViewPagerItem fiveitem = new ViewPagerItem(null, "精选专题", 55);
        items.add(fiveitem);


        SixGroupItem sixGroupItem = new SixGroupItem(fangxin, "最佳晒图");
        items.add(sixGroupItem);
         sixGroupItem = new SixGroupItem(imagePath6, "主题晒图");
        items.add(sixGroupItem);
        items.add(endView);
        adapter.notifyDataSetChanged();
    }

    private void requestPage() {
        OkHttpUtils.get().url("https://api.95vintage.com/gw/page/homepage?pageNumber=1&device=Android&version=2.6&appName=yi23&deviceId=RNnYQRuPV60pUZqN5uX40AhzGHSUJ7AcFitooV715l4&timestamp=1507800662878&wid=e8ad2c92-e0b9-4c6f-a31e-98d7bb6b9cb7&regionId=52&channelId=yingyongbao&uid=0&signature=bLuMPaZ60yA5WvIplnenFW5JAHBQJOIvtpwRgjb2kWCqHbpJNlOoB0SfGbUcYc08Ny43u+Ycc9LkXqgQbTJ4ItybuiQubC4B8DJqRlyU44L5lVdaXDLnxSHZUaNsk5/sYLtq+zHSdfYThaBdomiB/l1kU9slSs9PQqk+hIdd/dQ= http/1.1").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(getContext(), e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                String name = Thread.currentThread().getName();
                Gson gson = new Gson();
                MainPageEntity entity = gson.fromJson(response, MainPageEntity.class);
                String itemText = entity.getData().getItemList().get(0).getItemText();
            }
        });
    }
}