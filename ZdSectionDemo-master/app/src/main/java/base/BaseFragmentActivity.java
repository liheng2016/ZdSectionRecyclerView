package base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import java.util.ArrayList;
import java.util.List;

import contants.FragmentPage;
import contants.IBackPressHandled;
import contants.OnBackPressedListener;
import utils.Logger;

/**
 * Fragment容器Activity
 */
public class BaseFragmentActivity extends FragmentActivity implements IBackPressHandled {
    public static final String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public static final String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    protected List<OnBackPressedListener> mBackPressedListeners = new ArrayList<>();

    /**
     * 在DetailActivity中启动一个Fragment
     *
     * @param context 上下文环境
     * @param page    指定将要启动的fragment 在{@link FragmentPage}中注册
     */
    public static void startFragment(Context context, FragmentPage page) {
        startFragment(context, page, null);
    }

    /**
     * 在DetailActivity中启动一个Fragment
     *
     * @param context 上下文环境
     * @param page    指定将要启动的fragment 在{@link FragmentPage}中注册
     * @param args    需要的参数Bundle
     */
    public static void startFragment(Context context, FragmentPage page, Bundle args) {
        startFragment(context, 0, page, args);
    }

    /**
     * 在DetailActivity中启动一个Fragment
     *
     * @param context 上下文环境
     * @param flags   Intent的启动参数
     * @param page    指定将要启动的fragment序号 在{@link FragmentPage}中注册
     * @param args    需要的参数Bundle
     */
    public static void startFragment(Context context, int flags, FragmentPage page, Bundle args) {
        if(null == page) return;

        Intent intent = new Intent(context, BaseFragmentActivity.class);
        if (flags > 0) {
            intent.setFlags(flags);
        }
        intent.putExtra(BUNDLE_KEY_PAGE, page.ordinal());
        if (args != null) intent.putExtra(BUNDLE_KEY_ARGS, args);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 有返回值
     */
    public static void startFragmentForResult(Activity context, FragmentPage page, int requestCode, Bundle args) {
        if(null == page) return;

        Intent intent = new Intent(context, BaseFragmentActivity.class);
        intent.putExtra(BUNDLE_KEY_PAGE, page.ordinal());
        if (args != null) intent.putExtra(BUNDLE_KEY_ARGS, args);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置软键盘加载时不刷新布局
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        initFromIntent(getIntent());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        //只处理最后一个注册的
        boolean intercept = false;
        if (!mBackPressedListeners.isEmpty()) {
            OnBackPressedListener listener = mBackPressedListeners.get(mBackPressedListeners.size() - 1);
            if (listener.doBack()) {
                intercept = true;
            }
        }

        if (!intercept) {
            super.onBackPressed();
        }
    }

    @Override
    public void addOnBackPressedListener(OnBackPressedListener listener) {
        if (null != listener && !mBackPressedListeners.contains(listener)) {
            mBackPressedListeners.add(listener);
        }
    }

    @Override
    public void removeOnBackPressedListener(OnBackPressedListener listener) {
        if (null != listener) {
            mBackPressedListeners.remove(listener);
        }
    }


    @SuppressWarnings("TryWithIdenticalCatches")
    private void initFromIntent(Intent intent) {
        if (intent == null) {
            throw new RuntimeException("必须指定一个页面");
        }

        int pageValue = intent.getIntExtra(BUNDLE_KEY_PAGE, 0);
        FragmentPage page = FragmentPage.getPageByValue(pageValue);
        if (null == page) return;

        try {
            TestBaseFragment fragment = (TestBaseFragment) page.getClz().newInstance();

            Bundle args = intent.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                fragment.setArguments(args);
            }
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
        } catch (InstantiationException e) {
            Logger.printExceptionStackTrace(e);
        } catch (IllegalAccessException e) {
            Logger.printExceptionStackTrace(e);
        }
    }
}
