package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：
 * Created by niudong on 2018/10/20
 * Tel：18811793194
 * VersionChange：mthq1.0
 */
public class ShopData {
    /**
     * 171650 下的孩子
     */
    public String childTitle;
    public List<ChildData> childDataList = new ArrayList<>();

    public static class ChildData {
        public String sort;

    }

}
