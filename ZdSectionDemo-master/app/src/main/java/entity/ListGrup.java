package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：
 * Created by niudong on 2018/3/22.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class ListGrup {
    public String titleData;
    public int head;
    public List<HkHistoryRenGouData> mChildList = new ArrayList<>();
    public List<String> mChildStr;

    public void setmChildStr(List<String> mChildStr) {
        this.mChildStr = mChildStr;
    }

    public void setmChildList(List<HkHistoryRenGouData> mChildList) {
        this.mChildList = mChildList;
    }
}
