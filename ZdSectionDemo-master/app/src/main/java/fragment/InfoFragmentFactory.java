package fragment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;

import java.util.HashMap;

import base.BaseLazyFragment;

/**
 * 创建者  牛栋
 * 描述	  封装对Fragment的创建
 */
public class InfoFragmentFactory {
    /**
     * 对象锁方法第一次启动时比类锁方法快2秒
     */
    private static final Object LOCK = new Object();
    private static HashMap<Integer, Fragment> cacheFragment = new HashMap<>();

    public static Fragment createFragment(int position) {
        //定义Fragment对象
        Fragment fragment = null;
        //从缓存中取数据
        if (cacheFragment.containsKey(position)) {
            return cacheFragment.get(position);
        }
        switch (position) {
            case 0://返回 首页 对应的fragment
                fragment = new RecyWebFragment();
                break;
            case 1://返回 应用 对应的fragment
                fragment = new BuyFragment();
                break;
            case 2://返回 应用 对应的fragment
                fragment = new MimeFragment();
                break;
            default:
                break;
        }
        cacheFragment.put(position, fragment);
        return fragment;
    }
}
