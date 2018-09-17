package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niudong on 2017/6/15.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */


public class InvestorBillDetailGroupBean {
    public List<ChidBean> list_child = new ArrayList<ChidBean>();

    public InvestorBillDetailGroupBean() {
    }

    public static class ChidBean {
        public String childTxt;
        public String groupText;

        public ChidBean(String childTxt, String groupText) {
            this.childTxt = childTxt;
            this.groupText = groupText;
        }
    }


}
