package fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import base.TestBaseFragment;
import callback.DataCallBack;
import entity.ShopData;
import entity.ShopData.ChildData;
import utils.ToastUtils;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/10/20
 * Tel：18811793194
 * VersionChange：mthq1.0
 */
public class DataJsonFragment extends TestBaseFragment implements DataCallBack {

    private TextView textView;
    private List<ShopData> mTotalData;

    @Override
    protected int getLayoutResId() {
        return R.layout.data_json;
    }

    @Override
    protected String getFragmentTitle() {
        return "解析Json";
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        textView = view.findViewById(R.id.tv_json);
    }

    @Override
    protected void initData() {
        super.initData();
        String result = getStrFromAssets(getContext(), "data.json");
        getJson2Mode(result, this);
    }

    @Override
    public void backSuccess() {
        MyApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(getActivity(), "Success");
                Log.d(DataJsonFragment.TAG, "success");
                if (mTotalData != null && mTotalData.size() > 0) {
                    ShopData shopData = mTotalData.get(0);
                    String childTitle = "返回：" + shopData.childTitle;
                    textView.setText(childTitle);
                }
            }
        }, 1000);
    }

    @Override
    public void backErro() {
        ToastUtils.showToast(getActivity(), "Fial");
        String childTitle = "返回：Fial";
        textView.setText(childTitle);

    }

    private void getJson2Mode(String result, DataJsonFragment dataJsonFragment) {
        //      TODO  final 列表
        try {
            mTotalData = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject data = jsonObject.optJSONObject("data");
            JSONObject firstObj = data.optJSONObject("cart_sort_goods_money");
            //TODO 示例
            JSONObject twoObj = data.optJSONObject("cart_sort_goods");
            //171650
            JSONObject twoChild1 = twoObj.optJSONObject("171650");
            JSONArray shopTwoList = twoChild1.optJSONArray("shop_value");
            String shopTitle = twoChild1.optString("shop_title");
            ShopData shopData = new ShopData();
            shopData.childTitle = shopTitle;
            //创建一个列表
            // 遍历数组
            for (int i = 0; i < shopTwoList.length(); i++) {
                ChildData childData = new ChildData();
                JSONObject showChild = shopTwoList.optJSONObject(i);
                String resultSort = showChild.optString("sort");
                childData.sort = resultSort;
                shopData.childDataList.add(childData);
            }
//            shopData.childDataList=childDataList;
            mTotalData.add(shopData);
            ToastUtils.showToast(getActivity(), "" + mTotalData.size());
            //...........
            JSONObject twoChild2 = twoObj.optJSONObject("173643");
        } catch (JSONException e) {
            e.printStackTrace();
            dataJsonFragment.backErro();
        }
        dataJsonFragment.backSuccess();
    }

    /**
     * @return Json数据（String）
     * @description 通过assets文件获取json数据，这里写的十分简单，没做循环判断。
     */
    public static String getStrFromAssets(Context context, String name) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
