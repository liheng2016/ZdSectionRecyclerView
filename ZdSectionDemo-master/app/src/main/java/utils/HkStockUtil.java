package utils;

import android.os.Build;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Category;
import entity.HkHistoryRenGouData;
import entity.ImageItem;
import entity.ItemOneBean;
import entity.ItemTwoBean;
import entity.ListGrup;
import fragment.CustomClockFragment;
import fragment.CustomViewChiFragment;
import fragment.StockKlineFragment;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.R;

import static view.niudong.com.demo.R.id.webView;

/**
 * 5.5 金贝塔工具类/主要是港股通
 * 牛栋
 */
public class HkStockUtil {
    private String fangxin = "http://tu.yi23.net/TopicComponent/276-20171010_180541-1507629941639.png";
    private String redYi = "http://tu.yi23.net/posting/250-20171010_225353-1507647233430.jpg!750w";
    private String imagePath7 = "http://tu.yi23.net//collections/225-20171013_121307-1507867987554.jpg";

    private String vpOne = "http://tu.yi23.net/posting/507-20171012_204000-1507812000048.jpg";
    private static HkStockUtil sInstance;
    //缓存WebView
    private Map<Integer, WebView> mCacheData = new HashMap<>();
    private String url = "https://gitee.com/NiuDong/";
    private List<Category> popList2Data;

    public Map<Integer, WebView> getmCacheData() {
        return mCacheData;
    }

    public static HkStockUtil getInstance() {
        if (null == sInstance) {
            synchronized (HkStockUtil.class) {
                if (null == sInstance) {
                    sInstance = new HkStockUtil();
                }
            }
        }

        return sInstance;
    }

    /**
     * 预加载WebView 判空
     *
     * @param homeWebRootView
     * @param postion
     */
    public void loadWebViewData(LinearLayout homeWebRootView, int postion) {
        WebView webView;
        if (null == homeWebRootView || null == mCacheData || mCacheData.size() == 0 || mCacheData.get(postion) == null) {
            webView = loadWebView(url);
        } else {
            //移除父亲View
            webView = mCacheData.get(postion);
        }
        if (null != webView && webView.getParent() != null) {
            LinearLayout rootView = (LinearLayout) webView.getParent();
            rootView.removeAllViews();
        }
        if (null == webView) return;
        homeWebRootView.addView(webView);
    }

    public List<Category> getPopListData() {
        List<Category> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category data = new Category("跨平台flutter" + i);
            result.add(data);
        }
        return result;
    }

    public List<Category> getPopList2Data() {

        List<Category> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Category data = new Category("做一个自己的产品" + i);
            result.add(data);
        }
        return result;
    }


    /**
     * 标的证券的更新回调
     */
    public interface OnUpdateListener {
        void onSuccess(int isSuccess);
    }

    /**
     * webView 设置相关
     */
    public void initRegisterWebView(WebView webView, String url, int mPostion) {
        this.url = url;
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        if (mCacheData.containsKey(mPostion)) return;
        mCacheData.put(mPostion, webView);
    }

    /**
     * webView 设置相关
     */
    public WebView loadWebView(String url) {
        WebView webView = new WebView(MyApplication.mContext);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        return webView;
    }

    /**
     * 查询用户委托使用的代码市场
     *
     * @param queryMarketNum 用户开通的市场个数
     * @param mktType        代码所属市场类型
     * @param selectMarket   用户选择的市场类型 SharedPreUtils.readGlobalSharePreString(mContext, key, "");
     * @param queryMarketNum 用户开通的个股票市场
     *                       <p>
     *                       判断规则：
     *                       如果用户开通两个市场，并且购买代码有市场，那么代码市场采用用户选择的；
     *                       如果用户开通单市场:
     *                       代码支持一个市场：代码支持1个市场，用户开通1个市场，比较两个字符串
     *                       相同：采用用户开通的市场匹配/代码市场；
     *                       不同：返回””
     *                       代码支持两个市场：采用用户开通的市场匹配；
     */
    private String querySupportMarket(String queryMarketNum, String mktType, String selectMarket) {
        if (TextUtils.isEmpty(queryMarketNum) || TextUtils.isEmpty(mktType))
            return "";
        if (queryMarketNum.length() == 2) {//支持两个市场
            return mktType.length() > 1 ? selectMarket : mktType;
        } else {//单市场
            if (mktType.length() == 2) {//判断代码支持两个市场
                //用去用户开通的去匹配
                return queryMarketNum;
            } else {//代码支持1个市场  用户开通一个市场
                return TextUtils.equals(mktType, queryMarketNum) ? queryMarketNum : "";
            }
        }
    }


    /**
     * 认购时间
     */
    public List<ItemOneBean> getRenGouTimeList() {
        List<ItemOneBean> renGouTimeList = new ArrayList<>();
        ItemOneBean itemOneBean = new ItemOneBean();
        itemOneBean.mTitle = "-时间-";
        itemOneBean.mQuestion = "具体的认购时间点？";
        itemOneBean.mAnswer = "截止日期的17:00前均可认购，包含早盘期间，中午休市，以及晚间休市期间";
        renGouTimeList.add(itemOneBean);

        itemOneBean = new ItemOneBean();
        itemOneBean.mTitle = "-数量-";
        itemOneBean.mQuestion = "认购页的认购数量为什么一直显示为一手？";
        itemOneBean.mAnswer = "认购页默认显示一手，其他数量可通过下拉框进行选择。";
        renGouTimeList.add(itemOneBean);

        itemOneBean = new ItemOneBean();
        itemOneBean.mTitle = "-价格-";
        itemOneBean.mQuestion = "招股价是一个区间，那认购的时候以什么价格为准？";
        itemOneBean.mAnswer = "招股价格是一个区间值，认购是以最高招股价认购。";
        renGouTimeList.add(itemOneBean);
        return renGouTimeList;
    }

    /**
     * 认购时间
     */
    public List<ItemTwoBean> getRenGoTimeChildList() {
        List<ItemTwoBean> renGouTimeList = new ArrayList<>();
        ItemTwoBean itemOneBean = new ItemTwoBean();
        itemOneBean.mQuestion = "认购总金额都包含什么？";
        itemOneBean.mAnswer = "认购金额：招股最高价*认购数量\n" +
                "中签费：认购金额*1.0077%\n" +
                "手续费：50元";
        renGouTimeList.add(itemOneBean);

        itemOneBean = new ItemTwoBean();
        itemOneBean.mQuestion = "认购金额使用的是可用资金吗？";
        itemOneBean.mAnswer = "不是，认购资金使用的是可取资金。";
        renGouTimeList.add(itemOneBean);

        itemOneBean = new ItemTwoBean();
        itemOneBean.mQuestion = "认购完成后为什么可取资金无变化？";
        itemOneBean.mAnswer = "认购金额不会冻结，这部分资金还可以用来买股票，资金转出。";
        renGouTimeList.add(itemOneBean);

        itemOneBean = new ItemTwoBean();
        itemOneBean.mQuestion = "认购完成后如果用这部分金额买了股票，这新股还能认购成功吗？";
        itemOneBean.mAnswer = "认购资金其实只需在认购截止日的16:00前准备好，金贝塔会在第二个交易日的11:00前扣除，届时用户登录交易后会提示用户，后期可在新股-查询页面查看当日需准备金额。";
        renGouTimeList.add(itemOneBean);

        return renGouTimeList;
    }

    /**
     * 列表数据和
     *
     * @return
     */
    public List<ListGrup> getGroupList() {
        List<ListGrup> grupList = new ArrayList<>();
        //TODO   RecyclerView悬停
        ListGrup listGrup = new ListGrup();
        listGrup.titleData = "RecyclerView悬停";
        List<String> mRecyclerData = new ArrayList<>();
        mRecyclerData.add("采用ItemDecoration悬停");
        mRecyclerData.add("采用自定义布局方式悬停");
        listGrup.setmChildStr(mRecyclerData);
        grupList.add(listGrup);
        //TODO RecyclerView多条目
        listGrup = new ListGrup();
        listGrup.titleData = "RecyclerView多条目布局";
        mRecyclerData = new ArrayList<>();
        mRecyclerData.add("采用multitype复杂列表");
        mRecyclerData.add("采用自定义getItemViewType方式");
        mRecyclerData.add("RecyclerView嵌套水平RecyclerView无冲突");
        mRecyclerData.add("Pop中的RecyclerView");
        listGrup.setmChildStr(mRecyclerData);
        grupList.add(listGrup);
        //TODO 主页框架页面搭建
        listGrup = new ListGrup();
        listGrup.titleData = "主页框架页面搭建";
        mRecyclerData = new ArrayList<>();
        mRecyclerData.add("采用FragMentTabHost+子Fragment");
        mRecyclerData.add("采用NavigationBar+子Fragment");
        mRecyclerData.add("封装List列表管理子Fragment");
        listGrup.setmChildStr(mRecyclerData);
        grupList.add(listGrup);
        //TODO  滑动列表折叠效果实现
        listGrup = new ListGrup();
        listGrup.titleData = "滑动列表悬停头部";
        mRecyclerData = new ArrayList<>();
        mRecyclerData.add("折叠一：CoordinatorLayout+RecyclerView");
        mRecyclerData.add("折叠二：NestedScrollView+RecyclerView");
        mRecyclerData.add("折叠三：StickyNavLayout+RecyclerView");
        mRecyclerData.add("折叠四：替换索星ScrollView+Recycler");
        mRecyclerData.add("折叠五：一个RecyclerView切多Tab（悬停）");
        listGrup.setmChildStr(mRecyclerData);
        grupList.add(listGrup);
        //TODO 自定义View
        listGrup = new ListGrup();
        listGrup.titleData = "自定义View（纯手工）";
        mRecyclerData = new ArrayList<>();
        mRecyclerData.add("自定义图表相关");
        mRecyclerData.add("自定义刻度尺");
        mRecyclerData.add("自定义钟表");
        mRecyclerData.add("自定义股票K线");
        listGrup.setmChildStr(mRecyclerData);
        grupList.add(listGrup);
        //TODO 自定义View
        listGrup = new ListGrup();
        listGrup.titleData = "Fragment相关";
        mRecyclerData = new ArrayList<>();
        mRecyclerData.add("Fragment任务栈处理");
        mRecyclerData.add("LazyFragment懒加载+ViewPager");
        listGrup.setmChildStr(mRecyclerData);
        grupList.add(listGrup);
        listGrup = new ListGrup();
        listGrup.titleData = "跨平台嵌入RN模块";
        mRecyclerData = new ArrayList<>();
        mRecyclerData.add("ReactNative列表展示");
        listGrup.setmChildStr(mRecyclerData);
        grupList.add(listGrup);
        //TODO 其他相关
        listGrup = new ListGrup();
        listGrup.titleData = "其他相关";
        mRecyclerData = new ArrayList<>();
        mRecyclerData.add("封装接口回调方便传消息");
        mRecyclerData.add("列表点击跳详情多页面--左右滑动");
        mRecyclerData.add("数据库测试--Api 和Sql");
        mRecyclerData.add("App内存泄漏那些事");
        mRecyclerData.add("Okhttp-Ping++支付");
        mRecyclerData.add("Mvp+Retrofit+Okhttp列表(接口不通有代码)");
        mRecyclerData.add("H5和Js互调");
        mRecyclerData.add("线性布局写Tab切换");
        mRecyclerData.add("WebView预加载");
        mRecyclerData.add("Aidl跨进程通讯");
        mRecyclerData.add("BitMap合成图片");
        mRecyclerData.add("手动解析Json");

        listGrup.setmChildStr(mRecyclerData);
        grupList.add(listGrup);


        return grupList;
    }

    public List<String> getIvUrlData() {
        List<String> imageUrl = new ArrayList<>();
        imageUrl.add(redYi);
        imageUrl.add(fangxin);
        imageUrl.add(vpOne);
//        TODO
        return imageUrl;
    }

    /**
     * 中签公布
     */
    public List<ItemTwoBean> getZhongQianOtherList() {
        List<ItemTwoBean> renGouTimeList = new ArrayList<>();
        ItemTwoBean itemOneBean = new ItemTwoBean();
        itemOneBean.mQuestion = "都有哪些渠道可以了解到中签情况";
        itemOneBean.mAnswer = "软件通知：交易首页“新股”会标识今日中签支数\n" +
                "短信通知：金贝塔会短信通知用户具体中签情况\n" +
                "微信客服：微信客服金金-金贝塔“ Lishixiao199”";
        renGouTimeList.add(itemOneBean);

        itemOneBean = new ItemTwoBean();
        itemOneBean.mQuestion = "中签率是怎么计算，是不是申购越多中签越多？";
        itemOneBean.mAnswer = "先满足所有的认购的用户的一手中签，如果还有多余新股则剩余申购越多中签越多。";
        renGouTimeList.add(itemOneBean);

        itemOneBean = new ItemTwoBean();
        itemOneBean.mQuestion = "中签后股票会不会出现在持仓中？";
        itemOneBean.mAnswer = "当日21:00之后才会出现在持仓中。";
        renGouTimeList.add(itemOneBean);
        return renGouTimeList;
    }

    /**
     * 得到显示的数据  构建两个新数据  一次取两数据
     *
     * @param hkHistoryDataList 来源于前置
     */
    public List<ListGrup> getGroupChildList(List<HkHistoryRenGouData> hkHistoryDataList) {
        List<ListGrup> groupList = new ArrayList<>();
        List<HkHistoryRenGouData> mOneList = new ArrayList<>();
        ListGrup listGrup = new ListGrup();
        if (null != hkHistoryDataList && hkHistoryDataList.size() > 0) {

            HkHistoryRenGouData data = hkHistoryDataList.get(hkHistoryDataList.size() - 1);
            String lastDate = MyUtil.substring(data.mCreateDate, 0, 6);
            int value = 0;
            for (int i = 0; i < hkHistoryDataList.size() - 1; i++) {
                HkHistoryRenGouData hkHistoryRenGouOne = hkHistoryDataList.get(i);
                HkHistoryRenGouData hkHistoryRenGouTwo = hkHistoryDataList.get(i + 1);
                String mCreateDateOne = hkHistoryRenGouOne.mCreateDate;
                String mCreateDateTwo = hkHistoryRenGouTwo.mCreateDate;

                String one = MyUtil.substring(mCreateDateOne, 0, 6);//20180305
                String two = MyUtil.substring(mCreateDateTwo, 0, 6);//20180302

                if (TextUtils.equals(one, two)) {
                    mOneList.add(hkHistoryRenGouOne);
                    listGrup.titleData = one;
                    listGrup.setmChildList(mOneList);
                    groupList.add(listGrup);
                } else {
                    //不同
                    mOneList.add(hkHistoryRenGouOne);
                    listGrup.titleData = one;
                    listGrup.setmChildList(mOneList);
                    value++;
                    mOneList = new ArrayList<>();
                    listGrup = new ListGrup();
                }
            }
            mOneList.add(data);
            listGrup.titleData = lastDate;
            listGrup.setmChildList(mOneList);
            groupList.add(listGrup);

        }
        return groupList;
    }

    /**
     * 历史申购
     */
    public List<HkHistoryRenGouData> hkHistoryDataList() {
        List<HkHistoryRenGouData> hkHistoryDataList = new ArrayList<>();
        HkHistoryRenGouData data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180211";
        data.mlotWinQty = "222";
        data.mStatus = "2";
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
        data.mCreateDate = "20170111";
        data.mlotWinQty = "222";
        data.mStatus = "4";
        hkHistoryDataList.add(data);
        data.mAmount = "121";
        data.mCreateDate = "20180321";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180310";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180301";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);

        data.mAmount = "121";
        data.mCreateDate = "20180321";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180310";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180301";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180211";
        data.mlotWinQty = "222";
        data.mStatus = "2";
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
        data.mCreateDate = "20170111";
        data.mlotWinQty = "222";
        data.mStatus = "4";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180301";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180211";
        data.mlotWinQty = "222";
        data.mStatus = "2";
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
        data.mCreateDate = "20170111";
        data.mlotWinQty = "222";
        data.mStatus = "4";
        hkHistoryDataList.add(data);
        return hkHistoryDataList;
    }


    /**
     * 历史申购
     */
    public List<HkHistoryRenGouData> hkHistoryDataList2() {
        List<HkHistoryRenGouData> hkHistoryDataList = new ArrayList<>();
        HkHistoryRenGouData data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180321";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180310";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180301";
        data.mlotWinQty = "222";
        data.mStatus = "1";
        hkHistoryDataList.add(data);
        data = new HkHistoryRenGouData();
        data.mAmount = "121";
        data.mCreateDate = "20180211";
        data.mlotWinQty = "222";
        data.mStatus = "2";
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
        data.mCreateDate = "20170111";
        data.mlotWinQty = "222";
        data.mStatus = "4";
        hkHistoryDataList.add(data);

        return hkHistoryDataList;
    }
    /**
     *            //滑动的距离
     mDistanceY += dy;
     // toolbar的高度
     int toolbarHeight = tVbgHead.getBottom() == 0 ? UIUtils.dip2px(40) : tVbgHead.getBottom();
     // 当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，
     // 达到渐变的效果
     if (mDistanceY <= toolbarHeight) {
     float scale = (float) mDistanceY / toolbarHeight;
     float alpha = scale * 255;
     tVbgHead.setBackgroundColor(Color.argb((int) alpha, 128, 0, 0));
     } else {
     tVbgHead.setBackgroundResource(R.color.common_red);
     // 上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色 //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
     // 将标题栏的颜色设置为完全不透明状态 m
     // 这就轻松实现了标题栏渐变
     Logger.d("mHeadViewMeasuredHeightht", "mHeadViewMeasuredHeightht" + viewHeight);
     }
     */

}