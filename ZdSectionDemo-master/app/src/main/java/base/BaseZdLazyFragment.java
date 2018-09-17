package base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by niudong on 2017-5-2.
 */

public abstract class BaseZdLazyFragment extends Fragment {
    private boolean hasCreateView;

    private boolean isFragmentVisible;
    private View mView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mView) {
            if (getLayoutResId() != 0) {
                mView = inflater.inflate(getLayoutResId(), container, false);
            } else if ((mView = getLayoutView()) == null) {
                throw new IllegalStateException("布局不能为空");
            }
            initView(mView);
            initListener();
            initData();
        }

        return mView;
    }

    protected void initListener() {
    }

    /**
     * 获取布局View
     */
    protected View getLayoutView() {
        return null;
    }


    protected abstract int getLayoutResId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }

    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            lazyData();
        }
    }

    /**
     * 懒加载数据，可以用于联网请求数据
     */
    protected void lazyData() {

    }


    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected void initView(View mView) {
    }


    /**
     * @param inflater
     * @param container
     * @return 布局得到的View
     */
    protected abstract View setView(LayoutInflater inflater, ViewGroup container);


}
