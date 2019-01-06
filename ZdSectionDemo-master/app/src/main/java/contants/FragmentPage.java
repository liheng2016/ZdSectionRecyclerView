package contants;


import fragment.BannerviewFragment;
import fragment.CustomClockFragment;
import fragment.CustomViewChiFragment;
import fragment.CustomViewFragment;
import fragment.DataJsonFragment;
import fragment.LoadMoreRecyclerFragment;
import fragment.MulitTabFragment;
import fragment.PackageFragment;
import fragment.PopRecyclerFragment;
import fragment.RecyStickFragment;
import fragment.RecyStickLayoutFragment;
import fragment.RecyclerStickFragment;
import fragment.RecyclerViewRecFragment;
import fragment.StockKlineFragment;
import fragment.TabFragment;
import fragment.TestFragment;

/**
 * Fragment的集合类, 各类Fragment在此注册. <br/>
 * 使用方法 在Intent中传递整数值, 在Activity中使用 {@link #getPageByValue(int)}获取相应的Fragment<br/>
 * 再通过相应的方法获取Fragment的各个属性
 * <p>
 * 名称：
 * Created by niudong on 2018/9/20
 * Tel：18811793194
 * VersionChange：mthq1.0
 */
public enum FragmentPage {
    PERSONAL_INFO_MANAGER(TestFragment.class, "个人信息"),
    PACKAGE_TEST(PackageFragment.class, "测试包名"),
    MULIT_TAB(MulitTabFragment.class, "多条目"),
    MULIT_WEBVIEW(TabFragment.class, "WebView"),
    RECYCLER_STICK(RecyclerStickFragment.class, "固定头部RecyclerView"),
    RECYCLER_STICK_LAYOUT(RecyStickLayoutFragment.class, "固定头部RecyclerView1"),
    RECYCLERVIER_RECF(RecyclerViewRecFragment.class, "固定头部RecyclerView2"),
    BANNERVIEW_FRAGMENT(BannerviewFragment.class, "固定头部RecyclerView3"),
    RECY_STICK_FRAGMENT(RecyStickFragment.class, "多种股票"),
    CUSTOMVIEW_FRAGMENT(CustomViewFragment.class, "固定头部RecyclerView"),
    CUSTOMVIEWCHI_FRAGMENT(CustomViewChiFragment.class, "自定义刻度尺"),
    CUSTOM_CLOCK_FRAGMENT(CustomClockFragment.class, "自定义钟表"),
    STOCK_KLINE_FRAGMENT(StockKlineFragment.class, "自定义股票K线"),
    POP_RECYCLER_FRAGMENT(PopRecyclerFragment.class, "Pop中的RecyclerView"),
    LOADMOR_RECYLER_FRAGMENT(LoadMoreRecyclerFragment.class, "加载更多的RecyclerView"),
    DATA_JSON_FRAGMENT(DataJsonFragment.class, "解析Json");

    private String title;
    private Class<?> clz;

    FragmentPage(Class<?> clz, String tag) {
        this.title = tag;
        this.clz = clz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    /**
     * 通过int值查询相应的Fragment信息
     */
    public static FragmentPage getPageByValue(int val) {
        FragmentPage[] values = FragmentPage.values();
        if (null != values && values.length > 0 && val >= 0 && val < values.length) {
            return values[val];
        }
        return null;
    }
}
