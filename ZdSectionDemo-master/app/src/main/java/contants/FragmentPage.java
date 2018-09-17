package contants;


import fragment.BannerviewFragment;
import fragment.CustomViewFragment;
import fragment.MulitTabFragment;
import fragment.PackageFragment;
import fragment.RecyStickFragment;
import fragment.RecyStickLayoutFragment;
import fragment.RecyclerStickFragment;
import fragment.RecyclerViewRecFragment;
import fragment.TabFragment;
import fragment.TestFragment;

/**
 * Fragment的集合类, 各类Fragment在此注册. <br/>
 * 使用方法 在Intent中传递整数值, 在Activity中使用 {@link #getPageByValue(int)}获取相应的Fragment<br/>
 * 再通过相应的方法获取Fragment的各个属性
 *
 * @author niudong
 */
public enum FragmentPage {
    PERSONAL_INFO_MANAGER(TestFragment.class, "个人信息"),
    PACKAGE_TEST(PackageFragment.class, "测试包名"),
    MULIT_TAB(MulitTabFragment.class, "多条目"),
    MULIT_WEBVIEW(TabFragment.class, "WebView"),
    RECYCLER_STICK(RecyclerStickFragment.class, "固定头部RecyclerView"),
    RECYCLER_STICK_LAYOUT(RecyStickLayoutFragment.class, "固定头部RecyclerView"),
    RECYCLERVIER_RECF(RecyclerViewRecFragment.class, "固定头部RecyclerView"),
    BANNERVIEW_FRAGMENT(BannerviewFragment.class, "固定头部RecyclerView"),
    RECY_STICK_FRAGMENT(RecyStickFragment.class, "固定头部RecyclerView"),
    CUSTOMVIEW_FRAGMENT(CustomViewFragment.class, "固定头部RecyclerView");

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
