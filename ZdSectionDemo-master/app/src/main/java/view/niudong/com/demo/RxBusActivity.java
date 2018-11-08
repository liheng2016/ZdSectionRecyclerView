package view.niudong.com.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycleview.activity.ItemDecorationActivity;
import utils.Bus;
import utils.Logger;

/**
 * http://blog.csdn.net/u010687392/article/details/47809295
 */
public class RxBusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RxBusActivity.class.getSimpleName();
    private TextView textView;

    public List<Map<String, String>> mHodingParam;
    public SparseArray<Map<String, Object>> mDataArray = new SparseArray<Map<String, Object>>();
    private List<String> stockCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);

        textView = (TextView) findViewById(R.id.tv_send_msg);
        textView.setOnClickListener(this);
        mHodingParam = new ArrayList<>();

        stockCodes = new ArrayList<>();
        stockCodes.add("nd");
        stockCodes.add("ld");
        forTestM();

        testHa();
        Map<String, String> map1 = new HashMap<>();
        map1.put("Profit", "1");
        map1.put("ProfitRate", "1.1");
        map1.put("LastPrice", "666");
        mHodingParam.add(map1);

        map1 = new HashMap<>();
        map1.put("Profit", "2");
        map1.put("ProfitRate", "1.2");
        map1.put("LastPrice", "888");
        mHodingParam.add(map1);

        map1 = new HashMap<>();
        map1.put("Profit", "3");
        map1.put("ProfitRate", "1.3");
        map1.put("LastPrice", "999");
        mHodingParam.add(map1);

        prepeHodingData();

        packageStandardDataType();
    }

    private void forTestM() {
        int j = 3;
        for (int i = 0; i < 5; i++) {
            if (i == j) {
                break;
            }
            Logger.d(TAG, "i = " + i);
        }
        Logger.d(TAG, "循环结束");
    }

    private void testHa() {
        List<PositionDetailModel> datas = new ArrayList<>();
        for (int i = 0; i < stockCodes.size(); i++) {
            PositionDetailModel model = new PositionDetailModel();
            model.titleData = stockCodes.get(i);
            datas.add(model);
            Logger.d(TAG, "执行" + i);

        }
        Logger.d(TAG, "循环结束"+datas);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_msg:
                //发消息
                Bus.getInstance().post(ItemDecorationActivity.RECEVICE_DATA, "你好啊！，我的朋友");
                break;
            default:
                break;
        }
    }

    /**
     * 持仓收益数据,先显示--
     */
    private void prepeHodingData() {
        /**
         *  类型为持仓盈亏下的数据对应的的item的字段值
         */
        //数据转换
        List<Map<String, String>> mHoldParam = new ArrayList<Map<String, String>>();
        Map<String, Object> object = new HashMap<String, Object>();
        //有持仓
        if (mHodingParam != null && mHodingParam.size() > 0) {
            int size = mHodingParam.size();
            for (int i = 0; i < size; i++) {
                Map<String, String> newData = new HashMap<String, String>();
                Map<String, String> oldData = mHodingParam.get(i);
                newData.put("LastPrice", oldData.get("LastPrice"));//现价
                newData.put("todayProfit", "--");//持仓盈亏
                newData.put("todayProfRate", "--");//盈亏比
                mHoldParam.add(newData);
            }
            //放入对应的数据类型中
            object.put("myPositonArray", mHoldParam);
            object.put("profit", "--");
            object.put("profitRate", "--");
            mDataArray.put(2, object);
        }
    }

    /**
     * 计算今日盈亏，统一成和其他持仓盈亏等一样的格式
     */
    private void packageStandardDataType() {

        Map<String, Object> todyStandardMap = mDataArray.get(2);
        //头部的三个值
        //总的今日盈亏
        List<Map<String, String>> myPositinDatas = (List<Map<String, String>>) todyStandardMap.get("myPositonArray");
        //查看共用多少持仓
        for (int i = 0; i < myPositinDatas.size(); i++) {
            //行情取回的带昨收的集合
            Map<String, String> nHodingParamMap = myPositinDatas.get(i);

            //每一只计算完成后，放入最后的集合中
            //今日盈亏
//            nHodingParamMap.remove("todayProfit");
            nHodingParamMap.put("todayProfit", "22");
            nHodingParamMap.remove("todayProfRate");
            nHodingParamMap.put("todayProfRate", "2222");
        }
        //总的今日盈亏比
        todyStandardMap.remove("profitRate");
        todyStandardMap.put("profitRate", "8888");
        //总的今日盈亏
        todyStandardMap.remove("profit");
        todyStandardMap.put("profit", "9999");
    }

    private class PositionDetailModel {
        public String titleData;
    }
}
