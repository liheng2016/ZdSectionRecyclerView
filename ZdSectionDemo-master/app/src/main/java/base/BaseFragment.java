package base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by niudong on 2016/6/13.
 */
public abstract class BaseFragment extends Fragment {
    private boolean isVisible = false; //
    private boolean isInitView = false; //是否完成初始view标记
    private boolean isFirstLoad = true;
    protected LayoutInflater mLayoutInflater;
    public View mContentView;
    protected Context mContext;

    /**
     * TODO 1、当点击一个新的Tab时，首先会调用setUserVisibleHint方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    /**
     * * TODO 2、在setUserVisibleHint方法之后调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        mContext = getContext();
        mContentView = inflater.inflate(getLayoutId(), container, false);//得到根视图
        initView();
        isInitView = true;

        lazyLoad();
        //TODO view初始化完成设置为true
//        }
        // 缓存的viewiew需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。

        ViewGroup parent = (ViewGroup) mContentView.getParent();
        if (parent != null) {
            //否则会报已经有了父亲
            parent.removeView(mContentView);
        }

        return mContentView;
    }

    //懒加载数据（不会缓存相邻的页面）
    public void lazyLoad() {
        //如果不是第一次加载，不是初始化view，不是不可见，则不加载数据
        if (!isFirstLoad || !isInitView || !isVisible) {
            return;
        } else {
            //否则，加载数据
            initData();
            //已经不是第一次加载了
            isFirstLoad = false;
        }
    }

    /**
     * 给Activity传递数据
     */
    protected void enterActivity(Bundle bundle, Class<?> targetActivity) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }


    /**
     * 设置根部局
     *
     * @return 根部局id
     */
    protected abstract int getLayoutId();

    /**
     * 设置根部局
     *
     * @return 根部局id
     */
    protected abstract void initView();

    /**
     * 初始化
     */
    protected abstract void initData();

}
