package fragment;

import android.view.View;
import android.widget.LinearLayout;


import base.TestBaseFragment;
import utils.HkStockUtil;
import view.niudong.com.demo.R;

public class TabFragment extends TestBaseFragment
{

    private LinearLayout rootWebView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected String getFragmentTitle() {
        return "测试WebView";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        rootWebView = view.findViewById(R.id.root_web);
        HkStockUtil.getInstance().loadWebViewData(rootWebView,2);
    }
}
