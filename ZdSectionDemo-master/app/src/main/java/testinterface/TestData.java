package testinterface;

import java.util.ArrayList;
import java.util.List;

import entity.UserInfo;
import utils.TestDataUtils;

/**
 * 名称：
 * Created by niudong on 2017/11/12.
 * Tel：18811793194
 * VersionChange：金贝塔
 */
public class TestData {
    public TestDataUtils.OnUpdateListener getListener() {
        return listener;
    }

    public static List<UserInfo> stocks = new ArrayList<>();
    private TestDataUtils.OnUpdateListener listener;

    public TestData(TestDataUtils.OnUpdateListener listener) {
        this.listener = listener;
    }


    public List<UserInfo> respData() {
        UserInfo userInfo1 = new UserInfo();
        userInfo1.age = 25;
        userInfo1.username = "NiuDong";
        UserInfo userInfo2 = new UserInfo();
        userInfo2.age = 120;
        userInfo2.username = "Beijing";
        stocks.add(userInfo1);
        stocks.add(userInfo2);
        return stocks;
    }


}
