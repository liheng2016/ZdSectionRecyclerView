package utils;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import entity.Boy;
import entity.PlannBillListBean;
import entity.UserInfo;
import testinterface.TestData;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.TestLeakActivity;

/**
 * 名称：
 * Created by niudong on 2017/11/12.
 * Tel：18811793194
 * VersionChange：金贝塔
 */
public class TestDataUtils {
    public static TestDataUtils sInstance;
    public static TestLeakActivity.userInfo mInfo;

    //数据源
    List<UserInfo> dataList;

    public static TestDataUtils getsInstance() {
        if (null == sInstance) {
            synchronized (TestDataUtils.class) {
                if (null == sInstance) {
                    sInstance = new TestDataUtils();
                }
            }
        }
        return sInstance;
    }


    public List<String> getStockCode() {
        List<String> stockCodes = new ArrayList<>();
        stockCodes.add("nd");
        stockCodes.add("ld");
        stockCodes.add("54");
        stockCodes.add("43");
        return stockCodes;
    }


    public List<String> getStockStr() {
        List<String> stockCodes = new ArrayList<>();
        stockCodes.add("wh");
        stockCodes.add("nn");
        stockCodes.add("nd");
        stockCodes.add("ld");
        return stockCodes;
    }


    public void requestData(OnUpdateListener onUpdateListener) {
        TestData testData = new TestData(onUpdateListener);
        onUpdateListener.onSuccess(testData.respData());
    }

    public interface OnUpdateListener {
        void onSuccess(List<UserInfo> stocks);

        void onError(String error);
    }


    private void testSort() {
        List boys = new ArrayList();
        Boy b = new Boy();
        b.name = "tom";
        b.score = 89;
        boys.add(b);

        b = new Boy();
        b.name = "jim";
        b.score = 92;
        boys.add(b);

        b = new Boy();
        b.name = "jack";
        b.score = 77;
        boys.add(b);

        for (int i = 0; i < boys.size(); i++) {
            b = (Boy) boys.get(i);
            System.out.println(b.name + " : " + b.score);
        }

        Collections.sort(boys, new Comparator() {
            @Override
            public int compare(Object o, Object t1) {
                Boy p1 = (Boy) o;
                Boy p2 = (Boy) t1;
                if (p1.score < p2.score)
                    return 1;
                else if (p1.score > p2.score)
                    return -1;
                else
                    return 0;
            }
        });

        for (int i = 0; i < boys.size(); i++) {
            b = (Boy) boys.get(i);
            System.out.println(b.name + " : " + b.score);
        }
    }


//    private void testString1Int() {
//        List<String> list1 = new ArrayList();
//        list1.add("3");
//        list1.add("6");
//        list1.add("9");
//        list1.add("1");
//        for (String num : list1) {
//            if (result > 0)
//                continue;
//            result += Integer.parseInt(num);
//            Log.d("result", "testString1Int: " + result);
//        }
//
//    }

    private void testString2Int() {
//        list = new ArrayList();
//        list.add("3");
//        list.add("6");
//        list.add("9");
//        list.add("1");

/*        for (String num : list) {

            if (result > 0)
                continue;
            result += Integer.parseInt(num);
        }*/
/*        Log.d("int", "testString2Int: " + result);

        if (result > 0) {
            isHasArrear = true;
        }
        testBoolean();


        String s = String.valueOf(result);
        Log.d("String", "testString2Int: " + s);*/
    }

//    private void testBoolean() {
//
//        if (isHasArrear) {
//            Log.d("ok", "testBoolean: ");
//        }
//
//
//        if (!isHasArrear) {//TODO  最新一个月 缴清欠款 那么存入数据库
//            // 保存到数据库
//            Log.d("", "testBoolean: ");
//        } else {
//
//            Log.d("我走了", "testBoolean: ");
//        }
//
//    }


    private void testIsEmpty(List<String> listTestErro) {
        if (listTestErro == null || listTestErro.isEmpty()) {
            Log.d("我走了", "testN");
            return;
        }
        Log.d("我shi了", "testN");

    }


//    private void testDate() {
//        String currentDateString = getCurrentDateString("yyyy-MM-dd HH:mm:ss");
//
//        Log.d("currentDateString", "testDate: " + currentDateString);
//    }

    private void testJson() throws JSONException {
        List<Boy> boys = new ArrayList();
        //xctId为用户手机号
        boys.add(new Boy("hello", 256));
        boys.add(new Boy("nihao", 454));
        boys.add(new Boy("view_hkt_border_shadow", 3434));

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(new Gson().toJson(boys));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("incomeList", jsonArray);

        JSONObject object = new JSONObject(new Gson().toJson(jsonObject));
        JSONArray incomeList = object.getJSONArray("incomeList");
        for (int i = 0; i < incomeList.length(); i++) {
            JSONObject jsonObject1 = incomeList.getJSONObject(i);
            String hello = jsonObject.optString("hello");
            String nihao = jsonObject.optString("nihao");
            String test = jsonObject.optString("view_hkt_border_shadow");

        }
    }


    private void testJsonData() {
        try {
            JSONObject oceate = new JSONObject();
            for (int i = 0; i < 8; i++) {
                oceate.put("totalIncome", "373.5" + i);
                oceate.put("withdrawBalance", "32" + i);
                oceate.put("arrear", "2322" + i);
                oceate.put("billTime", "2017/7.27");
                JSONArray jsonArray = new JSONArray();
                for (int j = 1; j < 2; j++) {
                    JSONObject inner = new JSONObject();
                    inner.put("billDate", (j + "月/2017"));
                    inner.put("totalIncome", "" + (323.4 + j));
                    inner.put("contractNum", "" + (29 + j));
                    inner.put("endNum", "" + (6 + j));
                    inner.put("billType", "1");
                    jsonArray.put(inner);
                }
                oceate.put("billList", jsonArray);
            }
            String s = oceate.toString();
            Log.d("Json", "testJsonData: " + s);
            //paseJsonGson(s);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private void paseJsonGson(String s) {
        PlannBillListBean plannBillListBean = GsonUtils.jsonToBean(s, PlannBillListBean.class);
//        dataList.add(plannBillListBean);
        Log.d("listBeen", "paseJsonGson: " + dataList.toString());
    }

    /**
     * 获得当前日期格式的数据
     *
     * @param format 指定日期显示格式
     * @return
     */
    public static String getCurrentDateString(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        // 当前日期
        Calendar c = Calendar.getInstance();
        Date backDay = c.getTime();
        return dateFormat.format(backDay);
    }


    private void testDouble() {
//        String s = str2doubleDiv("2000", 2);

//        Log.d("总计", "testDouble: " + s);
    }

//    private void paseJson(String json) {
//        InputStream in = null;
//        DataInputStream inSteam = new DataInputStream(in);
//        Log.d("", inSteam + "");
//        //TODO  Json 字符串
//        JSONObject objs;
//        try {
//            objs = new JSONObject(json);
//            //返回成功
//            String totalIncome = objs.optString("totalIncome");
//            String withdrawBalance = objs.optString("withdrawBalance");
//            String arrear = objs.optString("arrear");
//            String billTime = objs.optString("billTime");
//            // 添加数据
//            PlannBillListBean plannBillListBean = new PlannBillListBean(totalIncome, withdrawBalance, arrear, billTime);
//            JSONArray plannerBillList = null;
//            plannerBillList = objs.getJSONArray("billList");
//
//            List<PlannBillListBean.BillListBean> billList = new ArrayList<>();
//            if (null != plannerBillList) {
//                for (int i = 0; i < plannerBillList.length(); i++) {
//                    JSONObject plannerJson = plannerBillList.getJSONObject(i);
//                    if (null == plannerJson) continue;
//                    String billDate = plannerJson.optString("billDate");
//                    String totalIncome1 = plannerJson.optString("totalIncome");
//                    String contractNum = plannerJson.optString("contractNum");
//                    String endNum = plannerJson.optString("endNum");
//                    String billType = plannerJson.optString("billType");
//                    PlannBillListBean.BillListBean billListBean = new PlannBillListBean.BillListBean(billDate, totalIncome1, contractNum, endNum, billType);
//                    billList.add(billListBean);
//                    plannBillListBean.setBillList(billList);
//                }
//            }
//
//            dataList.add(plannBillListBean);
//            Log.d("listBeen", "paseJsonGson: " + dataList.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }


    private void data() {
        List<String> result = new ArrayList<>();
        ToastUtils.showToast(MyApplication.mContext, "牛栋");
        List<String> stockCode = TestDataUtils.getsInstance().getStockCode();
        List<String> mStr = TestDataUtils.getsInstance().getStockStr();
        for (int i = 0; i < stockCode.size(); i++) {
            String s = stockCode.get(i);
            for (int j = 0; j < mStr.size(); j++) {
                if (s.equals(mStr.get(j))) {
                    result.add(mStr.get(j));
                }
            }
        }
    }

    public static void setData(TestLeakActivity.userInfo info) {
         mInfo = info;
    }

    /**
     * 筛选股票代码，用于请求自选资讯使用
     */
    private String processSendZiXunOk() {
        List<Map<String, String>> allStock = getTotalStock();

        StringBuilder builder = new StringBuilder();
        int stockSize = allStock.size();
        //本地默认的4支股票代码
        List<Map<String, String>> localStockCode = getLocalStockCode();
        if (null != allStock && stockSize >= 4) {
            for (int i = 0; i < allStock.size(); i++) {
                Map<String, String> map = allStock.get(i);
                String stockCode = map.get("stockCode");
                builder.append(stockCode + ",");
            }
        } else if (null == allStock || stockSize == 0) {//补默认4支代码的 自选逻辑
            for (int i = 0; i < localStockCode.size(); i++) {
                Map<String, String> valueMap = localStockCode.get(i);
                builder.append(valueMap.get("stockCode") + ",");
            }
        }
        //截取掉最后一个 字符
        String result = builder.toString();
        return result.substring(0, result.length() - 1);
    }

    private List<Map<String, String>> getLocalStockCode() {
        List<Map<String, String>> mResult = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("stockCode", "000088");
        map.put("stockType", "33");
        map.put("stockName", "神州高铁");
        mResult.add(map);
//大族激光
        map = new HashMap<>();
        map.put("stockCode", "002008");
        map.put("stockType", "40");
        map.put("stockName", "大族激光");
        mResult.add(map);
//亿帆医药
        map = new HashMap<>();
        map.put("stockCode", "002019");
        map.put("stockType", "40");
        map.put("stockName", "亿帆医药");
        mResult.add(map);
//联创电子
        map = new HashMap<>();
        map.put("stockCode", "002036");
        map.put("stockType", "40");
        map.put("stockName", "联创电子");
        mResult.add(map);
//美年健康
        map = new HashMap<>();
        map.put("stockCode", "002044");
        map.put("stockType", "40");
        map.put("stockName", "美年健康");
        mResult.add(map);

//        贵州茅台
        map = new HashMap<>();
        map.put("stockCode", "600519");
        map.put("stockType", "17");
        map.put("stockName", "贵州茅台");
        mResult.add(map);
//        海螺水泥
        map = new HashMap<>();
        map.put("stockCode", "600585");
        map.put("stockType", "17");
        map.put("stockName", "海螺水泥");
        mResult.add(map);
//        片仔癀
        map = new HashMap<>();
        map.put("stockCode", "600436");
        map.put("stockType", "17");
        map.put("stockName", "片仔癀");
        mResult.add(map);
//        北方导航
        map = new HashMap<>();
        map.put("stockCode", "600435");
        map.put("stockType", "17");
        map.put("stockName", "北方导航");
        mResult.add(map);
//        方大特钢
        map = new HashMap<>();
        map.put("stockCode", "600507");
        map.put("stockType", "17");
        map.put("stockName", "方大特钢");
        mResult.add(map);
        return mResult;
    }

    public List<Map<String, String>> getTotalStock() {

        List<Map<String, String>> mResult = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("stockCode", "000008");
        map.put("stockType", "33");
        map.put("stockName", "神州高铁");
        mResult.add(map);
//大族激光
        map = new HashMap<>();
        map.put("stockCode", "002008");
        map.put("stockType", "40");
        map.put("stockName", "大族激光");
        mResult.add(map);
//亿帆医药
        map = new HashMap<>();
        map.put("stockCode", "002019");
        map.put("stockType", "40");
        map.put("stockName", "亿帆医药");
//        mResult.add(map);
//联创电子
        map = new HashMap<>();
        map.put("stockCode", "002036");
        map.put("stockType", "40");
        map.put("stockName", "联创电子");
//        mResult.add(map);

        return mResult;
    }



}
