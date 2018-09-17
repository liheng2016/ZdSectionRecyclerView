package fragment;

import android.support.v4.app.Fragment;

/**
 * 创建者  牛栋
 * 描述	  封装对Fragment的创建
 */
public class TradeFragmentFactory {
    private static BuyFragment buyFragment;
    private static SellFragment sellFragment;
    private static CheFragment cheFragment;
    private static QueryFragment queryFragment;
    /**
     * 对象锁方法第一次启动时比类锁方法快2秒
     */
    private static final Object LOCK = new Object();
    private static volatile TradeFragmentFactory instance = null;

    public static TradeFragmentFactory getInstance() {
        if (null == instance) {
            synchronized (LOCK) {
                if (null == instance) {
                    instance = new TradeFragmentFactory();
                }
            }
        }
        return instance;
    }

    private TradeFragmentFactory() {
    }

    public Fragment createFragment(int position) {
        //定义Fragment对象
        Fragment fragment = null;
        switch (position) {
            case 0://返回 首页 对应的fragment
                if (buyFragment == null) {
                    buyFragment = new BuyFragment();
                }
                fragment = buyFragment;
                break;
            case 1://返回 应用 对应的fragment
                if (sellFragment == null) {
                    sellFragment = new SellFragment();
                }
                fragment = sellFragment;
                break;

            case 2://返回 应用 对应的fragment
                if (cheFragment == null) {
                    cheFragment = new CheFragment();
                }
                fragment = cheFragment;
                break;
            case 3://返回 应用 对应的fragment
                if (queryFragment == null) {
                    queryFragment = new QueryFragment();
                }
                fragment = queryFragment;
                break;
            default:
                break;
        }
        return fragment;
    }
}
