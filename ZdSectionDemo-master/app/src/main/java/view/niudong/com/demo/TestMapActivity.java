package view.niudong.com.demo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import utils.Logger;
import utils.PopDialogUtils;
import utils.RecentAccount;
import utils.ToastUtils;

import static view.niudong.com.demo.MyApplication.mContext;

//http://blog.csdn.net/Jersey_me/article/details/70265987
public class TestMapActivity extends AppCompatActivity {
    private TreeMap<String, String> treeMap = new TreeMap<>();
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map);
        linearLayout = (LinearLayout) findViewById(R.id.ll_show);
        final ImageView mImageView = (ImageView) findViewById(R.id.iv);
        for (int i = 0; i < 5; i++) {
            treeMap.put(i + "", "" + (i > 2 ? 2 : i));
        }

        List<String> valueList = getReverseList();
        ToastUtils.showToast(this, valueList.toString());

        String content = "2";
        String inputStr = "12345";
        test();
        int index = inputStr.indexOf(content);
        String str = content.concat(inputStr);//拼接
        Logger.d(index + "");


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    private void putData(String putUserName) {
        RecentAccount.getInstance().add(putUserName);
    }

    public List<String> getData() {
        List<String> list = RecentAccount.getInstance().getRecentList();
        return list;
    }

    private void test() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("12345" + (i > 2 ? 2 : i));
        }
        Set<String> set = new HashSet<String>(list);

        List<String> dataList = new ArrayList<>(set);
        if (dataList.size() < 4) return;
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(dataList.toString());

        Logger.d("info", stringBuffer.toString());
    }

    public List<String> getValueList() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            //对key进行排序
            Set<String> keySet = treeMap.keySet();
            ArrayList<String> keyList = new ArrayList<String>(keySet);
            Collections.sort(keyList);

            //按正序遍历出值
            for (String key : keyList) {
                list.add(treeMap.get(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<String> getReverseList() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            //对key进行排序
            Set<String> keySet = treeMap.keySet();
            ArrayList<String> keyList = new ArrayList<String>(keySet);
            Collections.sort(keyList);
            //按倒序遍历出值
            for (int i = keyList.size() - 1; i >= 0; i--) {

                if (!list.contains(treeMap.get(keyList.get(i)))) {
                    list.add(treeMap.get(keyList.get(i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
