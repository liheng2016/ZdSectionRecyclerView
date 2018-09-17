package fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import base.BaseLazyFragment;
import utils.CustomDialog;
import utils.Logger;
import utils.PopDialogUtils;
import utils.ProfitView;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/9/2.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class MimeFragment extends BaseLazyFragment implements View.OnClickListener {
    private static final java.lang.String TAG = MimeFragment.class.getSimpleName();
    private View view;
    private ProfitView mProfitView;
    /**
     * 收益构成的数据
     * {0累计收益，1实际累计收益，2、实际累计收益率  3、买入汇率差金额，4、卖出汇率差金额，5、持仓收益，6、已实现收益}
     */
    private Float[] gainComponent = new Float[]{666.60f, +2000.88f, +6.6f, -500f, -699f, +2350f, 2499f};
    private Float[] gainComponents = new Float[]{666666.60f, -3000000.88f, -6.6f, 500f, -199f, 2350f, +2499f};

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_mine_view, container, false);
        mProfitView = (ProfitView) view.findViewById(R.id.profitView);
        mProfitView.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        Logger.d(TAG, "我是Fragment  initData：  我的");
    }

    @Override
    protected void lazyData() {
        super.lazyData();
        //* {0累计收益，1实际累计收益，买入汇率差金额，3卖出汇率差金额，4实际持仓收益，5已实现收益}
        mProfitView.setHktParam(gainComponents[0], gainComponents[1], gainComponents[2],
                gainComponents[3], gainComponents[4]);
        Logger.d(TAG, "我是  lazyData Fragment：  我的");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profitView:
                final CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.show();
                customDialog.setYesOnclickListener(new CustomDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        customDialog.dismiss();
                        ToastUtils.showToast(getActivity(), "关闭");
                    }
                });
                break;
            default:
                break;
        }
    }
}