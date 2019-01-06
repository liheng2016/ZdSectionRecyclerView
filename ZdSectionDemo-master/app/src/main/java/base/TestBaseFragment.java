package base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import contants.IBackPressHandled;
import contants.OnBackPressedListener;
import view.niudong.com.demo.R;

public abstract class TestBaseFragment extends Fragment implements OnBackPressedListener {
    protected static final String TAG = TestBaseFragment.class.getSimpleName();
    protected Activity mContext;
    protected Bundle mFragmentArgs;
    /**
     * createView返回的view
     */
    protected View mView;
    /**
     * 跳转回调
     */

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mFragmentArgs = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.root_view, container, false);
            // 添加布局
            View child;
            if (getLayoutResId() != 0) {
                inflater.inflate(getLayoutResId(), (ViewGroup) mView, true);
            } else if ((child = getLayoutView()) != null) {
                ((LinearLayout)mView).addView(child, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            } else {
                throw new IllegalStateException("布局不能为空");
            }

            if (!TextUtils.isEmpty(getFragmentTitle())) {
                ViewStub stub = (ViewStub) mView.findViewById(R.id.stub_base_fragment_title);
                stub.inflate();
                initTitle(mView);
            }
            initView(mView);
            initListener();
            initData();
        }
        return mView;
    }
    /** 初始化标题相关 */
    private void initTitle(View view) {
        //获取共用的标题栏
      TextView mTitle = (TextView) view.findViewById(R.id.tv_title);
        mTitle.setText(getFragmentTitle());
    }

    /**
     * 功能: 转跳其它Fragment
     */
    public void gotoFragment(TestBaseFragment fragment, Bundle args) {
        gotoFragment(fragment, args, true);
    }

    /**
     * 功能: 转跳其它Fragment
     * @param addStack 是否添加至返回栈中
     */
    public void gotoFragment(TestBaseFragment fragment, Bundle args, boolean addStack) {
        if (args != null) {
            fragment.setArguments(args);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName());
        if (addStack) {
            transaction.addToBackStack(getClass().getSimpleName());
        }
        transaction.commit();
    }


    /**
     * 处理物理返回键
     *
     * @return 是否消费返回事件, true 消费 不返回, false 不消费 返回
     */
    @Override
    public boolean doBack() {
        return false;
    }

    /**
     * 添加返回键
     */
    protected void addBackPressedListener() {
        if (mContext instanceof IBackPressHandled) {
            ((IBackPressHandled) mContext).addOnBackPressedListener(this);
        }
    }

    /**
     * 删除返回键
     */
    protected void removeBackPressedListener() {
        if (mContext instanceof IBackPressHandled) {
            ((IBackPressHandled) mContext).removeOnBackPressedListener(this);
        }
    }

    /**
     * 初始布局
     */
    protected void initView(View view) {

    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 获取布局View
     */
    protected View getLayoutView() {
        return null;
    }

    /**
     * 获取布局id
     */
    protected abstract int getLayoutResId();

    protected abstract String getFragmentTitle();
    /**
     * 跳转Activity
     *
     * @param targetActivity
     */
    protected void enterActivity(Class<?> targetActivity) {
        startActivity(new Intent(mContext, targetActivity));
    }
}
