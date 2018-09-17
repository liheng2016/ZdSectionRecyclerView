package utils;

import java.util.List;

import view.niudong.com.demo.MyApplication;

/**
 * Created by qiang on 17-11-8.
 */

public class RecentAccount {

    private static RecentAccount instance;
    private final String KEY = "recent_account";

    private RecentAccount() {
    }

    public static RecentAccount getInstance() {
        if (instance == null) {
            instance = new RecentAccount();
        }
        return instance;
    }

    public void add(String account) {
        LimitedMap map = getLimitMap();
        map.add(account);
        SharedPreferencesUtil.saveData(MyApplication.mContext, KEY, map.convertToJson().toString());

    }

    public List<String> getRecentList() {
        return getLimitMap().getReverseList();
    }

    private LimitedMap getLimitMap() {
        String json = (String) SharedPreferencesUtil.getData(MyApplication.mContext, KEY, "");
        LimitedMap map = new LimitedMap(json);
        return map;
    }


}
