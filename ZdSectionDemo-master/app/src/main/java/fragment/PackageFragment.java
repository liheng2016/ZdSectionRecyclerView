package fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Iterator;
import java.util.List;

import base.TestBaseFragment;
import utils.MyTextWatcher;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/2/27.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class PackageFragment extends TestBaseFragment implements MyTextWatcher.CallBack {
    private static final String TAG = "PackageFragment";
    private EditText editText;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_package;
    }

    @Override
    protected String getFragmentTitle() {
        return "不建议采用";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        editText = (EditText) view.findViewById(R.id.et_input_package);
    }

    @Override
    protected void initData() {
        super.initData();
        getInputData();
        MyTextWatcher myTextWatcher = new MyTextWatcher(this);
        editText.addTextChangedListener(myTextWatcher);
    }

    public String getInputData() {
        return editText.getText().toString().trim();
    }


    private String getSign(Context context, String inputData) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            String packageName = packageinfo.packageName;
            if (packageName.equals(inputData)) {
                Log.d(TAG, packageinfo.signatures[0].toCharsString());
                return packageinfo.signatures[0].toCharsString();
            }
        }
        return null;
    }

    @Override
    public void textInfo(String s, int count) {
        String sign = getSign(mContext, s.trim());
        ToastUtils.showToast(mContext,sign);
    }
}
