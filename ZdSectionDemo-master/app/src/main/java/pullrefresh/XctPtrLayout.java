package pullrefresh;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import in.srain.cube.views.ptr.PtrHandler;
import utils.MyUtil;
import view.niudong.com.demo.R;

/**
 * 下拉刷新 - 具有默认的错误提示布局
 * <p>
 * Created by 牛栋 on 2018/5/24.
 */
public class XctPtrLayout extends XctRefreshLayout {

    private View mEmptyView;
    private View mContentView;
    /**
     * 错误页面 - 提示文字
     */
    private TextView mTxtEmptyPrompt;
    /**
     * 错误页面 - 3个点 加载中
     */
    private ImageView mImgEmptyLoading;
    /**
     * 错误页面 - logo图片
     */
    private ImageView mImgEmptyLogo;
    private PtrHandler mPtrHandler;
    private boolean mIsEnableClickRefresh = false;

    public XctPtrLayout(Context context) {
        super(context);
    }

    public XctPtrLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XctPtrLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (2 == childCount) {
            View contentView = getChildAt(1);

            View emptyView = null;
            if (contentView instanceof ViewGroup) {
                ViewGroup root = (ViewGroup) contentView;
                if (root.getChildCount() == 2) {
                    if (root.getChildAt(1).getId() == R.id.ly_ptr_error_empty) {
                        contentView = root.getChildAt(0);
                        emptyView = root.getChildAt(1);
                    }
                }
            }

            if (null == emptyView) {
                removeViewAt(1);

                Context context = getContext();
                FrameLayout root = new FrameLayout(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                root.setLayoutParams(params);

                LayoutInflater inflater = LayoutInflater.from(context);
                mEmptyView = inflater.inflate(getLayout(), root, false);
                initEmptyView(mEmptyView);
                mEmptyView.setVisibility(View.INVISIBLE);
                mContentView = contentView;

                root.addView(mEmptyView);
                root.addView(contentView);

                addView(root);
            } else {
                mContentView = contentView;
                mEmptyView = emptyView;
                initEmptyView(mEmptyView);
                mEmptyView.setVisibility(View.INVISIBLE);
            }
        }
        super.onFinishInflate();
    }


    protected int getLayout() {
        return R.layout.view_xct_ptr_empty;
    }

    @Override
    public void setPtrHandler(PtrHandler ptrHandler) {
        super.setPtrHandler(ptrHandler);

        mPtrHandler = ptrHandler;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * 是否开启错误页面点击重新刷新
     */
    public void setEmptyClickRefresh(boolean enable) {
        mIsEnableClickRefresh = enable;
    }

    /**
     * 提示信息 - 加载数据中
     */
    public void emptyViewLoading(String text) {
        if (TextUtils.isEmpty(text)) {
            text = "正在为您准备数据...";
        }

        if (null != mEmptyView) {
            mTxtEmptyPrompt.setText(text);
            MyUtil.setVisibility(mImgEmptyLoading, View.VISIBLE);
            MyUtil.setVisibility(mImgEmptyLogo, View.GONE);

            MyUtil.setVisibility(mEmptyView, View.VISIBLE);
            MyUtil.setVisibility(mContentView, View.INVISIBLE);
        }
    }

    /**
     * 提示信息 - 提示信息 (静态Logo + 提示语)
     */
    public void emptyViewPrompt(String text) {
        if (null != mEmptyView) {
            mTxtEmptyPrompt.setText(MyUtil.getDefaultString(text, "系统异常"));
            MyUtil.setVisibility(mImgEmptyLoading, View.GONE);
            MyUtil.setVisibility(mImgEmptyLogo, View.VISIBLE);

            MyUtil.setVisibility(mEmptyView, View.VISIBLE);
            MyUtil.setVisibility(mContentView, View.INVISIBLE);
        }
    }


    /**
     * 提示信息 - 提示信息 (静态Logo + 提示语)
     */
    public void emptyViewPrompt(String text, int resId) {
        if (null != mEmptyView) {
            mTxtEmptyPrompt.setText(MyUtil.getDefaultString(text, "系统异常"));
            MyUtil.setVisibility(mImgEmptyLoading, View.GONE);
            MyUtil.setVisibility(mImgEmptyLogo, View.VISIBLE);
            if (resId >= 0) {
                mImgEmptyLogo.setImageResource(resId);
            }

            MyUtil.setVisibility(mEmptyView, View.VISIBLE);
            MyUtil.setVisibility(mContentView, View.INVISIBLE);
        }
    }

    /**
     * 显示真实的内容页面
     */
    public void showContentView(boolean show) {
        MyUtil.setVisibility(mEmptyView, show ? View.INVISIBLE : View.VISIBLE);
        MyUtil.setVisibility(mContentView, show ? View.VISIBLE : View.INVISIBLE);
    }


    private void initEmptyView(View emptyView) {
        mTxtEmptyPrompt = (TextView) emptyView.findViewById(R.id.tv_xct_ptr_empty_view_prompt);
        mImgEmptyLoading = (ImageView) emptyView.findViewById(R.id.iv_xct_ptr_empty_view_loading);
        mImgEmptyLogo = (ImageView) emptyView.findViewById(R.id.iv_xct_ptr_empty_view_logo);

        //设置空白点击事件
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //非加载中
                if (mIsEnableClickRefresh && null != mPtrHandler && View.GONE == mImgEmptyLoading.getVisibility()) {
                    mPtrHandler.onRefreshBegin(XctPtrLayout.this);
                }
            }
        });
    }
}
